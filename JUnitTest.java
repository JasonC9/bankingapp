package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class JUnitTest 
{
	@BeforeClass
	public static void setupBeforeClass() throws Exception
	{
		System.out.println("Before Class");
	}
	@AfterClass
	public static void teadDownAfterClass() throws Exception
	{
		System.out.println("After Class");
	}
	@Test
	public void test() 
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testCustomer()
	{
		Customer c=new Customer("jason","1234");
		assertNotNull(c.getClass());
	}
	

}
