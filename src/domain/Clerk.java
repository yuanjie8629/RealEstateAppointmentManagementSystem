package domain;

public class Clerk {
	private String id;
	public String getId() {
		return id;
	}
	
	private String password;
	public String getPassword() {
		return password;
	}
	
	private String name;
	public String getName() {
		return name;
	}
	
	public Clerk(String id, String password, String name) {
		this.id = id;
		this.password = password;
		this.name = name;
	}
}
