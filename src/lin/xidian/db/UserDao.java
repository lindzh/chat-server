package lin.xidian.db;

import java.util.List;

public interface UserDao
{
	public void connect();
	public void close();
	public User login(long id,String password);
	public User register(String name,String password);
	public void deleteUser(long id);
	public User getUserById(long id);
	public User changeInfo(long id,String newname,String newpass);
	public List<User> getAllUsers();
}
