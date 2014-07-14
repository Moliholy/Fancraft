package se.umu.cs.fancraft.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="FanCraft_Comments")
public class Comment {
	
	private String postId;
	private String commentTimestamp;
	private String posterId;
	private String commentText;
	
	@DynamoDBHashKey(attributeName="PostId")
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	
	@DynamoDBRangeKey(attributeName="CommentTimestamp")
	public String getCommentTimestamp() {
		return commentTimestamp;
	}
	public void setCommentTimestamp(String commentTimestamp) {
		this.commentTimestamp = commentTimestamp;
	}
	
	@DynamoDBAttribute(attributeName="PosterId")
	public String getPosterId() {
		return posterId;
	}
	public void setPosterId(String posterId) {
		this.posterId = posterId;
	}
	
	@DynamoDBAttribute(attributeName="CommentText")
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	
	

}
