package com.eecs3311.profilemicroservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
		ProfileDriverImpl pDriver = new ProfileDriverImpl();
		DbQueryStatus test_status = pDriver.createUserProfile("user", "new user", "password");
		assertEquals(DbQueryExecResult.QUERY_OK, test_status.getdbQueryExecResult());
	}

	@Test
	public void testCreateUserProfile_2() {
		//	QUERY_ERROR_GENERIC Test
		ProfileDriverImpl pDriver = new ProfileDriverImpl();
		DbQueryStatus test_status = pDriver.createUserProfile(null, null, null);
		assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, test_status.getdbQueryExecResult());

	}

	@Test
	public void testFollowFriend_1() {
		//	QUERY_OK Test
		ProfileDriverImpl pDriver = new ProfileDriverImpl();
		DbQueryStatus test_status = pDriver.followFriend("user", "user2");
		assertEquals(DbQueryExecResult.QUERY_OK, test_status.getdbQueryExecResult());
	}

	@Test
	public void testFollowFriend_2() {
		//	QUERY_ERROR_NOT_FOUND Test
		ProfileDriverImpl pDriver = new ProfileDriverImpl();
		DbQueryStatus test_status = pDriver.followFriend("user", "Mr.69BombasticChad");
		assertEquals(DbQueryExecResult.QUERY_ERROR_NOT_FOUND, test_status.getdbQueryExecResult());

	}

	@Test
	public void testFollowFriend_3() {
		//	QUERY_ERROR_GENERIC Test
		ProfileDriverImpl pDriver = new ProfileDriverImpl();
		DbQueryStatus test_status = pDriver.followFriend("user", "user");
		assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, test_status.getdbQueryExecResult());
	}

	@Test
	public void testUnfollowFriend_1() {
		//	QUERY_OK Test
		ProfileDriverImpl pDriver = new ProfileDriverImpl();
		DbQueryStatus test_status = pDriver.unfollowFriend("user", "user2");
		assertEquals(DbQueryExecResult.QUERY_OK, test_status.getdbQueryExecResult());
	}

	@Test
	public void testUnfollowFriend_2() {
		//	QUERY_ERROR_NOT_FOUND Test
		ProfileDriverImpl pDriver = new ProfileDriverImpl();
		DbQueryStatus test_status = pDriver.unfollowFriend("user", "MasterOogway");
		assertEquals(DbQueryExecResult.QUERY_ERROR_NOT_FOUND, test_status.getdbQueryExecResult());
	}

	@Test
	public void testUnfollowFriend_3() {
		//	QUERY_ERROR_GENERIC Test
		ProfileDriverImpl pDriver = new ProfileDriverImpl();
		DbQueryStatus test_status = pDriver.unfollowFriend("user", "user");
		assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, test_status.getdbQueryExecResult());
	}

	@Test
	public void testGetAllSongFriendsLike_1() {
		//	QUERY_OK Test
		ProfileDriverImpl pDriver = new ProfileDriverImpl();
		DbQueryStatus test_status = pDriver.getAllSongFriendsLike("user");
		assertEquals(DbQueryExecResult.QUERY_OK, test_status.getdbQueryExecResult());
		assertNotNull(test_status.getData());
	}

	@Test
	public void testGetAllSongFriendsLike_2() {
		//	QUERY_ERROR_NOT_FOUND Test
		ProfileDriverImpl pDriver = new ProfileDriverImpl();
		DbQueryStatus test_status = pDriver.getAllSongFriendsLike("newUser");
		assertEquals(DbQueryExecResult.QUERY_ERROR_NOT_FOUND, test_status.getdbQueryExecResult());
	}

	@Test
	public void testGetAllSongFriendsLike_3() {
		//	QUERY_ERROR_GENERIC Test
		ProfileDriverImpl pDriver = new ProfileDriverImpl();
		DbQueryStatus test_status = pDriver.getAllSongFriendsLike("KonichiwaMeowMeow");
		assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, test_status.getdbQueryExecResult());
	}


	//	Playlist JUnit Test Cases
	@Test
	public void testLikeSong_1() {
		//	QUERY_OK Test
		PlaylistDriverImpl pDriver = new PlaylistDriverImpl();
		DbQueryStatus test_status = pDriver.likeSong("user", "userID");
		assertEquals(DbQueryExecResult.QUERY_OK, test_status.getdbQueryExecResult());
	}

	@Test
	public void testLikeSong_2() {
		//	QUERY_ERROR_NOT_FOUND Test
		PlaylistDriverImpl pDriver = new PlaylistDriverImpl();
		DbQueryStatus test_status = pDriver.likeSong("user", "jingalalahuhu");
		assertEquals(DbQueryExecResult.QUERY_ERROR_NOT_FOUND, test_status.getdbQueryExecResult());
	}

	@Test
	public void testLikeSong_3() {
		//	QUERY_ERROR_GENERIC Test
		PlaylistDriverImpl pDriver = new PlaylistDriverImpl();
		DbQueryStatus test_status = pDriver.likeSong(null, null);
		assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, test_status.getdbQueryExecResult());
	}

	@Test
	public void testUnlikeSong_1() {
		//	QUERY_OK Test
		PlaylistDriverImpl pDriver = new PlaylistDriverImpl();
		DbQueryStatus test_status = pDriver.unlikeSong("user", "userID");
		assertEquals(DbQueryExecResult.QUERY_OK, test_status.getdbQueryExecResult());
	}

	@Test
	public void testUnlikeSong_2() {
		//	QUERY_ERROR_NOT_FOUND Test
		PlaylistDriverImpl pDriver = new PlaylistDriverImpl();
		DbQueryStatus test_status = pDriver.unlikeSong("user", "yourPhoneLingingEatMyBlingBling");
		assertEquals(DbQueryExecResult.QUERY_ERROR_NOT_FOUND, test_status.getdbQueryExecResult());
	}

	@Test
	public void testUnlikeSong_3() {
		//	QUERY_ERROR_GENERIC Test
		PlaylistDriverImpl pDriver = new PlaylistDriverImpl();
		DbQueryStatus test_status = pDriver.unlikeSong(null, null);
		assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, test_status.getdbQueryExecResult());
	}

}
