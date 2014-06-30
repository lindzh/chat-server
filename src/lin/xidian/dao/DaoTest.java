package lin.xidian.dao;

import java.util.List;

import lin.xidian.pojo.Chatter;
import lin.xidian.pojo.Friends;


public class DaoTest
{
	public static void main(String[] args)
	{
		ChatterDao chatterDao = new ChatterDaoImpl();
		Chatter chatter = chatterDao.getChatterById(8300000);
		FriendsDao dao = new FriendsDaoImpl();
		Friends friends = new Friends();
		friends.setCreater(chatter);
		friends.setName("≈Û”—");
		//dao.addFriends(friends);
		List<Friends> list = dao.getFriendsByCreater(8300000);
		int len = list.size();
		if(len !=0)
		{
			for(int i=0;i<len;i++)
			{
				long fid = list.get(i).getId();
				String fname = list.get(i).getName();
				System.out.println("friends:"+fid+"   "+fname);
				List<Chatter> chatters = list.get(i).getChatters();
				int leng = chatters.size();
				for(int j=0;j<leng;j++)
				{
					long cid = chatters.get(j).getId();
					String cname = chatters.get(j).getName();
					String cdesc = chatters.get(j).getDescription();
					System.out.println(cid+"   "+cname+"   "+cdesc);
				}
			}
		}
		else
		{
			System.out.println("“—Œ™ø’");
		}
	}

}
