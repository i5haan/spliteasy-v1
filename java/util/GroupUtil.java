package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.ws.rs.core.Response;

import model.GroupMembers;
import model.Message;
import model.UserInfo;
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
}
