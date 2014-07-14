package se.umu.cs.fancraft.entity;

import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="FanCraft_Posts")
public class Post {
	
	private String posterId;
	private String posttimestamp;
	private String originalPostId; //in case this is a shared post
	private String originalPostTimestamp; //in case this is a shared post
	private String postText;
	private String pictureLink; //stored in S3
	private String fileLink; //stored in S3
	private String fileName;
	private String craftId;
	private String fandomId;
	private Set<String> likes;
	private Set<String> shares;
	
	
	@DynamoDBHashKey(attributeName="PosterId")
	public String getPosterId() {
		return posterId;
	}
	public void setPosterId(String posterId) {
		this.posterId = posterId;
	}
	
	@DynamoDBRangeKey(attributeName="PostTimestamp")
	@DynamoDBIndexRangeKey(globalSecondaryIndexNames={"CraftId-PostTimestamp-index","FandomId-PostTimestamp-index"}, attributeName="PostTimestamp")
	public String getPosttimestamp() {
		return posttimestamp;
	}
	public void setPosttimestamp(String posttimestamp) {
		this.posttimestamp = posttimestamp;
	}
	
	@DynamoDBAttribute(attributeName="OriginalPostId")
	public String getOriginalPostId() {
		return originalPostId;
	}
	public void setOriginalPostId(String originalPostId) {
		this.originalPostId = originalPostId;
	}
	
	@DynamoDBAttribute(attributeName="OriginalPostTimestamp")
	public String getOriginalPostTimestamp() {
		return originalPostTimestamp;
	}
	public void setOriginalPostTimestamp(String originalPostTimestamp) {
		this.originalPostTimestamp = originalPostTimestamp;
	}
	
	@DynamoDBAttribute(attributeName="PostText")
	public String getPostText() {
		return postText;
	}
	public void setPostText(String postText) {
		this.postText = postText;
	}
	
	@DynamoDBAttribute(attributeName="PictureLink")
	public String getPictureLink() {
		return pictureLink;
	}
	public void setPictureLink(String pictureLink) {
		this.pictureLink = pictureLink;
	}
	
	@DynamoDBAttribute(attributeName="FileLink")
	public String getFileLink() {
		return fileLink;
	}
	public void setFileLink(String fileLink) {
		this.fileLink = fileLink;
	}
	
	@DynamoDBAttribute(attributeName="FileName")
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@DynamoDBIndexHashKey(globalSecondaryIndexName="CraftId-PostTimestamp-index", attributeName="CraftId")
	public String getCraftId() {
		return craftId;
	}
	public void setCraftId(String craftId) {
		this.craftId = craftId;
	}
	
	@DynamoDBIndexHashKey(globalSecondaryIndexName="FandomId-PostTimestamp-index",attributeName="FandomId")
	public String getFandomId() {
		return fandomId;
	}
	public void setFandomId(String fandomId) {
		this.fandomId = fandomId;
	}
	
	@DynamoDBAttribute(attributeName="Likes")
	public Set<String> getLikes() {
		return likes;
	}
	public void setLikes(Set<String> likes) {
		this.likes = likes;
	}
	
	@DynamoDBAttribute(attributeName="Shares")
	public Set<String> getShares() {
		return shares;
	}
	public void setShares(Set<String> shares) {
		this.shares = shares;
	}

	
	

}
