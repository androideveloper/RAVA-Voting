package com.rava.voting.ui;

import java.util.List;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rava.voting.R;
import com.rava.voting.RavaApplication;
import com.rava.voting.utils.SystemInfo;

public class PreferencesActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.action_settings);
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.preference_headers, target);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// Overrided function. It is required for Android version 4.4(19) and higher
	protected boolean isValidFragment(String fragmentName) {
		if (PrefsConnectionFragment.class.getName().equals(fragmentName))
			return true;
		if (PrefsAboutFragment.class.getName().equals(fragmentName))
			return true;
		return false;
	}

	/**
	 * This fragment shows the preferences for the connection header.
	 */
	public static class PrefsConnectionFragment extends PreferenceFragment
			implements OnSharedPreferenceChangeListener {

		public static final String KEY_EDITTEXT_SERVER_NAME = "pref_edittext_server_name";
		public static final String KEY_EDITTEXT_SERVER_PORT = "pref_edittext_server_port";

		private EditTextPreference mEditTextPreferenceServerName;
		private EditTextPreference mEditTextPreferenceServerPort;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// Load the preferences from an XML resource
			addPreferencesFromResource(R.xml.preferences_connection);

			// Get a reference to the preferences
			mEditTextPreferenceServerName = (EditTextPreference) findPreference(KEY_EDITTEXT_SERVER_NAME);
			mEditTextPreferenceServerPort = (EditTextPreference) findPreference(KEY_EDITTEXT_SERVER_PORT);
		}

		@Override
		public void onResume() {
			super.onResume();
			// Set up a listener whenever a key changes
			getPreferenceScreen().getSharedPreferences()
					.registerOnSharedPreferenceChangeListener(this);
			updatePreference(KEY_EDITTEXT_SERVER_NAME);
			updatePreference(KEY_EDITTEXT_SERVER_PORT);
		}

		@Override
		public void onPause() {
			super.onPause();
			// Unregister the listener whenever a key changes
			getPreferenceScreen().getSharedPreferences()
					.unregisterOnSharedPreferenceChangeListener(this);
		}

		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			updatePreference(key);
			RavaApplication app = (RavaApplication) getActivity()
					.getApplication();
			app.initRestAdapter();
		}

		private void updatePreference(String key) {
			String value = "";
			if (key.equals(KEY_EDITTEXT_SERVER_NAME)) {
				value = mEditTextPreferenceServerName.getText();
				if (value != null && value.trim().length() > 0) {
					mEditTextPreferenceServerName.setSummary(value);
				} else {
					mEditTextPreferenceServerName
							.setSummary(R.string.please_input_server_name);
				}

			} else if (key.equals(KEY_EDITTEXT_SERVER_PORT)) {
				value = mEditTextPreferenceServerPort.getText();
				if (value != null && value.trim().length() > 0) {
					mEditTextPreferenceServerPort.setSummary(value);
				} else {
					mEditTextPreferenceServerPort
							.setSummary(R.string.please_input_server_port);
				}
			}
		}
	}

	/**
	 * This fragment shows the preferences for the about header.
	 */
	public static class PrefsAboutFragment extends PreferenceFragment implements
			OnSharedPreferenceChangeListener {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.fragment_about_page,
					container, false);
			return view;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			Context context = getActivity();
			TextView textViewVersion = (TextView) getView().findViewById(
					R.id.textview_program_version);

			String version = SystemInfo.getAppVersion(getActivity());

			textViewVersion.setText(String.format(context.getResources()
					.getString(R.string.program_version), version));
		}

		@Override
		public void onResume() {
			super.onResume();
		}

		@Override
		public void onPause() {
			super.onPause();
		}

		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			updatePreference(key);
		}

		private void updatePreference(String key) {
		}

	}
}
