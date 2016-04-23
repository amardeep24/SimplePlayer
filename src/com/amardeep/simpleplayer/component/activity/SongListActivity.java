package com.amardeep.simpleplayer.component.activity;

import java.util.List;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amardeep.simpleplayer.R;
import com.amardeep.simpleplayer.component.activity.adapter.SongAdapter;
import com.amardeep.simpleplayer.component.service.SongPlayerService;
import com.amardeep.simpleplayer.component.service.SongPlayerService.ServiceBinder;
import com.amardeep.simpleplayer.service.FetchSongService;
import com.amardeep.simpleplayer.service.impl.FetchSongServiceImpl;
import com.anardeep.simpleplayer.model.Song;

public class SongListActivity extends Activity {
	private boolean musicBound = false;
	private Intent playSongIntent;
	private Service songPlayerService;
	private List<Song> songs;
	private boolean nowPlaying = false;
	private Menu menu;
	private LinearLayout nowPlayingLayoutRoot;
	private LinearLayout nowPlayingLayout;
	private TextView nowPlayingTitleView;
	private TextView nowPlayingArtistView;
	private ImageView playPauseToggle;
	private ImageView nextSong;
	private ImageView previousSong;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		nowPlayingLayoutRoot = (LinearLayout) findViewById(R.id.now_playing_root);
		nowPlayingLayout = (LinearLayout) findViewById(R.id.now_playing_layout);
		playPauseToggle = (ImageView) findViewById(R.id.play_pause_toggle);
		nextSong = (ImageView) findViewById(R.id.next_song);
		previousSong = (ImageView) findViewById(R.id.previous_song);
		nowPlayingLayoutRoot.setVisibility(View.GONE);
		// nowPlayingLayout.setVisibility(View.GONE);
		FetchSongService fetchSongs = new FetchSongServiceImpl(this);
		songs = fetchSongs.getAllSongs();
		SongAdapter songAdapter = new SongAdapter(this, R.layout.songs, songs);
		ListView songView = (ListView) findViewById(R.id.music_list);
		songView.setAdapter(songAdapter);
		songView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LinearLayout songlayout = (LinearLayout) view;
				// long songId = (Long) songlayout.getTag();
				nowPlayingLayoutRoot.setVisibility(View.GONE);
				if (songPlayerService != null) {
					Log.i("SongListActivity", "onItemClick");
					// musicBound=bindService(playSongIntent, serviceConnection,
					// Context.BIND_AUTO_CREATE);

					if (musicBound) {
						nowPlayingTitleView = (TextView) nowPlayingLayout
								.findViewById(R.id.now_playing_title);
						nowPlayingArtistView = (TextView) nowPlayingLayout
								.findViewById(R.id.now_playing_artist);
						// nowPlayingView.setBackgroundColor(0xffffffe6);
						Log.i("SongListActivity", "musicBound is true");
						((SongPlayerService) songPlayerService)
								.playSong(position);
						if (((SongPlayerService) songPlayerService).isPng()) {
							playPauseToggle
									.setImageResource(R.drawable.ic_pause_black_24dp);
						} else {
							playPauseToggle
									.setImageResource(R.drawable.ic_play_arrow_black_24dp);
						}
						nowPlayingTitleView
								.setText(((SongPlayerService) songPlayerService)
										.getSong().getSongTitle());
						nowPlayingArtistView
								.setText(((SongPlayerService) songPlayerService)
										.getSong().getSongArtist());
						Intent songControllerActivityIntent = new Intent(
								SongListActivity.this,
								SongControllerActivity.class);
						startActivity(songControllerActivityIntent);

					}
				}

			}
		});

		nowPlayingLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent nowPlayingActivityIntent = new Intent(
						SongListActivity.this, SongControllerActivity.class);
				nowPlayingActivityIntent
						.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(nowPlayingActivityIntent);
			}
		});
		playPauseToggle.setOnClickListener(new OnClickListener() {
			int togglePos = 0;

			@Override
			public void onClick(View v) {
				if (togglePos == 0) {
					if (songPlayerService != null) {
						if (musicBound) {
							if (((SongPlayerService) songPlayerService).isPng()) {
								((SongPlayerService) songPlayerService)
										.pausePlayer();
								playPauseToggle
								.setImageResource(R.drawable.ic_play_arrow_black_24dp);
							}

						}
					}
					togglePos = 1;
				} else if (togglePos == 1) {
					
					if (songPlayerService != null) {
						if (musicBound) {
							if (!((SongPlayerService) songPlayerService)
									.isPng()) {
								((SongPlayerService) songPlayerService).go();
								playPauseToggle
								.setImageResource(R.drawable.ic_pause_black_24dp);

							}

						}
					}
					togglePos = 0;
				}

			}
		});
		nextSong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (songPlayerService != null) {
					if (musicBound) {
						if (((SongPlayerService) songPlayerService).isPng()) {
							((SongPlayerService) songPlayerService).playNext();
							if (nowPlayingTitleView != null
									&& nowPlayingArtistView != null) {
								nowPlayingTitleView
										.setText(((SongPlayerService) songPlayerService)
												.getSong().getSongTitle());
								nowPlayingArtistView
										.setText(((SongPlayerService) songPlayerService)
												.getSong().getSongArtist());
							}

						}

					}
				}
			}
		});
		previousSong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (songPlayerService != null) {
					if (musicBound) {
						if (((SongPlayerService) songPlayerService).isPng()) {
							((SongPlayerService) songPlayerService).playPrev();
							if (nowPlayingTitleView != null
									&& nowPlayingArtistView != null) {
								nowPlayingTitleView
										.setText(((SongPlayerService) songPlayerService)
												.getSong().getSongTitle());
								nowPlayingArtistView
										.setText(((SongPlayerService) songPlayerService)
												.getSong().getSongArtist());
							}

						}

					}
				}
			}
		});

	}

	@Override
	protected void onStart() {
		super.onStart();
		playSongIntent = new Intent(SongListActivity.this,
				SongPlayerService.class);
		bindService(playSongIntent, serviceConnection, Context.BIND_AUTO_CREATE);
		if (songPlayerService != null) {
			if (nowPlayingTitleView != null && nowPlayingArtistView != null) {
				nowPlayingTitleView
						.setText(((SongPlayerService) songPlayerService)
								.getSong().getSongTitle());
				nowPlayingArtistView
						.setText(((SongPlayerService) songPlayerService)
								.getSong().getSongArtist());
			}
			if (((SongPlayerService) songPlayerService).isPng()) {
				playPauseToggle
						.setImageResource(R.drawable.ic_pause_black_24dp);
			} else {
				playPauseToggle
						.setImageResource(R.drawable.ic_play_arrow_black_24dp);
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (songPlayerService != null) {
			if (nowPlayingTitleView != null && nowPlayingArtistView != null) {
				nowPlayingTitleView
						.setText(((SongPlayerService) songPlayerService)
								.getSong().getSongTitle());
				nowPlayingArtistView
						.setText(((SongPlayerService) songPlayerService)
								.getSong().getSongArtist());
			}
			if (((SongPlayerService) songPlayerService).isPng()) {
				playPauseToggle
						.setImageResource(R.drawable.ic_pause_black_24dp);
			} else {
				playPauseToggle
						.setImageResource(R.drawable.ic_play_arrow_black_24dp);
			}
		}

	}


	@Override
	protected void onPause() {
		super.onPause();
		//nowPlayingLayoutRoot.setVisibility(View.VISIBLE);
		/*
		 * if (nowPlayingTitleView != null && nowPlayingArtistView != null) {
		 * nowPlayingTitleView.setText(((SongPlayerService) songPlayerService)
		 * .getSong().getSongTitle()); nowPlayingArtistView
		 * .setText(((SongPlayerService) songPlayerService).getSong()
		 * .getSongArtist()); }
		 */
	}

	@Override
	protected void onStop() {
		super.onStop();
		nowPlayingLayoutRoot.setVisibility(View.VISIBLE);
		/*
		 * if (nowPlayingTitleView != null && nowPlayingArtistView != null) {
		 * nowPlayingTitleView.setText(((SongPlayerService) songPlayerService)
		 * .getSong().getSongTitle()); nowPlayingArtistView
		 * .setText(((SongPlayerService) songPlayerService).getSong()
		 * .getSongArtist()); }
		 */
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(serviceConnection);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		this.menu = menu;
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		/*
		 * if (id == R.id.action_settings) { return true; }
		 */
		switch (id) {
		case R.id.action_settings: {
			break;
		}
		case R.id.action_stop: {
			// unbindService(serviceConnection);
			// stopService(playSongIntent);
			// musicBound=false;
			if (songPlayerService != null) {
				((SongPlayerService) songPlayerService).stopPlay();
			}
			// songPlayerService = null;
			break;
		}
		case R.id.action_repeat: {
			// unbindService(serviceConnection);
			// stopService(playSongIntent);
			// musicBound=false;
			if (songPlayerService != null) {
				((SongPlayerService) songPlayerService).setRepeatFlag();
				((SongPlayerService) songPlayerService).repeat();
				if (((SongPlayerService) songPlayerService).isRepeatFlag())
					menu.getItem(2).setIcon(
							getResources().getDrawable(
									R.drawable.ic_repeat_one_white_24dp));
				else
					menu.getItem(2).setIcon(
							getResources().getDrawable(
									R.drawable.ic_repeat_white_24dp));
			}
			// songPlayerService = null;
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			ServiceBinder serviceBinder = (ServiceBinder) service;
			SongListActivity.this.songPlayerService = serviceBinder
					.getService();
			((SongPlayerService) SongListActivity.this.songPlayerService)
					.setSongList(songs);
			musicBound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			musicBound = false;

		}

	};
}
