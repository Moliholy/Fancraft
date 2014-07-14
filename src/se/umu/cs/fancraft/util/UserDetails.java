package se.umu.cs.fancraft.util;

import java.io.Serializable;
import java.util.List;

public class UserDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String displayName;
	private List<Friend> friends;
	private List<String> fandoms;
	private List<String> crafts;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public List<Friend> getFriends() {
		return friends;
	}
	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}
	public List<String> getFandoms() {
		return fandoms;
	}
	public void setFandoms(List<String> fandoms) {
		this.fandoms = fandoms;
	}
	public List<String> getCrafts() {
		return crafts;
	}
	public void setCrafts(List<String> crafts) {
		this.crafts = crafts;
	}
	
	

}
