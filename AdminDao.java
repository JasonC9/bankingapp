package test;

import java.sql.Connection;
import java.util.List;

public interface AdminDao<T>
{
	public boolean adminLogin(Connection conn,String user1,String pin);
}
