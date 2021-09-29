package domain;

import java.util.List;

public interface EstateDao {
	public Estate findEstate(String estateId);
	
	public List<Estate> getAllEstates();
	
	public void readEstateFile();
}
