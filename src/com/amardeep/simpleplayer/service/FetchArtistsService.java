package com.amardeep.simpleplayer.service;

import java.util.List;

import com.anardeep.simpleplayer.model.Artist;
import com.anardeep.simpleplayer.model.Song;

import android.content.Context;

public interface FetchArtistsService {
	public List<Artist> getAllArtists(Context context);
	public List<Song> getAllSongsByArtist(Context context, String[] artistId) ;
}
