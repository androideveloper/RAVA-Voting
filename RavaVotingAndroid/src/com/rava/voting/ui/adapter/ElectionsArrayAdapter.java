package com.rava.voting.ui.adapter;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rava.voting.R;
import com.rava.voting.model.Election;

public class ElectionsArrayAdapter extends ArrayAdapter<Election> {

	private List<Election> mElections = Collections.emptyList();
	private LayoutInflater mInflater;

	public ElectionsArrayAdapter(Context context, int resource,
			List<Election> objects) {
		super(context, resource, objects);
		mElections = objects;
		mInflater = LayoutInflater.from(context);
	}

	public void setElections(List<Election> elections) {
		mElections = elections;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;

		if (convertView == null) {
			v = mInflater.inflate(R.layout.list_item_elections, parent, false);
			holder = new ViewHolder();
			holder.name = (TextView) v
					.findViewById(R.id.textview_elections_name);
			holder.desc = (TextView) v
					.findViewById(R.id.textview_elections_desc);
			holder.state = (TextView) v
					.findViewById(R.id.textview_elections_state);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		holder.name.setText(mElections.get(position).getName());
		holder.desc.setText(mElections.get(position).getDescription());
		holder.state
				.setText(mElections.get(position).getState().getStateName());

		return v;
	}

	@Override
	public int getCount() {
		return mElections.size();
	}

	@Override
	public Election getItem(int position) {
		return mElections.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		public TextView name;
		public TextView desc;
		public TextView state;
	}
}
