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
	public Response getAllExpense(@PathParam("gid") int gid) 
	{
		String query="select *from  expense where grpid in (select grpid from group_member where userid="+UserInfo.userid+") and grpid="+gid;
		ArrayList<Entity> res=new ArrayList<>();
		DbUtil.dbConnection();
		res=dbUtil.runQuery(query, "expense");
		return Response.ok()
				.entity(res)
					.build();
//		
	
	}
	
	@GET
	@Path("/{eid}")	
	//@Produces(MediaType.APPLICATION_FORM_URLENCODED)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response getExpense(@PathParam("gid") int gid, @PathParam("eid") int eid) 
	{
		String query="select *from  expense where grpid in (select grpid from group_member where userid="+UserInfo.userid+") and grpid="+gid+" and eid="+eid;
		ArrayList<Entity> res=new ArrayList<>();
		DbUtil.dbConnection();
		res=dbUtil.runQuery(query, "expense");
		return Response.ok()
				.entity(res)
					.build();
//		
	
	}
	@POST
	@Path("")
	//@Produces(MediaType.APPLICATION_FORM_URLENCODED)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addExpense(@PathParam("gid") int gid, @FormParam("name")String ename, @FormParam("paid_by")int paid_by, @FormParam("amount")double amount) 
	{
		
		System.out.println(gid);
		System.out.println(ename);
		System.out.println(UserInfo.userid);
		System.out.println(amount);
		boolean res = expenseUtil.create(gid, ename, paid_by, amount);
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
//		
	
	}
}
	
	
	