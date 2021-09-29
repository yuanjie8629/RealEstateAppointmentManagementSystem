package domain;

import java.io.Serializable;

public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;
	
	private String icNum;
	
	private String name;
	
	private String phoneNum;
	
	public Customer(String icNum, String name, String phoneNum, String email) {
		this.email = email;
		this.icNum = icNum;
		this.name = name;
		this.phoneNum = phoneNum;
	}

	public String getEmail() {
		return email;
	}

	public String getIcNum() {
		return icNum;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNum() {
		return phoneNum;
	}
}
