package lin.xidian.dao;

import java.util.List;

import lin.xidian.pojo.Chatter;

public interface ChatterDao
{
	public long addChatter(Chatter chatter);
	public boolean removeChatter(Chatter chatter);
	public Chatter login(long id,String password);
	public Chatter getChatterById(long id);
	public List<Chatter> getAllChatters();
	public Chatter changeChatter(long id,Chatter newChatter);
	public void close();
}
