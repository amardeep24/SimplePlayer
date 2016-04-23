package com.amardeep.simpleplayer.component.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.TextView;

import com.amardeep.simpleplayer.R;
import com.amardeep.simpleplayer.component.activity.widget.MusicController;
import com.amardeep.simpleplayer.component.service.SongPlayerService;
import com.amardeep.simpleplayer.component.service.SongPlayerService.ServiceBinder;

public class SongControllerActivity extends Activity implements
		MediaPlayerControl {

	private MusicController controller;
	private SongPlayerService songPlayerService;
	private boolean mBound = false;
	private TextView songTitleView;
	private TextView songArtistView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_song_controller_activity);
		songTitleView = (TextView) findViewById(R.id.song_title);
		songArtistView = (TextView) findViewById(R.id.song_artist);
		LinearLayout root = (LinearLayout) findViewById(R.id.song_view);

		ViewTreeObserver vto = root.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				// Call your controller set-up now that the layout is loaded
				setController();
			}
		});

	}

	@Override
	protected void onStart() {
		super.onStart();
		Intent playSongIntent = new Intent(SongControllerActivity.this,
				SongPlayerService.class);
		bindService(playSongIntent, serviceConnection, Context.BIND_AUTO_CREATE);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(serviceConnection);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.song_controller_activity, menu);
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


	private void setController() {
		controller = new MusicController(this);
		controller.setMediaPlayer(this);
		controller.setAnchorView(findViewById(R.id.song_view));
		controller.setEnabled(true);
		controller.setPrevNextListeners(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playNext();

			}
		}, new OnClickListener() {

			@Override
			public void onClick(View v) {
				playPrevious();
			}
		});
		controller.show(0);

	}

	ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			songPlayerService = ((ServiceBinder) service).getService();
			songTitleView.setText("Title : "
					+ songPlayerService.getSong().getSongTitle());
			songArtistView.setText("Artist : "
					+ songPlayerService.getSong().getSongArtist());
			mBound = true;

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mBound = false;

		}

	};

	@Override
	public void start() {
		if (songPlayerService != null)
			songPlayerService.go();

	}

	@Override
	public void pause() {
		if (songPlayerService != null)
			songPlayerService.pausePlayer();

	}

	@Override
	public int getDuration() {
		if (songPlayerService != null && mBound && songPlayerService.isPng())
			return songPlayerService.getDur();
		else
			return 0;
	}

	@Override
	public int getCurrentPosition() {
		if (songPlayerService != null && mBound && songPlayerService.isPng())
			return songPlayerService.getPosn();
		else
			return 0;
	}

	@Override
	public void seekTo(int pos) {
		if (songPlayerService != null)
			songPlayerService.seek(pos);

	}

	@Override
	public boolean isPlaying() {
		if (songPlayerService != null && mBound)
			return songPlayerService.isPng();
		return false;
	}

	@Override
	public int getBufferPercentage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canPause() {
		return true;
	}

	@Override
	public boolean canSeekBackward() {
		return true;
	}

	@Override
	public boolean canSeekForward() {
		return true;
	}

	@Override
	public int getAudioSessionId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void playNext() {
		if (songPlayerService != null) {
			songPlayerService.playNext();
			songTitleView.setText("Title : "
					+ songPlayerService.getSong().getSongTitle());
			songArtistView.setText("Artist : "
					+ songPlayerService.getSong().getSongArtist());
			controller.show(0);
		}
	}

	public void playPrevious() {
		if (songPlayerService != null) {
			songPlayerService.playPrev();
			songTitleView.setText("Title : "
					+ songPlayerService.getSong().getSongTitle());
			songArtistView.setText("Artist : "
					+ songPlayerService.getSong().getSongArtist());
			controller.show(0);
		}
	}
}
