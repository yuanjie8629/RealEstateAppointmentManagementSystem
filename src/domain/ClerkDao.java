package domain;

public interface ClerkDao {
	public void readClerkFile();
	
	public boolean checkClerk(String id, String password);
}
