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

		DbQueryStatus status = profileDriverImpl.createUserProfile(null, null, null);
		assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, status.getDbQueryExecResult());

	}

	@Test
	public void testFollowFriend_1() {
		//	QUERY_OK Test

	}

	@Test
	public void testFollowFriend_2() {
		//	QUERY_ERROR_NOT_FOUND Test

	}

	@Test
	public void testFollowFriend_3() {
		//	QUERY_ERROR_GENERIC Test

	}

	@Test
	public void testUnfollowFriend_1() {
		//	QUERY_OK Test

	}

	@Test
	public void testUnfollowFriend_2() {
		//	QUERY_ERROR_NOT_FOUND Test


	}

	@Test
	public void testUnfollowFriend_3() {
		//	QUERY_ERROR_GENERIC Test

	}

	@Test
	public void testGetAllSongFriendsLike_1() {
		//	QUERY_OK Test

	}

	@Test
	public void testGetAllSongFriendsLike_2() {
		//	QUERY_ERROR_NOT_FOUND Test

	}

	@Test
	public void testGetAllSongFriendsLike_3() {
		//	QUERY_ERROR_GENERIC Test

	}


	//	Playlist JUnit Test Cases
	@Test
	public void testLikeSong_1() {
		//	QUERY_OK Test
	}

	@Test
	public void testLikeSong_2() {
		//	QUERY_ERROR_NOT_FOUND Test
	}

	@Test
	public void testLikeSong_3() {
		//	QUERY_ERROR_GENERIC Test
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
