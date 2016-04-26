package com.amardeep.simpleplayer.component.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.audiofx.Equalizer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.amardeep.simpleplayer.R;
import com.amardeep.simpleplayer.component.service.SongPlayerService;
import com.amardeep.simpleplayer.component.service.SongPlayerService.ServiceBinder;

public class EqualizerActivity extends Activity {

	private SongPlayerService songPlayerService;
	private boolean mBound=false;
	private Equalizer mEqualizer;
	short equalizerBands;
	short lowerEqualizerBandLevel;
	short upperEqualizerBandLevel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equalizer);
		//bind to music service
		Intent playSongIntent = new Intent(EqualizerActivity.this,
				SongPlayerService.class);
		bindService(playSongIntent, serviceConnection, Context.BIND_AUTO_CREATE);
		LinearLayout equalizerLayout=(LinearLayout)findViewById(R.id.equalizer_layout);
		
		setUpVisualizerUI();
		setUpEqualizerUI();
	}

	private void setUpVisualizerUI() {
		// TODO Auto-generated method stub
		
	}

	private void setUpEqualizerUI() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(serviceConnection);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.equalizer, menu);
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
	
	ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			songPlayerService = ((ServiceBinder) service).getService();
			mEqualizer=songPlayerService.getEqualizer();
			mBound = true;
			setUpEqualizer();

		}

		private void setUpEqualizer() {
			if(mEqualizer!=null){
			equalizerBands=mEqualizer.getNumberOfBands();
			lowerEqualizerBandLevel=mEqualizer.getBandLevelRange()[0];
			upperEqualizerBandLevel=mEqualizer.getBandLevelRange()[1];
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mBound = false;

		}

	};
}
