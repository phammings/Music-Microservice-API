package com.eecs3311.profilemicroservice;

/**
 * Defines the contract for playlist operations in the profile microservice.
 * This interface provides methods for liking and unliking songs within a user's playlist.
 *
 * <p>Interface provides the necessary logic to updatethe playlist preferences of a user
 * in the context of a music application or service.
 */
public interface PlaylistDriver {

	/**
	 * Likes a song for a user. This method should record the user's preference for a song as a 'like'.
	 *
	 * @param userName The username of the user who is liking the song.
	 * @param songId The identifier of the song to be liked.
	 * @return {@link DbQueryStatus} object containing the status of the like operation.
	 */
	DbQueryStatus likeSong(String userName, String songId);

	/**
	 * Unlikes a song for a user. This method should remove the user's 'like' preference for a song.
	 *
	 * @param userName The username of the user who is unliking the song.
	 * @param songId The identifier of the song to be unliked.
	 * @return {@link DbQueryStatus} object containing the status of the unlike operation.
	 */
	DbQueryStatus unlikeSong(String userName, String songId);

	/**
	 * Deletes a song from the database.
	 *
	 * @param songId The identifier of the song to be deleted.
	 * @return {@link DbQueryStatus} object containing the status of the unlike operation.
	 */
	DbQueryStatus deleteSongById(String songId);
}