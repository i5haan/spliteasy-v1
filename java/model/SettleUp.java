package model;

public class SettleUp {
	private double amount;
	private int grpid;
	private int paidBy;
	private int paidTo;
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
	public int getPaidBy() {
		return paidBy;
	}
	public void setPaidBy(int paidBy) {
		this.paidBy = paidBy;
	}
	public int getPaidTo() {
		return paidTo;
	}
	public void setPaidTo(int paidTo) {
		this.paidTo = paidTo;
	}
}
