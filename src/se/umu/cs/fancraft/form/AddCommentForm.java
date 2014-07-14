package se.umu.cs.fancraft.form;

import java.io.Serializable;
import org.hibernate.validator.constraints.NotEmpty;

public class AddCommentForm implements Serializable {

	private static final long serialVersionUID = -6639843976764766865L;

	@NotEmpty
	private String posterID;
	
	@NotEmpty
	private String postTimestamp;
	
	@NotEmpty
	private String text;

	public String getPosterID() {
		return posterID;
	}

	public void setPosterID(String posterID) {
		this.posterID = posterID;
	}

	public String getPostTimestamp() {
		return postTimestamp;
	}

	public void setPostTimestamp(String postTimestamp) {
		this.postTimestamp = postTimestamp;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
