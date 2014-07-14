package se.umu.cs.fancraft.form;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class ShareForm implements Serializable {

	private static final long serialVersionUID = -5976825887039478575L;
	
	@NotEmpty
	private String posterId;
	
	@NotEmpty
	private String postTimestamp;
	
	@NotEmpty
	private String shareText;

	public String getPosterId() {
		return posterId;
	}

	public void setPosterId(String posterId) {
		this.posterId = posterId;
	}

	public String getPostTimestamp() {
		return postTimestamp;
	}

	public void setPostTimestamp(String postTimestamp) {
		this.postTimestamp = postTimestamp;
	}

	public String getShareText() {
		return shareText;
	}

	public void setShareText(String shareText) {
		this.shareText = shareText;
	}

}
