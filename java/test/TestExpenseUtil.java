package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import model.SettleUp;
import persistance.Entity;
import util.DbUtil;
import util.ExpenseUtil;

public class TestExpenseUtil {

	static ExpenseUtil expenseUtil = null;
	public static final double dlf =1e-20;
	@BeforeClass
	public static void setup()
	{
		expenseUtil = new ExpenseUtil();
		
	}
	
	@Test
	public void testCreate() 
	{
		boolean output = expenseUtil.create(72, "taxi", 25, 700, Arrays.asList(1,2,3));
		assertEquals(true,output);
	}
	
	@Test
	public void testSettleupIndividiualInGroup() 
	{
		SettleUp output = expenseUtil.settleupInvidiualInGroup(72, 25, 26);
		assertEquals(26,output.getPaidById(),dlf);
	}
	
	@Test
	public void testSplitExpense() 
	
	{
		DbUtil dbUtil=new DbUtil();
	String sql="select * from group_member where grpid="+72+" order by userid";
	
	ArrayList<Entity> ulist = new ArrayList<>();
	ulist=dbUtil.runQuery(sql, "group_member");
		
		boolean output = expenseUtil.splitExpence("114",ulist, 600, 26, 72,expenseUtil.splitAmnt(Arrays.asList(1,2,3),600));
		assertEquals(true,output);
	}
	
	@Test
	public void testshowSettleUpUtil() 
	{
		SettleUp output = expenseUtil.showSettleUpUtil(72, 25, 26);
		assertEquals(26,output.getPaidById());
	}
	
	@AfterClass
	public static void destroy()
	{
		expenseUtil = null;
	}

}
