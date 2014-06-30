package lin.xidian.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import lin.xidian.pojo.Chatter;
import lin.xidian.pojo.Group;
import lin.xidian.sql.ConnectionFactory;

public class GroupDaoImpl implements GroupDao
{
	private Connection con;
	
	public GroupDaoImpl()
	{
		this.con = ConnectionFactory.getInstance().popConnection();
	}
	
	@Override
	public long addGroup(Group group)
	{
		long id = 0;
		String sql = "insert into cgroup(name,description,creater,admins) values("+group.getName()
		+","+group.getDescription()+","+group.getCreater().getId()+","+group.getAdminstrator().getId();
		try {
			Statement state = con.createStatement();
			state.execute(sql);
			String sql2 = "select max(id) from cgroup";
			ResultSet rst = state.executeQuery(sql2);
			if(rst.next())
			{
				id = rst.getLong(0);
			}
			state.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public Group changeGroup(long id, Group newGroup)
	{
		String sql = "update cgroup set name="+newGroup.getName()+",description="+newGroup.getDescription()+" where id="+id;
		try {
			Statement state = con.createStatement();
			state.execute(sql);
			state.close();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return getGroupById(id);
	}

	@Override
	public Group getGroupById(long id)
	{
		String sql = "select cgroup.name,cgroup.description,chatter.id,chatter.name,chatter.description from"
			+" cgroup,chatter,groupchatter where cgroup.id="+id+" and cgroup.id=groupchatter.id and chatter.id=groupchatter.id";
		Group group = new Group();
		group.setId(id);
		try {
			Statement state = con.createStatement();
			ResultSet rst = state.executeQuery(sql);
			while(rst.next())
			{
				String gname = rst.getString("cgroup.name");
				String gdescription = rst.getString("cgroup.description");
				group.setName(gname);
				group.setDescription(gdescription);
				long cid = rst.getLong("chatter.id");
				String cname = rst.getString("chatter.name");
				String cdescription = rst.getString("chatter.description");
				Chatter chatter = new Chatter(cid,cname,"",cdescription);
				group.addChatter(chatter);	
			}
			state.close();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return group;
	}

	@Override
	public List<Group> getGroupsByCreater(long creater)
	{
		String sql = "select cgroup.id,cgroup.name,cgroup.description,chatter.id,chatter.name,chatter.description from"
			+" cgroup,chatter,groupchatter where cgroup.creater="+creater+" and cgroup.id=groupchatter.id and chatter.id=groupchatter.id";
		List<Group> groups = new ArrayList<Group>();
		Group group;
		try {
			Statement state = con.createStatement();
			ResultSet rst = state.executeQuery(sql);
			int index = 0;
			long last = 0;
			while(rst.next())
			{
				long gid = rst.getLong("cgroup.id");
				String gname = rst.getString("cgroup.name");
				String gdescription = rst.getString("cgroup.description");
				long cid = rst.getLong("chatter.id");
				String cname = rst.getString("chatter.name");
				String cdescription = rst.getString("chatter.description");
				Chatter chatter = new Chatter(cid,cname,"",cdescription);
				if(last == gid)
				{
					groups.get(index).addChatter(chatter);
				}
				else
				{
					group = new Group(gid,gname,gdescription);
					group.addChatter(chatter);
					index++;
					last = gid;
				}	
			}
			state.close();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return groups;
	}

	@Override
	public boolean removeGroup(long id)
	{
		boolean finish = false;
		String sql = "delete from groupchatter where gid="+id;
		try {
			Statement state = con.createStatement();
			boolean isOK1 = state.execute(sql);
			String sql2 = "delete from cgroup where id="+id;
			boolean isOK2 = state.execute(sql2);
			state.close();
			if(isOK1&&isOK2)
			{
				finish = true;
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return finish;
	}

	@Override
	public void close()
	{
		ConnectionFactory.getInstance().pushConnection(con);
		con = null;
	}
}
