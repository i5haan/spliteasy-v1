package persistance;

public class Expense implements Entity
{
	private int eid;
	private int grpid;
	private String ename;
	private String created_at;
	private int paid_by;
	private double amount;
	
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	public int getGrpid() {
		return grpid;
	}
	public void setGrpid(int grpid) {
		this.grpid = grpid;
	}
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
	public int getPaid_by() {
		return paid_by;
	}
	public void setPaid_by(int paid_by) {
		this.paid_by = paid_by;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getEntity()
	{
		return "expense";
	}
}
