package com.rava.voting.ui;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.rava.voting.RavaApplication;
import com.rava.voting.api.ElectionService;
import com.rava.voting.model.Election;
import com.rava.voting.model.User;
import com.rava.voting.ui.adapter.ElectionsArrayAdapter;

public class ElectionsFragment extends ListFragment {

	public static final String TAG = "ElectionsFragment";

	private ElectionsArrayAdapter mAdapter;

	public static ElectionsFragment newInstance() {
		ElectionsFragment fragment = new ElectionsFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// mElections = new ArrayList<Election>();
		// mElections.add(new Election(1, "Presidental Election", "",
		// ElectionState.ONE));
		// mElections.add(new Election(1, "Best soccer player", "",
		// ElectionState.ONE));
		// mElections.add(new Election(1, "Best tennis player", "",
		// ElectionState.ZERO));
		// mElections.add(new Election(1, "Test election", "",
		// ElectionState.TWO));

		load();
		mAdapter = new ElectionsArrayAdapter(getActivity(), 0,
				new ArrayList<Election>());
		setListAdapter(mAdapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Toast.makeText(getActivity(), mAdapter.getItem(position).getName(),
				Toast.LENGTH_SHORT).show();
	}

	private void load() {
		User user = ((MainActivity) getActivity()).getUser();
		int userId = 0;
		if (user != null) {
			userId = user.getId();
		}
		RavaApplication app = (RavaApplication) getActivity().getApplication();
		ElectionService electionService = app.getElectionService();
		electionService.getUserCreatedElections(userId,
				new Callback<List<Election>>() {

					@Override
					public void success(List<Election> elections,
							Response response) {
						Toast.makeText(getActivity(),
								"success " + elections.size(),
								Toast.LENGTH_SHORT).show();
						mAdapter.setElections(elections);
					}

					@Override
					public void failure(RetrofitError error) {
						Toast.makeText(getActivity(), "error",
								Toast.LENGTH_SHORT).show();
					}
				});
	}

}
