package se.umu.cs.fancraft.entity;

import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="FanCraft_Fandoms")
public class Fandom {
	
	private String id;
	private Set<String> fandoms;
	
	@DynamoDBHashKey(attributeName="Id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@DynamoDBAttribute(attributeName="Fandoms")
	public Set<String> getFandoms() {
		return fandoms;
	}
	public void setFandoms(Set<String> fandoms) {
		this.fandoms = fandoms;
	}
	
	

}
