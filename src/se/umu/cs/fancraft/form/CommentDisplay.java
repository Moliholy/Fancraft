package se.umu.cs.fancraft.form;

import java.util.Date;

public class CommentDisplay {
	
	private String posterID;
	private String posterName;
	private String postText;
	private Date postTimestamp;
	public String getPosterID() {
		return posterID;
	}
	public void setPosterID(String posterID) {
		this.posterID = posterID;
	}
	public String getPosterName() {
		return posterName;
	}
	public void setPosterName(String posterName) {
		this.posterName = posterName;
	}
	public String getPostText() {
		return postText;
	}
	public void setPostText(String postText) {
		this.postText = postText;
	}
	public Date getPostTimestamp() {
		return postTimestamp;
	}
	public void setPostTimestamp(Date postTimestamp) {
		this.postTimestamp = postTimestamp;
	}
	
	

}
