package lin.xidian.pojo;

public class Chatter
{
	private long id;
	private String name;
	private String password;
	private String description;
	
	public Chatter()
	{
		
	}
	
	public Chatter(long id,String name,String password,String description)
	{
		this.id = id;
		this.name = name;
		this.password = password;
		this.description = description;
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

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
	
	
	
}
