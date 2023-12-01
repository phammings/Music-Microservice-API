package com.eecs3311.songmicroservice;

import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SongMicroserviceApplicationTests {

	@Test
	public void testAddSong_1() {
		MongoClient mongoClient = new MongoClient();
		MongoTemplate db = new MongoTemplate(mongoClient, "test");
		SongDalImpl test = new SongDalImpl(db);

		Song song = new Song("No More Parties in LA","Kanye West", "The Life of Pablo", "January 18th, 2016");
		ObjectId objectId = new ObjectId();
		song.setId(objectId);

		test.addSong(song);
		DbQueryStatus status = test.findSongById(song.getId());

		assertEquals(status.getdbQueryExecResult(), DbQueryExecResult.QUERY_OK);
	}

	@Test
	public void testAddSong_2() {
		MongoClient mongoClient = new MongoClient();
		MongoTemplate db = new MongoTemplate(mongoClient, "test");
		SongDalImpl test = new SongDalImpl(db);

		Song song = new Song("No More Parties in LA","Kanye West", "The Life of Pablo", "January 18th, 2016");
		ObjectId objectId = new ObjectId();
		ObjectId objectId2 = new ObjectId();
		song.setId(objectId);

		test.addSong(song);
		DbQueryStatus status = test.findSongById(objectId2.toHexString());

		assertEquals(status.getdbQueryExecResult(), DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
	}

}