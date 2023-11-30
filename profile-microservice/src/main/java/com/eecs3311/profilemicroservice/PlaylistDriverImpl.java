package com.eecs3311.profilemicroservice;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.springframework.stereotype.Repository;
import org.neo4j.driver.v1.Transaction;

@Repository
public class PlaylistDriverImpl implements PlaylistDriver {

	Driver driver = ProfileMicroserviceApplication.driver;

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

	@Override
	public DbQueryStatus likeSong(String userName, String songId) {
		StatementResult new_StatementResult;
		String query;
		if (userName == null || songId == null) return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_ERROR_GENERIC);
		try (Session new_session = driver.session()) {
			Map<String, Object> new_HashMap = new HashMap<>();
			new_HashMap.put("playlistName", userName + "-favourites");
			new_HashMap.put("songId", songId);

			try (Transaction new_transaction = new_session.beginTransaction()) {
				// if user playlist does not exist
				query = "MATCH (p:playlist {plName: $plName}) RETURN p";
				new_StatementResult = new_transaction.run(query, new_HashMap);
				if (new_StatementResult.hasNext() == false) return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_ERROR_GENERIC);

				// if song node does not exists
				query = "MATCH (s:song {songId: $songId}) RETURN s";
				new_StatementResult = new_transaction.run(query, new_HashMap);
				if (new_StatementResult.hasNext() == false) return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_ERROR_GENERIC);

				// if relationship between the nodes does not exists
				query = "MATCH r=(p:playlist {plName: $plName})-[:includes]->(s:song {songId: $songId}) RETURN r";
				new_StatementResult = new_transaction.run(query, new_HashMap);
				if (new_StatementResult.hasNext() == true) return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
				query = "MATCH (p:playlist {plName: $plName}) \n  MATCH (s:song {songId: $songId}) \n CREATE (p)-[:includes]->(s)";
				new_transaction.run(query, new_HashMap);
				new_transaction.success();
			}
			new_session.close();
			return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_ERROR_GENERIC);
		}
		//return null;
	}

	@Override
	public DbQueryStatus unlikeSong(String userName, String songId) {
		StatementResult new_StatementResult;
		String query;

		if (userName == null && songId == null) return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_ERROR_GENERIC);
		try (Session new_session = driver.session()) {
			Map<String, Object> new_HashMap = new HashMap<>();
			new_HashMap.put("playlistName", userName + "-favourites");
			new_HashMap.put("songId", songId);

			try (Transaction new_transaction = new_session.beginTransaction()) {
				// if user playlist exist
				query = "MATCH (p:playlist {playlistName: $playlistName}) RETURN p";
				new_StatementResult = new_transaction.run(query, new_HashMap);
				if (new_StatementResult.hasNext() == false) return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_ERROR_GENERIC);

				// if song node exist
				query = "MATCH (s:song {songId: $songId}) RETURN s";
				new_StatementResult = new_transaction.run(query, new_HashMap);
				if (new_StatementResult.hasNext() == true) return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_ERROR_GENERIC);

				// if relationship between nodes exist
				query = "MATCH (p:playlist {playlistName: $playlistName})-[r:includes]->(s:song {songId: $songId}) RETURN r";
				new_StatementResult = new_transaction.run(query, new_HashMap);
				if (new_StatementResult.hasNext() == false) return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_ERROR_GENERIC);
				query = "MATCH (p:playlist {playlistName: $playlistName})-[r:includes]->(s:song {songId: $songId}) DELETE r";
				new_transaction.run(query, new_HashMap);
				new_transaction.success();
			}
			new_session.close();
			return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new DbQueryStatus("PUT", DbQueryExecResult.QUERY_ERROR_GENERIC);
		}
		//return null;
	}
}
