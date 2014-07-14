package se.umu.cs.fancraft.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginForm {

	@NotEmpty
	@Size(min = 3, max = 50)
	private String userName;

	@NotEmpty
	@Size(min = 8, max = 20)
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
