package domain;

import java.io.Serializable;

public class Estate implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String location;
	
	private String type;
	
	public Estate(String id, String location, String type) {
		this.id = id;
		this.location = location;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public String getLocation() {
		return location;
	}

	public String getType() {
		return type;
	}
}
