package se.umu.cs.fancraft.form;

import java.io.Serializable;
import org.hibernate.validator.constraints.NotEmpty;

public class AddFandomForm implements Serializable {

	private static final long serialVersionUID = -3536671868467909278L;

	@NotEmpty
	private String fandomId;

	public String getFandomId() {
		return fandomId;
	}

	public void setFandomId(String fandomId) {
		this.fandomId = fandomId;
	}

}
