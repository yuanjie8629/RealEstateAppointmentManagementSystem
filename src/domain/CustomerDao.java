package domain;

public interface CustomerDao {
	public void addCustomer(Customer newCustomer);
	
	public Customer findCustomer(String icNum);
	
	public void readCustomerFile();
	
	public void updateCustomerFile();
}
