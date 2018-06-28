package servicesImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Group;
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
		if(gName==null	 || members.size()==0)
		{
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("One of the paramaters are missing");
			return Response.ok()
					.entity(m)
						.build();
		}
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
		System.out.println(gName);
		boolean res=grouputil.create(gName,Integer.toString(UserInfo.userid),members);
		if(res)
		{
			return Response.ok()
					.entity(true)
					.build();
		}
		else
		{
			return Response.ok()
					.entity(false)
					.build();
		}
		
	}
	
	
	@POST
	@Path("/{id}/member")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addMember(@PathParam("id") String gId, @FormParam("email") String email)
	{
		System.out.println(gId);
		System.out.println(email);
		boolean res=grouputil.addMemeber(email, gId);
		return Response.ok()
				.entity(res)
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
	
}
