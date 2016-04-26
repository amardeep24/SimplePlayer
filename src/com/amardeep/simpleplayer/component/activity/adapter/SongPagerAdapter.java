package com.amardeep.simpleplayer.component.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.amardeep.simpleplayer.component.fragment.ListAllAlbumsFragment;
import com.amardeep.simpleplayer.component.fragment.ListAllArtistsFragment;
import com.amardeep.simpleplayer.component.fragment.ListAllSongsFragment;

public class SongPagerAdapter extends FragmentStatePagerAdapter {

	private static final int NUM_PAGES = 3;

	public SongPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {

		ListAllSongsFragment ListAllSongsTab = new ListAllSongsFragment();
		ListAllAlbumsFragment ListAllAlbumsTab = new ListAllAlbumsFragment();
		ListAllArtistsFragment listAllArtistsTab=new ListAllArtistsFragment();
		switch (position) {
		case 0:
			return ListAllSongsTab;
		case 1:
			return ListAllAlbumsTab;
		case 2:
			return listAllArtistsTab;
		default:
			return ListAllSongsTab;
		}
	}

	@Override
	public int getCount() {
		return NUM_PAGES;
	}

	@Override
	public CharSequence getPageTitle(int position) {

		if (position == 0) {
			return "All songs";
		}
		if (position == 1) {
			return "Albums";
		}
		if (position == 2) {
			return "Artists";
		}
		return "All songs";
	}
}
