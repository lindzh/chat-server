package lin.xidian.db;

public class User
{
	private long id;
	private String name;
	private String password;
	private String ip;
	private int port;
	
	public User()
	{
		
	}
	
	public User(long id,String name,String password)
	{
		this.id = id;
		this.name = name;
		this.password = password;
	}
	
	public User(long id,String name,String password,String ip,int port)
	{
		this.id = id;
		this.name = name;
		this.password = password;
		this.ip = ip;
		this.port = port;
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

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}
	
}
