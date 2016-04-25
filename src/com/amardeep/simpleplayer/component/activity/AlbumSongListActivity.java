package com.amardeep.simpleplayer.component.activity;

import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.amardeep.simpleplayer.R;
import com.amardeep.simpleplayer.component.activity.adapter.SongAdapter;
import com.amardeep.simpleplayer.component.service.SongPlayerService;
import com.amardeep.simpleplayer.component.service.SongPlayerService.ServiceBinder;
import com.amardeep.simpleplayer.service.FetchAlbumService;
import com.amardeep.simpleplayer.service.impl.FetchAlbumServiceImpl;
import com.anardeep.simpleplayer.model.Song;

public class AlbumSongListActivity extends Activity {
	private SongPlayerService songPlayerService;
	private boolean mBound = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.album_song_list_activity);
		// Binding to service
		Intent playSongIntent = new Intent(AlbumSongListActivity.this,
				SongPlayerService.class);
		bindService(playSongIntent, serviceConnection, Context.BIND_AUTO_CREATE);
		String[] albumName = { getIntent().getStringExtra("album_name") };
		ListView albumSongListView = (ListView) findViewById(R.id.album_music_list);
		FetchAlbumService fetchSongs = new FetchAlbumServiceImpl();
		final List<Song> songList = fetchSongs.getSongsByAlbumName(this, albumName);
		Log.i("AlbumSongListActivity","song list from album ------>"+songList);
		ArrayAdapter<Song> songAdapter = new SongAdapter(this, R.layout.songs,
				songList);
		albumSongListView.setAdapter(songAdapter);
		albumSongListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i("AlbumSongListActivity", "onItemClick received");
				LinearLayout songLayout = (LinearLayout) view;
				long songId=(long) songLayout.getTag();
				if (songPlayerService != null) {
					songPlayerService.setSongList(songList);
					if (mBound) {
						Intent startSongControllerActivityIntent = new Intent(
								AlbumSongListActivity.this,
								SongControllerActivity.class);
						startActivity(startSongControllerActivityIntent);
						songPlayerService.playSong(position);
					}
				}

			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			songPlayerService = ((ServiceBinder) service).getService();
			mBound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mBound = false;
		}

	};

}
