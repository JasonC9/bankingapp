package test;

import java.sql.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert.*;

import test.Customer;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

public class JUnitTest2 
{
	static final String user="admin";
	static final String password="12345678";
	static final String db_url="jdbc:oracle:thin:@projectdatabase.cka1ypgyovgi.us-east-2.rds.amazonaws.com:1521:ORCL";
	
	Admin administrator=new Admin("jason","1234");
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
	public void testCustomer() throws ClassNotFoundException, IOException
	{	
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn=DriverManager.getConnection(db_url,user,password);
			assertTrue(administrator.adminLogin(conn,"admin", "admin123"));
		}
		catch(SQLException e)
		{
		}
	}
	@Test
	public void testDeposit() throws ClassNotFoundException, IOException
	{		try
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn=DriverManager.getConnection(db_url,user,password);
		
		administrator.deposit(conn,"jason",1,20);
		System.out.println("Test Deposit");
	}
	catch(SQLException e)
	{
	}
		

	}
	@Test
	public void testAccounts() throws IOException, ClassNotFoundException
	{
		try
		{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn=DriverManager.getConnection(db_url,user,password);
		administrator.accounts(conn,"jason");
		System.out.println("Test balance");
		}
		catch(SQLException e)
		{
			
		}
	}

}
