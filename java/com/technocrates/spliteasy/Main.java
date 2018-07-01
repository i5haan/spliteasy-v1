package com.technocrates.spliteasy;

import java.util.ArrayList;

import model.GroupMembers;
import model.Message;
import persistance.BalanceLedger;
import persistance.Entity;
import util.DbUtil;
import util.ExpenseUtil;
import util.GroupUtil;

public class Main {
	public static void main(String arg[])
	{
		DbUtil.dbConnection();
		GroupUtil grouputil=new GroupUtil();
//		ExpenseUtil e=new ExpenseUtil();
//		ArrayList<Integer> ratios=new ArrayList<>();
//		ratios.add(1);
//		ratios.add(1);
//		ratios.add(2);
//		boolean res=e.create(72, "Hello Whats Up", 26, 800,ratios);
//		boolean res=e.deleteExpence("130",107);
//		System.out.println(res);
//		System.out.println(e.showAllSettleUP(7));
		Message m=grouputil.deleteGroup("77");
		System.out.println(m.getMessage());
	}
}
