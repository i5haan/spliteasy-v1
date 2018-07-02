package com.technocrates.spliteasy;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.GroupMembers;
import model.Message;
import persistance.BalanceLedger;
import persistance.Entity;
import util.DbUtil;
import util.ExpenseUtil;
import util.GroupUtil;
import util.UserUtil;

public class Main {
	public static void main(String arg[])
	{
		DbUtil.dbConnection();
		
		try {
			Statement stmt = (DbUtil.con).createStatement();
			String sql="delete from balance_ledger";
			stmt.executeUpdate(sql);
			sql="delete from groups";
			stmt.executeUpdate(sql);
			sql="delete from login";
			stmt.executeUpdate(sql);
			sql="delete from user";
			stmt.executeUpdate(sql);
			sql="delete from expense";
			stmt.executeUpdate(sql);
			sql="delete from split_expense";
			stmt.executeUpdate(sql);
			
		}catch(Exception e) {
			System.out.println(e);
		}
		
		UserUtil userutil=new UserUtil();
		userutil.create("Ishaan Kohli", "kohliishan@hotmail.com", "123456");
		userutil.create("Gurpreet", "gurpreetmann2293@gmail.com", "123456");
		userutil.create("Gyanesh", "gyanmarkale@gmail.com", "123456");
		userutil.create("Hardeep Kaur", "hardeepkaur2331@gmail.com", "123456");
	}
}
