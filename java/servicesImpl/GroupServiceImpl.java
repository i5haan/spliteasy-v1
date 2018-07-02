package servicesImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Group;
import model.GroupStats;
import model.Message;
import model.UserInfo;
import persistance.Entity;
import persistance.Groups;
import persistance.User;

import java.util.ArrayList;
import java.util.List;

import services.GroupService;
import util.DbUtil;
import util.GroupUtil;

@Path("/group")
public class GroupServiceImpl implements GroupService
{
	GroupUtil grouputil = new GroupUtil();
	DbUtil dbUtil=new DbUtil();
	
	@GET
	@Path("")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response getAllGroups()
	{
		String query1="select *from user where userid="+UserInfo.userid;
		ArrayList<Entity> res2=new ArrayList<>();
		res2=dbUtil.runQuery(query1, "user");
		if(res2.isEmpty())
		{
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("User not Logged In");
			return Response.ok()
					.entity(m)
						.build();
		}
		
		
		ArrayList<Entity> res=new ArrayList<>();
		ArrayList<Entity> res3=new ArrayList<>();
		String query="select *from  groups where grpid in (select grpid from group_member where userid="+UserInfo.userid+")";
		res=dbUtil.runQuery(query,"groups");
		String query2="";
		ArrayList<Group> finalRes=new ArrayList<>();
		for(int i=0;i<res.size();i++)
		{
			Group tempGroup=new Group();
			Groups tempGroup2=((Groups) res.get(i));
			int tempGid=tempGroup2.getGrpid();
			query2="select user.userid,name,email,photo from user, groups, group_member where groups.grpid=group_member.grpid and group_member.userid=user.userid and groups.grpid="+tempGid;
			res3=dbUtil.runQuery(query2, "user");
			tempGroup.setCreated_at(tempGroup2.getCreated_at());
			tempGroup.setUser(dbUtil.findOneColumn("name", "user", "userid", tempGroup2.getCuserid()));
			tempGroup.setGname(tempGroup2.getGname());
			tempGroup.setGrpid(tempGroup2.getGrpid());
			tempGroup.setMember(res3);
			finalRes.add(tempGroup);

		}
		res3=dbUtil.runQuery(query2, "user");
		return Response.ok()
				.entity(finalRes)
				.build();
		
	}
	
	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response add(@FormParam("name") String gName, @FormParam("members") List<String> members)
	{
		String query="select *from user where userid="+UserInfo.userid;
		ArrayList<Entity> res2=new ArrayList<>();
		res2=dbUtil.runQuery(query, "user");
		if(res2.isEmpty())
		{
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("User not Logged In");
			return Response.ok()
					.entity(m)
						.build();
		}
		if(gName.equals("") || gName==null )
		{
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("Group Name is missing!");
			return Response.ok()
					.entity(m)
						.build();
		}
		if(members.size()==0)
		{
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("Atleast One or more members must be added!");
			return Response.ok()
					.entity(m)
						.build();
		}
		for(int i=0;i<members.size();i++)
		{
			if(members.get(i).equals(""))
			{
				Message m=new Message();
				m.setStatus("F");
				m.setMessage("Email Can't Be Empty");
				return Response.ok()
						.entity(m)
							.build();
			}
		}
		System.out.println(gName);
		Message m=grouputil.create(gName,Integer.toString(UserInfo.userid),members);
		return Response.ok()
				.entity(m)
				.build();
		
	}
	
	
	@POST
	@Path("/{id}/member")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addMember(@PathParam("id") String gId, @FormParam("email") String email)
	{
		if(email==null || email.equals("") || email.equals("null"))
		{
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("Email Cannto be null");
			return Response.ok()
					.entity(m)
						.build();
		}
		String query1="select *from user where userid="+UserInfo.userid;
		ArrayList<Entity> res2=new ArrayList<>();
		res2=dbUtil.runQuery(query1, "user");
		if(res2.isEmpty())
		{
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("User not Logged In");
			return Response.ok()
					.entity(m)
						.build();
		}
		query1="select *from user where userid in (select userid from group_member where grpid="+gId+") and userid="+UserInfo.userid;
		res2=new ArrayList<>();
		res2=dbUtil.runQuery(query1, "user");
		if(res2.isEmpty())
		{
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("Not Authorized");
			return Response.ok()
					.entity(m)
						.build();
		}
		Message m=new Message();
		
		System.out.println(gId);
		System.out.println(email);
		boolean res=grouputil.addMember(email, gId);
		if(res)
		{
			m.setStatus("S");
			m.setMessage("Member Added Succesfully");
		}
		else
		{
			m.setStatus("F");
			m.setMessage("Member Added could not be added");
		}
		return Response.ok()
				.entity(m)
				.build();
	}
	
	
	@GET
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response getGroup(@PathParam("id") String gId)
	{
		String query1="select *from user where userid="+UserInfo.userid;
		ArrayList<Entity> res2=new ArrayList<>();
		res2=dbUtil.runQuery(query1, "user");
		if(res2.isEmpty())
		{
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("User not Logged In");
			return Response.ok()
					.entity(m)
						.build();
		}
		query1="select *from user where userid in (select userid from group_member where grpid="+gId+") and userid="+UserInfo.userid;
		res2=new ArrayList<>();
		res2=dbUtil.runQuery(query1, "user");
		if(res2.isEmpty())
		{
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("Not Authorized");
			return Response.ok()
					.entity(m)
						.build();
		}
		System.out.println(gId);
		ArrayList<Entity> res=new ArrayList<>();
		String query="select *from  groups where grpid="+gId+" and grpid in (select grpid from group_member where userid="+UserInfo.userid+")";
		res=dbUtil.runQuery(query,"groups");
		Group tempGroup=new Group();
		Groups tempGroup2=((Groups) res.get(0));
		int tempGid=tempGroup2.getGrpid();
		String query2="select user.userid,name,email,photo from user, groups, group_member where groups.grpid=group_member.grpid and group_member.userid=user.userid and groups.grpid="+tempGid;
		ArrayList<Entity> res3=dbUtil.runQuery(query2, "user");
		tempGroup.setCreated_at(tempGroup2.getCreated_at());
		tempGroup.setGname(tempGroup2.getGname());
		tempGroup.setGrpid(tempGroup2.getGrpid());
		tempGroup.setUser(dbUtil.findOneColumn("name", "user", "userid", tempGroup2.getCuserid()));
		tempGroup.setMember(res3);
		return Response.ok()
				.entity(tempGroup)
				.build();
	}
	
	@GET
	@Path("{id}/groupstats")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response groupStats(@PathParam("id") String gid)
	{
		String query1="select *from user where userid="+UserInfo.userid;
		ArrayList<Entity> res2=new ArrayList<>();
		res2=dbUtil.runQuery(query1, "user");
		if(res2.isEmpty())
		{
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("User not Logged In");
			return Response.ok()
					.entity(m)
						.build();
		}
		query1="select *from user where userid in (select userid from group_member where grpid="+gid+") and userid="+UserInfo.userid;
		res2=new ArrayList<>();
		res2=dbUtil.runQuery(query1, "user");
		if(res2.isEmpty())
		{
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("Not Authorized");
			return Response.ok()
					.entity(m)
						.build();
		}
		System.out.println(gid);
		Message m=new Message();
		try {
			Integer.parseInt(gid);
		}catch(Exception e)
		{
			m.setMessage("Invalid Group Id");
			m.setStatus("F");
			return Response.ok().entity(m).build();
		}
		GroupStats gs=new GroupStats();
		String tTotal=dbUtil.findOneColumn("Sum(amount)", "expense", "grpid", Integer.parseInt(gid));
		String tPaid=dbUtil.findOneColumn("Sum(amount)", "expense", "paid_by="+UserInfo.userid+" and grpid", Integer.parseInt(gid));
		String tShare=dbUtil.findOneColumn("sum(s_amt)", "expense, split_expense", "expense.eid=split_expense.eid and grpid="+gid+" and userid", UserInfo.userid);
		if(tTotal==null)
		{
			gs.setTotal(0);
		}
		else
		{
			gs.setTotal(Double.parseDouble(tTotal));
		}
		
		if(tPaid==null)
		{
			gs.setTotaluserpaid(0);
		}
		else
		{
			gs.setTotaluserpaid(Double.parseDouble(tPaid));
		}
		
		if(tShare==null)
		{
			gs.setTotalusershare(0);;
		}
		else
		{
			gs.setTotalusershare(Double.parseDouble(tShare));
		}
		
		
		return Response.ok().entity(gs).build();
	}
	
	@DELETE
	@Path("/{id}/user")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response deleteMember(@PathParam("id") String gid, @FormParam("uid") String userid)
	{
		String query1="select *from user where userid="+UserInfo.userid;
		ArrayList<Entity> res2=new ArrayList<>();
		res2=dbUtil.runQuery(query1, "user");
		if(res2.isEmpty())
		{
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("User not Logged In");
			return Response.ok()
					.entity(m)
						.build();
		}
		query1="select *from user where userid in (select userid from group_member where grpid="+gid+") and userid="+UserInfo.userid;
		res2=new ArrayList<>();
		res2=dbUtil.runQuery(query1, "user");
		if(res2.isEmpty())
		{
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("Not Authorized");
			return Response.ok()
					.entity(m)
						.build();
		}
		System.out.println(gid);
		Message m=new Message();
		try {
			Integer.parseInt(gid);
		}catch(Exception e)
		{
			m.setMessage("Invalid Group Id");
			m.setStatus("F");
			return Response.ok().entity(m).build();
		}
		
		try {
			Integer.parseInt(userid);
		}catch(Exception e)
		{
			m.setMessage("Invalid User Id");
			m.setStatus("F");
			return Response.ok().entity(m).build();
		}
		Message res=grouputil.deleteMemeber(gid, userid);
		return Response.ok().entity(res).build();
		
		
	}
	
	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response deleteGroup(@PathParam("id") String gid)
	{
		String query1="select *from user where userid="+UserInfo.userid;
		ArrayList<Entity> res2=new ArrayList<>();
		res2=dbUtil.runQuery(query1, "user");
		if(res2.isEmpty())
		{
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("User not Logged In");
			return Response.ok()
					.entity(m)
						.build();
		}
		query1="select *from user where userid in (select userid from group_member where grpid="+gid+") and userid="+UserInfo.userid;
		res2=new ArrayList<>();
		res2=dbUtil.runQuery(query1, "user");
		if(res2.isEmpty())
		{
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("Not Authorized");
			return Response.ok()
					.entity(m)
						.build();
		}
		System.out.println(gid);
		Message m=new Message();
		try {
			Integer.parseInt(gid);
		}catch(Exception e)
		{
			m.setMessage("Invalid Group Id");
			m.setStatus("F");
			return Response.ok().entity(m).build();
		}
		
		Message res=grouputil.deleteGroup(gid);
		return Response.ok().entity(res).build();
	}
	
	
	
	
}
