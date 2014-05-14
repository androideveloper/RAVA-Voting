package com.rava.voting.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.rava.voting.R;
import com.rava.voting.ui.PreferencesActivity;

/**
 * Provides application settings
 */
public class SettingsManager {

	private static Context sContext;
	private static SharedPreferences sPreferences;

	private SettingsManager() {
	}

	public static void init(Context c) {
		sContext = c;
		PreferenceManager.setDefaultValues(c, R.xml.preferences_connection,
				true);

		sPreferences = PreferenceManager.getDefaultSharedPreferences(c);
	}

	public static String getServerName() {
		return sPreferences
				.getString(
						PreferencesActivity.PrefsConnectionFragment.KEY_EDITTEXT_SERVER_NAME,
						"").trim();
	}

	public static int getServerPort() {
		String temp = sPreferences
				.getString(
						PreferencesActivity.PrefsConnectionFragment.KEY_EDITTEXT_SERVER_PORT,
						"").trim();
		int port = 0;
		try {
			port = Integer.parseInt(temp);
		} catch (NumberFormatException e) {
		}
		return port;
	}
}
