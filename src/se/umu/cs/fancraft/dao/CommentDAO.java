package se.umu.cs.fancraft.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import se.umu.cs.fancraft.entity.Comment;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

public class CommentDAO {
	
	private DynamoDBMapper mapper;
	private AmazonDynamoDBClient client;

	public CommentDAO() {
		client = new AmazonDynamoDBClient(
				new ClasspathPropertiesFileCredentialsProvider());
		mapper = new DynamoDBMapper(client);
	}
	
	/**
	 * Adds a comment to DynamoDB
	 * @param posterId the userId of the person that made the post we're going to comment on
	 * @param postTimestamp the timestamp of the post we're going to comment on
	 * @param commenterId the userId of the person making the comment
	 * @param commentText the comment text
	 */
	public void addComment(String posterId, String postTimestamp, String commenterId, String commentText){
		Comment newComment = new Comment();
		
		newComment.setPostId(posterId + "#" + postTimestamp);
		newComment.setCommentTimestamp(dateToString(new Date()));
		newComment.setPosterId(commenterId);
		newComment.setCommentText(commentText);
		
		mapper.save(newComment);
	}
	
	public List<Comment> getCommentsForPost(String posterId, String postTimestamp){
		
		Comment keyComment = new Comment();
		keyComment.setPostId(posterId + "#" + postTimestamp);
		
		DynamoDBQueryExpression<Comment> queryExpression = new DynamoDBQueryExpression<Comment>()
				.withHashKeyValues(keyComment);
		
		List<Comment> comments = mapper.query(Comment.class, queryExpression);
		
		return comments;
	}
	
	private String dateToString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		return df.format(date);
	}

}
