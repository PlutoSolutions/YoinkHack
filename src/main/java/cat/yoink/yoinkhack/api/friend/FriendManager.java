package cat.yoink.yoinkhack.api.friend;

import java.util.ArrayList;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class FriendManager
{
	private final ArrayList<Friend> friends = new ArrayList<>();

	public ArrayList<Friend> getFriends()
	{
		return friends;
	}

	public void addFriend(String name)
	{
		friends.add(new Friend(name));
	}

	public void delFriend(String name)
	{
		friends.remove(getFriendByName(name));
	}

	public Friend getFriendByName(String name)
	{
		for (Friend friend : getFriends())
		{
			if (friend.getName().equalsIgnoreCase(name))
			{
				return friend;
			}
		}
		return null;
	}

	public boolean isFriend(String name)
	{
		for (Friend friend : getFriends())
		{
			if (friend.getName().equalsIgnoreCase(name))
			{
				return true;
			}
		}
		return false;
	}

	public ArrayList<String> getFriendsForConfig()
	{
		ArrayList<String> arr = new ArrayList<>();

		for (Friend friend : getFriends())
		{
			arr.add(friend.getName());
		}
		return arr;
	}
}
