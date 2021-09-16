package test;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;

public class Admin extends Employee
{

	public Admin(String user, String pin) 
	{
		super(user, pin);
	}
	
	public boolean adminLogin(Connection conn,String user1,String pin) throws IOException
	{
		boolean result=false;
		try
		{
			String myQuery=("Select* from ADMIN");
			
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
}