package com.eecs3311.songmicroservice;

import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller class for handling HTTP requests related to songs.
 */
@RestController
@RequestMapping
public class SongController {

	@Autowired
	private final SongDal songDal;

	private OkHttpClient client = new OkHttpClient();

	/**
	 * Constructor for SongController.
	 *
	 * @param songDal The data access layer for song-related operations.
	 */
	public SongController(SongDal songDal) {
		this.songDal = songDal;
	}

	/**
	 * Retrieves a song by its ID.
	 *
	 * @param songId  The ID of the song.
	 * @param request The HTTP servlet request.
	 * @return ResponseEntity containing information about the song.
	 */
	@RequestMapping(value = "/getSongById/{songId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getSongById(@PathVariable("songId") String songId,
			HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("GET %s", Utils.getUrl(request)));

		DbQueryStatus dbQueryStatus = songDal.findSongById(songId);

		response.put("message", dbQueryStatus.getMessage());
		return Utils.setResponseStatus(response, dbQueryStatus.getdbQueryExecResult(), dbQueryStatus.getData());
	}

	/**
	 * Retrieves the title of a song by its ID.
	 *
	 * @param songId  The ID of the song.
	 * @param request The HTTP servlet request.
	 * @return ResponseEntity containing the title of the song.
	 */
	@RequestMapping(value = "/getSongTitleById/{songId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getSongTitleById(@PathVariable("songId") String songId,
			HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("GET %s", Utils.getUrl(request)));

		DbQueryStatus newStatus = songDal.getSongTitleById(songId);

		response.put("message", newStatus.getMessage());
		return Utils.setResponseStatus(response, newStatus.getdbQueryExecResult(), newStatus.getData());
	}

	/**
	 * Retrieves the release date of a song by its ID.
	 *
	 * @param songId  The ID of the song.
	 * @param request The HTTP servlet request.
	 * @return ResponseEntity containing the release date of the song.
	 */
	@RequestMapping(value = "/getReleaseDateById/{songId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getReleaseDateById(@PathVariable("songId") String songId,
																HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("GET %s", Utils.getUrl(request)));

		DbQueryStatus newStatus = songDal.getReleaseDateById(songId);

		response.put("message", newStatus.getMessage());
		return Utils.setResponseStatus(response, newStatus.getdbQueryExecResult(), newStatus.getData());
	}

	/**
	 * Deletes a song by its ID.
	 *
	 * @param songId  The ID of the song.
	 * @param request The HTTP servlet request.
	 * @return ResponseEntity containing information about the deletion newStatus.
	 */
	@RequestMapping(value = "/deleteSongById/{songId}", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Object>> deleteSongById(@PathVariable("songId") String songId,
			HttpServletRequest request) throws IOException {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("DELETE %s", Utils.getUrl(request)));

		DbQueryStatus newStatus = songDal.deleteSongById(songId);
		if (newStatus.getdbQueryExecResult().equals(DbQueryExecResult.QUERY_OK)) {
			String url = "http://localhost:8080/deleteSongById/" + songId;
			Request newRequestForm = new Request.Builder().url(url).put(new FormBody.Builder().build()).build();
			client.newCall(newRequestForm).execute();
		}
		response.put("message", newStatus.getMessage());
		return Utils.setResponseStatus(response, newStatus.getdbQueryExecResult(), newStatus.getData());
	}

	/**
	 * Adds a new song.
	 *
	 * @param params  The parameters for the new song.
	 * @param request The HTTP servlet request.
	 * @return ResponseEntity containing information about the addition newStatus.
	 */
	@RequestMapping(value = "/addSong", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> addSong(@RequestBody Map<String, String> params,
			HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("path", String.format("POST %s", Utils.getUrl(request)));

		String artistName = params.get("songName");
		String songName = params.get("songArtistFullName");
		String album = params.get("songAlbum");
		String releaseDate = params.get("songReleaseDate");

		Song song = new Song(artistName, songName, album, releaseDate);
		DbQueryStatus newStatus = songDal.addSong(song);

		response.put("message", newStatus.getMessage());
		return Utils.setResponseStatus(response, newStatus.getdbQueryExecResult(), newStatus.getData());
	}

	/**
	 * Updates the favorite count of a song.
	 *
	 * @param params  The parameters for updating the favorite count.
	 * @param request The HTTP servlet request.
	 * @return ResponseEntity containing information about the update newStatus.
	 */
	@RequestMapping(value = "/updateSongFavouritesCount", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> updateFavouritesCount(@RequestBody Map<String, String> params, HttpServletRequest request) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("data", String.format("PUT %s", Utils.getUrl(request)));

		String songId = params.get("songId");
		String shouldDecrement = params.get("shouldDecrement");
		boolean shouldDecrementBoolean = (shouldDecrement.equals("true"));

		DbQueryStatus newStatus = songDal.updateSongFavouritesCount(songId, shouldDecrementBoolean);

		response.put("message", newStatus.getMessage());
		return Utils.setResponseStatus(response, newStatus.getdbQueryExecResult(), newStatus.getData());
	}
}