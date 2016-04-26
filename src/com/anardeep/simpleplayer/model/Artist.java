package com.anardeep.simpleplayer.model;

public class Artist implements Comparable<Artist> {
	private String artistName;
	private String artistId;

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getArtistId() {
		return artistId;
	}

	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}

	@Override
	public int hashCode() {
		int primeNumber = 7;
		int hashId = this.getArtistId().hashCode();
		int hashCode = primeNumber * hashId;
		return hashCode;
	}

	@Override
	public boolean equals(Object o) {
		boolean result = false;

		if (o == null) {
			return result;
		}
		if (!(o instanceof Artist)) {
			return result;
		}
		Artist artist = (Artist) o;
		if (!(artist.getArtistId().equals(this.getArtistId()))) {
			return result;
		}
		return result;
	}

	@Override
	public int compareTo(Artist another) {
		return this.getArtistName().compareToIgnoreCase(another.getArtistName());
	}

}
