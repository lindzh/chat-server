package lin.xidian.dao;

import java.util.List;

import lin.xidian.pojo.Chatter;
import lin.xidian.pojo.Friends;

public interface FriendsDao
{
	public long addFriends(Friends friends);//
	public boolean removeFriends(long id);//
	public Friends getFriendsById(long id);
	public List<Friends> getFriendsByCreater(long creater);//
	public Friends changeFriends(long id,String name);
	public void addChatter(long id,Chatter chatter);
	public void removeChatter(long id,long chatterId);
	public void close();
}
