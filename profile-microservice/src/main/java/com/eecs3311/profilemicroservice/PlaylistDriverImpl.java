package com.eecs3311.profilemicroservice;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.springframework.stereotype.Repository;
import org.neo4j.driver.v1.Transaction;

// Imported libraries
import java.util.HashMap;
import java.util.List;
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

		try (Session newSession = ProfileMicroserviceApplication.driver.session()) {
			try (Transaction newTrans = newSession.beginTransaction()) {
				queryStr = "CREATE CONSTRAINT ON (nPlaylist:playlist) ASSERT exists(nPlaylist.plName)";
				newTrans.run(queryStr);
				newTrans.success();
			} catch (Exception e) {
				if (e.getMessage().contains("An equivalent constraint already exists")) {
					System.out.println("INFO: Playlist constraint already exist (DB likely already initialized), should be OK to continue");
				} else {
					// something else, yuck, bye
					throw e;
				}
			}
			newSession.close();
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
		if (userName == null || songId == null) {
			return new DbQueryStatus("userName not found", DbQueryExecResult.QUERY_ERROR_GENERIC);
		}
		try (Session newSession = driver.session()) {
			try (Transaction newTrans = newSession.beginTransaction()) {
				if (newTrans.run(String.format("MATCH (pl:playlist {plName: \"%s-favourites\" }) RETURN pl", userName)).list().isEmpty()) {
					newTrans.failure();
					return new DbQueryStatus("userName not found", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
				}
				else {
					if(newTrans.run(String.format("MATCH (pl:playlist {plName: \"%s-favourites\" }), (s:song {songId: \"%s\" }) \nMATCH (pl)-[r:includes]-(s) \nRETURN r", userName, songId)).list().isEmpty()) {
						newTrans.run(String.format("MATCH (pl:playlist {plName: \"%s-favourites\" }) \nMERGE (s:song {songId: \"%s\" }) \nMERGE (pl)-[r:includes]->(s)\nRETURN r", userName, songId));
						newTrans.success();
						return new DbQueryStatus("OK", DbQueryExecResult.QUERY_OK);
					}
					//	Song already liked
					newTrans.success();
					return new DbQueryStatus("OK", DbQueryExecResult.QUERY_OK);
				}
			}
		}
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
		if (userName == null || songId == null) {
			return new DbQueryStatus("userName not found", DbQueryExecResult.QUERY_ERROR_GENERIC);
		}
		try (Session newSession = driver.session()) {
			try (Transaction newTrans = newSession.beginTransaction()) {
				if (newTrans.run(String.format("MATCH (pl:playlist {plName: \"%s-favourites\" }) RETURN pl", userName)).list().isEmpty()) {
					newTrans.failure();
					return new DbQueryStatus("userName not found", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
				} else {
					if (newTrans.run(String.format("MATCH (pl:playlist {plName: \"%s-favourites\" }), (pl)-[r:includes]->(:song {songId: \"%s\" })\n" + "RETURN r", userName, songId)).list().isEmpty()) {
						newTrans.failure();
						return new DbQueryStatus("userName has not liked song", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
					}
					newTrans.run(String.format("MATCH (pl:playlist {plName: \"%s-favourites\" }), (pl)-[r:includes]->(:song {songId: \"%s\" })\nDELETE r", userName, songId));
					newTrans.success();
					return new DbQueryStatus("OK", DbQueryExecResult.QUERY_OK);
				}
			}
		}
	}

	/**
	 * Deletes a song in the database.
	 *
	 * @param songId   The ID of the song to be deleted.
	 * @return {@link DbQueryStatus} indicating the result of the operation.
	 */
	public DbQueryStatus deleteSongById(String songId) {
		if (songId == null) {
			return new DbQueryStatus("songId null", DbQueryExecResult.QUERY_ERROR_GENERIC);
		}
		try (Session newSession = driver.session()) {
			try (Transaction newTrans = newSession.beginTransaction()) {
				if (newTrans.run(String.format("MATCH (s:song {songId: \"%s\"}) RETURN s", songId)).list().isEmpty()) {
					newTrans.failure();
					return new DbQueryStatus("songId not found", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
				} else {
					newTrans.run(String.format("MATCH (s:song {songId: \"%s\"}) DETACH DELETE s", songId));
					newTrans.success();
					return new DbQueryStatus("OK", DbQueryExecResult.QUERY_OK);
				}
			}
		}
	}
}
