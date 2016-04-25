package com.amardeep.simpleplayer.component.activity.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.amardeep.simpleplayer.R;
import com.anardeep.simpleplayer.model.Album;


public class AlbumAdapter extends  ArrayAdapter<Album> {

	private List<Album> albums;
	private int layoutId;
	private LayoutInflater layoutinflater;
	public AlbumAdapter(Context context, int resource, List<Album> albums) {
		super(context, resource, albums);
		this.albums=albums;
		this.layoutId=resource;
		this.layoutinflater=((Activity)context).getLayoutInflater();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		FrameLayout albumLayout=(FrameLayout)layoutinflater.inflate(this.layoutId,parent,false);
		TextView albumName=(TextView)albumLayout.findViewById(R.id.album_name);
		albumName.setText(albums.get(position).getAlbumName());
		return albumLayout;
	}

	
}
