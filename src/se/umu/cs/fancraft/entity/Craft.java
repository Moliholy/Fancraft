package se.umu.cs.fancraft.entity;

import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="FanCraft_Crafts")
public class Craft {
	
	private String id;
	private Set<String> crafts;
	
	@DynamoDBHashKey(attributeName="Id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@DynamoDBAttribute(attributeName="Crafts")
	public Set<String> getCrafts() {
		return crafts;
	}
	public void setCrafts(Set<String> crafts) {
		this.crafts = crafts;
	}
	
	

}
