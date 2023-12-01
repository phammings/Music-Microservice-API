package com.eecs3311.songmicroservice;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents a song in the Song Microservice.
 * This class is annotated as a MongoDB document.
 */
@Document(collection="songs")
public class Song {
	@Id
	@JsonIgnore
	public ObjectId _id;
	
	private String songName;
	private String songArtistFullName;
	private String songAlbum;
	private long songAmountFavourites;
	private String songReleaseDate;
	
	public static final String KEY_SONG_NAME = "songName";
	public static final String KEY_SONG_ARTIST_FULL_NAME = "songArtistFullName";
	public static final String KEY_SONG_ALBUM = "songAlbum";
	public static final String KEY_SONG_RELEASE_DATE = "songReleaseDate";

	/**
	 * Constructs a Song object with specified parameters.
	 *
	 * @param songName             The name of the song.
	 * @param songArtistFullName  The full name of the song artist.
	 * @param songAlbum            The album containing the song.
	 * @param songReleaseDate      The release date of the song.
	 */
	public Song(String songName, String songArtistFullName, String songAlbum, String songReleaseDate) {
		this.songName = songName;
		this.songArtistFullName = songArtistFullName;
		this.songAlbum = songAlbum;
		this.songAmountFavourites = 0;
		this.songReleaseDate = songReleaseDate;
	}

	/**
	 * Gets the name of the song.
	 *
	 * @return The name of the song.
	 */
	public String getSongName() {
		return songName;
	}

	/**
	 * Sets the name of the song.
	 *
	 * @param songName The name to set.
	 */
	public void setSongName(String songName) {
		this.songName = songName;
	}

	/**
	 * Gets the full name of the song artist.
	 *
	 * @return The full name of the song artist.
	 */
	public String getSongArtistFullName() {
		return songArtistFullName;
	}

	/**
	 * Sets the full name of the song artist.
	 *
	 * @param songArtistFullName The full name to set.
	 */
	public void setSongArtistFullName(String songArtistFullName) {
		this.songArtistFullName = songArtistFullName;
	}

	/**
	 * Gets the album containing the song.
	 *
	 * @return The album containing the song.
	 */
	public String getSongAlbum() {
		return songAlbum;
	}

	/**
	 * Sets the album containing the song.
	 *
	 * @param songAlbum The album to set.
	 */
	public void setSongAlbum(String songAlbum) {
		this.songAlbum = songAlbum;
	}

	/**
	 * Gets the number of favorites for the song.
	 *
	 * @return The number of favorites for the song.
	 */
	public long getSongAmountFavourites() {
		return songAmountFavourites;
	}

	/**
	 * Sets the number of favorites for the song.
	 *
	 * @param songAmountFavourites The number of favorites to set.
	 */
	public void setSongAmountFavourites(long songAmountFavourites) {
		this.songAmountFavourites = songAmountFavourites;
	}

	/**
	 * Gets the release date of the song.
	 *
	 * @return The release date of the song.
	 */
	public String getSongReleaseDate() { return songReleaseDate; }

	/**
	 * Sets the release date of the song.
	 *
	 * @param songReleaseDate The release date to set.
	 */
	public void setSongReleaseDate(String songReleaseDate) { this.songReleaseDate = songReleaseDate; }

	// ObjectId needs to be converted to string
	/**
	 * Gets the unique identifier of the song.
	 *
	 * @return The unique identifier of the song.
	 */
	public String getId() {
		return _id.toHexString();
	}

	/**
	 * Sets the unique identifier of the song.
	 *
	 * @param _id The unique identifier to set.
	 */
	public void setId(ObjectId _id) {
		this._id = _id;
	}

	/**
	 * Overrides the default toString method to return the JSON representation of the Song.
	 *
	 * @return A String containing the JSON representation of the Song.
	 */
	@Override
	public String toString() {
		return this.getJsonRepresentation().toString();
	}

	/**
	 * Converts the Song object to its JSON representation.
	 *
	 * @return A Map containing key-value pairs representing the JSON representation of the Song.
	 */
	@JsonIgnore
	public Map<String, String> getJsonRepresentation() {
		HashMap<String, String> jsonRepresentation = new HashMap<String, String>();
		jsonRepresentation.put("id", this.getId());
		jsonRepresentation.put("songName", this.songName);
		jsonRepresentation.put("songArtistFullName", this.songArtistFullName);
		jsonRepresentation.put("songAlbum", this.songAlbum);
		jsonRepresentation.put("songAmountFavourites", String.valueOf(this.songAmountFavourites));
		
		return jsonRepresentation;
	}
}