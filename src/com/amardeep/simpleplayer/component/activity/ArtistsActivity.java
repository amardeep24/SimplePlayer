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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.amardeep.simpleplayer.R;
import com.amardeep.simpleplayer.component.activity.adapter.SongAdapter;
import com.amardeep.simpleplayer.component.service.SongPlayerService;
import com.amardeep.simpleplayer.component.service.SongPlayerService.ServiceBinder;
import com.amardeep.simpleplayer.constants.SimplePlayerConstants;
import com.amardeep.simpleplayer.service.FetchArtistsService;
import com.amardeep.simpleplayer.service.impl.FetchArtistsServiceImpl;
import com.anardeep.simpleplayer.model.Song;

public class ArtistsActivity extends Activity {
	private SongPlayerService songPlayerService;
	private boolean mBound = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_artists);
		Intent playSongIntent = new Intent(ArtistsActivity.this,
				SongPlayerService.class);
		bindService(playSongIntent, serviceConnection, Context.BIND_AUTO_CREATE);
		//setting up view 
		ListView songsByArtistsView=(ListView)findViewById(R.id.songs_by_artists);
		FetchArtistsService fetchSongsByArtist=new FetchArtistsServiceImpl();
		//get artist data from intent
		String[] artistId={getIntent().getStringExtra(SimplePlayerConstants.ARTIST_ID)};
		final List<Song> songs=fetchSongsByArtist.getAllSongsByArtist(ArtistsActivity.this, artistId);
		SongAdapter songAdapter=new SongAdapter(ArtistsActivity.this, R.layout.songs, songs);
		songsByArtistsView.setAdapter(songAdapter);
		songsByArtistsView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			
					if(songPlayerService!=null)
					{
						songPlayerService.setSongList(songs);
						if(mBound)
						{
							
							songPlayerService.playSong(position);
							Intent songControllerActivityIntent = new Intent(
									ArtistsActivity.this, SongControllerActivity.class);
							startActivity(songControllerActivityIntent);
						}
					}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.artists, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
		unbindService(serviceConnection);
	}

	@Override
	public void onBackPressed() {
		 Intent intent = new Intent(this, SongListActivity.class);
	        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	        startActivity(intent);
	}
	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i("ListAllSongsFragment", "Inside serviceConnection");
			ServiceBinder serviceBinder = (ServiceBinder) service;
			ArtistsActivity.this.songPlayerService = serviceBinder.getService();
			Log.i("ListAllSongsFragment", "service connected");
			mBound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mBound = false;

		}

	};
}
