package com.eecs3311.profilemicroservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		try (Session session = ProfileMicroserviceApplication.driver.session()) {
			try (Transaction trans = session.beginTransaction()) {
				queryStr = "CREATE CONSTRAINT ON (nProfile:profile) ASSERT exists(nProfile.userName)";
				trans.run(queryStr);

				queryStr = "CREATE CONSTRAINT ON (nProfile:profile) ASSERT exists(nProfile.password)";
				trans.run(queryStr);

				queryStr = "CREATE CONSTRAINT ON (nProfile:profile) ASSERT nProfile.userName IS UNIQUE";
				trans.run(queryStr);

				trans.success();
			} catch (Exception e) {
				if (e.getMessage().contains("An equivalent constraint already exists")) {
					System.out.println("INFO: Profile constraints already exist (DB likely already initialized), should be OK to continue");
				} else {
					// something else, yuck, bye
					throw e;
				}
			}
			session.close();
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
		try (Session session = driver.session()) {
			try (Transaction trans = session.beginTransaction()) {
				if (trans.run(String.format("MATCH (p:profile {userName: \"%s\"}) RETURN p", userName)).list().isEmpty()) {
					String playlistName = userName + "-favourites";
					trans.run(String.format("MERGE (a:profile {userName: \"%s\", fullName: \"%s\", password: \"%s\" })\nMERGE (b:playlist {plName: \"%s\" })\nCREATE (a)-[:created]->(b)\nRETURN a, b", userName, fullName, password, playlistName));
					trans.success();
					return new DbQueryStatus("OK", DbQueryExecResult.QUERY_OK);
				}
				trans.failure();
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
		try (Session session = driver.session()) {
			try (Transaction trans = session.beginTransaction()) {
				if (trans.run(String.format("MATCH (p:profile {userName: \"%s\"}) RETURN p", userName)).list().isEmpty()) {
					return new DbQueryStatus("userName not found", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
				}
				if (trans.run(String.format("MATCH (p:profile {userName: \"%s\"}) RETURN p", frndUserName)).list().isEmpty()) {
					return new DbQueryStatus("friendUserName not found", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
				}

				if (trans.run(String.format("MATCH (a:profile), (b:profile) WHERE a.userName = \"%s\" AND b.userName = \"%s\" \nMATCH (a)-[f:follows]->(b) \nRETURN f", userName, frndUserName)).list().isEmpty()) {
					trans.run(String.format("MATCH (a:profile), (b:profile) WHERE a.userName = \"%s\" AND b.userName = \"%s\" \nCREATE (a)-[:follows]->(b) \nRETURN a,b", userName, frndUserName));
					trans.success();
					return new DbQueryStatus("OK", DbQueryExecResult.QUERY_OK);

				}
				trans.failure();
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
		try (Session session = driver.session()) {
			try (Transaction trans = session.beginTransaction()) {
				if (trans.run(String.format("MATCH (a:profile), (b:profile) WHERE a.userName = \"%s\" AND b.userName = \"%s\" \nMATCH (a)-[f:follows]->(b) \n" + "RETURN f", userName, frndUserName)).list().isEmpty()) {
					trans.failure();
					return new DbQueryStatus("Error userName not following friendUserName", DbQueryExecResult.QUERY_ERROR_GENERIC);
				}
				trans.run(String.format("MATCH (a:profile), (b:profile) WHERE a.userName = \"%s\" AND b.userName = \"%s\" \nMATCH (a)-[f:follows]->(b) \nDELETE f", userName, frndUserName));
				trans.success();
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
		String query;
		StatementResult user_StatementResult;
		StatementResult song_StatementResult;
		DbQueryStatus dbQueryStatus = new DbQueryStatus("Success Getting All Friend's Songs", DbQueryExecResult.QUERY_OK);
		if (userName == null) new DbQueryStatus("Success Getting All Friend's Songs", DbQueryExecResult.QUERY_ERROR_GENERIC);

		try (Session new_session = driver.session()) {
			Map<String, Object> new_HashMap = new HashMap<>();
			new_HashMap.put("userName", userName);

			try (Transaction new_transaction = new_session.beginTransaction()) {
				// list of frnduser the user follows
				// searches up every song the user has liked
				query = "MATCH (p1:profile {userName: $userName})-[:follows]->(p2:profile) RETURN collect(p2.userName) as userName";
				user_StatementResult = new_transaction.run(query, new_HashMap);
				Map<String, Object> song_HashMap = new HashMap<>();
				query = "MATCH (c:playlist {plName: $userName + '-favourites'})-[:includes]->(s:song) RETURN collect(s.songName) as songs";
				if (user_StatementResult.hasNext() == false) return new DbQueryStatus("Error Getting All Friend's Songs", DbQueryExecResult.QUERY_ERROR_GENERIC);
				List<Object> user_followers = user_StatementResult.next().get("userName").asList();
				for (Object f : user_followers) {
					new_HashMap.put("userName", (String) f);
					song_StatementResult = new_transaction.run(query, new_HashMap);
					song_HashMap.put((String) f, (song_StatementResult.hasNext() == false) ? new ArrayList<String>() : song_StatementResult.next().get("songs").asList());
				}
				dbQueryStatus.setData(song_HashMap);
				new_transaction.success();
			}
			new_session.close();
			return dbQueryStatus;

		} catch (Exception e) {
			e.printStackTrace();
			return new DbQueryStatus(e.getMessage(), DbQueryExecResult.QUERY_ERROR_GENERIC);
		}
		//return null;
	}
}
