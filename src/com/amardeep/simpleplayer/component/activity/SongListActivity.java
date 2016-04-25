package com.amardeep.simpleplayer.component.activity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.amardeep.simpleplayer.R;
import com.amardeep.simpleplayer.component.activity.adapter.SongPagerAdapter;

public class SongListActivity extends FragmentActivity {
	private Menu menu;
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i("SongListActivity","Inside onCreate()");
		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new SongPagerAdapter(
				getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i("SongListActivity","Inside onStart()");
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onPause() {
		super.onPause();
		// nowPlayingLayoutRoot.setVisibility(View.VISIBLE);
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
	}
	 @Override
	    public void onBackPressed() {
	        if (mPager.getCurrentItem() == 0) {
	            // If the user is currently looking at the first step, allow the system to handle the
	            // Back button. This calls finish() on this activity and pops the back stack.
	            super.onBackPressed();
	        } else {
	            // Otherwise, select the previous step.
	            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
	        }
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
		
		}
		return super.onOptionsItemSelected(item);
	}


	
	


}
