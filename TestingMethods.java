package test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class TestingMethods 
{
	static final String user="admin";
	static final String password="12345678";
	static final String db_url="jdbc:oracle:thin:@projectdatabase.cka1ypgyovgi.us-east-2.rds.amazonaws.com:1521:ORCL";
	
	public static void main(String[] args) throws SQLException 
	{
//		boolean result=false;
//		String user1="jason";
//		String pin="1234";
//		
//		
//		Connection conn=DriverManager.getConnection(db_url,user,password);
//		try
//		{
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			
//			
//			Statement stmt=conn.createStatement();
//			
//			String myQuery=("Select* from Customer");
//			
//			
//			ResultSet rs=stmt.executeQuery(myQuery);
//			while(rs.next())
//			{
//				String verifyUser=rs.getString(2);
//				String verifyPin=rs.getString(3);
//				if(verifyUser.equals(user1)&&verifyPin.equals(pin))
//				{
//					result=true;
//				}
//			}
//		}
//		catch(ClassNotFoundException e)
//		{
//			System.out.println("Unable to load driver class");
//		}
//		catch(SQLException e)
//		{
//			System.out.println("SQL Exception");
//		}
//		System.out.println(result);
//		
//		try
//		{
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			Statement stmt=conn.createStatement();
//			String myQuery="INSERT INTO PENDINGAPPS (name,pin) VALUES ('"+a+"','"+b+"')";
//			PreparedStatement ps=conn.prepareStatement(myQuery);
//			ResultSet rs=stmt.executeQuery(myQuery);
//		}
//		catch(ClassNotFoundException e)
//		{
//			
//		}
//		try
//		{
//			Statement stmt=conn.createStatement();
//			
//			int custID=0;
//			String myQuery2=("Select * from Customer where name='"+user1+"'");
//			Statement stmt2=conn.createStatement();
//			ResultSet rs=stmt2.executeQuery(myQuery2);
//			while(rs.next())
//			{
//				System.out.println("test");
//				custID=rs.getInt(1);
//			}
//			String myQuery="Select * FROM ACCOUNTS WHERE CUSTID="+custID;
//			ResultSet rs2=stmt.executeQuery(myQuery);
//			while(rs2.next())
//			{
//				System.out.println("AccountID :"+rs2.getString(2));
//				System.out.println("Joint :"+rs2.getString(3));
//				System.out.println("CustID: "+rs2.getInt(4));
//			}
//		}
//		catch(SQLException e)
//		{
//			
//		}
//		

	}

}
