package se.umu.cs.fancraft.form;

import java.io.Serializable;
import org.hibernate.validator.constraints.NotEmpty;

public class AddCraftForm implements Serializable {

	private static final long serialVersionUID = -6374456176632435673L;
	
	@NotEmpty
	private String craftId;

	public String getCraftId() {
		return craftId;
	}

	public void setCraftId(String craftId) {
		this.craftId = craftId;
	}

}
