package test;

import java.util.List;

public class CustomerOracleDao implements CustomerDao 
{

	@Override
	public boolean approval(Customer c) 
	{
		
		return true;
		// TODO Auto-generated method stub
	}

	@Override
	public User getUser(Customer c) 
	{
		// TODO Auto-generated method stub
		User u=new User(c.getUser(),c.getPin());
		return u;
	}

	@Override
	public String getLogin(String name,String pin) 
	{
		// TODO Auto-generated method stub
		Customer c=new Customer(name,pin);
		return c.getUser()+" "+c.getPin();
	}

}
