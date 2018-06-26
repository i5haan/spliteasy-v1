package model;

import persistance.Entity;

public class SplitExpense implements Entity
{
	private int eid;
	private int userid;
	private double s_amt;
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public double getS_amt() {
		return s_amt;
	}
	public void setS_amt(double s_amt) {
		this.s_amt = s_amt;
	}
	public String getEntity()
	{
		return "split_expense";
	}
	
}
