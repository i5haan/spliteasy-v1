package model;

import persistance.Entity;

public class GroupMembers implements Entity  {
	String userId;
	String gId;
	public String getuserId() {
		return userId;
	}
	public void setuserId(String userId) {
		this.userId = userId;
	}
	public String getgId() {
		return gId;
	}
	public void setgId(String gId) {
		this.gId = gId;
	}
	public String getEntity()
	{
		return "group_member";
	}
}
