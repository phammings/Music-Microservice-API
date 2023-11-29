package com.eecs3311.songmicroservice;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class SongDalImpl implements SongDal {

	private final MongoTemplate db;

	@Autowired
	public SongDalImpl(MongoTemplate mongoTemplate) {
		this.db = mongoTemplate;
	}

	@Override
	public DbQueryStatus addSong(Song songToAdd) {
		if (songToAdd.getSongAlbum() != null && songToAdd.getSongName() != null && songToAdd.getSongArtistFullName() != null ) {
			db.insert(songToAdd);
			DbQueryStatus status = new DbQueryStatus("OK", DbQueryExecResult.QUERY_OK);
			status.setData(songToAdd);
			return status;
		}
		DbQueryStatus status = new DbQueryStatus("Song invalid", DbQueryExecResult.QUERY_ERROR_GENERIC);
		return status;
	}

	@Override
	public DbQueryStatus findSongById(String songId) {
		if (db.findById(songId, Song.class) != null) {
			DbQueryStatus status = new DbQueryStatus("OK", DbQueryExecResult.QUERY_OK);
			status.setData(db.findById(songId, Song.class));
			return status;
		}
		DbQueryStatus status = new DbQueryStatus("Song not found", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
		return status;
	}

	@Override
	public DbQueryStatus getSongTitleById(String songId) {
		if (db.findById(songId, Song.class) != null) {
			DbQueryStatus status = new DbQueryStatus("OK", DbQueryExecResult.QUERY_OK);
			status.setData(db.findById(songId, Song.class).getSongName());
			return status;
		}
		DbQueryStatus status = new DbQueryStatus("Song not found", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
		return status;
	}

	@Override
	public DbQueryStatus deleteSongById(String songId) {
		if (db.findById(songId, Song.class) != null) {
			DbQueryStatus status = new DbQueryStatus("OK", DbQueryExecResult.QUERY_OK);
			db.remove(db.findById(songId, Song.class));
			return status;
		}
		DbQueryStatus status = new DbQueryStatus("Song not found", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
		return status;
	}

	@Override
	public DbQueryStatus updateSongFavouritesCount(String songId, boolean shouldDecrement) {
		// TODO Auto-generated method stub
		return null;
	}
}