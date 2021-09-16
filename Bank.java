package test;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Bank 
{
	static int directLine=0;
	static int saveLine;
	static boolean check=false;
	private static AdminDao<Admin> admininistrator1;
	
	static final String user="admin";
	static final String password="12345678";
	static final String db_url="jdbc:oracle:thin:@projectdatabase.cka1ypgyovgi.us-east-2.rds.amazonaws.com:1521:ORCL";
	

	public static void main(String[] args) throws IOException, SQLException 
	{
		Scanner scan=new Scanner(System.in);
		int returning=1;
		while(returning!=2)
		{
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn=DriverManager.getConnection(db_url,user,password);
		// TODO Auto-generated method stub
		int menu;
		System.out.println("Welcome! Please select a choice below");
		System.out.println("1.User Login\n2.Create an account\n3.Employee Login\n4.Admin Login\n0 to exit the application");
		menu=scan.nextInt();
		switch(menu)
		{
			case 1: System.out.println("Enter your username");
				String user=scan.next();
				System.out.println("Enter your password");
				String pin=scan.next();
				Customer a=new Customer(user,pin);
				boolean verify=a.login(conn,user,pin);
				if(verify==true)
				{
					while(menu!=0)
					{
					System.out.println("Welcome "+user);
					System.out.println("What would you like to do?\n1.Choose an account\n2.Open an account\n0 to exit");
					menu=scan.nextInt();
					switch(menu)
					{
					case 1:
						System.out.println("Choose your account\n");
						a.accounts(conn,user);
						int accountID=scan.nextInt();
						System.out.println("\nWhat would you like to to?\n1.Deposit\n2.Withdraw\n3.transfer\n4.Sign up for Joint\n0 to exit");
						menu=scan.nextInt();
							switch(menu)
							{
							case 1: System.out.println("Put in the amount you want to deposit");
							double deposit=scan.nextFloat();
							a.deposit(conn, user,accountID, deposit);
							break;
							case 2:System.out.println("Put in the amount you want to withdraw");
							double withdraw=scan.nextFloat();
							a.withdraw(conn,user,accountID, withdraw);
							break;
							case 3:System.out.println("Give us the recipient");
							String recipient=scan.next();
							System.out.println("What is the amount you want to transfer?");
							double transfer=scan.nextFloat();
							a.transfer(conn,user,recipient,accountID,transfer);
							break;
							case 4:System.out.println("Give us the AccountID of who you want to open a joint account with.\nThey can find their Account ID by logging in");
							int sendID=scan.nextInt();
							a.jointSignUp(conn, accountID, sendID);
							}
						break;
					case 2:
						String Choice1="Checking";
						String Choice2="Savings";
						System.out.println("What kind of account would you like?\n1:Checking\n2:Savings");
						int typeChoice=scan.nextInt();
						if(typeChoice==1)
						{
							a.openAccount(conn,Choice1,"N",user);
						}
						if(typeChoice==2)
						{
							a.openAccount(conn,Choice2,"N",user);
						}
						
					}
					}
					
				}
				else
				{
					System.out.println("Unable to find "+user);
				}
				
				
				break;
			case 2: System.out.println("Create a username");
			String user1=scan.next();
			System.out.println("Enter a password");
			String pin1=scan.next();
			Customer b=new Customer(user1,pin1);
			b.pending(conn,user1, pin1);
			break;
			case 3: System.out.println("Enter user");
			String user2=scan.next();
			System.out.println("Enter password");
			String pass=scan.next();
			Employee e=new Employee(user2,pass);
			if(e.employeeLogin(conn,user2, pass)==true)
			{
				System.out.println("Successful Login.\n Welcome "+user2);
			while(menu!=0)
			{
				System.out.println("What would you like to do?\n1.View Accounts\n2.Bank Applications\n3.Joint Applications\n 0 to exit");
				menu=scan.nextInt();
				switch(menu)
				{
				case 1: 
						System.out.println("Type in their user");
						String selectUser=scan.next();
						e.view(conn,selectUser);
						break;
				case 2: e.getApps(conn);
						System.out.println("Select an ID");
						int selectAccount=scan.nextInt();
						System.out.println("1.Approve\n2.Deny");
						int appordeny=scan.nextInt();
						if(appordeny==1)
						{
							e.createAccount(conn,selectAccount);
							e.deletepending(conn,selectAccount);
						}
						else
						{
							
							System.out.println("Denied Account");
							e.deletepending(conn, selectAccount);
						}
						break;
				case 3: e.getJointApps(conn);
				System.out.println("Select a Joint Application ID");
				int jointInput=scan.nextInt();
				System.out.println("1.Approve\n2.Deny");
				int jointDecision=scan.nextInt();
				if(jointDecision==1)
				{
					e.createJointApp(conn,jointInput);
					System.out.println("Created joint application");
				}
				break;
				}
			}
			}
			else
			{
				System.out.println("Wrong Login");
			}
			break;
			case 4: System.out.println("Enter user");
			String admin=scan.next();
			System.out.println("Enter password");
			String passAdmin=scan.next();
			Admin administrator=new Admin(admin,passAdmin);
			if(administrator.adminLogin(conn,admin,passAdmin)==true)
			{
				System.out.println("Successful Login.\n Welcome "+admin);
				while(menu!=0)
				{
				System.out.println("What would you like to do next?\n1.Withdraw money from an account\n2.Deposit money into an account\n3.View an account\n4.Bank Applications\n5.Joint Applications\n0 to exit");
				menu=scan.nextInt();
				switch(menu)
				{
					case 1:
						System.out.println("What is the user you are looking for");
						String adminInp=scan.next();
						System.out.println("What is the amount you want to withdraw?");
						double withdraw2=scan.nextFloat();
						administrator.accounts(conn, adminInp);
						System.out.println("Select an Account ID:");
						int selectID=scan.nextInt();
						administrator.withdraw(conn, adminInp, selectID, withdraw2);
						
						break;
					case 2:
						System.out.println("What is the user you are looking for");
						String adminInp2=scan.next();
						System.out.println("What is the amount you want to deposit?");
						double deposit2=scan.nextFloat();
						administrator.accounts(conn, adminInp2);
						System.out.println("Select an Account ID:");
						int selectID2=scan.nextInt();
						administrator.deposit(conn,adminInp2,selectID2,deposit2);
						break;
					case 3:
						System.out.println("Type in their user");
						String selectUser=scan.next();
						administrator.view(conn,selectUser);
						break;
					case 4:
						administrator.getApps(conn);
						System.out.println("Select an ID");
						int selectAccount=scan.nextInt();
						System.out.println("1.Approve\n2.Deny");
						int appordeny=scan.nextInt();
						if(appordeny==1)
						{
							administrator.createAccount(conn,selectAccount);
							administrator.deletepending(conn,selectAccount);
						}
						else
						{
							System.out.println("Denied Account");
							administrator.deletepending(conn, selectAccount);
						}
						break;
					case 5:administrator.getJointApps(conn);
					System.out.println("Select a Joint Application ID");
					int jointInput=scan.nextInt();
					System.out.println("1.Approve\n2.Deny");
					int jointDecision=scan.nextInt();
					if(jointDecision==1)
					{
						administrator.createJointApp(conn,jointInput);
						System.out.println("Created joint application");
					}
					break;
						
				}
			}
			}
			else
			{
				System.out.println("Invalid Login");
			}
				
			}
		}
		catch (ClassNotFoundException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch(SQLException e)
		{
			System.out.println("Invalid input or duplicate");
		}
		System.out.println("Go back to main menu?\n1.Yes\n2.No");
		returning=scan.nextInt();
	}
	}
	}
		
		
//		File file=new File("info.text");
//		try 
//		{
//			int lines=0;
//			Scanner scan2=new Scanner(file);
//			while(scan2.hasNextLine())
//		{
//			String line=scan2.nextLine();
//			lines++;
//			if(line.equals(line));
//			{
//				
//			}
//					
//		}
//		}
//		catch(FileNotFoundException e)
//		{
//			
//		}

