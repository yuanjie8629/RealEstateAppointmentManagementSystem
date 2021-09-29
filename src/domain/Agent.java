package domain;

import java.io.Serializable;

public class Agent implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String email;
	
	private String icNum;
	
	private String id;
	
	private String name;
	
	private String phoneNum;
	
	public Agent(String email, String icNum, String id, String name, String phoneNum) {
		this.email = email;
		this.icNum = icNum;
		this.id = id;
		this.name = name;
		this.phoneNum = phoneNum;
	}

	public String getEmail() {
		return email;
	}

	public String getIcNum() {
		return icNum;
	}

	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public String getPhoneNum() {
		return phoneNum;
	}
}
