package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminDaoImplement implements AdminDao<Admin>
{
	public boolean adminLogin(Connection conn, String user1, String pin) 
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
