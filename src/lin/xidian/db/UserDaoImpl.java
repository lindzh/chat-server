package lin.xidian.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao
{
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String userName = "root";
	private static final String userPassword = "star*3066";
	private static final String url = "jdbc:mysql://127.0.0.1:3306/chat"; 
	private Connection con;
	private boolean isConnected = false;
	
	@Override
	public User changeInfo(long id, String newname, String newpass)
	{
		String sql = "update userinfo set userName="+newname+" userPassword="+newpass+" where id="+id;
		Statement statement;
		try {
			statement = con.createStatement();
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getUserById(id);
	}

	@Override
	public void close()
	{
		if(isConnected == true)
		{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void connect()
	{
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userName, userPassword);
			isConnected = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	@Override
	public void deleteUser(long id)
	{
		String sql = "delete from userinfo where id="+id;
		try {
			Statement statement = con.createStatement();
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<User> getAllUsers()
	{
		List<User> users = new ArrayList<User>();
		String sql = "select * from userinfo";
		try {
			Statement statement = con.createStatement();
			ResultSet rst = statement.executeQuery(sql);
			if(rst.next())
			{
				long mid = rst.getLong("id");
				String name = rst.getString("userName");
				String pass = rst.getString("userPassword");
				User user = new User(mid,name,pass);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public User getUserById(long id)
	{
		String sql = "select * from userinfo where id="+id;
		try {
			Statement statement = con.createStatement();
			ResultSet rst = statement.executeQuery(sql);
			if(rst.next())
			{
				long mid = rst.getLong("id");
				String name = rst.getString("userName");
				String pass = rst.getString("userPassword");
				User user = new User(mid,name,pass);
				return user;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User login(long id, String password)
	{
		String sql = "select * from userinfo where id="+id+" and userPassword="+password;
		try {
			Statement statement = con.createStatement();
			ResultSet rst = statement.executeQuery(sql);
			if(rst.next())
			{
				long mid = rst.getLong("id");
				String name = rst.getString("userName");
				String pass = rst.getString("userPassword");
				User user = new User(mid,name,pass);
				return user;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User register(String name, String password)
	{
		String sql = "insert into userinfo(userName,userPassword) values("+name+","+password+")";
		if(isConnected)
		{
			try {
				Statement statement = con.createStatement();
				statement.execute(sql);
				String sql2 = "select * from userinfo where userName="+name+" and userPassword="+password;	
				ResultSet rst = statement.executeQuery(sql2);
				if(rst.next())
				{
					long mid = rst.getLong("id");
					String nname = rst.getString("userName");
					String pass = rst.getString("userPassword");
					User user = new User(mid,nname,pass);
					return user;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
