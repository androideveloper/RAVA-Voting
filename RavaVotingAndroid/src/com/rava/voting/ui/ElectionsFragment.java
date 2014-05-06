package com.rava.voting.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.rava.voting.model.Election;
import com.rava.voting.model.ElectionState;
import com.rava.voting.ui.adapter.ElectionsArrayAdapter;

public class ElectionsFragment extends ListFragment {

	public static final String TAG = "ElectionsFragment";

	private ElectionsArrayAdapter mAdapter;
	private List<Election> mElections;

	public static ElectionsFragment newInstance() {
		ElectionsFragment fragment = new ElectionsFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		//
		mElections = new ArrayList<Election>();
		mElections.add(new Election(1, "Presidental Election", "",
				ElectionState.ONE));
		mElections.add(new Election(1, "Best soccer player", "",
				ElectionState.ONE));
		mElections.add(new Election(1, "Best tennis player", "",
				ElectionState.ZERO));
		mElections.add(new Election(1, "Test election", "", ElectionState.TWO));

		mAdapter = new ElectionsArrayAdapter(getActivity(), 0, mElections);
		setListAdapter(mAdapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Toast.makeText(getActivity(), mElections.get(position).getName(),
				Toast.LENGTH_SHORT).show();
	}

}
