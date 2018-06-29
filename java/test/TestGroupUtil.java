package test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import util.GroupUtil;
import util.UserUtil;

public class TestGroupUtil {

static GroupUtil groupUtil = null;
	

	@BeforeClass
	public static void setup()
	{
		groupUtil = new GroupUtil();
		
	}
//	@Test
//	public void testCreate() {
//		boolean output = groupUtil.create("nokia1", "30", Arrays.asList("26","25"));
//		assertEquals(true,output);
//	}
//	

	@Test
	public void testAddMember() {
		boolean output = groupUtil.addMember("abc12@abc.com", "72");
		assertEquals(true,output);
	}

	@AfterClass
	public static void destroy()
	{
		groupUtil = null;
	}

}
