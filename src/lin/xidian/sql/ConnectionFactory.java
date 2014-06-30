package lin.xidian.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class ConnectionFactory
{
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String userName = "lin";
	private static final String userPassword = "123456";
	private static final String url = "jdbc:mysql://127.0.0.1:3306/chat"; 
	private static List<Connection> conntections = new ArrayList<Connection>();
	
	private static ConnectionFactory factory = new ConnectionFactory(); 
	private int top = 9;
	
	private ConnectionFactory()
	{
		try {
			Class.forName(driver);
			
			for(int i=0;i<10;i++)
			{
				Connection con = DriverManager.getConnection(url, userName, userPassword);
				conntections.add(con);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	
	}
	
	public static ConnectionFactory getInstance()
	{
		if(factory==null)
		{
			factory = new ConnectionFactory();
		}
		return factory;
	}
	
	public Connection popConnection()
	{
		Connection con = null;
		if(top!=-1)
		{
			con = conntections.get(top);
			conntections.set(top, null);
			top--;
		}
		return con;
	}
	
	public void pushConnection(Connection connection)
	{
		if(top+1<9)
		{
			top++;
			conntections.set(top, connection);
		}
	}
}
