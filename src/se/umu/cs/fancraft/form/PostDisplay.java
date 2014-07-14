package se.umu.cs.fancraft.form;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class PostDisplay implements Serializable {

	private static final long serialVersionUID = 7032237763337384986L;

	private String posterId;
	private String posterName;
	private Date postTimestamp;
	private String originalPosterId;
	private String originalPosterName;
	private Date originalPostTimestamp;
	private String originalPostText;
	private String text;
	private String pictureLink;
	private String fileLink;
	private String fileLabel;
	private String craftId;
	private String fandomId;
	private boolean liked;
	private boolean shared;
	private String likes;
	private String shares;
	private ArrayList<CommentDisplay> comments;

	public String getOriginalPosterName() {
		return originalPosterName;
	}

	public void setOriginalPosterName(String originalPosterName) {
		this.originalPosterName = originalPosterName;
	}

	public String getPosterName() {
		return posterName;
	}

	public void setPosterName(String posterName) {
		this.posterName = posterName;
	}

	public ArrayList<CommentDisplay> getComments() {
		return comments;
	}

	public void setComments(ArrayList<CommentDisplay> comments) {
		this.comments = comments;
	}

	public String getPosterId() {
		return posterId;
	}

	public void setPosterId(String posterId) {
		this.posterId = posterId;
	}

	public String getOriginalPosterId() {
		return originalPosterId;
	}

	public void setOriginalPosterId(String originalPosterId) {
		this.originalPosterId = originalPosterId;
	}

	public Date getOriginalPostTimestamp() {
		return originalPostTimestamp;
	}

	public void setOriginalPostTimestamp(Date originalPostTimestamp) {
		this.originalPostTimestamp = originalPostTimestamp;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPictureLink() {
		return pictureLink;
	}

	public void setPictureLink(String pictureLink) {
		this.pictureLink = pictureLink;
	}

	public String getFileLink() {
		return fileLink;
	}

	public void setFileLink(String fileLink) {
		this.fileLink = fileLink;
	}

	public String getCraftId() {
		return craftId;
	}

	public void setCraftId(String craftId) {
		this.craftId = craftId;
	}

	public String getFandomId() {
		return fandomId;
	}

	public void setFandomId(String fandomId) {
		this.fandomId = fandomId;
	}

	public String getLikes() {
		return likes;
	}

	public void setLikes(String likes) {
		this.likes = likes;
	}

	public String getShares() {
		return shares;
	}

	public void setShares(String shares) {
		this.shares = shares;
	}

	public Date getPostTimestamp() {
		return postTimestamp;
	}

	public void setPostTimestamp(Date postTimestamp) {
		this.postTimestamp = postTimestamp;
	}

	public String getOriginalPostText() {
		return originalPostText;
	}

	public void setOriginalPostText(String originalPostText) {
		this.originalPostText = originalPostText;
	}

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}

	public boolean isShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	public String getFileLabel() {
		return fileLabel;
	}

	public void setFileLabel(String fileLabel) {
		this.fileLabel = fileLabel;
	}

	public String getPostTimestampToString() {
		SimpleDateFormat df = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		return df.format(postTimestamp);
	}

}
