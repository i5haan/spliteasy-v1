package util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.List;

import model.GroupMembers;
import model.UserInfo;
import persistance.Groups;

import java.time.LocalDateTime;   


public class GroupUtil {
	
	DbUtil dbUtil=new DbUtil();
	
	public GroupUtil() 
	{
		DbUtil.dbConnection();
	}
	
	public boolean create(String gName, String userId,List<String> members)
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		String createdDate=dtf.format(now);
		try {
			(DbUtil.con).setAutoCommit(false);
		}catch(Exception e)
		{
			
		}
		Groups group=new Groups();
		group.setGname(gName);
		group.setCreated_at(createdDate);
		group.setCuserid(Integer.parseInt(userId));
		group.setGrpid(0);
		String sql = "insert into groups values(null,26,?,?)";
		dbUtil.create(group);
//		sql="select max(grpid) from groups";
		
		try {
			String grpId=dbUtil.findOneColumn("max(grpid)", "groups", "0", 0);
			for(int i=0;i<members.size();i++)
			{
				String eId=dbUtil.findOneColumn("userid", "user", "email", members.get(i));
				GroupMembers groupMembers=new GroupMembers();
				groupMembers.setuserId(eId);
				groupMembers.setgId(grpId);
				boolean res1=dbUtil.create(groupMembers);
				if(res1==false)
				{
					try {
						(DbUtil.con).rollback();
					}catch(Exception exc)
					{
						
					}
					return false;
				}
//				sql = "insert into group_member values("+grpId+","+eId+")";
//				System.out.println(sql);
//				PreparedStatement stmt = (DbUtil.con).prepareStatement(sql);
//				stmt.executeUpdate();
			}	
			GroupMembers groupMembers=new GroupMembers();
			groupMembers.setuserId(Integer.toString(UserInfo.userid));
			groupMembers.setgId(grpId);
			boolean res1=dbUtil.create(groupMembers);
			if(res1==false)
			{
				try {
					(DbUtil.con).rollback();
				}catch(Exception exc)
				{
					
				}
				return false;
			}
		}catch(Exception e) {
			System.out.println(e);
			try {
				(DbUtil.con).rollback();
			}catch(Exception exc)
			{
				
			}
			return false;
		}
		try
		{
			(DbUtil.con).commit();
		}catch(Exception e)
		{
			
		}
		
		return true;
	}
	
	public boolean addMemeber(String email, String gId) {
		String eId=dbUtil.findOneColumn("userid", "user", "email", email);
		GroupMembers groupMember=new GroupMembers();
		groupMember.setuserId(eId);
		groupMember.setgId(gId);
		boolean res=dbUtil.create(groupMember);
		return true;
	}
	

}
