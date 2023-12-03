package com.eecs3311.profilemicroservice;

/**
 * Interface for handling profile-related operations in the profile microservice.
 * This interface defines methods for creating user profiles, managing friend relationships,
 * and a method to retrieve songs liked by a user's friends.
 *
 * <p>Interface provides the necessary logic to interact with the underlying data storage mechanism.
 * Also, includes operations like adding new user profiles, and managing follow and unfollow actions
 * among users.
 */
public interface ProfileDriver {

	/**
	 * Creates a new user profile.
	 *
	 * @param userName The username for the new profile.
	 * @param fullName The full name of the user.
	 * @param password The password for the new profile.
	 * @return {@link DbQueryStatus} indicating the result of the profile creation operation.
	 */
	DbQueryStatus createUserProfile(String userName, String fullName, String password);

	/**
	 * Allows a user to follow a friend.
	 *
	 * @param userName The username of the user who wants to follow.
	 * @param frndUserName The username of the friend to be followed.
	 * @return {@link DbQueryStatus} indicating the result of the follow operation.
	 */
	DbQueryStatus followFriend(String userName, String frndUserName);

	/**
	 * Allows a user to unfollow a friend.
	 *
	 * @param userName The username of the user who wants to unfollow.
	 * @param frndUserName The username of the friend to be unfollowed.
	 * @return {@link DbQueryStatus} indicating the result of the unfollow operation.
	 */
	DbQueryStatus unfollowFriend(String userName, String frndUserName );

	/**
	 * Retrieves all songs liked by a user's friends.
	 *
	 * @param userName The username of the user whose friends' liked songs are to be retrieved.
	 * @return {@link DbQueryStatus} containing the result of the retrieval operation along with the list of songs.
	 */
	DbQueryStatus getAllSongFriendsLike(String userName);
}