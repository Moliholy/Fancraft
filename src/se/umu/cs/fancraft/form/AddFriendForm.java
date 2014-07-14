package se.umu.cs.fancraft.form;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class AddFriendForm implements Serializable {

	private static final long serialVersionUID = 3967285306747566621L;

	@NotEmpty
	private String friendName;

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

}
