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
		boolean res=e.create(7, "Chal jaa yaar", 26, 4000);
		System.out.println(res);
	}
}
