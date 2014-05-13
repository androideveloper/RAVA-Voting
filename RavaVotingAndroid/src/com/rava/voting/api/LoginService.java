package com.rava.voting.api;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

import com.rava.voting.model.User;

public interface LoginService {

	@FormUrlEncoded
	@POST("/login")
	void login(@Field("fbtoken") String token, Callback<User> callback);
}
