package com.eecs3311.profilemicroservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfileMicroserviceApplicationTests {

	//	Profile JUnit Test Cases
	@Test
	public void test1_CreateUserProfile_1() {//	QUERY_OK Test
		ProfileDriverImpl pDriver = new ProfileDriverImpl();
		DbQueryStatus test_status = pDriver.createUserProfile("user", "new user", "password");
		assertEquals(DbQueryExecResult.QUERY_OK, test_status.getdbQueryExecResult());
	}

	@Test
	public void test2_CreateUserProfile_2() {
		//	QUERY_ERROR_GENERIC Test
		ProfileDriverImpl pDriver = new ProfileDriverImpl();
		DbQueryStatus test_status = pDriver.createUserProfile(null, null, null);
		assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, test_status.getdbQueryExecResult());

	}

	@Test
	public void test3_FollowFriend_1() {
		//	QUERY_OK Test
		ProfileDriverImpl pDriver = new ProfileDriverImpl();
		DbQueryStatus test_status = pDriver.createUserProfile("user2", "new user", "password");
		test_status = pDriver.followFriend("user", "user2");
		assertEquals(DbQueryExecResult.QUERY_OK, test_status.getdbQueryExecResult());
	}

	@Test
	public void test4_FollowFriend_2() {
		//	QUERY_ERROR_NOT_FOUND Test
		ProfileDriverImpl pDriver = new ProfileDriverImpl();
		DbQueryStatus test_status = pDriver.followFriend("user", "Mr.69BombasticChad");
		assertEquals(DbQueryExecResult.QUERY_ERROR_NOT_FOUND, test_status.getdbQueryExecResult());

	}

	@Test
	public void test5_FollowFriend_3() {
		//	QUERY_ERROR_GENERIC Test
		ProfileDriverImpl pDriver = new ProfileDriverImpl();
		DbQueryStatus test_status = pDriver.followFriend("user", "user");
		assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, test_status.getdbQueryExecResult());
	}

	@Test
	public void test6_UnfollowFriend_1() {
		//	QUERY_OK Test
		ProfileDriverImpl pDriver = new ProfileDriverImpl();

		DbQueryStatus test_status = pDriver.unfollowFriend("user", "user2");
		assertEquals(DbQueryExecResult.QUERY_OK, test_status.getdbQueryExecResult());
	}

	@Test
	public void test7_UnfollowFriend_2() {
		//	QUERY_ERROR_NOT_FOUND Test
		ProfileDriverImpl pDriver = new ProfileDriverImpl();

		DbQueryStatus test_status = pDriver.unfollowFriend("user", "MasterOogway");
		assertEquals(DbQueryExecResult.QUERY_ERROR_NOT_FOUND, test_status.getdbQueryExecResult());
	}

	@Test
	public void test8_UnfollowFriend_3() {
		//	QUERY_ERROR_GENERIC Test
		ProfileDriverImpl pDriver = new ProfileDriverImpl();
		DbQueryStatus test_status = pDriver.unfollowFriend("user", "user");
		assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, test_status.getdbQueryExecResult());
	}

	@Test
	public void test8_GetAllSongFriendsLike_1() {
		//	QUERY_OK Test
		ProfileDriverImpl pDriver = new ProfileDriverImpl();
		DbQueryStatus test_status = pDriver.getAllSongFriendsLike("user");
		assertEquals(DbQueryExecResult.QUERY_OK, test_status.getdbQueryExecResult());
		assertNotNull(test_status.getData());
	}

	@Test
	public void test9_GetAllSongFriendsLike_2() {
		//	QUERY_ERROR_NOT_FOUND Test
		ProfileDriverImpl pDriver = new ProfileDriverImpl();
		DbQueryStatus test_status = pDriver.getAllSongFriendsLike("newUser");
		assertEquals(DbQueryExecResult.QUERY_ERROR_NOT_FOUND, test_status.getdbQueryExecResult());
	}

	@Test
	public void test10_GetAllSongFriendsLike_3() {
		//	QUERY_ERROR_GENERIC Test
		ProfileDriverImpl pDriver = new ProfileDriverImpl();
		DbQueryStatus test_status = pDriver.getAllSongFriendsLike("KonichiwaMeowMeow");
		assertEquals(DbQueryExecResult.QUERY_ERROR_NOT_FOUND, test_status.getdbQueryExecResult());
	}


	//	Playlist JUnit Test Cases
	@Test
	public void test99_LikeSong_1() {
		//	QUERY_OK Test
		PlaylistDriverImpl pDriver = new PlaylistDriverImpl();
		DbQueryStatus test_status = pDriver.likeSong("user2", "5d620f54d78b833e34e65b49");
		assertEquals(DbQueryExecResult.QUERY_OK, test_status.getdbQueryExecResult());
	}

	@Test
	public void test12_LikeSong_2() {
		//	QUERY_ERROR_NOT_FOUND Test
		PlaylistDriverImpl pDriver = new PlaylistDriverImpl();
		DbQueryStatus test_status = pDriver.likeSong("user", "jingalalahuhu");
		assertEquals(DbQueryExecResult.QUERY_ERROR_NOT_FOUND, test_status.getdbQueryExecResult());
	}

	@Test
	public void test13_LikeSong_3() {
		//	QUERY_ERROR_GENERIC Test
		PlaylistDriverImpl pDriver = new PlaylistDriverImpl();
		DbQueryStatus test_status = pDriver.likeSong(null, null);
		assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, test_status.getdbQueryExecResult());
	}

	@Test
	public void test99_UnlikeSong_1() {
		//	QUERY_OK Test
		PlaylistDriverImpl pDriver = new PlaylistDriverImpl();
		DbQueryStatus test_status = pDriver.unlikeSong("user2", "5d620f54d78b833e34e65b49");
		assertEquals(DbQueryExecResult.QUERY_OK, test_status.getdbQueryExecResult());
	}

	@Test
	public void test15_UnlikeSong_2() {
		//	QUERY_ERROR_NOT_FOUND Test
		PlaylistDriverImpl pDriver = new PlaylistDriverImpl();
		DbQueryStatus test_status = pDriver.unlikeSong("user", "CurryCurry");
		assertEquals(DbQueryExecResult.QUERY_ERROR_NOT_FOUND, test_status.getdbQueryExecResult());
	}

	@Test
	public void test16_UnlikeSong_3() {
		//	QUERY_ERROR_GENERIC Test
		PlaylistDriverImpl pDriver = new PlaylistDriverImpl();
		DbQueryStatus test_status = pDriver.unlikeSong(null, null);
		assertEquals(DbQueryExecResult.QUERY_ERROR_GENERIC, test_status.getdbQueryExecResult());
	}
}
