package com.eecs3311.profilemicroservice;

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

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

	public ProfileController(ProfileDriverImpl profileDriver, PlaylistDriverImpl playlistDriver) {
		this.profileDriver = profileDriver;
		this.playlistDriver = playlistDriver;
	}

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

	@RequestMapping(value = "/getAllFriendFavouriteSongTitles/{userName}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getAllFriendFavouriteSongTitles(@PathVariable("userName") String userName,
			HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("PUT %s", Utils.getUrl(request)));
		// TODO: add any other values to the map following the example in SongController.getSongById

		// Done, need to test
		DbQueryStatus new_dbQueryStatus = profileDriver.getAllSongFriendsLike(userName);
		response.put("msg", new_dbQueryStatus.getMessage());
		return Utils.setResponseStatus(response,new_dbQueryStatus.getdbQueryExecResult(),new_dbQueryStatus.getData());
		//return ResponseEntity.status(HttpStatus.OK).body(response); // TODO: replace with return statement similar to in getSongById
	}


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

	@RequestMapping(value = "/likeSong", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> likeSong(@RequestBody Map<String, String> params, HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("PUT %s", Utils.getUrl(request)));
		// TODO: add any other values to the map following the example in SongController.getSongById

		// Done need to test
		DbQueryStatus new_dbQueryStatus = playlistDriver.likeSong(params.get(KEY_USER_NAME), params.get(KEY_SONG_ID));
		FormBody new_RequestBody = new FormBody.builder().build();
		String song_url = "http://localhost:3001" + params.get(KEY_SONG_ID) + "?shouldDecrement=false";
		Request new_Request = new Request.Builder().url(song_url).put(new_RequestBody).build();

		try{
			if(new_dbQueryStatus.getdbQueryExecResult().equals(DbQueryExecResult.QUERY_OK)){
				Response new_response = client.newCall(new_Request).execute();
				JSONObject new_JSONOBJECT = new JSONObject(new_Request.body().toString());
				if(new_JSONOBJECT.get("status").toString().equals("OK") == false) new_dbQueryStatus.setdbQueryExecResult(DbQueryExecResult.QUERY_ERROR_NOT_FOUND);
				else if(new_dbQueryStatus.getdbQueryExecResult().equals(DbQueryExecResult.QUERY_ERROR_NOT_FOUND)) new_dbQueryStatus.setdbQueryExecResult(DbQueryExecResult.QUERY_OK);
			}
		} catch(Exception e){
			new_dbQueryStatus.setdbQueryExecResult(DbQueryExecResult.QUERY_ERROR_GENERIC);
			e.printStackTrace();
		}
		response.put("msg", new_dbQueryStatus.getMessage());
		return Utils.setResponseStatus(response,new_dbQueryStatus.getdbQueryExecResult(),new_dbQueryStatus.getData());
		//return ResponseEntity.status(HttpStatus.OK).body(response); // TODO: replace with return statement similar to in getSongById
	}

	@RequestMapping(value = "/unlikeSong", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> unlikeSong(@RequestBody Map<String, String> params, HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("PUT %s", Utils.getUrl(request)));
		// TODO: add any other values to the map following the example in SongController.getSongById

		DbQueryStatus new_dbQueryStatus = playlistDriver.likeSong(params.get(KEY_USER_NAME), params.get(KEY_SONG_ID));
		RequestBody new_ResponseBody = new FormBody.Builder().build();
		String song_url = "http://localhost:3001" + params.get(KEY_SONG_ID) + "?shouldDecrement=true";
		Request new_Request = new Request.Builder().url(song_url).put(new_ResponseBody).build();

		try{
			if(new_dbQueryStatus.getdbQueryExecResult().equals(DbQueryExecResult.QUERY_OK)) {
				Response new_response = client.newCall(new_Request).execute();
				JSONObject new_JSONOBJECT = new JSONObject(new_Request.body().toString());
				if(new_ResponseBody.get("status").toString().equals("OK") == false) new_dbQueryStatus.setdbQueryExecResult(DbQueryExecResult.QUERY_ERROR_GENERIC);
			}
		} catch(Exception e){
			dbQueryStatus.setdbQueryExecResult(DbQueryExecResult.QUERY_ERROR_GENERIC);
			e.printStackTrace();
		}
		response.put("msg", new_dbQueryStatus.getMessage());
		return Utils.setResponseStatus(response,new_dbQueryStatus.getdbQueryExecResult(),new_dbQueryStatus.getData());

		//return ResponseEntity.status(HttpStatus.OK).body(response); // TODO: replace with return statement similar to in getSongById
	}
}