package lin.xidian.dao;

import java.util.List;

import lin.xidian.pojo.Group;

public interface GroupDao
{
	public long addGroup(Group group);
	public boolean removeGroup(long id);
	public Group changeGroup(long id,Group newGroup);
	public Group getGroupById(long id);
	public List<Group> getGroupsByCreater(long creater);
	public void close();
	
}
