package app;

import java.util.Scanner;

import domain.*;

public class ConsoleApp {
	
	public static void main(String[] args) {
	
		AgentDao agentDao = new AgentDaoImpl();
		
		AppointmentDao appointmentDao = new AppointmentDaoImpl();
		
		CustomerDao customerDao = new CustomerDaoImpl();
		
		EstateDao estateDao = new EstateDaoImpl();

		ClerkDao clerkDao = new ClerkDaoImpl();
		
		agentDao.readAgentFile();
		appointmentDao.readAppointmentFile();
		customerDao.readCustomerFile();
		estateDao.readEstateFile();
		clerkDao.readClerkFile();
		
		Controller controller = new Controller(agentDao, appointmentDao, customerDao, estateDao, clerkDao);
		
		Scanner scanner = new Scanner(System.in);
		
		ConsoleUI userInterface = new ConsoleUI(controller, scanner);
		
		userInterface.start();
	}
}
