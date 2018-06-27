package com.technocrates.spliteasy;

import java.util.ArrayList;

import model.GroupMembers;
import persistance.BalanceLedger;
import persistance.Entity;
import util.DbUtil;
import util.ExpenseUtil;

public class Main {
	public static void main(String arg[])
	{
		ExpenseUtil e=new ExpenseUtil();
//		boolean res=e.create(7, "Hello Whats Up", 26, 500);
		System.out.println(e.showAllSettleUP(7));
	}
}
