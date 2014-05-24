package com.rau.evoting.models;

public enum ElectionState {
	ZERO("Not Opened"), ONE("Opened"), TWO("Mix Net"), THREE("Decode"), FOUR("Counted");
	
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
