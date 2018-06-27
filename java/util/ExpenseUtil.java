package util;


import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import model.GroupMembers;
import model.SettleUp;
import model.SplitExpense;
import model.UserInfo;
import persistance.BalanceLedger;
import persistance.Entity;
import persistance.Expense;

public class ExpenseUtil {
	DbUtil dbUtil=new DbUtil();
	
	public boolean create(int gid,String ename, int paid_by,double amount)
	{
		System.out.println(paid_by);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		String sql="";
		String createdDate=dtf.format(now);
		
		Expense expense=new Expense();
		expense.setAmount(amount);
		expense.setEid(0);
		expense.setCreated_at(createdDate);
		expense.setGrpid(gid);
		expense.setEname(ename);
		expense.setPaid_by(paid_by);
		dbUtil.create(expense);
		
		
		try {
			(DbUtil.con).setAutoCommit(false);
			String teId=dbUtil.findOneColumn("max(eId)", "expense", "0", 0);
			sql="select * from group_member where grpid="+gid;
			
			ArrayList<Entity> ulist = new ArrayList<>();
			ulist=dbUtil.runQuery(sql, "group_member");
			boolean res1=splitExpence(teId, ulist, amount, paid_by, gid);
			if(res1==false)
			{
				(DbUtil.con).rollback();
			}
			else
			{
				(DbUtil.con).commit();
			}
		}
			catch(Exception e) {
				try {
					(DbUtil.con).rollback();
				}catch(Exception e1)
				{
					
				}
					
					System.out.println(e);
					return false;
			}
		return true;
	}
		
		boolean splitExpence(String eid, ArrayList<Entity> ulist, double amount, int paid_by, int gid) {
			   
			int n = ulist.size();
			double splitAmount = amount/n;
			boolean flag=true;
			for(int i=0;i<n;i++) {
					try {
						(DbUtil.con).setAutoCommit(false);
					}catch(Exception e)
					{
						
					}
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
					
					
					
					
					
				}
			
			 return flag;

 	       }
		
		public SettleUp settleupInvidiualInGroup(int gid,int to,int from) {
			SettleUp settleUp=new SettleUp();
			BalanceLedger balLedger1 = new BalanceLedger();
			BalanceLedger balLedger2 = new BalanceLedger();
		    double x=0, y=0;
		    double z;
		    ArrayList<Entity> res1=new ArrayList<>();
		    ArrayList<Entity> res2=new ArrayList<>();
		    String query1="select *from balance_ledger where fromUser="+from+" and toUser="+to+" and grpid="+gid;
		    String query2="select *from balance_ledger where fromUser="+to+" and toUser="+from+" and grpid="+gid;
		    
		    res1=dbUtil.runQuery(query1, "balance_ledger");
		    res2=dbUtil.runQuery(query2, "balance_ledger");
		    try {
		    	try {
		    		balLedger1=(BalanceLedger)res1.get(0);
			    	x=balLedger1.getAmount();
		    	}catch(Exception e)
		    	{
		    		x=0;
		    	}
		    	try
		    	{
		    		balLedger2=(BalanceLedger)res2.get(0);
				    y=balLedger2.getAmount();
		    	}
			    catch(Exception e)
		    	{
			    	y=0;
		    	}
			    balLedger1.setAmount(-x);
			    balLedger2.setAmount(-y);
			    dbUtil.create(balLedger1);
			    dbUtil.create(balLedger2);
			    
			    System.out.println(x);
			    System.out.println(y);
		    }catch(Exception e)
		    {
		    	System.out.println(e);
		    	settleUp.setAmount(0);
		    	settleUp.setGrpid(gid);
		    	settleUp.setPaidBy(from);
		    	settleUp.setPaidTo(to);
		    	
		    	return settleUp;
		    }
		    
		    
			if(x>y)
			{
				z=x-y;
				System.out.println(from+" will pay z rupee to "+to);
				settleUp.setAmount(z);
		    	settleUp.setGrpid(gid);
		    	settleUp.setPaidBy(from);
		    	settleUp.setPaidTo(to);
			}
			
			else 
			{
				z=y-x;
				System.out.println(from+" will pay z rupee to"+to);
				settleUp.setAmount(z);
		    	settleUp.setGrpid(gid);
		    	settleUp.setPaidBy(to);
		    	settleUp.setPaidTo(from);
			}
			return settleUp;
		
		}
		
		public SettleUp showSettleUpUtil(int gid,int to,int from) {
			if(to==from)
			{
				return null;
			}
			SettleUp settleUp=new SettleUp();
			BalanceLedger balLedger1 = new BalanceLedger();
			BalanceLedger balLedger2 = new BalanceLedger();
		    double x=0, y=0;
		    double z;
		    ArrayList<Entity> res1=new ArrayList<>();
		    ArrayList<Entity> res2=new ArrayList<>();
		    String query1="select *from balance_ledger where fromUser="+from+" and toUser="+to+" and grpid="+gid;
		    String query2="select *from balance_ledger where fromUser="+to+" and toUser="+from+" and grpid="+gid;
		    
		    res1=dbUtil.runQuery(query1, "balance_ledger");
		    res2=dbUtil.runQuery(query2, "balance_ledger");
		    try {
		    	try {
		    		balLedger1=(BalanceLedger)res1.get(0);
			    	x=balLedger1.getAmount();
		    	}catch(Exception e)
		    	{
		    		x=0;
		    	}
		    	try
		    	{
		    		balLedger2=(BalanceLedger)res2.get(0);
				    y=balLedger2.getAmount();
		    	}
			    catch(Exception e)
		    	{
			    	y=0;
		    	}
			    
			    System.out.println(x);
			    System.out.println(y);
		    }catch(Exception e)
		    {
		    	System.out.println(e);
		    	settleUp.setAmount(0);
		    	settleUp.setGrpid(gid);
		    	settleUp.setPaidBy(from);
		    	settleUp.setPaidTo(to);
		    	
		    	return settleUp;
		    }
		    
		    if(x==y && x==0 && y==0)
		    {
		    	return null;
		    }
		    	
			if(x>y)
			{
				z=x-y;
				System.out.println(from+" will pay z rupee to "+to);
				settleUp.setAmount(z);
		    	settleUp.setGrpid(gid);
		    	settleUp.setPaidBy(from);
		    	settleUp.setPaidTo(to);
			}
			
			else 
			{
				z=y-x;
				System.out.println(from+" will pay z rupee to"+to);
				settleUp.setAmount(z);
		    	settleUp.setGrpid(gid);
		    	settleUp.setPaidBy(to);
		    	settleUp.setPaidTo(from);
			}
			return settleUp;
		
		}
		
		public ArrayList<SettleUp> showAllSettleUP(int gid)
		{
			ArrayList<SettleUp> res=new ArrayList<>();
			String sql="select * from group_member where grpid="+gid;
			ArrayList<Entity> ulist = new ArrayList<>();
			ulist=dbUtil.runQuery(sql, "group_member");
			for(int i=0;i<ulist.size();i++)
			{
				int e=Integer.parseInt(((GroupMembers)ulist.get(i)).getuserId());
				SettleUp temp=showSettleUpUtil(gid,UserInfo.userid,e);
				if(temp!=null)
				{
					res.add(temp);
				}
				
			}
			
			ulist=dbUtil.runQuery(sql, "group_member");
			return res;
		}
		
		
		
}
