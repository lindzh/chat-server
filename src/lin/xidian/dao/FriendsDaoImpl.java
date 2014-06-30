package lin.xidian.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import lin.xidian.pojo.Chatter;
import lin.xidian.pojo.Friends;
import lin.xidian.sql.ConnectionFactory;

public class FriendsDaoImpl implements FriendsDao
{
	private Connection con;
	public FriendsDaoImpl()
	{
		con = ConnectionFactory.getInstance().popConnection();
	}
	
	public long addFriends(Friends friends)
	{
		long id = 0;
		String sql = "insert into friends(name,creater) values('"+friends.getName()
		+"',"+friends.getCreater().getId()+")";
		try {
			Statement state = con.createStatement();
			state.execute(sql);
			String sqlcheck = "select id from friends where name='"+friends.getName()
			+"' and creater="+friends.getCreater().getId();
			ResultSet rst = state.executeQuery(sqlcheck);
			if(rst.next())
			{
				id = rst.getLong("id");
				rst.close();
				state.close();
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public List<Friends> getFriendsByCreater(long creater)
	{
		String sql = "select friends.id,friends.name,chatter.id,chatter.id,chatter.name,chatter.description from" 
			+" friends,chatter,friendschatter where friends.creater="
			+creater+" and friends.id=friendschatter.fid and chatter.id=friendschatter.cid";
		List<Friends> friends = new ArrayList<Friends>();
		try {
			Statement state = con.createStatement();
			ResultSet rst = state.executeQuery(sql);
			Friends friend = new Friends();
			long lastid = 0;
			int index = 0;
			while(rst.next())
			{
				long fid = rst.getLong("friends.id");
				String fname = rst.getString("friends.name");
				long cid = rst.getLong("chatter.id");
				String cname = rst.getString("chatter.name");
				String description = rst.getString("chatter.description");
				Chatter chatter = new Chatter(cid,cname,"",description);
				if(lastid == fid)
				{
					friends.get(index-1).addChatter(chatter);
				}
				else
				{
					friend = new Friends(fid,fname);
					friend.addChatter(chatter);
					//Ìí¼Óchatter
					friends.add(friend);
					index++;
					lastid = fid;
				}
			}
			rst.close();
			state.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return friends ;
	}

	@Override
	public Friends getFriendsById(long id)
	{
		String sql1 = "select * from friends where id="+id;
		Friends friends = new Friends();
		try {
			Statement state = con.createStatement();
			ResultSet rst = state.executeQuery(sql1);
			if(rst.next())
			{
				long fid = rst.getLong("id");
				String name = rst.getString("name");
				friends.setId(fid);
				friends.setName(name);
			}
				String sql2 = "select chatter.id,chatter.name,chatter.description from chatter," +
						"friendschatter where friendschatter.fid="+id+"and chatter.id=friendschatter.id";
				ResultSet res = state.executeQuery(sql2);
				while(res.next())
				{
					long cid = res.getLong("id");
					String cname = res.getString("name");
					String description = res.getString("description");
					Chatter chatter = new Chatter(cid,cname,"",description);
					friends.addChatter(chatter);
				}
				rst.close();
				res.close();
				state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return friends;
	}

	@Override
	public boolean removeFriends(long id)
	{
		boolean isOk = false;
		boolean isOk1 = true;
		boolean isOk2 = true;
		String sql = "delete from friendschatter where fid="+id;
		try {
			Statement state = con.createStatement();
			isOk1 = state.execute(sql);
			String sql2 = "delete from friends where id="+id;
			isOk2 = state.execute(sql2);
			if(isOk1&&isOk2)
			{
				isOk = true;
			}
			state.close();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return isOk;
	}

	@Override
	public void addChatter(long id, Chatter chatter)
	{
		String sql = "insert into friendschatter(fid,cid) values("+id+","+chatter.getId()+")";
		try
		{
			Statement state = con.createStatement();
			state.execute(sql);
			state.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public Friends changeFriends(long id, String name)
	{
		String sql = "update friends set name='"+name+"' where id="+id;
		try
		{
			Statement state = con.createStatement();
			state.execute(sql);
			state.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return getFriendsById(id);
	}

	@Override
	public void removeChatter(long id, long chatterId)
	{
		String sql = "delete from friendschatter where fid="+id+" and cid="+chatterId;
		try
		{
			Statement state = con.createStatement();
			state.execute(sql);
			state.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void close()
	{
		ConnectionFactory.getInstance().pushConnection(con);
		con = null;
	}

}
