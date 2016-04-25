package com.amardeep.simpleplayer.component.fragment;

import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amardeep.simpleplayer.R;
import com.amardeep.simpleplayer.component.activity.SongControllerActivity;
import com.amardeep.simpleplayer.component.activity.SongListActivity;
import com.amardeep.simpleplayer.component.activity.adapter.SongAdapter;
import com.amardeep.simpleplayer.component.service.SongPlayerService;
import com.amardeep.simpleplayer.component.service.SongPlayerService.ServiceBinder;
import com.amardeep.simpleplayer.constants.SimplePlayerConstants;
import com.amardeep.simpleplayer.service.FetchSongService;
import com.amardeep.simpleplayer.service.impl.FetchSongServiceImpl;
import com.anardeep.simpleplayer.model.Song;

public class ListAllSongsFragment extends Fragment {
	private boolean musicBound = false;
	private Intent playSongIntent;
	private SongPlayerService songPlayerService;
	/* private List<Song> songs; */
	private boolean nowPlaying = false;
	private LinearLayout nowPlayingLayoutRoot;
	private LinearLayout nowPlayingLayout;
	private TextView nowPlayingTitleView;
	private TextView nowPlayingArtistView;
	private ImageView playPauseToggle;
	private ImageView nextSong;
	private ImageView previousSong;
	private SongListActivity songListActivity;
	private String currentSongTitle;
	private String currentSongArtist;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		songListActivity = (SongListActivity) getActivity();
		playSongIntent = new Intent(songListActivity, SongPlayerService.class);
		getActivity().bindService(playSongIntent, serviceConnection,
				Context.BIND_AUTO_CREATE);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_all_songs_fragment,
				container, false);
		nowPlayingLayoutRoot = (LinearLayout) view
				.findViewById(R.id.now_playing_root);
		nowPlayingLayoutRoot.setVisibility(View.GONE);
		nowPlayingLayout = (LinearLayout) view
				.findViewById(R.id.now_playing_layout);
		playPauseToggle = (ImageView) view.findViewById(R.id.play_pause_toggle);
		nextSong = (ImageView) view.findViewById(R.id.next_song);
		previousSong = (ImageView) view.findViewById(R.id.previous_song);
		nowPlayingTitleView = (TextView) nowPlayingLayout
				.findViewById(R.id.now_playing_title);
		nowPlayingArtistView = (TextView) nowPlayingLayout
				.findViewById(R.id.now_playing_artist);
		// setting text
		fetchCurrentSongData();
		nowPlayingTitleView.setText(currentSongTitle);
		nowPlayingArtistView.setText(currentSongArtist);
		FetchSongService fetchSongs = new FetchSongServiceImpl(getContext());
		final List<Song> songs = fetchSongs.getAllSongs();
		SongAdapter songAdapter = new SongAdapter(getActivity(),
				R.layout.songs, songs);
		ListView songView = (ListView) view.findViewById(R.id.music_list);
		songView.setAdapter(songAdapter);
		songView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LinearLayout songlayout = (LinearLayout) view;
				nowPlayingLayoutRoot.setVisibility(View.GONE);
				if (songPlayerService != null) {
					Log.i("SongListActivity", "onItemClick");
					// setting song list
					songPlayerService.setSongList(songs);
					if (musicBound) {
						// nowPlayingView.setBackgroundColor(0xffffffe6);
						Log.i("SongListActivity", "musicBound is true");
						songPlayerService.playSong(position);
						nowPlayingLayoutRoot.setVisibility(View.VISIBLE);
						if (((SongPlayerService) songPlayerService).isPng()) {
							playPauseToggle
									.setImageResource(R.drawable.ic_pause_black_24dp);
						} else {
							playPauseToggle
									.setImageResource(R.drawable.ic_play_arrow_black_24dp);
						}
						nowPlayingTitleView.setText(songPlayerService.getSong()
								.getSongTitle());
						nowPlayingArtistView.setText(songPlayerService
								.getSong().getSongArtist());
						Intent songControllerActivityIntent = new Intent(
								getActivity(), SongControllerActivity.class);
						startActivity(songControllerActivityIntent);

					}
				}

			}
		});

		nowPlayingLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent nowPlayingActivityIntent = new Intent(getActivity(),
						SongControllerActivity.class);
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
		return view;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStart() {
		super.onStart();
		if (songPlayerService != null) {
			if (nowPlayingTitleView != null && nowPlayingArtistView != null) {
				Log.i("SongListActivity",
						"onStart() nowPlayingTitleView!=null setText()");
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
	public void onResume() {
		super.onResume();
		if (songPlayerService != null) {
			if (nowPlayingTitleView != null && nowPlayingArtistView != null) {
				Log.i("SongListActivity",
						"onResume() nowPlayingTitleView!=null setText()");
				nowPlayingTitleView.setText(songPlayerService.getSong()
						.getSongTitle());
				nowPlayingArtistView.setText(songPlayerService.getSong()
						.getSongArtist());
			}
			if (((SongPlayerService) songPlayerService).isPng()) {
				nowPlayingLayoutRoot.setVisibility(View.VISIBLE);
				playPauseToggle
						.setImageResource(R.drawable.ic_pause_black_24dp);
			} else {
				playPauseToggle
						.setImageResource(R.drawable.ic_play_arrow_black_24dp);
			}
		}
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().unbindService(serviceConnection);
	}

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i("ListAllSongsFragment", "Inside serviceConnection");
			ServiceBinder serviceBinder = (ServiceBinder) service;
			ListAllSongsFragment.this.songPlayerService = serviceBinder
					.getService();
			Log.i("ListAllSongsFragment", "service connected");
			musicBound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			musicBound = false;

		}

	};

	public void fetchCurrentSongData() {
		SharedPreferences prefs = getContext().getSharedPreferences(
				SimplePlayerConstants.CURRENT_SONG_DATA,Context.MODE_PRIVATE);
		currentSongTitle=prefs.getString(SimplePlayerConstants.CURRENT_SONG_TITLE, "No title");
		currentSongArtist=prefs.getString(SimplePlayerConstants.CURRENT_SONG_ARTIST,"No artist");
	}
}
