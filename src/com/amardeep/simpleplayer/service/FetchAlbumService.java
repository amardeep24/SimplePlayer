package com.amardeep.simpleplayer.service;

import java.util.List;

import android.content.Context;

import com.anardeep.simpleplayer.model.Album;
import com.anardeep.simpleplayer.model.Song;

public interface FetchAlbumService {
	public List<Album> getAllAlbums(Context context);
	public List<Song> getSongsByAlbumName(Context context,String[] albumName);
}
