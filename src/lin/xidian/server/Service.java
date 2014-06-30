package lin.xidian.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import lin.xidian.db.User;
import lin.xidian.db.UserDao;
import lin.xidian.db.UserDaoImpl;
/**
 * 线程安全性问题，同步
 * @author lindia
 *
 */
public class Service implements Server
{
	private DatagramSocket socket;
	private UserDao dao = new UserDaoImpl();
	private boolean running = false;
	private List<User> users = new ArrayList<User>();

	@Override
	public void start()
	{
		try {
			socket = new DatagramSocket(9999);
			dao.connect();
			new Thread(this).start();
			running = true;
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void tickOutUser(long id)
	{
		int len = users.size();
		for(int i=0;i<len;i++)
		{
			if(users.get(i).getId() == id)
			{
				users.remove(i);
			}
		}
	}
	
	public User getUserById(long id)
	{
		int len = users.size();
		for(int i=0;i<len;i++)
		{
			User u = users.get(i);
			if(u.getId() == id)
			{
				return u;
			}
		}
		return null;
	}
	
	public void sendOthersTo(final User toUser)
	{
		new Thread(new Runnable()
		{
			private int len = users.size();
			DatagramPacket packet;
			InetAddress address;
			public void run()
			{
				try {
					address = InetAddress.getByName(toUser.getIp());
					for(int i=0;i<len;i++)
					{
						User u = users.get(i);
						if(u.getId()!=toUser.getId())
						{
							String info = "login:"+u.getId()+","+u.getName()+","+u.getIp()+","+u.getPort();
							packet = new DatagramPacket(info.getBytes(),info.getBytes().length,address,toUser.getPort());
							socket.send(packet);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();
	}
	
	public void sendToAll(final String str)
	{
		new Thread(new Runnable()
		{
			public void run()
			{
				byte[] buffer = str.getBytes();
				int len = users.size();
				for(int i=0;i<len;i++)
				{
					User user = users.get(i);
					InetAddress addr;
					try {
						addr = InetAddress.getByName(user.getIp());
						DatagramPacket packet = new DatagramPacket(buffer,buffer.length,addr,user.getPort());
						socket.send(packet);
						System.out.println("SEND:"+addr.getHostAddress()+"  "+user.getPort()+"   "+str);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}				
			}
		}).start();
	}
	
	public void sendSingle(final String ip,final int port,final String message)
	{
		new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					byte[] buffer = message.getBytes();
					InetAddress addr = InetAddress.getByName(ip);
					DatagramPacket packet = new DatagramPacket(buffer,buffer.length,addr,port);
					socket.send(packet);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	@Override
	public void stop()
	{
		dao.close();
	}

	@Override
	public void run()
	{
		byte [] buffer = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
		while(running)
		{
			try {
				buffer = new byte[1024];
				packet = new DatagramPacket(buffer,buffer.length);
				socket.receive(packet);
				String str = new String(buffer).trim();
				System.out.println(str);
				if(str.startsWith("login"))
				{
					String[] recc = str.split(":")[1].split("\\,");
					long id = Long.parseLong(recc[0]);
					String password = recc[1];
					String ip = recc[2].trim();
					int port = packet.getPort();
					User user = dao.login(id, password);
					if(user!=null)
					{
						user.setIp(ip);
						user.setPort(port);
						users.add(user);
						String logStr = "login:"+id+","+user.getName()+","+ip+","+port;
						sendToAll(logStr);
					}
					else
					{
						String logStr = "logfail:"+id;
						sendSingle(ip,port,logStr);
					}
				}
				
				if(str.startsWith("requestFriendsAndGroups"))
				{
					long id = Long.parseLong(str.split(":")[1]);
					User user = getUserById(id);
					if(user!=null)
					{
						sendOthersTo(user);
					}
				}
				
				if(str.startsWith("logout"))
				{
					String[] recc = str.split(":");
					long id = Long.parseLong(recc[1]);
					tickOutUser(id);
					sendToAll(str);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
