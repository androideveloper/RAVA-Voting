package com.rava.voting;

import retrofit.RestAdapter;

import com.facebook.SessionDefaultAudience;
import com.rava.voting.api.ElectionService;
import com.rava.voting.api.LoginService;
import com.rava.voting.utils.SettingsManager;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.utils.Logger;

import android.app.Application;

public class RavaApplication extends Application {
	private static final String APP_ID = "254740274727830";
	private static final String APP_NAMESPACE = "ravavoting";

	private ElectionService electionService;
	private LoginService loginService;

	@Override
	public void onCreate() {
		super.onCreate();

		// set log to true
		Logger.DEBUG_WITH_STACKTRACE = true;

		// initialize facebook configuration
		Permission[] permissions = new Permission[] { Permission.BASIC_INFO,
				Permission.EMAIL, Permission.USER_GROUPS };

		SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
				.setAppId(APP_ID).setNamespace(APP_NAMESPACE)
				.setPermissions(permissions)
				.setDefaultAudience(SessionDefaultAudience.FRIENDS)
				.setAskForAllPermissionsAtOnce(false).build();

		SimpleFacebook.setConfiguration(configuration);

		SettingsManager.init(this);
	}

	public void initRestAdapter() {
		String webService = "http://" + SettingsManager.getServerName() + ":"
				+ SettingsManager.getServerPort() + "/RavaWebService/api";
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
				webService).build();
		electionService = restAdapter.create(ElectionService.class);
		loginService = restAdapter.create(LoginService.class);
	}

	public ElectionService getElectionService() {
		if (electionService == null)
			initRestAdapter();
		return electionService;
	}

	public LoginService getLoginService() {
		if (loginService == null)
			initRestAdapter();
		return loginService;
	}

}
