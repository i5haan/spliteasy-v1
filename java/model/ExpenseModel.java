package model;

import java.util.ArrayList;

import persistance.Entity;

public class ExpenseModel {
	private String ename;
	private String created_at;
	private double amount;
	private ArrayList<Entity> split;
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
	public ArrayList<Entity> getSplit() {
		return split;
	}
	public void setSplit(ArrayList<Entity> split) {
		this.split = split;
	}
	

}
