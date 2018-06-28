package model;

public class SettleUp {
	private double amount;
	private int grpid;
	private String paidBy;
	private String paidTo;
	private int paidById;
	private int padiToId;
	public int getPaidById() {
		return paidById;
	}
	public void setPaidById(int paidById) {
		this.paidById = paidById;
	}
	public int getPadiToId() {
		return padiToId;
	}
	public void setPadiToId(int padiToId) {
		this.padiToId = padiToId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getGrpid() {
		return grpid;
	}
	public void setGrpid(int grpid) {
		this.grpid = grpid;
	}
	public String getPaidBy() {
		return paidBy;
	}
	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}
	public String getPaidTo() {
		return paidTo;
	}
	public void setPaidTo(String paidTo) {
		this.paidTo = paidTo;
	}
	
}
