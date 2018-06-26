package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import model.GroupMembers;
import model.SplitExpense;
import persistance.Expense;
import persistance.Groups;
import persistance.User;
import persistance.BalanceLedger;
import persistance.Entity;

public class DbUtil 
{
	public static Connection con = null;
	public 	static void dbConnection()
	{
		String url = "jdbc:mysql://localhost:3306/spliteasy";
		String username = "root";
		String password = "pwd";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
			//DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	
	public boolean create(Entity obj)
	{
		String sql="";
		String res=obj.getEntity();
		System.out.println(res);
		if(res.equals("groups"))
		{
			try {
				Groups ob=(Groups) obj;
				sql="insert into groups values("+ob.getGrpid()+","+ob.getCuserid()+",'"+ob.getGname()+"','"+ob.getCreated_at()+"')";
				PreparedStatement stmt = (DbUtil.con).prepareStatement(sql);
				stmt.executeUpdate();
				System.out.println(sql);
			}catch(Exception e)
			{
				System.out.println(e);
				return false;
			}
		
		}
		else if(res.equals("user"))
		{
			try {
				User ob=(User) obj;
				sql="insert into user values("+ob.getUserid()+",'"+ob.getName()+"','"+ob.getEmail()+"','"+ob.getPhoto()+"')";
				PreparedStatement stmt = (DbUtil.con).prepareStatement(sql);
				stmt.executeUpdate();
				System.out.println(sql);
			}catch(Exception e)
			{
				System.out.println(e);
				return false;
			}
		
		}
		else if(res.equals("expense"))
		{
			try {
				Expense ob=(Expense) obj;
				sql="insert into expense values("+ob.getEid()+","+ob.getGrpid()+",'"+ob.getEname()+"','"+ob.getCreated_at()+"',"+ob.getPaid_by()+","+ob.getAmount()+")";
				PreparedStatement stmt = (DbUtil.con).prepareStatement(sql);
				stmt.executeUpdate();
				System.out.println(sql);
			}catch(Exception e)
			{
				System.out.println(e);
				return false;
			}
		
		}
		else if(res.equals("group_member"))
		{
			try {
				GroupMembers ob=(GroupMembers) obj;
				sql="insert into group_member values("+ob.getgId()+","+ob.getuserId()+")";
				PreparedStatement stmt = (DbUtil.con).prepareStatement(sql);
				stmt.executeUpdate();
				System.out.println(sql);
			}catch(Exception e)
			{
				System.out.println(e);
				return false;
			}
		
		}
		else if(res.equals("split_expense"))
		{
			try {
				System.out.println("Yaay");
				SplitExpense ob=(SplitExpense) obj;
				sql="insert into split_expense values("+ob.getEid()+","+ob.getUserid()+","+ob.getS_amt()+",'"+ob.getType()+"')";
				PreparedStatement stmt = (DbUtil.con).prepareStatement(sql);
				stmt.executeUpdate();
				System.out.println(sql);
			}catch(Exception e)
			{
				System.out.println(e);
				return false;
			}
		
		}
		else if(res.equals("balance_ledger"))
		{
			try {
				BalanceLedger ob=(BalanceLedger) obj;
				sql="insert into balance_ledger values("+ob.getFrom()+","+ob.getTo()+","+ob.getGrpid()+","+ob.getAmount()+") on duplicate key update amount=amount+"+ob.getAmount();
				PreparedStatement stmt = (DbUtil.con).prepareStatement(sql);
				stmt.executeUpdate();
				System.out.println(sql);
			}catch(Exception e)
			{
				System.out.println(e);
				return false;
			}
		}
		
		return true;
	}
	//Returns Only 1 entry
	public String findOneColumn(String colName, String entity, String key, String value)
	{
		String sql="";
		try {
			sql="select "+colName+" from "+entity+" where "+key+"='"+value+"'";
			System.out.println(sql);
			Statement stmt = con.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.next())
			{
				return rs.getString(1);
			}
			System.out.println(sql+"dwe");
		}catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
		
	}
	
	public String findOneColumn(String colName, String entity, String key, int value)
	{
		String sql="";
		try {
			sql="select "+colName+" from "+entity+" where "+key+"="+value+"";
			System.out.println(sql);
			Statement stmt = con.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			if(rs.next())
			{
				return rs.getString(1);
			}
			System.out.println(sql+"dwe");
		}catch(Exception e)
		{
			System.out.println(e);
		}
		return null;
	}
	
	public ArrayList<Entity> runQuery(String query,String entity)
	{
		System.out.println(entity);
		System.out.println(query);
		
		ArrayList<Entity> resultSet=new ArrayList<>();
		try {
			System.out.println(entity.equals("group_member"));
			System.out.println(DbUtil.con);
			Statement stmt = (DbUtil.con).createStatement();
			System.out.println(entity.equals("group_member"));
			ResultSet rs=stmt.executeQuery(query);
			if(entity.equals("user"))
			{
				while(rs.next())
				{
					User tEntity=new User();
					tEntity.setEmail(rs.getString("email"));
					tEntity.setName(rs.getString("name"));
					tEntity.setPhoto(rs.getString("photo"));
					tEntity.setUserid(rs.getInt("userid"));
					tEntity.setPswrd(null);
					resultSet.add((Entity)tEntity);
				}
				
			}
			else if(entity.equals("groups"))
			{
				while(rs.next())
				{
					Groups tEntity=new Groups();
					tEntity.setCreated_at(rs.getString("created_at"));
					tEntity.setCuserid(rs.getInt("cuserid"));
					tEntity.setGname(rs.getString("gname"));
					tEntity.setGrpid(rs.getInt("grpid"));
					resultSet.add((Entity)tEntity);
				}
			}
			else if(entity.equals("expense"))
			{
				while(rs.next())
				{
					Expense tEntity=new Expense();
					tEntity.setCreated_at(rs.getString("created_at"));
					tEntity.setAmount(rs.getDouble("amount"));
					tEntity.setEid(rs.getInt("eid"));
					tEntity.setEname(rs.getString("ename"));
					tEntity.setGrpid(rs.getInt("grpid"));
					resultSet.add((Entity)tEntity);
				}
			}
			else if(entity.equals("balance_ledger"))
			{
				while(rs.next())
				{
					BalanceLedger tEntity=new BalanceLedger();
					tEntity.setFrom(rs.getInt("from"));
					tEntity.setTo(rs.getInt("to"));
					tEntity.setAmount(rs.getDouble("amount"));
					tEntity.setGrpid(rs.getInt("grpid"));
					resultSet.add((Entity)tEntity);
				}
			}
			else if(entity.equals("group_member"))
			{
				System.out.println("Reached here");
				while(rs.next())
				{
					GroupMembers tEntity=new GroupMembers();
					tEntity.setuserId(rs.getString("userid"));
					tEntity.setgId(rs.getString("grpid"));
					resultSet.add((Entity)tEntity);
				}
			}
			else if(entity.equals("split_expense"))
			{
				while(rs.next())
				{
					System.out.println("Yaay");
					SplitExpense tEntity=new SplitExpense();
					tEntity.setEid(rs.getInt("eid"));
					tEntity.setS_amt(rs.getDouble("s_amt"));
					tEntity.setType(rs.getString("type"));
					tEntity.setUserid(rs.getInt("userid"));
					resultSet.add((Entity)tEntity);
				}
			}
		}catch(Exception e)
		{
			System.out.println(e);
		}
		return resultSet;
	}
}
