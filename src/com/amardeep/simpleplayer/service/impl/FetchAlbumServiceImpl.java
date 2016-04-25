package com.amardeep.simpleplayer.service.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.amardeep.simpleplayer.service.FetchAlbumService;
import com.anardeep.simpleplayer.model.Album;
import com.anardeep.simpleplayer.model.Song;

public class FetchAlbumServiceImpl implements FetchAlbumService {

	@Override
	public List<Album> getAllAlbums(Context context) {
		List<Album> albumList = new ArrayList<>();
		ContentResolver contentResolver = context.getContentResolver();
		String[] columns = { android.provider.MediaStore.Audio.Albums._ID,
				android.provider.MediaStore.Audio.Albums.ALBUM };
		Cursor cursor = null;
		try {
			cursor = contentResolver.query(
					MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, columns,
					null, null, android.provider.MediaStore.Audio.Albums.ALBUM );
			while (cursor.moveToNext()) {
				Album album = new Album();
				album.setId(cursor.getLong(cursor
						.getColumnIndex(android.provider.MediaStore.Audio.Albums._ID)));
				album.setAlbumName(cursor.getString(cursor
						.getColumnIndex(android.provider.MediaStore.Audio.Albums.ALBUM)));
				albumList.add(album);
			}
		} finally {
			cursor.close();
		}
		return albumList;
	}

	@Override
	public List<Song> getSongsByAlbumName(Context context, String[] albumName) {
		List<Song> songlistbyAlbumName = new ArrayList<>();
		ContentResolver contentResolver = context.getContentResolver();
		String[] columns = { android.provider.MediaStore.Audio.Media._ID,
				android.provider.MediaStore.Audio.Media.TITLE,
				android.provider.MediaStore.Audio.Media.ARTIST, };
		Cursor cursor = null;
		String where = android.provider.MediaStore.Audio.Media.ALBUM + "=?";
		try {
			cursor = contentResolver
					.query(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
							columns, where, albumName,
							android.provider.MediaStore.Audio.Media.TITLE);
			while (cursor.moveToNext()) {
				long id = cursor
						.getLong(cursor
								.getColumnIndex(android.provider.MediaStore.Audio.Media._ID));
				String title = cursor
						.getString(cursor
								.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE));
				String artist = cursor
						.getString(cursor
								.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST));
				Song song = new Song(id, title, artist);
				songlistbyAlbumName.add(song);

			}
		} finally {
			cursor.close();
		}
		return songlistbyAlbumName;
	}
}
