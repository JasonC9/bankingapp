package test;

import java.io.IOException;
import java.sql.Connection;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Employee extends Customer
{
	public Employee(String user, String pin) 
	{
		super(user, pin);
	}
	public void view(Connection conn,String user2) throws IOException
	{
		int custID=0;
		try 
		{
			String myQueryTest="SELECT * FROM CUSTOMER WHERE NAME=('"+user2+"')";
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(myQueryTest);
			while(rs.next())
			{
				custID=rs.getInt(1);
				System.out.println(rs.getString(2));
			}
			String myQuery2="SELECT* FROM ACCOUNTS WHERE CUSTID="+custID;
			Statement stmt2=conn.createStatement();
			ResultSet rs2=stmt2.executeQuery(myQuery2);
			while(rs2.next())
			{
				System.out.println("----------------------------");
				System.out.println("Account ID: "+rs2.getInt(1));
				System.out.println("Account Type: "+rs2.getString(2));
				System.out.println("Joint Status: "+rs2.getString(3));
				System.out.println("Balance: "+rs2.getFloat(4));
				System.out.println("----------------------------");
			}
		}
		catch(SQLException e)
		{
			System.out.println("This user has not opened an account or does not exist");
		}
		
	}
	public boolean approve()
	{
		approval=true;
		return approval;
	}
	public boolean deny()
	{
		approval=false;
		return approval;
	}
	public boolean employeeLogin(Connection conn,String user1, String pin)throws IOException
	{
	boolean result=false;
	try
	{
		String myQuery="Select* from EMPLOYEE";
		
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery(myQuery);
		while(rs.next())
		{
			String verifyUser=rs.getString(2);
			String verifyPin=rs.getString(3);
			if(verifyUser.equals(user1)&&verifyPin.equals(pin))
			{
				result=true;
			}
		}
	}
	catch(SQLException e)
	{
		System.out.println("SQL Exception");
	}
	return result;
}
	
	public String getPin(String user) throws IOException
	{
		return null;
	}
	public void getApps(Connection conn) throws IOException
	{
		try
		{
		String myQuery="Select* from PENDINGAPPS";
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery(myQuery);
		while(rs.next())
		{
			System.out.println("ID: "+rs.getInt(1));
			System.out.println("Name: "+rs.getString(2));
			System.out.println("Pin:"+ rs.getString(3));
		}
		}
		catch(SQLException e)
		{
			
		}
	}
	public void getJointApps(Connection conn)
	{
		try
		{
			int firstAcc=0;
			int secondAcc=0;
			String myQuery="Select* from JOINTPENDING";
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(myQuery);
			while(rs.next())
			{
				System.out.println("Joint ID: "+rs.getInt(1));
				firstAcc=rs.getInt(2);
				System.out.println("Account ID: "+rs.getInt(2));
				secondAcc=rs.getInt(3);
				System.out.println("Second Account ID: "+rs.getInt(3));
				System.out.println("----------------------------");
				
				
				String myQuery2="Select * FROM ACCOUNTS WHERE ACCOUNTID="+firstAcc;
				Statement stmt2=conn.createStatement();
				ResultSet rs2=stmt2.executeQuery(myQuery2);
				while(rs2.next())
				{
					System.out.println("ID: "+rs2.getInt(1));
					System.out.println("Type: "+rs2.getString(2));
					System.out.println("Joint: "+rs2.getString(3));
					System.out.println("Balance: "+rs2.getFloat(4));
					System.out.println("CustID: "+rs2.getInt(5));
					
					String myQuery3="Select * FROM ACCOUNTS WHERE ACCOUNTID="+secondAcc;
					Statement stmt3=conn.createStatement();
					ResultSet rs3=stmt3.executeQuery(myQuery3);
					while(rs3.next())
					{
						System.out.println("ID: "+rs3.getInt(1));
						System.out.println("Type: "+rs3.getString(2));
						System.out.println("Joint: "+rs3.getString(3));
						System.out.println("Balance: "+rs3.getFloat(4));
						System.out.println("CustID: "+rs3.getInt(5));
						System.out.println("----------------------------");
						System.out.println("----------------------------");
					}
					
				}
			}
			
		
		}
		catch(SQLException e)
		{
			System.out.println("Something wrong");
		}
	}
	public void createJointApp(Connection conn,int jointID)
	{
		int firstAccID=0;
		int secondAccID=0;
		try
		{
			String myQuery="SELECT * FROM JOINTPENDING WHERE JOINTID="+jointID;
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(myQuery);
			while(rs.next())
			{
				firstAccID=rs.getInt(2);
				secondAccID=rs.getInt(3);
			}
			double firstBal=0;
			String myQuery2="SELECT * FROM ACCOUNTS WHERE ACCOUNTID="+firstAccID;
			Statement stmt2=conn.createStatement();
			ResultSet rs2=stmt2.executeQuery(myQuery2);
			while(rs2.next())
			{
				firstBal=rs2.getFloat(4);
			}
			double secondBal=0;
			String myQuery3="SELECT * FROM ACCOUNTS WHERE ACCOUNTID="+secondAccID;
			Statement stmt3=conn.createStatement();
			ResultSet rs3=stmt3.executeQuery(myQuery3);
			while(rs3.next())
			{
				secondBal=rs3.getFloat(4);
			}
			double finalBal=firstBal+secondBal;
			
			String myQuery4="UPDATE ACCOUNTS SET JOINT='Y', BALANCE="+finalBal+" WHERE ACCOUNTID="+firstAccID+" OR ACCOUNTID="+secondAccID;
			Statement stmt4=conn.createStatement();
			ResultSet rs4=stmt4.executeQuery(myQuery4);
			
			String myQueryAdd="INSERT INTO JOINTS(ACCOUNTID,ACCOUNTIDT) VALUES("+firstAccID+","+secondAccID+")";
			Statement stmtAdd=conn.createStatement();
			ResultSet rsAdd=stmtAdd.executeQuery(myQueryAdd);
			
			String myQuery5="DELETE FROM JOINTPENDING WHERE JOINTID="+jointID;
			Statement stmt5=conn.createStatement();
			ResultSet rs5=stmt5.executeQuery(myQuery5);
		}
		
		catch(SQLException e)
		{
			
		}
	}
	
	public void deletepending(Connection conn,int user) throws IOException
	{
		try
		{
			String myQuery="DELETE FROM PENDINGAPPS WHERE PENDID='"+user+"'";
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(myQuery);
		}
		catch(SQLException e)
		{
			
		}
	}
}
