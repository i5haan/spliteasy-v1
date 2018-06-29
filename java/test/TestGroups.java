package test;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import persistance.Groups;

public class TestGroups {

	static Groups testGroups = null;
	
	@BeforeClass
	public static void setup()
	{
		testGroups = new Groups();
	}
	
	@Test
	public void testGetGrpid() {
		testGroups.setGrpid(11);
		assertEquals(11,testGroups.getGrpid());
	}
	
	@Test
	public void testGetCuserid() {
		testGroups.setCuserid(21);;
		assertEquals(21,testGroups.getCuserid());
	}
	@Test
	public void testGetGname() {
		testGroups.setGname("abc");;
		assertEquals("abc",testGroups.getGname());
	}
	
	@Test
	public void testCreatedAt() {
		testGroups.setCreated_at("22-02-2018");
		assertEquals("22-02-2018",testGroups.getCreated_at());
	}
	
	
	@AfterClass
	public static void destroy()
	{
		testGroups = null;
	}
	
}
