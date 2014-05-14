package com.rava.voting.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rava.voting.R;
import com.rava.voting.model.User;
import com.rava.voting.ui.PreferencesActivity;

/**
 * Provides application settings
 */
public class SettingsManager {

	private static Context sContext;
	private static SharedPreferences sPreferences;

	private static final String KEY_USER = "key_user";

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

	public static void saveUser(User user) {
		Gson gson = new GsonBuilder().create();
		String userJson = gson.toJson(user);
		Log.i("user to prefs", userJson);
		SharedPreferences.Editor editor = sPreferences.edit();
		editor.putString(KEY_USER, userJson);
		editor.commit();
	}

	public static void clearUser() {
		SharedPreferences.Editor editor = sPreferences.edit();
		editor.remove(KEY_USER);
		editor.commit();
	}

	public static User getUser() {
		Gson gson = new GsonBuilder().create();
		String userJson = sPreferences.getString(KEY_USER, "");
		Log.i("user from prefs", userJson);
		if (userJson.isEmpty())
			return null;
		User user = gson.fromJson(userJson, User.class);
		return user;
	}
}
