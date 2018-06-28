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
import model.SplitExpenseModel;
import model.UserInfo;
import persistance.Expense;
import util.ExpenseUtil;
import model.ExpenseModel;
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
		ArrayList<ExpenseModel> finalRes=new ArrayList<>();
		for(int i=0;i<res.size();i++)
		{
			ExpenseModel obj=new ExpenseModel();
			obj.setPaidBy(dbUtil.findOneColumn("name", "user", "userid", ((Expense)res.get(i)).getPaid_by()));
			obj.setAmount(((Expense)res.get(i)).getAmount());
			obj.setCreated_at(((Expense)res.get(i)).getCreated_at());
			obj.setEname(((Expense)res.get(i)).getEname());
			obj.setEid(((Expense)res.get(i)).getEid());
			finalRes.add(obj);
		}
		
		
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
				.entity(finalRes)
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
		String query="select *from user where userid="+UserInfo.userid;
		ArrayList<Entity> res2=new ArrayList<>();
		DbUtil.dbConnection();
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
		query="select *from  expense where grpid in (select grpid from group_member where userid="+UserInfo.userid+") and grpid="+gid+" and eid="+eid;
		ArrayList<Entity> res=new ArrayList<>();
		res=dbUtil.runQuery(query, "expense");
		ArrayList<ExpenseModel> finalRes=new ArrayList<>();
	
		Expense e=new Expense();
		try {
			e=(Expense)res.get(0);
		}catch(Exception e1)
		{
			System.out.println(e1);
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("The Expense Id does not belong to the Given Group");
			return Response.ok()
					.entity(m)
						.build();
		}
		
		ArrayList<Entity> sp=dbUtil.runQuery("select *from split_expense where eid="+e.getEid(), "split_expense");
		ArrayList<SplitExpenseModel> spm=new ArrayList<>();
		ExpenseModel em=new ExpenseModel();
		em.setAmount(e.getAmount());
		em.setCreated_at(e.getCreated_at());
		em.setEname(e.getEname());
		em.setPaidBy(dbUtil.findOneColumn("name", "user", "userid", e.getPaid_by()));
		em.setEid(e.getEid());
		for(int i=0;i<sp.size();i++)
		{
			SplitExpenseModel obj=new SplitExpenseModel();
			obj.setName(dbUtil.findOneColumn("name", "user", "userid", ((SplitExpense)sp.get(i)).getUserid()));
			obj.setS_amt(((SplitExpense)sp.get(i)).getS_amt());
			obj.setType(((SplitExpense)sp.get(i)).getType());
			spm.add(obj);
		}
		em.setSplit(spm);
		query="select *from user where userid="+UserInfo.userid;
		return Response.ok()
				.entity(em)
					.build();
//		
	
	}
	@POST
	@Path("")
	//@Produces(MediaType.APPLICATION_FORM_URLENCODED)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addExpense(@PathParam("gid") int gid, @FormParam("name")String ename,@FormParam("amount")double amount, @FormParam("ratio")List<Integer> ratios) 
	{
		System.out.println(ratios);
		if(ename==null || ename.equals(""))
		{
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("Name cannot be null");
			return Response.ok()
					.entity(m)
						.build();
		}
		if(amount==0)
		{
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("Invalid Amount");
			return Response.ok()
					.entity(m)
						.build();
		}
		DbUtil.dbConnection();
		int count=Integer.parseInt(dbUtil.findOneColumn("count(*)", "group_member", "grpid", gid));
		if(count!=ratios.size())
		{
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("Invalid Ratios");
			return Response.ok()
					.entity(m)
						.build();
		}
		System.out.println(ratios);
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
		query="select *from user where userid in (select userid from group_member where grpid="+gid+") and userid="+UserInfo.userid;
		res2=new ArrayList<>();
		res2=dbUtil.runQuery(query, "user");
		if(res2.isEmpty())
		{
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("Not Authorized");
			return Response.ok()
					.entity(m)
						.build();
		}
		boolean res = expenseUtil.create(gid, ename, UserInfo.userid, amount,ratios);
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
					m.setStatus("F");
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
		DbUtil.dbConnection();
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
		DbUtil.dbConnection();
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
	
	
	