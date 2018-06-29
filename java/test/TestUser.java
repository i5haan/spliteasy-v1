package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import persistance.User;

public class TestUser {

	static User testUser = null;
	
	@BeforeClass
	public static void setup()
	{
		testUser = new User();
	}

	@Test
	public void testGetUserid()
	{
		testUser.setUserid(11);
		assertEquals(11,testUser.getUserid());
	}
	
	@Test
	public void testGetName()
	{
		testUser.setName("abc");
		assertEquals("abc",testUser.getName());
	}
	
	@Test
	public void testGetEmail()
	{
		testUser.setEmail("def");
		assertEquals("def",testUser.getEmail());
	}
	
	@Test
	public void testGetPhoto()
	{
		testUser.setPhoto("");
		assertEquals("",testUser.getPhoto());
	}
	
	@Test
	public void testGetPswrd()
	{
		testUser.setPswrd("1234");
		assertEquals("1234",testUser.getPswrd());
	}
	
	@AfterClass
	public static void destroy()
	{
		testUser = null;
	}
	
}
