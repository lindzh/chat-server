package lin.xidian.pojo;

import java.util.ArrayList;
import java.util.List;

public class Friends
{
	private long id;
	private String name;
	private Chatter creater;
	private List<Chatter> chatters = new ArrayList<Chatter>();
	
	public Friends()
	{
		
	}
	
	public Friends(long id,String name)
	{
		this.id = id;
		this.name = name;
	}
	
	public Friends(long id,String name,Chatter creater)
	{
		this.id = id;
		this.name = name;
		this.creater = creater;
	}
	
	public Friends(long id,String name,Chatter creater,List<Chatter> chatters)
	{
		this.id = id;
		this.name = name;
		this.creater = creater;
		this.chatters = chatters;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Chatter getCreater()
	{
		return creater;
	}

	public void setCreater(Chatter creater)
	{
		this.creater = creater;
	}

	public List<Chatter> getChatters()
	{
		return chatters;
	}

	public void setChatters(List<Chatter> chatters)
	{
		this.chatters = chatters;
	}
	
	public void addChatter(Chatter chatter)
	{
		chatters.add(chatter);
	}
}
