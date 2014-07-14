package se.umu.cs.fancraft.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import se.umu.cs.fancraft.entity.Fandom;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

/**
 * This class is a bit special, as there will only be one entry Id will be
 * "fandoms"
 * 
 */
public class FandomDAO {

	private DynamoDBMapper mapper;
	private AmazonDynamoDBClient client;

	private static FandomDAO helper = new FandomDAO();

	public FandomDAO() {
		client = new AmazonDynamoDBClient(
				new ClasspathPropertiesFileCredentialsProvider());
		mapper = new DynamoDBMapper(client);

		Fandom test = mapper.load(Fandom.class, "fandoms");
		if (test == null) {
			test = new Fandom();
			test.setId("fandoms");
			mapper.save(test);
		}
	}

	public static FandomDAO getInstance() {
		return helper;
	}

	public Set<String> getFandoms() {
		Fandom retrieved = mapper.load(Fandom.class, "fandoms");
		return retrieved.getFandoms();
	}

	public Map<String, String> getFandomsMap() {
		Set<String> fandoms = getFandoms();
		if (fandoms == null)
			return null;
		HashMap<String, String> map = new HashMap<String, String>();
		for (String s : fandoms)
			map.put(s, s);

		return map;
	}

	public void addCraft(String craft) {
		Fandom retrieved = mapper.load(Fandom.class, "fandoms");
		if (retrieved.getFandoms() == null) { // this happens if there are no
												// values for the attribute
			retrieved.setFandoms(new HashSet<String>());
		}
		if (!retrieved.getFandoms().contains(craft)) {
			retrieved.getFandoms().add(craft);
			mapper.save(retrieved);
		}
	}

}
