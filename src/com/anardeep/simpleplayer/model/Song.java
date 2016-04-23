package com.anardeep.simpleplayer.model;

public class Song {

	private long id;
	private String songTitle;
	private String songArtist;
	

	public Song(long id, String songTitle, String songArtist) {
		this.id = id;
		this.songTitle = songTitle;
		this.songArtist = songArtist;
	}

	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getSongTitle() {
		return songTitle;
	}

	public void setSongTitle(String songTitle) {
		this.songTitle = songTitle;
	}

	public String getSongArtist() {
		return songArtist;
	}

	public void setSongArtist(String songArtist) {
		this.songArtist = songArtist;
	}

}
