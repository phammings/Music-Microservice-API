package com.eecs3311.profilemicroservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfileMicroserviceApplicationTests {

	//	Profile JUnit Test Cases
	@Test
	public void testCreateUserProfile_1() {
		//	QUERY_OK Test
		Driver test_driver = GraphDatabase.driver("https://localhost:7687", AuthTokens.basic("neo4j", "12345678"));
		ProfileDriverImpl test = new ProfileDriverImpl(test_driver);

		DbQueryStatus test_status = profileDriverImpl.createUserProfile("user", "new user", "password");
		assertEquals(DbQueryExecResult.QUERY_OK, test_status.getDbQueryExecResult());
	}

	@Test
	public void testCreateUserProfile_2() {
		//	QUERY_ERROR_GENERIC Test
		Driver test_driver = GraphDatabase.driver("https://localhost:7687", AuthTokens.basic("neo4j", "12345678"));
		ProfileDriverImpl test = new ProfileDriverImpl(test_driver);

		DbQueryStatus test_status = profileDriverImpl.createUserProfile(null, null, null);
		assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, test_status.getDbQueryExecResult());

	}

	@Test
	public void testFollowFriend_1() {
		//	QUERY_OK Test
		Driver test_driver = GraphDatabase.driver("https://localhost:7687", AuthTokens.basic("neo4j", "12345678"));
		ProfileDriverImpl test = new ProfileDriverImpl(test_driver);

		DbQueryStatus test_status = profileDriverImpl.followFriend("user", "user2");
		assertEquals(DbQueryExecResult.QUERY_OK, test_status.getDbQueryExecResult());
	}

	@Test
	public void testFollowFriend_2() {
		//	QUERY_ERROR_NOT_FOUND Test
		Driver test_driver = GraphDatabase.driver("https://localhost:7687", AuthTokens.basic("neo4j", "12345678"));
		ProfileDriverImpl test = new ProfileDriverImpl(test_driver);

		DbQueryStatus test_status = profileDriverImpl.followFriend("user", "Mr.69BombasticChad");
		assertEquals(DbQueryExecResult.QUERY_ERROR_NOT_FOUND, test_status.getDbQueryExecResult());

	}

	@Test
	public void testFollowFriend_3() {
		//	QUERY_ERROR_GENERIC Test
		Driver test_driver = GraphDatabase.driver("https://localhost:7687", AuthTokens.basic("neo4j", "12345678"));
		ProfileDriverImpl test = new ProfileDriverImpl(test_driver);

		DbQueryStatus test_status = profileDriverImpl.followFriend("user", "user");
		assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, test_status.getDbQueryExecResult());
	}

	@Test
	public void testUnfollowFriend_1() {
		//	QUERY_OK Test
		Driver test_driver = GraphDatabase.driver("https://localhost:7687", AuthTokens.basic("neo4j", "12345678"));
		ProfileDriverImpl test = new ProfileDriverImpl(test_driver);

		DbQueryStatus test_status = profileDriverImpl.unfollowFriend("user", "user2");
		assertEquals(DbQueryExecResult.QUERY_OK, test_status.getDbQueryExecResult());
	}

	@Test
	public void testUnfollowFriend_2() {
		//	QUERY_ERROR_NOT_FOUND Test
		Driver test_driver = GraphDatabase.driver("https://localhost:7687", AuthTokens.basic("neo4j", "12345678"));
		ProfileDriverImpl test = new ProfileDriverImpl(test_driver);

		DbQueryStatus test_status = profileDriverImpl.unfollowFriend("user", "MasterOogway");
		assertEquals(DbQueryExecResult.QUERY_ERROR_NOT_FOUND, test_status.getDbQueryExecResult());
	}

	@Test
	public void testUnfollowFriend_3() {
		//	QUERY_ERROR_GENERIC Test
		Driver test_driver = GraphDatabase.driver("https://localhost:7687", AuthTokens.basic("neo4j", "12345678"));
		ProfileDriverImpl test = new ProfileDriverImpl(test_driver);

		DbQueryStatus test_status = profileDriverImpl.unfollowFriend("user", "user");
		assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, test_status.getDbQueryExecResult());
	}

	@Test
	public void testGetAllSongFriendsLike_1() {
		//	QUERY_OK Test
		Driver test_driver = GraphDatabase.driver("https://localhost:7687", AuthTokens.basic("neo4j", "12345678"));
		ProfileDriverImpl test = new ProfileDriverImpl(test_driver);

		DbQueryStatus test_status = profileDriverImpl.getAllSongFriendsLike("user");
		assertEquals(DbQueryExecResult.QUERY_OK, test_status.getDbQueryExecResult());
		assertNotNull(test_status.getData());
	}

	@Test
	public void testGetAllSongFriendsLike_2() {
		//	QUERY_ERROR_NOT_FOUND Test
		Driver test_driver = GraphDatabase.driver("https://localhost:7687", AuthTokens.basic("neo4j", "12345678"));
		ProfileDriverImpl test = new ProfileDriverImpl(test_driver);

		DbQueryStatus test_status = profileDriverImpl.getAllSongFriendsLike("newUser");
		assertEquals(DbQueryExecResult.QUERY_ERROR_NOT_FOUND, test_status.getDbQueryExecResult());
	}

	@Test
	public void testGetAllSongFriendsLike_3() {
		//	QUERY_ERROR_GENERIC Test
		Driver test_driver = GraphDatabase.driver("https://localhost:7687", AuthTokens.basic("neo4j", "12345678"));
		ProfileDriverImpl test = new ProfileDriverImpl(test_driver);

		DbQueryStatus test_status = profileDriverImpl.getAllSongFriendsLike("KonichiwaMeowMeow");
		assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, test_status.getDbQueryExecResult());
	}


	//	Playlist JUnit Test Cases
	@Test
	public void testLikeSong_1() {
		//	QUERY_OK Test
		Driver test_driver = GraphDatabase.driver("https://localhost:7687", AuthTokens.basic("neo4j", "12345678"));
		ProfileDriverImpl test = new ProfileDriverImpl(test_driver);

		DbQueryStatus status = playlistDriverImpl.likeSong("user", "userID");
		assertEquals(DbQueryExecResult.QUERY_OK, status.getDbQueryExecResult());
	}

	@Test
	public void testLikeSong_2() {
		//	QUERY_ERROR_NOT_FOUND Test
		Driver test_driver = GraphDatabase.driver("https://localhost:7687", AuthTokens.basic("neo4j", "12345678"));
		ProfileDriverImpl test = new ProfileDriverImpl(test_driver);

		DbQueryStatus status = playlistDriverImpl.likeSong("user", "jingalalahuhu");
		assertEquals(DbQueryExecResult.QUERY_ERROR_NOT_FOUND, status.getDbQueryExecResult());
	}

	@Test
	public void testLikeSong_3() {
		//	QUERY_ERROR_GENERIC Test
		Driver test_driver = GraphDatabase.driver("https://localhost:7687", AuthTokens.basic("neo4j", "12345678"));
		ProfileDriverImpl test = new ProfileDriverImpl(test_driver);

		DbQueryStatus status = playlistDriverImpl.likeSong(null, null);
		assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, status.getDbQueryExecResult());
	}

	@Test
	public void testUnlikeSong_1() {
		//	QUERY_OK Test
	}

	@Test
	public void testUnlikeSong_2() {
		//	QUERY_ERROR_NOT_FOUND Test
	}

	@Test
	public void testUnlikeSong_3() {
		//	QUERY_ERROR_GENERIC Test
	}

}
