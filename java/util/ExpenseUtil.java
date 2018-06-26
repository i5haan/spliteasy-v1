package util;


import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import model.GroupMembers;
import model.SplitExpense;
import persistance.BalanceLedger;
import persistance.Entity;
import persistance.Expense;

public class ExpenseUtil {
	DbUtil dbUtil=new DbUtil();
	
	public boolean create(int gid,String ename, int paid_by,double amount)
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		String sql="";
		String createdDate=dtf.format(now);
		dbUtil.dbConnection();
		try {
			(DbUtil.con).setAutoCommit(false);
		}catch(Exception e)
		{
			System.out.println(e);
			return false;
		}
		
		Expense expense=new Expense();
		expense.setAmount(amount);
		expense.setEid(0);
		expense.setCreated_at("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		expense.setGrpid(gid);
		expense.setEname(ename);
		expense.setPaid_by(paid_by);
		dbUtil.create(expense);
		
		
		try {
			String teId=dbUtil.findOneColumn("max(eId)", "expense", "0", 0);
			sql="select * from group_member where grpid="+gid;
			
			ArrayList<Entity> ulist = new ArrayList<>();
			ulist=dbUtil.runQuery(sql, "group_member");
			boolean res1=splitExpence(teId, ulist, amount, paid_by, gid);
			if(res1==false)
			{
				(DbUtil.con).rollback();
				return false;
			}
			else
			{
				(DbUtil.con).commit();
			}
			
		}
			catch(Exception e) {
					System.out.println(e);
					return false;
			}
		
		try {
			(DbUtil.con).setAutoCommit(true);
		}catch(Exception e)
		{
		}
		return true;
	}
		
		boolean splitExpence(String eid, ArrayList<Entity> ulist, double amount, int paid_by, int gid) {
			   
			int n = ulist.size();
			double splitAmount = amount/n;
			boolean flag=true;
			
			for(int i=0;i<n;i++) {
					SplitExpense splitexpense=new SplitExpense();
					splitexpense.setEid(Integer.parseInt(eid));
					splitexpense.setS_amt(splitAmount);
					splitexpense.setUserid(Integer.parseInt(((GroupMembers) ulist.get(i)).getuserId()));
					if(paid_by==Integer.parseInt(((GroupMembers) ulist.get(i)).getuserId()))
					{
						splitexpense.setType("O");
					}
					else
					{
						splitexpense.setType("B");
					}
					flag=dbUtil.create(splitexpense);
					System.out.println("flagw"+flag);
					if(flag==false)
					{
						System.out.println("flag"+flag);
						try {
							(DbUtil.con).rollback();
						}catch(Exception e)
						{
							
						}
						
						break;
					}
					if(paid_by!=Integer.parseInt(((GroupMembers) ulist.get(i)).getuserId()))
					{
						BalanceLedger b=new BalanceLedger();
						b.setFrom(Integer.parseInt(((GroupMembers) ulist.get(i)).getuserId()));
						b.setAmount(splitAmount);
						b.setGrpid(gid);
						b.setTo(paid_by);
						flag=dbUtil.create(b);
					}
					if(flag==false)
					{
						try {
							(DbUtil.con).rollback();
						}catch(Exception e)
						{
							
						}
						break;
					}
					
					
					
					
					
				}
			 return flag;

 	       }
	
//	boolean updateBalLeger(gid, paid_by, ulist.get(i), splitAmount) {
//		//String sql3 ="{(?,?,?,?)}"---> create procedure having attibutes group_id, frmon user, to user, split amount 
//	    	CallableStatement stmt = (CallableStatement) (DbUtil.con).prepareCall(sql3);
//	  
//		    if(gid == stmt.getInt(1) && paid_by ==stmt.getInt(2) && ulist.get(i) == stmt.getInt(3)) {
//			stmt.setDouble(4, splitAmount);
//	     	}
//		    else {
//		    	stmt.setInt(1, gid);
//		    	stmt.setInt(2, paid_by);
//		    	stmt.setInt(3, ulist.get(i));
//		    	stmt.setInt(4, splitAmount);
//		    }
	
//	void settleupInvidiualGroup(int gid,int user1,int user2) {
//		//call table balance leger-----sql;
//		try {
//			CallableStatement stmt = (CallableStatement) (DbUtil.con).prepareCall(sql);
//			double x,y,z;
//			if(gid == stmt.getInt(1) && user1 == stmt.getInt(2) && user2 == stmt.getInt(3)) {
//				x = stmt.getDouble(4); 
//				stmt.getDouble(4)=0;
//			}
//			if(gid == stmt.getInt(1) && user2 == stmt.getInt(2) && user1 == stmt.getInt(3)) {
//				y = stmt.getDouble(4);
//				stmt.getDouble(4)=0;
//			}
//			if(x>y) {
//				z=x-y;
//				System.out.println("user1 will pay z rupee to user2");
//			}
//				
//			else {
//				z=y-x;
//				System.out.println("user1 will pay z rupee to user2");
//			}
//			
//			stmt.executeUpdate();
//			stmt.close();
//			return true;
//		}catch(Exception e) {
//			System.out.println(e);
//			return false;
//		}
//	}
//	void settleupTotal(int user1,int user2) {
//		//call table balance leger-----sql;
//		try {
//			CallableStatement stmt = (CallableStatement) (DbUtil.con).prepareCall(sql);
//			double x=0,y=0,z;
//			while(rs.next())
//			{
//				if(user1 == stmt.getInt(2) && user2 == stmt.getInt(3)) {
//					x = x + stmt.getDouble(4); 
//					stmt.getDouble(4)=0;
//				}
//				else if(user2 == stmt.getInt(2) && user1 == stmt.getInt(3)) {
//					y = y + stmt.getDouble(4);
//					stmt.getDouble(4)=0;
//				}
//			}
//			if(x>y) {
//				z=x-y;
//				System.out.println("user1 will pay z rupee to user2");
//			}
//			else {
//	    		z=y-x;
//				System.out.println("user1 will pay z rupee to user2");
//			}
//				
//			stmt.executeUpdate();
//			stmt.close();
//			return true;
//		}
//		
//		catch(Exception e)
//		{
//			System.out.println(e);
//			return false;
//		}
//	}
//	}
//
//
}
