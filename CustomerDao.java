package test;

import java.util.List;

public interface CustomerDao 
{
	boolean approval(Customer c);
	User getUser(Customer c);
	String getLogin(String name, String pin);
}
