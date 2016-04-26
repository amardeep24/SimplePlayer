package com.amardeep.simpleplayer.component.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.amardeep.simpleplayer.R;
import com.amardeep.simpleplayer.component.activity.ArtistsActivity;
import com.amardeep.simpleplayer.component.activity.adapter.ArtistAdapter;
import com.amardeep.simpleplayer.constants.SimplePlayerConstants;
import com.amardeep.simpleplayer.service.FetchArtistsService;
import com.amardeep.simpleplayer.service.impl.FetchArtistsServiceImpl;
import com.anardeep.simpleplayer.model.Artist;

public class ListAllArtistsFragment extends Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_all_artists_fragment,
				container, false);
		ListView artistList = (ListView) view
				.findViewById(R.id.all_artist_list);
		FetchArtistsService fetchArtists = new FetchArtistsServiceImpl();
		final List<Artist> artistsList = fetchArtists.getAllArtists(getActivity());
		ArtistAdapter artistAdapter = new ArtistAdapter(getActivity(),
				R.layout.artists, artistsList);
		artistList.setAdapter(artistAdapter);
		artistList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent artistsActivityIntent = new Intent(
						ListAllArtistsFragment.this.getActivity(),
						ArtistsActivity.class);
				artistsActivityIntent.putExtra(SimplePlayerConstants.ARTIST_ID,artistsList.get(position).getArtistId());
				startActivity(artistsActivityIntent);
			}
		});
		return view;
	}
}
