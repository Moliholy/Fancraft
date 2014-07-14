package se.umu.cs.fancraft.util;

import java.io.Serializable;

public class Friend implements Serializable {

	private static final long serialVersionUID = 2505007768963649829L;

	public Friend() {
		super();
	}

	public Friend(String userId, String displayName) {
		super();
		this.userId = userId;
		this.displayName = displayName;
	}

	private String userId;
	private String displayName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
