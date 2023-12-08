package com.eecs3311.profilemicroservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import org.springframework.stereotype.Repository;
import org.neo4j.driver.v1.Transaction;

/**
 * Implementation of the {@link ProfileDriver} interface using Neo4j as the database.
 * This class provides concrete methods for profile-related operations including
 * creating user profiles, following and unfollowing friends, and retrieving songs liked
 * by friends.
 *
 * <p>The class interacts with the Neo4j database using its driver and sessions to execute
 * database operations, handling profile-related data persistently.
 */
@Repository
public class ProfileDriverImpl implements ProfileDriver {

	Driver driver = ProfileMicroserviceApplication.driver;

	/**
	 * Initializes the profile database by setting up necessary constraints.
	 * This method is called to ensure the profile database is properly configured
	 * before performing any operations.
	 */
	public static void InitProfileDb() {
		String queryStr;

		try (Session newSession = ProfileMicroserviceApplication.driver.session()) {
			try (Transaction newTrans = newSession.beginTransaction()) {
				queryStr = "CREATE CONSTRAINT ON (nProfile:profile) ASSERT exists(nProfile.userName)";
				newTrans.run(queryStr);

				queryStr = "CREATE CONSTRAINT ON (nProfile:profile) ASSERT exists(nProfile.password)";
				newTrans.run(queryStr);

				queryStr = "CREATE CONSTRAINT ON (nProfile:profile) ASSERT nProfile.userName IS UNIQUE";
				newTrans.run(queryStr);

				newTrans.success();
			} catch (Exception e) {
				if (e.getMessage().contains("An equivalent constraint already exists")) {
					System.out.println("INFO: Profile constraints already exist (DB likely already initialized), should be OK to continue");
				} else {
					// something else, yuck, bye
					throw e;
				}
			}
			newSession.close();
		}
	}

	/**
	 * Creates a new user profile in the database.
	 *
	 * @param userName The username for the new profile.
	 * @param fullName The full name of the user.
	 * @param password The password for the new profile.
	 * @return {@link DbQueryStatus} indicating the result of the profile creation operation.
	 */
	@Override
	public DbQueryStatus createUserProfile(String userName, String fullName, String password) {
		if (userName == null || fullName == null || password == null) {
			return new DbQueryStatus("Error blank fields", DbQueryExecResult.QUERY_ERROR_GENERIC);
		}
		try (Session newSession = driver.session()) {
			try (Transaction newTrans = newSession.beginTransaction()) {
				if (newTrans.run(String.format("MATCH (p:profile {userName: \"%s\"}) RETURN p", userName)).list().isEmpty()) {
					String playlistName = userName + "-favourites";
					newTrans.run(String.format("MERGE (a:profile {userName: \"%s\", fullName: \"%s\", password: \"%s\" })\nMERGE (b:playlist {plName: \"%s\" })\nCREATE (a)-[:created]->(b)\nRETURN a, b", userName, fullName, password, playlistName));
					newTrans.success();
					return new DbQueryStatus("OK", DbQueryExecResult.QUERY_OK);
				}
				newTrans.failure();
				return new DbQueryStatus("Error creating duplicate userName", DbQueryExecResult.QUERY_ERROR_GENERIC);
			}
		}

	}

	/**
	 * Allows a user to follow another user.
	 *
	 * @param userName The username of the user who wants to follow.
	 * @param frndUserName The username of the friend to be followed.
	 * @return {@link DbQueryStatus} indicating the result of the follow operation.
	 */
	@Override
	public DbQueryStatus followFriend(String userName, String frndUserName) {
		if (userName.equals(frndUserName)) {
			return new DbQueryStatus("userName cannot follow userName", DbQueryExecResult.QUERY_ERROR_GENERIC);
		}

		try (Session newSession = driver.session()) {
			try (Transaction newTrans = newSession.beginTransaction()) {
				if (newTrans.run(String.format("MATCH (p:profile {userName: \"%s\"}) RETURN p", userName)).list().isEmpty()) {
					newTrans.failure();
					return new DbQueryStatus("userName not found", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
				}
				if (newTrans.run(String.format("MATCH (p:profile {userName: \"%s\"}) RETURN p", frndUserName)).list().isEmpty()) {
					newTrans.failure();
					return new DbQueryStatus("friendUserName not found", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
				}

				if (newTrans.run(String.format("MATCH (a:profile), (b:profile) WHERE a.userName = \"%s\" AND b.userName = \"%s\" \nMATCH (a)-[f:follows]->(b) \nRETURN f", userName, frndUserName)).list().isEmpty()) {
					newTrans.run(String.format("MATCH (a:profile), (b:profile) WHERE a.userName = \"%s\" AND b.userName = \"%s\" \nCREATE (a)-[:follows]->(b) \nRETURN a,b", userName, frndUserName));
					newTrans.success();
					return new DbQueryStatus("OK", DbQueryExecResult.QUERY_OK);

				}
				newTrans.failure();
				return new DbQueryStatus("Error userName already follows friendUserName", DbQueryExecResult.QUERY_ERROR_GENERIC);

			}
		}
	}

	/**
	 * Allows a user to unfollow another user.
	 *
	 * @param userName The username of the user who wants to unfollow.
	 * @param frndUserName The username of the friend to be unfollowed.
	 * @return {@link DbQueryStatus} indicating the result of the unfollow operation.
	 */
	@Override
	public DbQueryStatus unfollowFriend(String userName, String frndUserName) {
		if (userName.equals(frndUserName)) {
			return new DbQueryStatus("Error userName cannot follow userName", DbQueryExecResult.QUERY_ERROR_GENERIC);
		}
		try (Session newSession = driver.session()) {
			try (Transaction newTrans = newSession.beginTransaction()) {
				if (newTrans.run(String.format("MATCH (a:profile), (b:profile) WHERE a.userName = \"%s\" AND b.userName = \"%s\" \nMATCH (a)-[f:follows]->(b) \n" + "RETURN f", userName, frndUserName)).list().isEmpty()) {
					newTrans.failure();
					return new DbQueryStatus("Error userName not following friendUserName", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
				}
				newTrans.run(String.format("MATCH (a:profile), (b:profile) WHERE a.userName = \"%s\" AND b.userName = \"%s\" \nMATCH (a)-[f:follows]->(b) \nDELETE f", userName, frndUserName));
				newTrans.success();
				return new DbQueryStatus("OK", DbQueryExecResult.QUERY_OK);

			}
		}
	}

	/**
	 * Retrieves all songs liked by a user's friends.
	 *
	 * @param userName The username of the user whose friends' liked songs are to be retrieved.
	 * @return {@link DbQueryStatus} containing the result of the retrieval operation along with the list of songs.
	 */
	@Override
	public DbQueryStatus getAllSongFriendsLike(String userName) {
		if (userName == null) {
			return new DbQueryStatus("userName not found", DbQueryExecResult.QUERY_ERROR_GENERIC);
		}
		try (Session newSession = driver.session()) {
			try (Transaction newTrans = newSession.beginTransaction()) {
				if (newTrans.run(String.format("MATCH (p:profile {userName: \"%s\"}) RETURN p", userName)).list().isEmpty()) {
					newTrans.failure();
					return new DbQueryStatus("userName not found", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
				}
				List<Record> list = newTrans.run(String.format("MATCH (p:profile),(nProfile:profile) WHERE p.userName = \"%s\"\nAND (p)-[:follows]->(nProfile)\nRETURN nProfile", userName)).list();
				List<String> allUsersNamesFollowed = list.stream()
						.map(record -> record.get(0).get("userName").toString())
						.collect(Collectors.toList());

				Map<String, List<String>> totalSongsFriendsLike = new HashMap<String, List<String>>();
				for (String name : allUsersNamesFollowed) {
					name = name.substring(1, name.length()-1);
					List<Record> resultList = newTrans.run(String.format("MATCH (p:profile {userName: \"%s\" }), (pl:playlist {plName: \"%s\" })\nMATCH (pl)-[:includes]-(s:song)\nRETURN s", userName, name+"-favourites")).list();
					if (resultList.isEmpty()) {
						List<String> songs = resultList.stream()
								.map(song -> "")
								.collect(Collectors.toList());
						totalSongsFriendsLike.put(name.replaceAll("\"", ""), songs);
					}
					else {
						List<String> songs = resultList.stream()
								.map(song -> song.get(0).get("songId").toString().replace("\"", ""))
								.collect(Collectors.toList());
						totalSongsFriendsLike.put(name.replaceAll("\"", ""), songs);
					}
				}
				newTrans.success();
				DbQueryStatus status = new DbQueryStatus("OK", DbQueryExecResult.QUERY_OK);
				status.setData(totalSongsFriendsLike);
				return status;
			}
		}
	}
}
