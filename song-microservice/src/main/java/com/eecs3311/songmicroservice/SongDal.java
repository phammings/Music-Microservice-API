package com.eecs3311.songmicroservice;

/**
 * An interface representing the data access layer for Song entities.
 */
public interface SongDal {

	/**
	 * Adds a new song to the data store.
	 *
	 * @param songToAdd The song to add.
	 * @return The query status indicating the success or failure of the operation.
	 */
	DbQueryStatus addSong(Song songToAdd);

	/**
	 * Finds a song by its unique identifier.
	 *
	 * @param songId The unique identifier of the song.
	 * @return The query status indicating the success or failure of the operation, along with the found song data.
	 */
	DbQueryStatus findSongById(String songId);

	/**
	 * Gets the title of a song by its unique identifier.
	 *
	 * @param songId The unique identifier of the song.
	 * @return The query status indicating the success or failure of the operation, along with the song title data.
	 */
	DbQueryStatus getSongTitleById(String songId);

	/**
	 * Gets the release date of a song by its unique identifier.
	 *
	 * @param songId The unique identifier of the song.
	 * @return The query status indicating the success or failure of the operation, along with the song release date data.
	 */
	DbQueryStatus getReleaseDateById(String songId);

	/**
	 * Deletes a song by its unique identifier.
	 *
	 * @param songId The unique identifier of the song to delete.
	 * @return The query status indicating the success or failure of the operation.
	 */
	DbQueryStatus deleteSongById(String songId);

	/**
	 * Updates the favorites count of a song by its unique identifier.
	 *
	 * @param songId            The unique identifier of the song.
	 * @param shouldDecrement   A boolean indicating whether to decrement the favorites count.
	 * @return The query status indicating the success or failure of the operation.
	 */
	DbQueryStatus updateSongFavouritesCount(String songId, boolean shouldDecrement);
}
