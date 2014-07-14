package se.umu.cs.fancraft.entity;

import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="FanCraft_Users")
public class User {
	
	private String id; //user name is also key
	private String pass;
	private String name;
	private Set<String> friends;
	private Set<String> fandoms;
	private Set<String> crafts;
	
	@DynamoDBHashKey(attributeName="Id")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@DynamoDBAttribute(attributeName="Password")
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	@DynamoDBAttribute(attributeName="Name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@DynamoDBAttribute(attributeName="Friends")
	public Set<String> getFriends() {
		return friends;
	}
	public void setFriends(Set<String> friends) {
		this.friends = friends;
	}
	
	@DynamoDBAttribute(attributeName="Fandoms")
	public Set<String> getFandoms() {
		return fandoms;
	}
	public void setFandoms(Set<String> fandoms) {
		this.fandoms = fandoms;
	}
	
	@DynamoDBAttribute(attributeName="Crafts")
	public Set<String> getCrafts() {
		return crafts;
	}
	public void setCrafts(Set<String> crafts) {
		this.crafts = crafts;
	}
	
	

}
