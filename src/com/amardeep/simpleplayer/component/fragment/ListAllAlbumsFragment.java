package com.amardeep.simpleplayer.component.fragment;


import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.amardeep.simpleplayer.R;
import com.amardeep.simpleplayer.component.activity.AlbumSongListActivity;
import com.amardeep.simpleplayer.component.activity.adapter.AlbumAdapter;
import com.amardeep.simpleplayer.service.FetchAlbumService;
import com.amardeep.simpleplayer.service.impl.FetchAlbumServiceImpl;
import com.anardeep.simpleplayer.model.Album;
public class ListAllAlbumsFragment extends Fragment{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.list_all_albums_fragment, container, false);
		FetchAlbumService fetchAlbum=new FetchAlbumServiceImpl();
		List<Album> albumList=fetchAlbum.getAllAlbums(getActivity());
		ArrayAdapter<Album> albumAdapter=new AlbumAdapter(getActivity(),R.layout.albums,albumList);
		GridView albumView=(GridView)view.findViewById(R.id.album_list);
		albumView.setAdapter(albumAdapter);
		albumView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i("ListAllAlbumsFragment", "onItemClick received");
				FrameLayout albumLayout=(FrameLayout)view;
				TextView albumNameView=(TextView)albumLayout.findViewById(R.id.album_name);
				String albumName=(String)albumNameView.getText();
				Intent startAlbumSongListActivityintent=new Intent(getActivity(),AlbumSongListActivity.class);
				startAlbumSongListActivityintent.putExtra("album_name", albumName);
				startActivity(startAlbumSongListActivityintent);
			}
		});
		return  view;
	}

}
