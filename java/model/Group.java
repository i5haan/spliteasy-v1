package model;

import java.util.ArrayList;

import persistance.Entity;
import persistance.User;

public class Group {
	private int grpid;
	public ArrayList<Entity> getMember() {
		return member;
	}
	public void setMember(ArrayList<Entity> member) {
		this.member = member;
	}
	private String user;
	private String gname;
	private String created_at;
	private ArrayList<Entity> member;
	public int getGrpid() {
		return grpid;
	}
	public void setGrpid(int grpid) {
		this.grpid = grpid;
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

}
