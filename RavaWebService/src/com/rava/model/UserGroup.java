package com.rava.model;

public class UserGroup {

	private String groupId;
	private int userId;
	private String name;

	public UserGroup() {
	}

	public UserGroup(String groupId, int userId, String name) {
		super();
		this.groupId = groupId;
		this.userId = userId;
		this.name = name;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
