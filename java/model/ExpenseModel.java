package model;

import java.util.ArrayList;

import persistance.Entity;

public class ExpenseModel {
	private String ename;
	private String created_at;
	private double amount;
	private String paidBy;
	private int eid;
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	public String getPaidBy() {
		return paidBy;
	}
	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}
	private ArrayList<SplitExpenseModel> split;
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public ArrayList<SplitExpenseModel> getSplit() {
		return split;
	}
	public void setSplit(ArrayList<SplitExpenseModel> split) {
		this.split = split;
	}
	

}
