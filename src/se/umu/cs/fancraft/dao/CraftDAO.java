package se.umu.cs.fancraft.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import se.umu.cs.fancraft.entity.Craft;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

/**
 * This class is a bit special, as there will only be one entry
 * Id will be "crafts"
 *
 */
public class CraftDAO {
	
	private DynamoDBMapper mapper;
	private AmazonDynamoDBClient client;
	
	private static CraftDAO helper = new CraftDAO();
	
	public CraftDAO(){
		client = new AmazonDynamoDBClient(new ClasspathPropertiesFileCredentialsProvider());
		mapper = new DynamoDBMapper(client);
		
		Craft test = mapper.load(Craft.class, "crafts");
		if(test == null){
			test = new Craft();
			test.setId("crafts");
			mapper.save(test);
		}
	}

	public static CraftDAO getInstance(){
		
		return helper;
		
	}
	
	public Set<String> getCrafts(){
		Craft retrieved = mapper.load(Craft.class, "crafts");
		return retrieved.getCrafts();
	}
	
	public Map<String,String> getCraftsMap(){
		Set<String> crafts = getCrafts();
		if(crafts == null)
			return null;
		HashMap<String, String> map = new HashMap<String,String>();
		for(String s : crafts)
			map.put(s, s);
		
		return map;
	}
	
	public void addCraft(String craft){
		Craft retrieved = mapper.load(Craft.class, "crafts");
		if(retrieved.getCrafts() == null){ //this happens if there are no values for the attribute
			retrieved.setCrafts(new HashSet<String>());
		}
		if(!retrieved.getCrafts().contains(craft)){
			retrieved.getCrafts().add(craft);
			mapper.save(retrieved);
		}
	}
	
}
