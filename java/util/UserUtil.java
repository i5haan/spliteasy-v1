package util;

import util.DbUtil;

import java.sql.CallableStatement;
import model.MapUserLogin;
import model.UserInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserUtil 
{
	MapUserLogin mapData=new MapUserLogin();
	DbUtil dbUtil=new DbUtil();
	
	public UserUtil()
	{
		DbUtil.dbConnection();
	}

	public int create(String name, String email, String pswd)
	{
		String sql="select count(*) from login where email=?";
		try
		{
			PreparedStatement pstmt = (DbUtil.con).prepareStatement(sql);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			int count = 0;
			if(rs.next())
			{
				count = rs.getInt(1);
			}
			if(count == 0)
			{
				sql="{call register(?,?,?,null)}";
		
				CallableStatement stmt = (CallableStatement) (DbUtil.con).prepareCall(sql);
				stmt.setString(1, email);
				stmt.setString(2, name);
				stmt.setString(3, pswd);
				stmt.executeUpdate();
				stmt.close();
				return 1;
			}
			else
			{
				return 0;
			}
		}catch(Exception e) {
			System.out.println(e);
			return -1;
		}
	}
	
	public int validate(String email, String pswd)
	{
		String sql="select login.*, userid from login, user where login.email=? and user.email=? and pwd=?";
		try {
			PreparedStatement stmt = (DbUtil.con).prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, email);
			stmt.setString(3, pswd);
			ResultSet rs = stmt.executeQuery();
			int userid;
			if(rs.next())
			{
				userid = rs.getInt(3);
				UserInfo.userid = userid;
				 
				mapData.putMapData(Integer.toString(userid));
				return userid;
			}
			else 
			{
				return 0;
			}
		}catch(Exception e) {
			System.out.println(e);
			return 0;
		}
	}
	public boolean updateUser(String name,int id)
	{
		String sql="update user set name=?,photo='null' where userid=?";
		try {
			PreparedStatement stmt1 = (DbUtil.con).prepareStatement(sql);
			stmt1.setString(1, name);
			stmt1.setInt(2, id);
			if(stmt1.executeUpdate()>0)
			{
				stmt1.close();
				return true;
			}
			else
			{
				return false;
			}
		}catch(Exception e) {
			System.out.println(e);
			return false;
		}
		
	}
	public boolean changePassword(int id,String password,String npassword,String cpassword)
	{
		String sql="select email from user where userid=?";
		try
		{
			PreparedStatement mystatement=(DbUtil.con).prepareStatement(sql);
			mystatement.setInt(1,id);
			ResultSet myres=mystatement.executeQuery();
			String email;
			if(myres.next())
			{
				email=myres.getString("email");
				String sql1="select pwd from login where email=?";
				PreparedStatement mystatement1=(DbUtil.con).prepareStatement(sql1);
				mystatement1.setString(1,email);
				ResultSet myres1=mystatement1.executeQuery();
				if(myres1.next())
				{
					String sql2="update login set pwd=? where email=?";
					PreparedStatement mystatement2=(DbUtil.con).prepareStatement(sql2);
					mystatement2.setString(1,npassword);
					mystatement2.setString(2,email);
					if(mystatement2.executeUpdate()>0)
					{
						System.out.println("sucess");
						return true;
					}
					else
					{
						System.out.println("error0");
						return false;
					}
					
				}
				else
				{
					System.out.println("error1");
					return false;
				}
				
			}
			else
			{
				System.out.println("error2");
				return false;
			}
			
		}
			catch(Exception e)
			{
				System.out.println(e);
				return false;
			}
		
	}
	public boolean logout()
	{
		boolean res = mapData.removeMapData(UserInfo.userid+"");
		UserInfo.userid = 0;
		return res;		
	}

	public String findUserid(String email) 
	{
		String uID = dbUtil.findOneColumn("userid", "user", "email", email);
		System.out.println(uID);
		return uID;
	}
}
