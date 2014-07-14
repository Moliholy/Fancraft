package se.umu.cs.fancraft.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class RegisterForm {

	@NotEmpty
	@Size(min = 3, max = 50)
	private String userName;

	@NotEmpty
	@Size(min = 5, max = 100)
	private String displayName;

	@NotEmpty
	@Size(min = 8, max = 20)
	private String password;

	@NotEmpty
	@Size(min = 8, max = 20)
	private String confirmPassword;

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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
