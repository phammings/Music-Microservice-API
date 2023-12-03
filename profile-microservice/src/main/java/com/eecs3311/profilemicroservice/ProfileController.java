package com.eecs3311.profilemicroservice;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eecs3311.profilemicroservice.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller class for handling profile-related HTTP requests in the profile microservice.
 * This class provides endpoints for managing user profiles and their interactions with playlists.
 *
 * <p>It includes methods for adding a profile, following and unfollowing friends, and liking
 * or unliking songs. These methods interact with the {@link ProfileDriverImpl} and
 * {@link PlaylistDriverImpl} to perform the necessary operations.
 */
@RestController
@RequestMapping
public class ProfileController {
	public static final String KEY_USER_NAME = "userName";
	public static final String KEY_USER_FULLNAME = "fullName";
	public static final String KEY_USER_PASSWORD = "password";
	public static final String KEY_FRIEND_USER_NAME = "friendUserName";
	public static final String KEY_SONG_ID = "songId";

	@Autowired
	private final ProfileDriverImpl profileDriver;

	@Autowired
	private final PlaylistDriverImpl playlistDriver;

	OkHttpClient client = new OkHttpClient();

	/**
	 * Constructs a ProfileController with the specified profile and playlist drivers.
	 *
	 * @param profileDriver The driver for handling profile operations.
	 * @param playlistDriver The driver for handling playlist operations.
	 */
	public ProfileController(ProfileDriverImpl profileDriver, PlaylistDriverImpl playlistDriver) {
		this.profileDriver = profileDriver;
		this.playlistDriver = playlistDriver;
	}

	/**
	 * Endpoint for adding a new user profile.
	 *
	 * @param params A map containing user details such as userName, fullName, and password.
	 * @param request The HTTP request object.
	 * @return ResponseEntity containing the operation status.
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> addProfile(@RequestBody Map<String, String> params, HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("POST %s", Utils.getUrl(request)));
		// Done for now, need to test
		DbQueryStatus new_dbQueryStatus = profileDriver.createUserProfile(params.get(KEY_USER_NAME), params.get(KEY_USER_FULLNAME), params.get(KEY_USER_PASSWORD));
		response.put("msg", new_dbQueryStatus.getMessage());
		return Utils.setResponseStatus(response, new_dbQueryStatus.getdbQueryExecResult(), new_dbQueryStatus.getData());

		//return ResponseEntity.status(HttpStatus.OK).body(response); // TODO: replace with return statement similar to in getSongById
	}

	/**
	 * Endpoint for following a friend.
	 *
	 * @param params A map containing the usernames of the user and the friend to follow.
	 * @param request The HTTP request object.
	 * @return ResponseEntity containing the operation status.
	 */
	@RequestMapping(value = "/followFriend", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> followFriend(@RequestBody Map<String, String> params, HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("PUT %s", Utils.getUrl(request)));
		// TODO: add any other values to the map following the example in SongController.getSongById

		// Done, need to test
		DbQueryStatus new_dbQueryStatus = profileDriver.followFriend(params.get(KEY_USER_NAME), params.get(KEY_FRIEND_USER_NAME));
		response.put("msg", new_dbQueryStatus.getMessage());
		return Utils.setResponseStatus(response,new_dbQueryStatus.getdbQueryExecResult(),new_dbQueryStatus.getData());


		//return ResponseEntity.status(HttpStatus.OK).body(response); // TODO: replace with return statement similar to in getSongById
	}

	/**
	 * Endpoint for retrieving all favourite song titles of a user's friends.
	 *
	 * @param userName The username of the user.
	 * @param request The HTTP request object.
	 * @return ResponseEntity containing the operation status.
	 */
	@RequestMapping(value = "/getAllFriendFavouriteSongTitles/{userName}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getAllFriendFavouriteSongTitles(@PathVariable("userName") String userName,
			HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("PUT %s", Utils.getUrl(request)));
		// TODO: add any other values to the map following the example in SongController.getSongById

		// Done, need to test
		DbQueryStatus dbQueryStatus = profileDriver.getAllSongFriendsLike(userName);

		Map<String, List<String>> totalSongsFriendsLike = (Map<String, List<String>>) dbQueryStatus.getData();

		totalSongsFriendsLike.entrySet().forEach(entry -> {
			String name = entry.getKey();
			List<String> songName = new ArrayList<>();
			for (String songId : totalSongsFriendsLike.get(name)) {
				String url = "http://localhost:3001/getSongTitleById/" + songId;
				Request req = new Request.Builder().url(url).build();
				try (Response getReq = this.client.newCall(req).execute()) {
					songName.add(getReq.body().string());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			entry.setValue(songName);
		});

		response.put("msg", dbQueryStatus.getMessage());
		return Utils.setResponseStatus(response,dbQueryStatus.getdbQueryExecResult(), totalSongsFriendsLike);
		//return ResponseEntity.status(HttpStatus.OK).body(response); // TODO: replace with return statement similar to in getSongById
	}

	/**
	 * Endpoint for unfollowing a friend.
	 *
	 * @param params A map containing the usernames of the user and the friend to unfollow.
	 * @param request The HTTP request object.
	 * @return ResponseEntity containing the operation status.
	 */
	@RequestMapping(value = "/unfollowFriend", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> unfollowFriend(@RequestBody Map<String, String> params, HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("PUT %s", Utils.getUrl(request)));
		// TODO: add any other values to the map following the example in SongController.getSongById

		// Done, need to test
		DbQueryStatus new_dbQueryStatus = profileDriver.unfollowFriend(params.get(KEY_USER_NAME), params.get(KEY_FRIEND_USER_NAME));
		response.put("msg", new_dbQueryStatus.getMessage());
		return Utils.setResponseStatus(response,new_dbQueryStatus.getdbQueryExecResult(),new_dbQueryStatus.getData());
		//return ResponseEntity.status(HttpStatus.OK).body(response); // TODO: replace with return statement similar to in getSongById
	}


	/**
	 * Endpoint for liking a song.
	 *
	 * @param params A map containing the username of the user and the ID of the song to like.
	 * @param request The HTTP request object.
	 * @return ResponseEntity containing the operation status.
	 */
	@RequestMapping(value = "/likeSong", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> likeSong(@RequestBody Map<String, String> params, HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("PUT %s", Utils.getUrl(request)));
		// TODO: add any other values to the map following the example in SongController.getSongById

		// Done need to test
		DbQueryStatus new_dbQueryStatus = playlistDriver.likeSong(params.get(KEY_USER_NAME), params.get(KEY_SONG_ID));
		String song_url = "http://localhost:3001/updateSongFavouritesCount/" + params.get(KEY_SONG_ID) + "?shouldDecrement=false";
		Request new_Request = new Request.Builder().url(song_url).put(new FormBody.Builder().build()).build();

		try{
			if(new_dbQueryStatus.getdbQueryExecResult().equals(DbQueryExecResult.QUERY_OK)){
				Response new_response = client.newCall(new_Request).execute();
				String json_str = new_Request.body().toString();
				try{
					JSONObject new_JSONOBJECT = new JSONObject(json_str);
					if(new_JSONOBJECT.get("status").toString().equals("OK") == false) new_dbQueryStatus.setdbQueryExecResult(DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
					else if(new_dbQueryStatus.getdbQueryExecResult().equals(DbQueryExecResult.QUERY_ERROR_NOT_FOUND)) new_dbQueryStatus.setdbQueryExecResult(DbQueryExecResult.QUERY_OK);
				} catch(JSONException e){
					e.printStackTrace();
				}
			}
		} catch(Exception e){
			new_dbQueryStatus.setdbQueryExecResult(DbQueryExecResult.QUERY_ERROR_GENERIC);
			e.printStackTrace();
		}
		response.put("msg", new_dbQueryStatus.getMessage());
		return Utils.setResponseStatus(response,new_dbQueryStatus.getdbQueryExecResult(),new_dbQueryStatus.getData());
		//return ResponseEntity.status(HttpStatus.OK).body(response); // TODO: replace with return statement similar to in getSongById
	}

	/**
	 * Endpoint for unliking a song.
	 *
	 * @param params A map containing the username of the user and the ID of the song to unlike.
	 * @param request The HTTP request object.
	 * @return ResponseEntity containing the operation status.
	 */
	@RequestMapping(value = "/unlikeSong", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> unlikeSong(@RequestBody Map<String, String> params, HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("PUT %s", Utils.getUrl(request)));
		// TODO: add any other values to the map following the example in SongController.getSongById


		DbQueryStatus new_dbQueryStatus = playlistDriver.likeSong(params.get(KEY_USER_NAME), params.get(KEY_SONG_ID));
		String song_url = "http://localhost:3001/updateSongFavouritesCount/" + params.get(KEY_SONG_ID) + "?shouldDecrement=true";
		Request new_Request = new Request.Builder().url(song_url).put(new FormBody.Builder().build()).build();

		try{
			if(new_dbQueryStatus.getdbQueryExecResult().equals(DbQueryExecResult.QUERY_OK)) {
				Response new_response = client.newCall(new_Request).execute();
				String json_str = new_Request.body().toString();
				try{
					JSONObject new_JSONOBJECT = new JSONObject(json_str);
					if(new_JSONOBJECT.get("status").toString().equals("OK") == false) new_dbQueryStatus.setdbQueryExecResult(DbQueryExecResult.QUERY_ERROR_GENERIC);

				} catch(JSONException e){
					e.printStackTrace();
				}
			}
		} catch(Exception e){
			new_dbQueryStatus.setdbQueryExecResult(DbQueryExecResult.QUERY_ERROR_GENERIC);
			e.printStackTrace();
		}
		response.put("msg", new_dbQueryStatus.getMessage());
		return Utils.setResponseStatus(response,new_dbQueryStatus.getdbQueryExecResult(),new_dbQueryStatus.getData());

		//return ResponseEntity.status(HttpStatus.OK).body(response); // TODO: replace with return statement similar to in getSongById
	}
}