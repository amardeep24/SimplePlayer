package com.amardeep.simpleplayer.component.service;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import com.amardeep.simpleplayer.constants.SimplePlayerConstants;
import com.anardeep.simpleplayer.model.Song;

public class SongPlayerService extends Service {
	ArrayList<Song> songList;
	private MediaPlayer mediaPlayer;
	private IBinder mBinder;
	private int songPosn;

	private boolean repeatFlag = false;

	public boolean isRepeatFlag() {
		return repeatFlag;
	}

	public int getSongPosn() {
		return songPosn;
	}

	public void setRepeatFlag() {
		if (this.repeatFlag)
			this.repeatFlag = false;
		else
			this.repeatFlag = true;
	}

	public SongPlayerService() {
	}

	@Override
	public void onCreate() {
		this.mediaPlayer = new MediaPlayer();
		this.mBinder = new ServiceBinder();
		songPosn = 0;
		initMusicPlayer();
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.i("SongPlayerService", "mediaPlayer----> " + mediaPlayer);
		return mBinder;
		// throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.i("SongPlayerService", "Service unBound");
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
		return false;

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mediaPlayer.release();
	}

	public void setSongList(List<Song> songList) {
		this.songList = (ArrayList<Song>) songList;
	}

	public class ServiceBinder extends Binder {
		public SongPlayerService getService() {
			return SongPlayerService.this;
		}
	}

	/**
	 * This method initializes the media player object
	 */
	public void initMusicPlayer() {
		if (mediaPlayer != null) {
			this.mediaPlayer.setWakeMode(getApplicationContext(),
					PowerManager.PARTIAL_WAKE_LOCK);
			this.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			// OnPreparedListener
			mediaPlayer.setOnPreparedListener(new OnPreparedListener() {

				@Override
				public void onPrepared(MediaPlayer mp) {
					mp.start();
				}
			});
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub

				}
			});
			mediaPlayer.setOnErrorListener(new OnErrorListener() {

				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					// TODO Auto-generated method stub
					return false;
				}
			});
		}
	}

	/*
	 * public void playSong(long songId) { if (mediaPlayer != null) {
	 * mediaPlayer.reset(); Uri trackUri = ContentUris .withAppendedId(
	 * android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songId);
	 * try { mediaPlayer.setDataSource(getApplicationContext(), trackUri); }
	 * catch (Exception e) { Log.e("SongPlayerService",
	 * "Error setting data source", e); } mediaPlayer.prepareAsync(); } }
	 */
	public void playSong(int position) {
		if (mediaPlayer != null) {
			mediaPlayer.reset();
			this.songPosn = position;
			Log.i("SongPlayerService", "position ----> " + this.songPosn);
			Log.i("SongPlayerService", "song list ----> " + this.songList);
			Uri trackUri = ContentUris
					.withAppendedId(
							android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
							this.songList.get(position).getId());
			try {
				mediaPlayer.setDataSource(getApplicationContext(), trackUri);
				saveCurrentSongData();
			} catch (Exception e) {
				Log.e("SongPlayerService", "Error setting data source", e);
			}
			mediaPlayer.prepareAsync();
		}
	}

	public void stopPlay() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();

		}

	}

	public int getPosn() {
		return mediaPlayer.getCurrentPosition();
	}

	public int getDur() {
		return mediaPlayer.getDuration();
	}

	public boolean isPng() {
		return mediaPlayer.isPlaying();
	}

	public void pausePlayer() {
		mediaPlayer.pause();
	}

	public void seek(int posn) {
		mediaPlayer.seekTo(posn);
	}

	public void go() {
		mediaPlayer.start();
	}

	public void playPrev() {
		songPosn--;
		if (songPosn < 0)
			songPosn = songList.size() - 1;
		playSong(songPosn);
	}

	public void playNext() {
		songPosn++;
		if (songPosn >= songList.size())
			songPosn = 0;
		playSong(songPosn);
	}

	public Song getSong() {
		if (songList != null) {
			return songList.get(songPosn);
		}
		return null;
	}

	public Song getSong(int position) {
		if (songList != null) {
			return songList.get(position);
		}
		return null;
	}

	public void repeat() {
		mediaPlayer.setLooping(repeatFlag);
	}

	public void saveCurrentSongData() {
		SharedPreferences pref = getApplicationContext().getSharedPreferences(
				SimplePlayerConstants.CURRENT_SONG_DATA, MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putString(SimplePlayerConstants.CURRENT_SONG_TITLE, getSong().getSongTitle());
		editor.putString(SimplePlayerConstants.CURRENT_SONG_ARTIST,getSong().getSongArtist());
		editor.apply();
	}
}
