package com.amardeep.simpleplayer.component.activity.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amardeep.simpleplayer.R;
import com.anardeep.simpleplayer.model.Song;

public class SongAdapter extends ArrayAdapter<Song> {
	private List<Song> songs;
	private LayoutInflater songViewLayoutInflater;
	private int layoutId;

	public SongAdapter(Context c, int layoutId,List<Song> songs) {
		super(c, layoutId, songs);
		this.songs =songs;
		this.layoutId=layoutId;
		this.songViewLayoutInflater = ((Activity)c).getLayoutInflater();
	}

	@Override
	public int getCount() {
		return this.songs.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout songLayout=(LinearLayout)songViewLayoutInflater.inflate(this.layoutId,parent,false);
		TextView songTitle=(TextView)songLayout.findViewById(R.id.song_title);
		TextView songArtist=(TextView)songLayout.findViewById(R.id.song_artist);
		songArtist.setText(this.songs.get(position).getSongArtist());
		songTitle.setText(this.songs.get(position).getSongTitle());
		songLayout.setTag(this.songs.get(position).getId());
		return songLayout;
	}

}
