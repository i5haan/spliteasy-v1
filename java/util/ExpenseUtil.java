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
		DbUtil.dbConnection();
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
		expense.setCreated_at(createdDate);
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
		
		public double settleupInvidiualInGroup(int gid,int to,int from) {
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
		    	balLedger1=(BalanceLedger)res1.get(0);
			    balLedger2=(BalanceLedger)res2.get(0);
			    x=balLedger1.getAmount();
			    y=balLedger2.getAmount();
			    balLedger1.setAmount(-x);
			    balLedger2.setAmount(-y);
			    
			    System.out.println(x);
			    System.out.println(y);
		    }catch(Exception e)
		    {
		    	System.out.println(e);
		    	return 0;
		    }
		    
		    
			if(x>y)
			{
				z=x-y;
				System.out.println(from+" will pay z rupee to "+to);
			}
			
			else 
			{
				z=y-x;
				System.out.println(from+" will pay z rupee to"+to);
			}
			return z;
		
		}
		
		
//		void settleupTotal(int userId1,int userId2) {
//			BalanceLedger balLeger = new BalanceLedger();
//		    double x=0, y=0;
//		    double z;
//		    while(rs.next())
//		    {
//		    	if(userId1 == balLeger.getFrom() && userId2 == balLeger.getTo()) {
//					x = x + balLeger.getAmount(); 
//					balLeger.setAmount(0);
//				}
//			    if(userId1 == balLeger.getTo() && userId2 == balLeger.getFrom()) {
//					y = y + balLeger.getAmount(); 
//					balLeger.setAmount(0);
//				}
//				if(x>y)
//				{
//					z=x-y;
//					System.out.println("user1 will pay z rupee to user2");
//				}
//				
//				else 
//				{
//					z=y-x;
//					System.out.println("user1 will pay z rupee to user2");
//				}
//			
//		    
//		    }
//			
//		}
}
