package com.amardeep.simpleplayer.component.activity.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amardeep.simpleplayer.R;
import com.anardeep.simpleplayer.model.Artist;

public class ArtistAdapter extends ArrayAdapter<Artist> {

	private Activity context;
	private int resource;
	List<Artist> artists;

	public ArtistAdapter(Context context, int resource, List<Artist> artists) {
		super(context, resource, artists);
		this.artists = artists;
		this.context = (Activity) context;
		this.resource = resource;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout artistLayout = (LinearLayout) context.getLayoutInflater()
				.inflate(resource, parent,false);
		TextView artistName=(TextView)artistLayout.findViewById(R.id.artist_name);
		artistName.setText(artists.get(position).getArtistName());
		artistName.setTextSize(20);
		artistName.setTypeface(null, Typeface.BOLD);
		artistLayout.setTag(artists.get(position).getArtistId());
		return artistLayout;
	}

}
