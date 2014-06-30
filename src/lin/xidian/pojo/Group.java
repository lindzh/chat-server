package lin.xidian.pojo;

import java.util.ArrayList;
import java.util.List;

public class Group
{
	private long id;
	private String name;
	private String description;
	private Chatter creater;
	private Chatter adminstrator;
	private List<Chatter> chatters;
	
	public Group()
	{
		
	}
	
	public Group(long id,String name,String description)
	{
		this.id = id;
		this.name = name;
		this.description = description;
		chatters = new ArrayList<Chatter>();
	}
	
	public Group(long id,String name,String description,Chatter creater)
	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.creater = creater;
		chatters = new ArrayList<Chatter>();
	}
	
	public Group(long id,String name,String description,Chatter creater,Chatter adminstrator)
	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.creater = creater;
		this.adminstrator = adminstrator;
		chatters = new ArrayList<Chatter>();
	}

	public Group(long id,String name,String description,Chatter creater,Chatter adminstrator,List<Chatter> chatters)
	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.creater = creater;
		this.adminstrator = adminstrator;
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

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Chatter getCreater()
	{
		return creater;
	}

	public void setCreater(Chatter creater)
	{
		this.creater = creater;
	}

	public Chatter getAdminstrator()
	{
		return adminstrator;
	}

	public void setAdminstrator(Chatter adminstrator)
	{
		this.adminstrator = adminstrator;
	}

	public void addChatter(Chatter chatter)
	{
		chatters.add(chatter);
	}
	
	public List<Chatter> getChatters()
	{
		return chatters;
	}

	public void setChatters(List<Chatter> chatters)
	{
		this.chatters = chatters;
	}
}
