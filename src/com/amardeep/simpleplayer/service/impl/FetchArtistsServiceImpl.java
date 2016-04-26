package com.amardeep.simpleplayer.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import com.amardeep.simpleplayer.service.FetchArtistsService;
import com.anardeep.simpleplayer.model.Artist;
import com.anardeep.simpleplayer.model.Song;

public class FetchArtistsServiceImpl implements FetchArtistsService {

	@Override
	public List<Artist> getAllArtists(Context context) {
		ContentResolver contentResolver = context.getContentResolver();
		List<Artist> artistList = new ArrayList<>();
		Set<Artist> artistSet=new TreeSet<>();
		String[] projection = { android.provider.MediaStore.Audio.Media.ARTIST,
				android.provider.MediaStore.Audio.Media.ARTIST_ID };
		Cursor cursor = null;
		try {
			cursor = contentResolver
					.query(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
							projection, null, null,
							android.provider.MediaStore.Audio.Media.ARTIST);
			while (cursor.moveToNext()) {
				Artist artist = new Artist();
				artist.setArtistId(cursor.getString(cursor
						.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST_ID)));
				artist.setArtistName(cursor.getString(cursor
						.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST)));
				artistSet.add(artist);

			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		for(Iterator<Artist> itr=artistSet.iterator();itr.hasNext();)
		{
			artistList.add(itr.next());
		}
		return artistList;
	}

	public List<Song> getAllSongsByArtist(Context context, String[] artistId) {
		ContentResolver contentResolver = context.getContentResolver();
		List<Song> songList = new ArrayList<>();
		String[] projection = { android.provider.MediaStore.Audio.Media._ID,
				android.provider.MediaStore.Audio.Media.ARTIST,
				android.provider.MediaStore.Audio.Media.TITLE };
		String selection = android.provider.MediaStore.Audio.Media.ARTIST_ID + "=?";
		Cursor cursor = null;
		try {
			cursor = contentResolver
					.query(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
							projection, selection, artistId,
							android.provider.MediaStore.Audio.Media.TITLE);
			while (cursor.moveToNext()) {
				long songId = cursor
						.getLong(cursor
								.getColumnIndex(android.provider.MediaStore.Audio.Media._ID));
				String songArtist = cursor
						.getString(cursor
								.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST));
				String songTitle = cursor
						.getString(cursor
								.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE));
				Song song = new Song(songId, songTitle, songArtist);
				songList.add(song);

			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return songList;
	}
}
