package lin.xidian.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import lin.xidian.pojo.Chatter;
import lin.xidian.sql.ConnectionFactory;

public class ChatterDaoImpl implements ChatterDao
{
	private Connection con;
	
	public ChatterDaoImpl()
	{
		ConnectionFactory factory = ConnectionFactory.getInstance();
		con = factory.popConnection();
	}
	
	@Override
	public long addChatter(Chatter chatter)
	{
		long id = 0;
		String sql = "insert into chatter(name,password,description) values('"+chatter.getName()
		+"','"+chatter.getPassword()+"','"+chatter.getDescription()+"')";
		System.out.println(sql);
		try {
			Statement state = con.createStatement();
			state.execute(sql);
			String sqlcheck = "select id from chatter where name='"+chatter.getName()
			+"'and password='"+chatter.getPassword()+"' and description='"+chatter.getDescription()+"'";
			ResultSet rst = state.executeQuery(sqlcheck);
			if(rst.next())
			{
				id = rst.getLong("id");
				rst.close();
				state.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public Chatter changeChatter(long id, Chatter newChatter)
	{
		Chatter chatter = null;
		String sql = "update chatter set name='"+newChatter.getName()+"',password='"+newChatter.getPassword()
		+"',description='"+newChatter.getDescription()+"' where id="+id;
		try {
			Statement state = con.createStatement();
			state.execute(sql);
			String sqlc = "select * from chatter where id="+id;
			ResultSet rst = state.executeQuery(sqlc);
			if(rst.next())
			{
				long nid = rst.getLong("id");
				String name = rst.getString("name");
				String password = rst.getString("password");
				String desc = rst.getString("description");
				chatter = new Chatter(nid,name,password,desc);
				rst.close();
				state.close();
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return chatter;
	}

	@Override
	public List<Chatter> getAllChatters()
	{
		List<Chatter> chatters = new ArrayList<Chatter>();
		try {
			Statement state = con.createStatement();
			String sqlc = "select * from chatter";
			ResultSet rst = state.executeQuery(sqlc);
			while(rst.next())
			{
				long id = rst.getLong("id");
				String name = rst.getString("name");
				String password = rst.getString("password");
				String desc = rst.getString("description");
				Chatter chatter = new Chatter(id,name,password,desc);
				chatters.add(chatter);
			}
			rst.close();
			state.close();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return chatters;
	}

	@Override
	public Chatter getChatterById(long id)
	{
		Chatter chatter = null;
		try {
			Statement state = con.createStatement();
			String sqlc = "select * from chatter where id="+id;
			ResultSet rst = state.executeQuery(sqlc);
			if(rst.next())
			{
				long nid = rst.getLong("id");
				String name = rst.getString("name");
				String password = rst.getString("password");
				String desc = rst.getString("description");
				chatter = new Chatter(nid,name,password,desc);
				rst.close();
				state.close();
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return chatter;
	}

	@Override
	public Chatter login(long id, String password)
	{
		Chatter chatter = null;
		try {
			Statement state = con.createStatement();
			String sqlc = "select * from chatter where id="+id+" and password='"+password+"'";
			ResultSet rst = state.executeQuery(sqlc);
			if(rst.next())
			{
				long nid = rst.getLong("id");
				String name = rst.getString("name");
				String npassword = rst.getString("password");
				String desc = rst.getString("description");
				chatter = new Chatter(nid,name,npassword,desc);
				rst.close();
				state.close();
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return chatter;
	}

	@Override
	public boolean removeChatter(Chatter chatter)
	{
		boolean finish = false;
		try {
			Statement state = con.createStatement();
			String sql = "delete from chatter where id="+chatter.getId();
			finish = state.execute(sql);
		} catch (SQLException e) 
		{
			finish = false;
			e.printStackTrace();
		}
		return finish;
	}
	
	public void close()
	{
		ConnectionFactory.getInstance().pushConnection(con);
		con = null;
	}

}
