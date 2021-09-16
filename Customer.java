package test;


import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Customer extends User 
{
	
	
	static final String user="admin";
	static final String password="12345678";
	static final String db_url="jdbc:oracle:thin:@projectdatabase.cka1ypgyovgi.us-east-2.rds.amazonaws.com:1521:ORCL";
	
	protected double balance;
	Scanner scan=new Scanner(System.in);
	
	public Customer(String user,String i) 
	{
		super(user,i);
		this.balance=0;
	}
	
	public boolean approval()
	{
		return approval;
	}
	public String getUser()
	{
		return this.user;
	}
	
	
	public String getLogin(String user)
	{
		return this.user;
	}
	
	public int getPin(Connection conn,int ID)
	{
		int result=0;
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			Statement stmt=conn.createStatement();
			
			ResultSet rs=stmt.executeQuery("Select * from CUSTOMER WHERE ID="+ID);
			
			while(rs.next())
			{
				System.out.println("Pin: "+rs.getInt(3));
			}
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Unable to load driver class");
		}
		catch(SQLException e)
		{
			System.out.println("SQL Exception");
		}
		return result;
	}
	
	
	
	public boolean login(Connection conn,String user1,String pin) throws IOException
	{
		boolean result=false;
		try
		{
			
			String myQuery=("Select* from Customer");
			
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
	
	public void pending(Connection conn,String user1,String pin1) throws ClassNotFoundException, SQLException
	{
		try
		{
			Statement stmt=conn.createStatement();
			String myQuery="INSERT INTO PENDINGAPPS (name,pin) VALUES ('"+user1+"','"+pin1+"')";
			PreparedStatement ps=conn.prepareStatement(myQuery);
			ResultSet rs=stmt.executeQuery(myQuery);
			System.out.println("Created Account Application");
		}
		catch(SQLException e)
		{
			System.out.println("User Account exists or is already pending");
		}
		
	}
	public void accounts(Connection conn,String user1) throws SQLException
	{
		try
		{
			Statement stmt=conn.createStatement();
			
			int custID=0;
			String myQuery2=("Select* from Customer WHERE name="+"'"+user1+"'");
			Statement stmt2=conn.createStatement();
			ResultSet rs=stmt2.executeQuery(myQuery2);
			while(rs.next())
			{
				custID=rs.getInt(1);
			}
			String myQuery="Select* FROM ACCOUNTS WHERE CUSTID="+"'"+custID+"'";
			ResultSet rs2=stmt.executeQuery(myQuery);
			while(rs2.next())
			{
				System.out.println("AccountID :"+rs2.getInt(1));
				System.out.print("\tType:"+rs2.getString(2));
				System.out.print("\tJoint :"+rs2.getString(3));
				System.out.print("\tBalance:"+rs2.getFloat(4));
				System.out.print("\tCustID: "+rs2.getInt(5));
				System.out.println();
			}
		}
		catch(SQLException e)
		{
			
		}
		
	}
	public void openAccount(Connection conn,String type,String joint,String user1)
	{
		try
		{
			int custID=0;
			String myQuery2=("Select* from Customer WHERE name="+"'"+user1+"'");
			Statement stmt2=conn.createStatement();
			ResultSet rs=stmt2.executeQuery(myQuery2);
			while(rs.next())
			{
				custID=rs.getInt(1);
			}
			String myQuery="INSERT INTO Accounts (TYPE,JOINT,BALANCE,CUSTID) VALUES ('"+type+"','"+joint+"',"+0.00+",'"+custID+"')";
			ResultSet rs2=stmt2.executeQuery(myQuery);
		}
		catch(SQLException e)
		{
			System.out.println("Invalid Input");
		}
		System.out.println("Account Created");
	}

	public void createAccount(Connection conn,int user1) throws IOException
	{
		try
		{
			String name="error";
			String pinc="error";
			Statement stmt=conn.createStatement();
			String myQuery2="Select* from PENDINGAPPS WHERE PENDID="+user1;
			Statement stmt2=conn.createStatement();
			ResultSet rs=stmt2.executeQuery(myQuery2);
			while(rs.next())
			{
				name=rs.getString(2);
				pinc=rs.getString(3);
			}
			String myQuery="INSERT INTO CUSTOMER(NAME,PASSWORD) VALUES('"+name+"','"+pinc+"')";
			PreparedStatement ps=conn.prepareStatement(myQuery);
			ResultSet rs2=stmt.executeQuery(myQuery);
		}
		catch(SQLException e)
		{
			
		}
	}
	
	
	
	
	public void deposit(Connection conn, String user1,int AccountID,double deposit) throws IOException
	{
		double balance=0;
		try
		{
		int custID=0;
		String myQuery=("Select* from Customer WHERE name="+"'"+user1+"'");
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery(myQuery);
		while(rs.next())
		{
			custID=rs.getInt(1);
		}
		
		String jointCheck="";
		Statement stmt2=conn.createStatement();
		String myQuery2="SELECT * FROM ACCOUNTS WHERE AccountID="+AccountID+" AND custID="+custID;
		ResultSet rs2=stmt.executeQuery(myQuery2);
		while(rs2.next())
		{
			jointCheck=rs2.getString(3);
			balance=rs2.getFloat(4);
		}
				
		balance=balance+deposit;
		Statement stmt3=conn.createStatement();
		String myQuery3="UPDATE ACCOUNTS SET BALANCE="+balance+" WHERE custID="+custID+" AND AccountID="+AccountID;
		ResultSet rs3=stmt3.executeQuery(myQuery3);
		System.out.println("New Balance: "+balance);
		if(jointCheck.equalsIgnoreCase("Y"))
		{
			int getJointCust=0;
			int getJointCust2=0;
			Statement stmt4=conn.createStatement();
			String myQuery4="SELECT * FROM JOINTS WHERE ACCOUNTID="+AccountID+" OR ACCOUNTIDT="+AccountID;
			ResultSet rs4=stmt4.executeQuery(myQuery4);
			while(rs4.next())
			{
				getJointCust2=rs4.getInt(3);
				getJointCust=rs4.getInt(2);
			}
			if(getJointCust==AccountID)
			{
				double secondBalance=0;
				String myQuery6="SELECT * FROM ACCOUNTS WHERE AccountID="+getJointCust2;
				ResultSet rs6=stmt.executeQuery(myQuery6);
				while(rs6.next())
			{
				secondBalance=rs6.getFloat(4);
			}
				secondBalance=secondBalance+deposit;
				Statement stmt5=conn.createStatement();
				String myQuery5="UPDATE ACCOUNTS SET BALANCE="+secondBalance+" WHERE ACCOUNTID="+getJointCust2;
				ResultSet rs5=stmt.executeQuery(myQuery5);
			}
			else
			{
				double secondBalance=0;
				String myQuery6="SELECT * FROM ACCOUNTS WHERE AccountID="+getJointCust;
				ResultSet rs6=stmt.executeQuery(myQuery6);
				while(rs6.next())
				{
					secondBalance=rs6.getFloat(4);
				}
				secondBalance=secondBalance+deposit;
				Statement stmt7=conn.createStatement();
				String myQuery7="UPDATE ACCOUNTS SET BALANCE="+secondBalance+" WHERE ACCOUNTID="+getJointCust;
				ResultSet rs7=stmt7.executeQuery(myQuery7);
			}
		}
		}
		catch(SQLException e)
		{
			
		}
		
		
	}
	public void withdraw(Connection conn,String user1,int AccountID,double withdraw) throws IOException
	{
		double balance=0;
		try
		{
		int custID=0;
		String myQuery=("Select* from Customer WHERE name="+"'"+user1+"'");
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery(myQuery);
		while(rs.next())
		{
			custID=rs.getInt(1);
		}
		
		String jointCheck="";
		Statement stmt2=conn.createStatement();
		String myQuery2="SELECT * FROM ACCOUNTS WHERE AccountID="+AccountID+" AND custID="+custID;
		ResultSet rs2=stmt.executeQuery(myQuery2);
		while(rs2.next())
		{
			jointCheck=rs2.getString(3);
			balance=rs2.getFloat(4);
		}
				
		balance=balance-withdraw;	
		if(balance>=0)
		{
			Statement stmt3=conn.createStatement();
			String myQuery3="UPDATE ACCOUNTS SET BALANCE="+balance+" WHERE custID="+custID+" AND AccountID="+AccountID;
			ResultSet rs3=stmt3.executeQuery(myQuery3);
			System.out.println("\nYour new Balance: "+balance);
			if(jointCheck.equalsIgnoreCase("Y"))
			{
				int getJointCust=0;
				int getJointCust2=0;
				Statement stmt4=conn.createStatement();
				String myQuery4="SELECT * FROM JOINTS WHERE ACCOUNTID="+AccountID+" OR ACCOUNTIDT="+AccountID;
				ResultSet rs4=stmt4.executeQuery(myQuery4);
				while(rs4.next())
				{
					getJointCust2=rs4.getInt(3);
					getJointCust=rs4.getInt(2);
				}
				
				
				if(getJointCust==AccountID)
				{
					double secondBalance=0;
					String myQuery6="SELECT * FROM ACCOUNTS WHERE AccountID="+getJointCust2;
					ResultSet rs6=stmt.executeQuery(myQuery6);
					while(rs6.next())
				{
					secondBalance=rs6.getFloat(4);
				}
					secondBalance=secondBalance-withdraw;
					Statement stmt5=conn.createStatement();
					String myQuery5="UPDATE ACCOUNTS SET BALANCE="+secondBalance+" WHERE ACCOUNTID="+getJointCust2;
					ResultSet rs5=stmt.executeQuery(myQuery6);
					System.out.println(secondBalance+" for "+getJointCust2);
				}
				else
				{
					double secondBalance=0;
					String myQuery6="SELECT * FROM ACCOUNTS WHERE AccountID="+getJointCust;
					ResultSet rs6=stmt.executeQuery(myQuery6);
					while(rs6.next())
					{
						secondBalance=rs6.getFloat(4);
					}
					secondBalance=secondBalance-withdraw;
					Statement stmt5=conn.createStatement();
					String myQuery5="UPDATE ACCOUNTS SET BALANCE="+secondBalance+" WHERE ACCOUNTID="+getJointCust;
					ResultSet rs5=stmt5.executeQuery(myQuery5);
				}
			}
		}
		else
		{
			System.out.println("Cannot withdraw more than your account!");
		}
		}
		catch(SQLException e)
		{
			
		}
	}
	
	
	public double getBalance(Connection conn,String user1,int accountID) throws IOException
	{
		double balance=0;
		try
		{
		int custID=0;
		String myQuery=("Select* from Customer WHERE name="+"'"+user1+"'");
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery(myQuery);
		while(rs.next())
		{
			custID=rs.getInt(1);
		}
		
		
		Statement stmt2=conn.createStatement();
		String myQuery2="SELECT * FROM ACCOUNTS WHERE AccountID="+accountID+" AND custID="+custID;
		ResultSet rs2=stmt.executeQuery(myQuery2);
		while(rs2.next())
		{
			balance=rs2.getFloat(4);
		}
		}
		catch(SQLException e)
		{
			
		}
		return balance;
	}
	public void transfer(Connection conn,String sender,String recipient,int accountID,double transfer) throws IOException
	{
		int recipientID=0;
		if(getBalance(conn,sender,accountID)>transfer)
		{
		try
		{
			Statement stmt=conn.createStatement();
			
			int custID=0;
			String myQuery2=("Select* from Customer WHERE name="+"'"+recipient+"'");
			Statement stmt2=conn.createStatement();
			ResultSet rs=stmt2.executeQuery(myQuery2);
			while(rs.next())
			{
				custID=rs.getInt(1);
			}
			String myQuery="Select* FROM ACCOUNTS WHERE CUSTID="+"'"+custID+"'";
			ResultSet rs2=stmt.executeQuery(myQuery);
			System.out.println("Choose an account ID:");
			while(rs2.next())
			{
				System.out.println("Recipient AccountID :"+rs2.getInt(1));
				System.out.println("Type:"+rs2.getString(2));
				recipientID=rs2.getInt(1);
			}
			int choose=scan.nextInt();
			
			withdraw(conn,sender,accountID,transfer);
			deposit(conn,recipient,choose,transfer);
			
		}
		catch(SQLException e)
		{
			
		}
		}
		else
		{
			System.out.println("Insufficient Funds");
		}
	}
	public void jointSignUp(Connection conn,int accountID,int secondAccountID)
	{
		try
		{	
			String type="";
			String check="";
			Statement stmt=conn.createStatement();
			String myQuery="SELECT * FROM ACCOUNTS WHERE ACCOUNTID="+accountID;
			ResultSet rs=stmt.executeQuery(myQuery);
			while(rs.next())
			{
				type=rs.getString(2);
				check=rs.getString(3);
			}
			String typeT="";
			Statement stmt2=conn.createStatement();
			String myQuery2="SELECT * FROM ACCOUNTS WHERE ACCOUNTID="+secondAccountID;
			ResultSet rs2=stmt2.executeQuery(myQuery2);
			while(rs2.next())
			{
				typeT=rs2.getString(2);
			}
			if(type.equals(typeT)&&check.equals("N"))
			{
				Statement stmt3=conn.createStatement();
				String myQuery3="INSERT INTO JOINTPENDING(ACCOUNTID,ACCOUNTIDTWO) VALUES("+accountID+","+secondAccountID+")";
				ResultSet rs3=stmt2.executeQuery(myQuery3);
				System.out.println("Joint application sent");
			}
			else
			{
				System.out.println("Both accounts must be the same type for a joint account and cannot be a joint account already.");
			}
			
		}
		catch(SQLException e)
		{
			
		}
	}
		
		
		
//		try(FileWriter fw=new FileWriter("Files/info.text",true);
//		BufferedWriter bw=new BufferedWriter(fw);
//		PrintWriter out=new PrintWriter(bw))
//		{
//			out.println(user+pin);
//			fw.close();
//			System.out.println("\n Account Created");
//		}
//		catch(IOException e)
//		{
//			
//		}
//		
		
		
		
		
		
		
//		
//		fw=new FileWriter("Files/info.text");
//		Scanner scanning = new Scanner(new FileInputStream("Files/info.text"));
//		String[] writeMe= {user,pin};
//		for(int i=0;i<2;i++)
//		{
//			if(scanning.hasNextLine())
//			{
//				fw.write(System.getProperty("line.separator"));
//			}
//			fw.write(writeMe[i]);
//		}
//		fw.close();
//		System.out.println("\n Account Created");
		
		
//		Scanner create=new Scanner(System.in);
//		Scanner read =new Scanner(new FileInputStream("Files/info.text"));
//		fw=new FileWriter("Files/info,text",true);
//		if(read.hasNextLine())
//		{
//			fw.write(System.getProperty("line.separator"));
//			fw.write(user+pin);
//		}
//		else
//		{
//			fw.write(user+pin);
//		}
//		fw.close();
	
	
	

}
