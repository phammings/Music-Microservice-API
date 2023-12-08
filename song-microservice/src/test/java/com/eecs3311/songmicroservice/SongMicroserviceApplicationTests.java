package com.eecs3311.songmicroservice;

import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.data.mongodb.core.query.Criteria.where;

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

	@Test
	public void testGetSongById_1() {
		MongoClient mongoClient = new MongoClient();
		MongoTemplate db = new MongoTemplate(mongoClient, "test");
		SongDalImpl test = new SongDalImpl(db);

		Song song = new Song("No More Parties in LA","Kanye West", "The Life of Pablo", "January 18th, 2016");
		ObjectId objectId = new ObjectId();
		song.setId(objectId);

		test.addSong(song);
		DbQueryStatus status = test.findSongById(objectId.toHexString());

		assertEquals(status.getdbQueryExecResult(), DbQueryExecResult.QUERY_OK);
	}

	@Test
	public void testGetSongById_2() {
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

	@Test
	public void testGetSongTitleById_1() {
		MongoClient mongoClient = new MongoClient();
		MongoTemplate db = new MongoTemplate(mongoClient, "test");
		SongDalImpl test = new SongDalImpl(db);

		Song song = new Song("No More Parties in LA","Kanye West", "The Life of Pablo", "January 18th, 2016");
		ObjectId objectId = new ObjectId();
		song.setId(objectId);

		test.addSong(song);
		DbQueryStatus status = test.getSongTitleById(song.getId());

		assertEquals(status.getdbQueryExecResult(), DbQueryExecResult.QUERY_OK);
	}

	@Test
	public void testGetSongTitleById_2() {
		MongoClient mongoClient = new MongoClient();
		MongoTemplate db = new MongoTemplate(mongoClient, "test");
		SongDalImpl test = new SongDalImpl(db);

		Song song = new Song("No More Parties in LA","Kanye West", "The Life of Pablo", "January 18th, 2016");
		ObjectId objectId = new ObjectId();
		ObjectId objectId2 = new ObjectId();
		song.setId(objectId);

		test.addSong(song);
		DbQueryStatus status = test.getSongTitleById(objectId2.toHexString());

		assertEquals(status.getdbQueryExecResult(), DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
	}

	@Test
	public void testDeleteSongById() {
		MongoClient mongoClient = new MongoClient();
		MongoTemplate db = new MongoTemplate(mongoClient, "test");
		SongDalImpl test = new SongDalImpl(db);

		Song song = new Song("No More Parties in LA","Kanye West", "The Life of Pablo", "January 18th, 2016");
		ObjectId objectId = new ObjectId();
		song.setId(objectId);

		test.addSong(song);
		test.deleteSongById(song.getId());
		DbQueryStatus status = test.findSongById(song.getId());

		assertEquals(status.getdbQueryExecResult(), DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
	}

	@Test
	public void testGetReleaseDateById_1() {
		MongoClient mongoClient = new MongoClient();
		MongoTemplate db = new MongoTemplate(mongoClient, "test");
		SongDalImpl test = new SongDalImpl(db);

		Song song = new Song("No More Parties in LA","Kanye West", "The Life of Pablo", "January 18th, 2016");
		ObjectId objectId = new ObjectId();
		song.setId(objectId);

		test.addSong(song);
		DbQueryStatus status = test.getReleaseDateById(song.getId());

		assertEquals(status.getdbQueryExecResult(), DbQueryExecResult.QUERY_OK);
	}

	@Test
	public void testGetReleaseDateById_2() {
		MongoClient mongoClient = new MongoClient();
		MongoTemplate db = new MongoTemplate(mongoClient, "test");
		SongDalImpl test = new SongDalImpl(db);

		Song song = new Song("No More Parties in LA","Kanye West", "The Life of Pablo", "January 18th, 2016");
		ObjectId objectId = new ObjectId();
		ObjectId objectId2 = new ObjectId();
		song.setId(objectId);

		test.addSong(song);
		DbQueryStatus status = test.getReleaseDateById(objectId2.toHexString());

		assertEquals(status.getdbQueryExecResult(), DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
	}

	@Test
	public void testUpdateSongFavouritesCount_1() {
		MongoClient mongoClient = new MongoClient();
		MongoTemplate db = new MongoTemplate(mongoClient, "test");
		SongDalImpl test = new SongDalImpl(db);

		Song song = new Song("No More Parties in LA","Kanye West", "The Life of Pablo", "January 18th, 2016");
		ObjectId objectId = new ObjectId();
		song.setId(objectId);

		test.addSong(song);
		DbQueryStatus status = test.updateSongFavouritesCount(song.getId(), false);
		List<Song> songList = db.find(new Query(where("_id").is(objectId.toHexString())), Song.class);
		Song retrievedSong = songList.get(0);

		assertEquals(retrievedSong.getSongAmountFavourites(), 1);
		assertEquals(status.getdbQueryExecResult(), DbQueryExecResult.QUERY_OK);
	}

	@Test
	public void testUpdateSongFavouritesCount_2() {
		MongoClient mongoClient = new MongoClient();
		MongoTemplate db = new MongoTemplate(mongoClient, "test");
		SongDalImpl test = new SongDalImpl(db);

		Song song = new Song("No More Parties in LA","Kanye West", "The Life of Pablo", "January 18th, 2016");
		ObjectId objectId = new ObjectId();
		song.setId(objectId);

		test.addSong(song);
		DbQueryStatus status = test.updateSongFavouritesCount(song.getId(), true);

		assertEquals(status.getdbQueryExecResult(), DbQueryExecResult.QUERY_ERROR_GENERIC);
	}

	@Test
	public void testUpdateSongFavouritesCount_3() {
		MongoClient mongoClient = new MongoClient();
		MongoTemplate db = new MongoTemplate(mongoClient, "test");
		SongDalImpl test = new SongDalImpl(db);

		Song song = new Song("No More Parties in LA","Kanye West", "The Life of Pablo", "January 18th, 2016");
		ObjectId objectId = new ObjectId();
		ObjectId objectId2 = new ObjectId();
		song.setId(objectId);

		test.addSong(song);
		DbQueryStatus status = test.updateSongFavouritesCount(objectId2.toHexString(), true);

		assertEquals(status.getdbQueryExecResult(), DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
	}

}