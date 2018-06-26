package persistance;

public class Groups implements Entity
{
	private int grpid;
	private int cuserid;
	private String gname;
	private String created_at;
	
	public int getGrpid() {
		return grpid;
	}
	public void setGrpid(int grpid) {
		this.grpid = grpid;
	}
	public int getCuserid() {
		return cuserid;
	}
	public void setCuserid(int cuserid) {
		this.cuserid = cuserid;
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
	public String getEntity()
	{
		return "groups";
	}
}
