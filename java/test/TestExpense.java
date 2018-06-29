package test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import persistance.Expense;

public class TestExpense {

	static Expense testExpense = null;
	private static final double DELTA = 1e-15;
	@BeforeClass
	public static void setup()
	{
		testExpense = new Expense();
	}
	
	@Test
	public void testGetEid()
	{
		testExpense.setEid(11);
		assertEquals(11,testExpense.getEid());
	}
	
	@Test
	public void testGetGrpid()
	{
		testExpense.setGrpid(11);
		assertEquals(11,testExpense.getGrpid());
	}
	
	@Test
	public void testGetEname()
	{
		testExpense.setEname("guri");
		assertEquals("guri",testExpense.getEname());
	}
	
	@Test
	public void testGetCreated_at()
	{
		testExpense.setCreated_at("abc");
		assertEquals("abc",testExpense.getCreated_at());
	}
	
	@Test
	public void testGetPaid_by()
	{
		testExpense.setPaid_by(12);
		assertEquals(12,testExpense.getPaid_by());
	}
	
	
	@Test
	public void testGetAmount()
	{
		testExpense.setAmount(110.0000000000000001);
		assertEquals(110, testExpense.getAmount(),DELTA);
	}
	
	@AfterClass
	public static void destroy() 
	{
		testExpense = null;
	}
	
	
}
