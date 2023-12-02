package com.eecs3311.profilemicroservice;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.springframework.stereotype.Repository;
import org.neo4j.driver.v1.Transaction;

// Imported libraries
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the {@link PlaylistDriver} interface using Neo4j as the database.
 * This class provides concrete methods to like and unlike songs in a user's playlist.
 *
 * <p>The class interacts with the Neo4j database using its driver and sessions to execute
 * database operations. It encapsulates the logic to modify user's playlist preferences
 * by liking or unliking songs.
 */
@Repository
public class PlaylistDriverImpl implements PlaylistDriver {

	Driver driver = ProfileMicroserviceApplication.driver;

	/**
	 * Initializes the playlist database by setting up necessary constraints.
	 * This method is called to ensure the playlist database is properly configured
	 * before performing any operations.
	 */
	public static void InitPlaylistDb() {
		String queryStr;

		try (Session session = ProfileMicroserviceApplication.driver.session()) {
			try (Transaction trans = session.beginTransaction()) {
				queryStr = "CREATE CONSTRAINT ON (nPlaylist:playlist) ASSERT exists(nPlaylist.plName)";
				trans.run(queryStr);
				trans.success();
			} catch (Exception e) {
				if (e.getMessage().contains("An equivalent constraint already exists")) {
					System.out.println("INFO: Playlist constraint already exist (DB likely already initialized), should be OK to continue");
				} else {
					// something else, yuck, bye
					throw e;
				}
			}
			session.close();
		}
	}

	/**
	 * Likes a song for a user by creating a relationship in the database.
	 * This method adds a 'like' relation between a user's playlist and a song.
	 *
	 * @param userName The username of the user liking the song.
	 * @param songId   The ID of the song to be liked.
	 * @return {@link DbQueryStatus} indicating the result of the operation.
	 */
	@Override
	public DbQueryStatus likeSong(String userName, String songId) {
		StatementResult new_StatementResult;
		String query;
		if (userName == null || songId == null) return new DbQueryStatus(DbQueryExecResult.QUERY_ERROR_GENERIC);
		try (Session new_session = driver.session()) {
			Map<String, Object> new_HashMap = new HashMap<>();
			new_HashMap.put("playlistName", userName + "-favourites");
			new_HashMap.put("songId", songId);

			try (Transaction new_transaction = new_session.beginTransaction()) {
				// if user playlist does not exist
				query = "MATCH (p:playlist {plName: $plName}) RETURN p";
				new_StatementResult = new_transaction.run(query, new_HashMap);
				if (new_StatementResult.hasNext() == false) return new DbQueryStatus(DbQueryExecResult.QUERY_ERROR_GENERIC);

				// if song node does not exists
				query = "MATCH (s:song {songId: $songId}) RETURN s";
				new_StatementResult = new_transaction.run(query, new_HashMap);
				if (new_StatementResult.hasNext() == false) return new DbQueryStatus(DbQueryExecResult.QUERY_ERROR_GENERIC);

				// if relationship between the nodes does not exists
				query = "MATCH r=(p:playlist {plName: $plName})-[:includes]->(s:song {songId: $songId}) RETURN r";
				new_StatementResult = new_transaction.run(query, new_HashMap);
				if (new_StatementResult.hasNext() == true) return new DbQueryStatus(DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
				query = "MATCH (p:playlist {plName: $plName}) \n  MATCH (s:song {songId: $songId}) \n CREATE (p)-[:includes]->(s)";
				new_transaction.run(query, new_HashMap);
				new_transaction.success();
			}
			new_session.close();
			return new DbQueryStatus(DbQueryExecResult.QUERY_OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new DbQueryStatus(DbQueryExecResult.QUERY_ERROR_GENERIC);
		}
		//return null;
	}

	/**
	 * Unlikes a song for a user by removing a relationship in the database.
	 * This method removes a 'like' relation between a user's playlist and a song.
	 *
	 * @param userName The username of the user unliking the song.
	 * @param songId   The ID of the song to be unliked.
	 * @return {@link DbQueryStatus} indicating the result of the operation.
	 */
	@Override
	public DbQueryStatus unlikeSong(String userName, String songId) {
		StatementResult new_StatementResult;
		String query;

		if (userName == null && songId == null) return new DbQueryStatus(DbQueryExecResult.QUERY_ERROR_GENERIC);
		try (Session new_session = driver.session()) {
			Map<String, Object> new_HashMap = new HashMap<>();
			new_HashMap.put("playlistName", userName + "-favourites");
			new_HashMap.put("songId", songId);

			try (Transaction new_transaction = new_session.beginTransaction()) {
				// if user playlist exist
				query = "MATCH (p:playlist {playlistName: $plName}) RETURN p";
				new_StatementResult = new_transaction.run(query, new_HashMap);
				if (new_StatementResult.hasNext() == false) return new DbQueryStatus(DbQueryExecResult.QUERY_ERROR_GENERIC);

				// if song node exist
				query = "MATCH (s:song {songId: $songId}) RETURN s";
				new_StatementResult = new_transaction.run(query, new_HashMap);
				if (new_StatementResult.hasNext() == true) return new DbQueryStatus(DbQueryExecResult.QUERY_ERROR_GENERIC);

				// if relationship between nodes exist
				query = "MATCH (p:playlist {playlistName: $playlistName})-[r:includes]->(s:song {songId: $songId}) RETURN r";
				new_StatementResult = new_transaction.run(query, new_HashMap);
				if (new_StatementResult.hasNext() == false) return new DbQueryStatus(DbQueryExecResult.QUERY_ERROR_GENERIC);
				query = "MATCH (p:playlist {plName: $plName})-[r:includes]->(s:song {songId: $songId}) DELETE r";
				new_transaction.run(query, new_HashMap);
				new_transaction.success();
			}
			new_session.close();
			return new DbQueryStatus(DbQueryExecResult.QUERY_OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new DbQueryStatus(DbQueryExecResult.QUERY_ERROR_GENERIC);
		}
		//return null;
	}
}
