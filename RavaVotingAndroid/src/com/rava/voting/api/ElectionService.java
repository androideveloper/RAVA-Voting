package com.rava.voting.api;

import java.util.List;

import com.rava.voting.model.Election;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface ElectionService {

	@GET("/elections")
	void getElections(Callback<List<Election>> callback);

	@GET("/elections/creator/{userid}")
	void getUserCreatedElections(@Path("userid") int id,
			Callback<List<Election>> callback);

	@GET("/elections/user/{userid}")
	void getUserOpenElections(@Path("userid") int id,
			Callback<List<Election>> callback);

	@GET("/elections/uservoted/{userid}")
	void getUserVotedElections(@Path("userid") int id,
			Callback<List<Election>> callback);

	@GET("/elections/{id}")
	void getElection(@Path("id") int id, Callback<Election> callback);

}
