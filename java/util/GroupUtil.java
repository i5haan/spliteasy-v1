package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import model.Group;
import model.GroupMembers;
import model.Message;
import model.UserInfo;
import persistance.BalanceLedger;
import persistance.Entity;
import persistance.Groups;
import services.EmailService;

public class GroupUtil
{
	DbUtil dbUtil=new DbUtil();
	
	public GroupUtil() 
	{
		DbUtil.dbConnection();
	}
	
	public Message create(String gName, String userId, List<String> members)
	{ 
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		String createdDate = dtf.format(now);
		try {
			(DbUtil.con).setAutoCommit(false);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		Groups group=new Groups();
		group.setGname(gName);
		group.setCreated_at(createdDate);
		group.setCuserid(Integer.parseInt(userId));
		group.setGrpid(0);
		dbUtil.create(group);
		
		System.out.println("=====================");
		
		try {
				String grpId=dbUtil.findOneColumn("max(grpid)", "groups", "cuserid", Integer.parseInt(userId));
				for(int i=0;i<members.size();i++)
				{
					String eId=dbUtil.findOneColumn("userid", "user", "email", members.get(i));
					if(eId == null)
					{
						String uName = dbUtil.findOneColumn("name", "user", "userid", Integer.parseInt(userId));
						System.out.println(uName);
						
						EmailService.prepareEmail(members.get(i),Integer.parseInt(userId), Integer.parseInt(grpId), uName, gName);
						continue;
					}
					GroupMembers groupMembers=new GroupMembers();
					groupMembers.setuserId(eId);
					groupMembers.setgId(grpId);
					boolean res1 = dbUtil.create(groupMembers);
					if(res1==false)
					{
						try {
							(DbUtil.con).rollback();
						}catch(Exception exc)
					{
						exc.printStackTrace();
					}
					Message m=new Message();
					m.setStatus("F");
					m.setMessage("Something went wrong, Please try again!");
					return m;
				}
			}	
			GroupMembers groupMembers=new GroupMembers();
			groupMembers.setuserId(Integer.toString(UserInfo.userid));
			groupMembers.setgId(grpId);
			boolean res2 = dbUtil.create(groupMembers);
			if(res2==false)
			{
				try {
						(DbUtil.con).rollback();
					}catch(Exception exc)
					{
						exc.printStackTrace();
					}
				Message m=new Message();
				m.setStatus("F");
				m.setMessage("Something went wrong, Please try again!");
				return m;
			 }
		}catch(Exception e) {
			System.out.println(e);
			try {
				(DbUtil.con).rollback();
			}catch(Exception exc)
			{
				exc.printStackTrace();
			}
			Message m=new Message();
			m.setStatus("F");
			m.setMessage("Something went wrong, Please try again!");
			return m;
		}
		
		try
		{
			(DbUtil.con).commit();
			(DbUtil.con).setAutoCommit(true);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		Message m=new Message();
		m.setStatus("S");
		m.setMessage("Group Successfully Created");
		return m;
	}
	
	public boolean addMember(String email, String gId) {
		String eId = dbUtil.findOneColumn("userid", "user", "email", email);
		if(eId != null)
		{
			GroupMembers groupMember = new GroupMembers();
			groupMember.setuserId(eId);
			groupMember.setgId(gId);
			boolean res = dbUtil.create(groupMember);
			return res;
		}
		else
			return false;
	}
	public Message deleteMemeber(String gId, String userId) 
	{
		Message m=new Message();
		DbUtil.dbConnection();
		GroupMembers gm = new GroupMembers();
		String s1=dbUtil.findOneColumn("sum(amount)", "balance_ledger", "grpid="+gId+" and fromUser", Integer.parseInt(userId));
		String s2=dbUtil.findOneColumn("sum(amount)", "balance_ledger", "grpid="+gId+" and toUser", Integer.parseInt(userId));
		int sum1=0;
		int sum2=0;
		if(s1==null)
		{
			sum1=0;
		}
		else
		{
			sum1=Integer.parseInt(s1);
		}
		if(s2==null)
		{
			sum2=0;
		}
		else
		{
			sum1=Integer.parseInt(s2);
		}
		System.out.println(sum1+sum2);
		if(sum1+sum2==0)
		{
			gm.setgId(gId);
			gm.setuserId(userId);
			boolean res1 =  dbUtil.delete(gm);
			if(res1)
			{
				m.setMessage("Member Deleted Succesfully");
				m.setStatus("S");
				return m;
				
			}
			else
			{
				m.setMessage("Somthing Went Wrong!!");
				m.setStatus("F");
				return m;
			}
		}else
		{
			m.setMessage("Member has to settle up before Deltetion");
			m.setStatus("F");
			return m;
			
		}
	}
	
	public Message deleteGroup(String gid) 
	{
		Message m=new Message();
		Groups group= new Groups();
		String sql="select *from groups where grpid="+gid;
		String sql2="select *from balance_ledger where grpid="+gid;
		ArrayList<Entity> resList2=dbUtil.runQuery(sql, "groups");
		ArrayList<Entity> resList=new ArrayList<>();
		boolean flag=false;
		if(resList2.isEmpty())
		{
			m.setMessage("No Group Found");
			m.setStatus("F");
			return m;
		}
		resList=dbUtil.runQuery(sql2, "balance_ledger");
		String sum=dbUtil.findOneColumn("sum(amount)", "balance_ledger", "grpid", Integer.parseInt(gid));
		int totalGroupSum=0;
		System.out.println(sum);
		if(sum==null)
		{
			totalGroupSum=0;
		}
		else
		{
			totalGroupSum=Integer.parseInt(sum);
		}
		if(totalGroupSum==0) {
			if(!resList.isEmpty())
			{
				for(int i=0;i<resList.size();i++)
				{
					BalanceLedger bl=new BalanceLedger();
					bl=(BalanceLedger) resList.get(i);
					flag=dbUtil.delete(bl);
					if(flag==false)
					{
						break;
					}
				}
				if(flag==false)
				{
					m.setMessage("Somthing Went Wrong!!");
					m.setStatus("F");
					return m;
				}
			}
			boolean res =dbUtil.delete(resList2.get(0));
			if(res)
			{
				m.setMessage("Group Deleted Succesfully");
				m.setStatus("S");
				return m;
				
			}
			else
			{
				m.setMessage("Somthing Went Wrong!!");
				m.setStatus("F");
				return m;
			}
		}else
		{
			m.setStatus("F");
			m.setMessage("Group is Not Setteled Up");
			return m;
			
		}
	}

}



