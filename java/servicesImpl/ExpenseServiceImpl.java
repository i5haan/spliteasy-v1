package servicesImpl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.SplitExpense;
import model.UserInfo;
import persistance.Expense;
import util.ExpenseUtil;
import model.GroupMembers;
import model.Message;
import model.SettleUp;
import persistance.BalanceLedger;
import persistance.Entity;
import util.DbUtil;
import util.ExpenseUtil;


@Path("/group/{gid}/expense")

public class ExpenseServiceImpl {
	
	ExpenseUtil expenseUtil = new ExpenseUtil();
	DbUtil dbUtil= new DbUtil();
	
	
	@GET
	@Path("")
	//@Produces(MediaType.APPLICATION_FORM_URLENCODED)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response getAllExpense(@PathParam("gid") String gid) 
	{
		try
		{
			Integer.parseInt(gid);
		}catch(Exception e)
		{
			System.out.println(e);
			Message m=new Message();
			m.setStatus("F");
			m.setMessage(e.getClass().getCanonicalName()+" for group Id");
			return Response.ok()
					.entity(m)
						.build();
		}
		
		String query="select *from  expense where grpid in (select grpid from group_member where userid="+UserInfo.userid+") and grpid="+gid;
		ArrayList<Entity> res=new ArrayList<>();
		DbUtil.dbConnection();
		res=dbUtil.runQuery(query, "expense");
		query="select *from user where userid="+UserInfo.userid;
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
		return Response.ok()
				.entity(res)
					.build();
//		
	
	}
	
	@GET
	@Path("/{eid}")	
	//@Produces(MediaType.APPLICATION_FORM_URLENCODED)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response getExpense(@PathParam("gid") String gid, @PathParam("eid") String eid) 
	{
		try
		{
			Integer.parseInt(gid);
		}catch(Exception e)
		{
			System.out.println(e);
			Message m=new Message();
			m.setStatus("F");
			m.setMessage(e.getClass().getCanonicalName()+" for group Id");
			return Response.ok()
					.entity(m)
						.build();
		}
		try
		{
			Integer.parseInt(eid);
		}catch(Exception e)
		{
			System.out.println(e);
			Message m=new Message();
			m.setStatus("F");
			m.setMessage(e.getClass().getCanonicalName()+" for Expense Id");
			return Response.ok()
					.entity(m)
						.build();
		}
		String query="select *from  expense where grpid in (select grpid from group_member where userid="+UserInfo.userid+") and grpid="+gid+" and eid="+eid;
		ArrayList<Entity> res=new ArrayList<>();
		DbUtil.dbConnection();
		res=dbUtil.runQuery(query, "expense");
		query="select *from user where userid="+UserInfo.userid;
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
		return Response.ok()
				.entity(res)
					.build();
//		
	
	}
	@POST
	@Path("")
	//@Produces(MediaType.APPLICATION_FORM_URLENCODED)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addExpense(@PathParam("gid") int gid, @FormParam("name")String ename,@FormParam("amount")double amount) 
	{
		if(amount<0)
		{
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("Invalid Amount");
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
		boolean res = expenseUtil.create(gid, ename, UserInfo.userid, amount);
				if(res)
				{
					Message m=new Message();
					m.setStatus("S");
					m.setMessage("Ok");
					return Response.ok()
							.entity(m)
								.build();
				}
				else
				{
					Message m=new Message();
					m.setStatus("S");
					m.setMessage("Expense Could Not be created!!");
					return Response.ok()
							.entity(m)
								.build();
				}
//		
	
	}
	
	@POST
	@Path("settleup")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response settleup(@PathParam("gid") int gid, @FormParam("u1") int userId1, @FormParam("u2") int userId2) 
	{
		SettleUp res  = expenseUtil.settleupInvidiualInGroup(gid,userId1,userId2);
			return Response.ok()
					.entity(res)
					.build();
	}
	
	@GET
	@Path("settleup")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response settleup(@PathParam("gid") int gid) 
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
		ArrayList<SettleUp> res  = expenseUtil.showAllSettleUP(gid);
			return Response.ok()
					.entity(res)
					.build();
	}
}
	
	
	