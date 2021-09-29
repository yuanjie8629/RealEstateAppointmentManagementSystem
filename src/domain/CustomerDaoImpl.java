package domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerDaoImpl implements CustomerDao{

	private List<Customer> customerList;
	
	public CustomerDaoImpl() {
		this.customerList = new ArrayList<>();
	}
	
	@Override
	public void addCustomer(Customer newCustomer) {
		customerList.add(newCustomer);
	}

	@Override
	public Customer findCustomer(String icNum) {
		Customer selectedCustomer = null;
		
		for (Customer customer : customerList) {
			if (customer.getIcNum().equals(icNum))
				selectedCustomer = customer;
		}
		
		return selectedCustomer;
	}
	
	@Override
	public void readCustomerFile() {
		ArrayList<String[]> linesRead = new ArrayList<String[]>();
		String fileName = "customers.txt";
		Scanner inputStream = null;
		
		try {
			inputStream = new Scanner(new File(fileName));
			
			while (inputStream.hasNextLine()) {
				String singleLine = inputStream.nextLine();
				String[] tokens = singleLine.split(",");
				linesRead.add(tokens);
			}
			
			for (String[] strArray : linesRead) {
				Customer customer = new Customer(strArray[0], strArray[1], strArray[2], strArray[3]);
				
				customerList.add(customer);
			}
			
		} catch (FileNotFoundException exc) {
			System.out.println("\"" + fileName + "\" not found.");
		}
		
		inputStream.close();
	}

	@Override
	public void updateCustomerFile() {
		try {
			File file = new File("customers.txt");
			FileWriter myWriter = new FileWriter(file, true);
			
			if (!file.createNewFile() && file.length() != 0) {
				// append new customer to the existing file with existing customer records
				// go to next line before writing
				myWriter.write("\n");
			}
			
			Customer newCustomer = customerList.get(customerList.size() - 1);
			
	    	myWriter.write(newCustomer.getIcNum() + "," + newCustomer.getName() + "," + newCustomer.getPhoneNum() + "," + newCustomer.getEmail());
	    	myWriter.close();
	    	
	    } catch (IOException e) {
	    	System.out.println("\nError while registering new customer, pleas try again later.\n");
	    }
	}
}
