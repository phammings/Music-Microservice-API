package com.eecs3311.songmicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import static org.springframework.data.mongodb.core.query.Criteria.where;


/**
 * Implementation of the Song Data Access Layer (SongDal) interface using MongoDB.
 * Provides methods for interacting with the MongoDB database for song-related operations.
 */
@Repository
public class SongDalImpl implements SongDal {

	private final MongoTemplate db;

	/**
	 * Constructor for SongDalImpl.
	 *
	 * @param mongoTemplate The MongoDB template for interacting with the database.
	 */
	@Autowired
	public SongDalImpl(MongoTemplate mongoTemplate) {
		this.db = mongoTemplate;
	}

	/**
	 * Adds a new song to the database.
	 *
	 * @param songToAdd The song to be added.
	 * @return DbQueryStatus indicating the result of the operation.
	 */
	@Override
	public DbQueryStatus addSong(Song songToAdd) {
		if (songToAdd.getSongAlbum() != null && songToAdd.getSongName() != null && songToAdd.getSongArtistFullName() != null ) {
			db.insert(songToAdd);
			DbQueryStatus newStatus = new DbQueryStatus("OK", DbQueryExecResult.QUERY_OK);
			newStatus.setData(songToAdd);
			return newStatus;
		}
		DbQueryStatus newStatus = new DbQueryStatus("Song invalid", DbQueryExecResult.QUERY_ERROR_GENERIC);
		return newStatus;
	}

	/**
	 * Retrieves a song by its ID from the database.
	 *
	 * @param songId The ID of the song to retrieve.
	 * @return DbQueryStatus indicating the result of the operation.
	 */
	@Override
	public DbQueryStatus findSongById(String songId) {
		if (db.findById(songId, Song.class) != null) {
			DbQueryStatus newStatus = new DbQueryStatus("OK", DbQueryExecResult.QUERY_OK);
			newStatus.setData(db.findById(songId, Song.class));
			return newStatus;
		}
		DbQueryStatus newStatus = new DbQueryStatus("Song not found", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
		return newStatus;
	}

	/**
	 * Retrieves the title of a song by its ID from the database.
	 *
	 * @param songId The ID of the song to retrieve the title for.
	 * @return DbQueryStatus indicating the result of the operation.
	 */
	@Override
	public DbQueryStatus getSongTitleById(String songId) {
		if (db.findById(songId, Song.class) != null) {
			DbQueryStatus newStatus = new DbQueryStatus("OK", DbQueryExecResult.QUERY_OK);
			newStatus.setData(db.findById(songId, Song.class).getSongName());
			return newStatus;
		}
		DbQueryStatus newStatus = new DbQueryStatus("Song not found", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
		return newStatus;
	}

	/**
	 * Retrieves the release date of a song by its ID from the database.
	 *
	 * @param songId The ID of the song to retrieve the release date for.
	 * @return DbQueryStatus indicating the result of the operation.
	 */
	@Override
	public DbQueryStatus getReleaseDateById(String songId) {
		if (db.findById(songId, Song.class) != null) {
			DbQueryStatus newStatus = new DbQueryStatus("OK", DbQueryExecResult.QUERY_OK);
			newStatus.setData(db.findById(songId, Song.class).getSongReleaseDate());
			return newStatus;
		}
		DbQueryStatus newStatus = new DbQueryStatus("Song not found", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
		return newStatus;
	}

	/**
	 * Deletes a song by its ID from the database.
	 *
	 * @param songId The ID of the song to delete.
	 * @return DbQueryStatus indicating the result of the operation.
	 */
	@Override
	public DbQueryStatus deleteSongById(String songId) {
		if (db.findById(songId, Song.class) != null) {
			DbQueryStatus newStatus = new DbQueryStatus("OK", DbQueryExecResult.QUERY_OK);
			db.remove(db.findById(songId, Song.class));
			return newStatus;
		}
		DbQueryStatus newStatus = new DbQueryStatus("Song not found", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
		return newStatus;
	}

	/**
	 * Updates the favorite count of a song by its ID in the database.
	 *
	 * @param songId          The ID of the song to update.
	 * @param shouldDecrement A boolean indicating whether to decrement the favorite count.
	 * @return DbQueryStatus indicating the result of the operation.
	 */
	@Override
	public DbQueryStatus updateSongFavouritesCount(String songId, boolean shouldDecrement) {
		if (db.findById(songId, Song.class) != null) {
			Song song = db.findById(songId, Song.class);

			if (shouldDecrement == true && song.getSongAmountFavourites() == 0) {
				DbQueryStatus newStatus = new DbQueryStatus("Song like count must be above 0", DbQueryExecResult.QUERY_ERROR_GENERIC);
				return newStatus;
			}

			if (shouldDecrement) {
				song.setSongAmountFavourites(song.getSongAmountFavourites()-1);
			}
			else {
				song.setSongAmountFavourites(song.getSongAmountFavourites() + 1);
			}
			this.db.findAndReplace(new Query(where("_id").is(songId)), song);
			DbQueryStatus newStatus = new DbQueryStatus("OK", DbQueryExecResult.QUERY_OK);
			return newStatus;
		}
		else {
			DbQueryStatus newStatus = new DbQueryStatus("Song not found", DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
			return newStatus;
		}
	}
}