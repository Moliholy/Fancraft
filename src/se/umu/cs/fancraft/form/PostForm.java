package se.umu.cs.fancraft.form;

import java.io.Serializable;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class PostForm implements Serializable {

	private static final long serialVersionUID = -6281524756841721178L;
	private String text;
	private CommonsMultipartFile picture;
	private CommonsMultipartFile file;
	private String craftId;
	private String fandomId;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public CommonsMultipartFile getPicture() {
		return picture;
	}

	public void setPicture(CommonsMultipartFile picture) {
		this.picture = picture;
	}

	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}
}
