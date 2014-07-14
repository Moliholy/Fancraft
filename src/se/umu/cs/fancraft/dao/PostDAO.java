package se.umu.cs.fancraft.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import se.umu.cs.fancraft.entity.Post;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

public class PostDAO {

	private DynamoDBMapper mapper;
	private AmazonDynamoDBClient client;

	// private static PostDAO helper = new PostDAO();

	public PostDAO() {
		client = new AmazonDynamoDBClient(
				new ClasspathPropertiesFileCredentialsProvider());
		mapper = new DynamoDBMapper(client);
	}

	// public static PostDAO getInstance(){
	//
	// return helper;
	//
	// }

	public void savePost(Post post) {
		mapper.save(post);
	}

	public Post getPost(String userId, String postTimestamp) {
		Post result = mapper.load(Post.class, userId, postTimestamp);
		return result;
	}

	/**
	 * 
	 * @param userId
	 *            poster user id
	 * @param timeWindow
	 *            how far back to go
	 * @return
	 */
	public List<Post> getpostsByUser(String userId, int timeWindow) {

		long t = (new Date()).getTime()
				- (((long) timeWindow) * 24L * 60L * 60L * 1000L);
		Date time = new Date();
		time.setTime(t);
		SimpleDateFormat df = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		String timeString = df.format(time);

		Condition rangeKeyCondition = new Condition().withComparisonOperator(
				ComparisonOperator.GT.toString()).withAttributeValueList(
				new AttributeValue().withS(timeString));

		Post postKey = new Post();
		postKey.setPosterId(userId);

		DynamoDBQueryExpression<Post> queryExpression = new DynamoDBQueryExpression<Post>()
				.withHashKeyValues(postKey).withRangeKeyCondition(
						"PostTimestamp", rangeKeyCondition);

		List<Post> latestsPosts = mapper.query(Post.class, queryExpression);

		return latestsPosts;
	}

	/**
	 * 
	 * @param craftId
	 *            craft id
	 * @param timeWindow
	 *            how far back to go
	 * @return
	 */
	public List<Post> getpostsByCraft(String craftId, int timeWindow) {

		long t = (new Date()).getTime()
				- (((long) timeWindow) * 24L * 60L * 60L * 1000L);
		Date time = new Date();
		time.setTime(t);
		SimpleDateFormat df = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String timeString = df.format(time);

		Condition rangeKeyCondition = new Condition().withComparisonOperator(
				ComparisonOperator.GT.toString()).withAttributeValueList(
				new AttributeValue().withS(timeString));

		Post postKey = new Post();
		postKey.setCraftId(craftId);

		DynamoDBQueryExpression<Post> queryExpression = new DynamoDBQueryExpression<Post>()
				.withHashKeyValues(postKey)
				.withRangeKeyCondition("PostTimestamp", rangeKeyCondition)
				.withIndexName("CraftId-PostTimestamp-index");
		queryExpression.setConsistentRead(false);

		List<Post> latestsPosts = mapper.query(Post.class, queryExpression);

		return latestsPosts;
	}

	/**
	 * 
	 * @param fandomId
	 *            fandom id
	 * @param timeWindow
	 *            how far back to go
	 * @return
	 */
	public List<Post> getpostsByFandom(String fandomtId, int timeWindow) {

		long t = (new Date()).getTime()
				- (((long) timeWindow) * 24L * 60L * 60L * 1000L);
		Date time = new Date();
		time.setTime(t);
		SimpleDateFormat df = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String timeString = df.format(time);

		Condition rangeKeyCondition = new Condition().withComparisonOperator(
				ComparisonOperator.GT.toString()).withAttributeValueList(
				new AttributeValue().withS(timeString));

		Post postKey = new Post();
		postKey.setFandomId(fandomtId);

		DynamoDBQueryExpression<Post> queryExpression = new DynamoDBQueryExpression<Post>()
				.withHashKeyValues(postKey)
				.withRangeKeyCondition("PostTimestamp", rangeKeyCondition)
				.withIndexName("FandomId-PostTimestamp-index");
		queryExpression.setConsistentRead(false);// this is necessary, otherwise
													// throws an error

		List<Post> latestsPosts = mapper.query(Post.class, queryExpression);

		return latestsPosts;
	}

}
