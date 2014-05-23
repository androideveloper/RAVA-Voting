package com.rava.voting.model;

public enum ElectionState {
	ZERO("Not Opened"), ONE("Opened"), TWO("Closed"), THREE("Counted");

	private final String stateName;

	private ElectionState(String stateName) {
		this.stateName = stateName;
	}

	public String getStateName() {
		return stateName;
	}

	public static ElectionState getStateById(int id) {
		return ElectionState.values()[id];
	}

}
