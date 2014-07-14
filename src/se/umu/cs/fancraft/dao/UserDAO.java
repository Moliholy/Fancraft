package se.umu.cs.fancraft.dao;

import java.util.HashSet;
import java.util.Set;

import se.umu.cs.fancraft.entity.User;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class UserDAO {

	private DynamoDBMapper mapper;
	private AmazonDynamoDBClient client;

	private static UserDAO helper = new UserDAO();

	public UserDAO() {
		client = new AmazonDynamoDBClient(
				new ClasspathPropertiesFileCredentialsProvider());
		mapper = new DynamoDBMapper(client);
	}

	public static UserDAO getInstance() {
		return helper;
	}

	public void saveUser(String username, String password, String displayName) {
		User newUser = new User();
		newUser.setId(username);
		newUser.setPass(password);
		newUser.setName(displayName);

		mapper.save(newUser);
	}

	public User getUser(String id) {
		User retrieved = mapper.load(User.class, id);
		return retrieved;
	}

	public Set<String> getFriends(String id) {
		User u = getUser(id);
		return u.getFriends();
	}

	public User addFriend(String myID, String friendID) {
		User me = getUser(myID);
		User friend = getUser(friendID);
		if (friend != null) {
			if (me.getFriends() == null)
				me.setFriends(new HashSet<String>());
			me.getFriends().add(friend.getId());
			mapper.save(me);
			return friend;
		}
		return null;
	}

	public boolean addCraft(String myID, String craft) {
		User me = getUser(myID);
		if (craft != null) {
			if (me.getCrafts() == null)
				me.setCrafts(new HashSet<String>());
			Set<String> mycrafts = me.getCrafts();
			mycrafts.add(craft);
			mapper.save(me);
			return true;
		}
		return false;
	}

	public boolean addFandom(String myID, String fandom) {
		User me = getUser(myID);
		if (fandom != null) {
			/*
			 * mapper.save(me, new DynamoDBMapperConfig(
			 * DynamoDBMapperConfig.SaveBehavior.CLOBBER));
			 */
			if (me.getFandoms() == null)
				me.setFandoms(new HashSet<String>());
			Set<String> myfandom = me.getFandoms();
			myfandom.add(fandom);
			mapper.save(me);
			return true;
		}
		return false;
	}

	public boolean exists(String id) {
		User retrieved = mapper.load(User.class, id);
		return retrieved != null;
	}

	public String getDisplayName(String id) {
		return getUser(id).getName();
	}

}
