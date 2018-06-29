package test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import persistance.BalanceLedger;

public class TestBalanceLedger {

	static BalanceLedger testBalanceLedger = null;
	public static final double del = 1e-15;
	
	@BeforeClass
	public static void setup()
	{
		testBalanceLedger = new BalanceLedger();
	}
	
	@Test
	public void testGetFrom()
	{
		testBalanceLedger.setFrom(76);
		assertEquals(76,testBalanceLedger.getFrom());
	}
	
	@Test
	public void testGetTo()
	{
		testBalanceLedger.setTo(76);
		assertEquals(76,testBalanceLedger.getTo());
	}
	
	@Test
	public void testGetGrpid()
	{
		testBalanceLedger.setGrpid(76);
		assertEquals(76,testBalanceLedger.getGrpid());
	}
	
	@Test
	public void testGetAmount()
	{
		testBalanceLedger.setAmount(76);
		assertEquals(76,testBalanceLedger.getAmount(),del);
	}
	
	@AfterClass
	public static void destroy()
	{
		testBalanceLedger = null;
	}
	
	
}
