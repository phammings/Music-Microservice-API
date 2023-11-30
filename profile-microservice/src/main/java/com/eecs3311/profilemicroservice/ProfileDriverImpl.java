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

@Repository
public class ProfileDriverImpl implements ProfileDriver {

	Driver driver = ProfileMicroserviceApplication.driver;

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
	
	@Override
	public DbQueryStatus createUserProfile(String userName, String fullName, String password) {
		if(userName == null || fullName == null || password == null) return new DbQueryStatus("POST", DbQueryExecResult.QUERY_ERROR_GENERIC);
		try{
			(Session new_session = driver.session()){
				Map<String, Object,> new_HashMap = new HashMap<>();
				new_HashMap.put("userName", userName);
				new_HashMap.put("fullName", fullName);
				new_HashMap.put("password", password);
				new_HashMap.put("playlistName" + userName + "-favourites");
				new_session.writeTransaction((Transaction new_transaction) -> new_transaction.run){
					statementTemplate:"Create (m:profile {userName: $userName, fullName: $fullName, password: $password})-[r:created]->(n:playlist {playlistName: playlistName})", new_HashMap));
				new_session.close();
				return new DbQueryStatus("POST", DbQueryExecResult.QUERY_OK);
				}
			}
		} catch(Exception e){
			System.out.println(e);
			return new DbQueryStatus("POST", DbQueryExecResult.QUERY_ERROR_GENERIC);
		}

	}

	@Override
	public DbQueryStatus followFriend(String userName, String frndUserName) {
		
		return null;
	}

	@Override
	public DbQueryStatus unfollowFriend(String userName, String frndUserName) {
		
		return null;
	}

	@Override
	public DbQueryStatus getAllSongFriendsLike(String userName) {
			
		return null;
	}
}
