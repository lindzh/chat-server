package lin.xidian.server;

public class StartUp
{
	public void startMySQLServer()
	{
		Runtime cmd = Runtime.getRuntime();
		try
		{
			cmd.exec("net start mysql5");
			System.out.println("MySQL Server 启动成功！");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		StartUp startUp = new StartUp();
		startUp.startMySQLServer();
		Server server = new Service();
		server.start();
		System.out.println("Chat server 已启动！");
	}
}
