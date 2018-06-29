package test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import model.Message;
import util.UserUtil;



public class TestUserUtil {

	static UserUtil userUtil = null;
	
	@BeforeClass
	public static void setup()
	{
		userUtil = new UserUtil();
		
	}
	
	@Test
	public void testCreate() {
		int output = userUtil.create("abc1", "abc12@abc.com", "1");
		assertEquals(true,output);
	}

	@Test
	public void testValidate() {
		int output = userUtil.validate("abc1@abc.com", "1111");
		assertEquals(30,output);
	}
	
//	@Test
//	public void testUpdateUser() {
//		int output = userUtil.updateUser(name, id)
//		assertEquals(30,output);
//	}
//	
	@Test
	public void testChangePassword() {
		boolean output = userUtil.changePassword(30,"1111", "1112", "1112");
		assertEquals(true,output);
	}
	

	@AfterClass
	public static void destroy()
	{
		userUtil = null;
	}

}
