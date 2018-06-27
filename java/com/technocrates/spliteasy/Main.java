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
		ArrayList<Integer> ratios=new ArrayList<>();
		ratios.add(1);
		ratios.add(1);
		ratios.add(2);
		boolean res=e.create(72, "Hello Whats Up", 26, 800,ratios);
//		System.out.println(e.showAllSettleUP(7));
	}
}
