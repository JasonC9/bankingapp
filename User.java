package test;

public class User 
{
	protected boolean approval;
	String user;
	String pin;
	
	
	User(String ID,String pin)
	{
		this.pin=pin;
		this.user=user;
		approval=false;
	}
	
	public void setPin()
	{
		this.pin=pin;
	}
	
	public boolean approval()
	{
		return this.approval;
	}
	public String getPin()
	{
		return this.pin;
	}
	public String getLogin()
	{
		return user;
	}
	public String toString()
	{
		return user+" "+pin;
	}
	
}
