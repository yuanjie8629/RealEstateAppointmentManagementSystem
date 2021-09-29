package domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ClerkDaoImpl implements ClerkDao{
	List<Clerk> clerkList;
	
	public ClerkDaoImpl() {
		this.clerkList = new ArrayList<>();
	}
	
	@Override
	public void readClerkFile() {
		ArrayList<String[]> linesRead = new ArrayList<String[]>();
		String fileName = "clerk.txt";
		Scanner inputStream = null;
		
		try {
			inputStream = new Scanner(new File(fileName));
			
			while (inputStream.hasNextLine()) {
				String singleLine = inputStream.nextLine();
				String[] tokens = singleLine.split(",");
				linesRead.add(tokens);
			}
			
			for (String[] strArray : linesRead) {
				Clerk clerk = new Clerk(strArray[0], strArray[1], strArray[2]);
				
				clerkList.add(clerk);
			}
		
		} catch (FileNotFoundException exc) {
			System.out.println("\"" + fileName + "\" not found.");
		}
		
		inputStream.close();
	}

	@Override
	public boolean checkClerk(String id, String password) {
		for (Clerk clerk : clerkList) {
			if (clerk.getId().equals(id) && clerk.getPassword().equals(password))
				return true;
		}
		
		return false;
	}

}
