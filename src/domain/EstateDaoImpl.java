package domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EstateDaoImpl implements EstateDao{

	private List<Estate> estateList;
	
	public EstateDaoImpl() {
		this.estateList = new ArrayList<>();
	}
	
	@Override
	public Estate findEstate(String estateId) {
		Estate selectedEstate = null;
		
		for (Estate estate : estateList) {
			if (estate.getId().equals(estateId))
				selectedEstate = estate;
		}
		
		return selectedEstate;
	}

	@Override
	public List<Estate> getAllEstates() {
		return estateList;
	}

	@Override
	public void readEstateFile() {
		ArrayList<String[]> linesRead = new ArrayList<String[]>();
		String fileName = "estates.txt";
		Scanner inputStream = null;
		
		try {
			inputStream = new Scanner(new File(fileName));
			
			while (inputStream.hasNextLine()) {
				String singleLine = inputStream.nextLine();
				String[] tokens = singleLine.split(",");
				linesRead.add(tokens);
			}
			
			for (String[] strArray : linesRead) {
				Estate estate = new Estate(strArray[0], strArray[1], strArray[2]);
				
				estateList.add(estate);
			}
		
		} catch (FileNotFoundException exc) {
			System.out.println("\"" + fileName + "\" not found.");
		}
		
		inputStream.close();
	}
}
