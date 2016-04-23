package com.amardeep.simpleplayer.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.amardeep.simpleplayer.service.FetchSongService;
import com.anardeep.simpleplayer.model.Song;

public class FetchSongServiceImpl implements FetchSongService {

	private Context context;

	public FetchSongServiceImpl(Context context) {
		this.context = context;
	}

	/**
	 * method to fetch all sings from device external memory
	 */

	@Override
	public List<Song> getAllSongs() {
		Log.i("FetchSongServiceImpl","Inside getAllSongs");
		ContentResolver contentResolver;
		Uri musicUri;
		Cursor cursor;
		List<Song> songs=new ArrayList<Song>();
		if (context != null) {
			contentResolver = context.getContentResolver();
			musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
			cursor = contentResolver.query(musicUri, null, null, null, null);

			/**
			 * code to iterate through cursor checking null cursor and method
			 * moveToFirst checks if the cursor is empty and if not moves it to
			 * first place.
			 */
			try {
				while (cursor.moveToNext()) {
					int titleColumn = cursor
							.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
					int idColumn = cursor
							.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
					int artistColumn = cursor
							.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST);
					long id=cursor.getLong(idColumn);
					Log.i("FetchSongServiceImpl","id :-----> "+id);
					String title=cursor.getString(titleColumn);
					Log.i("FetchSongServiceImpl","title :-----> "+title);
					String artist=cursor.getString(artistColumn);
					Log.i("FetchSongServiceImpl","artist :-----> "+artist);
					songs.add(new Song(id,title,artist));
				}
			} finally {
				cursor.close();
			}
		}
		Collections.sort(songs,new Comparator<Song>()
				{

					@Override
					public int compare(Song songOne, Song songTwo) {
						
						return (songOne.getSongTitle()).compareTo(songTwo.getSongTitle());
					}
					
				});
		return songs;
	}

}
