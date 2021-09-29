package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Controller {

	private AppointmentDao appointmentDao;
	
	private AgentDao agentDao;
	
	private CustomerDao customerDao;
	
	private EstateDao estateDao;
	
	private ClerkDao clerkDao;
	
	public Controller(AgentDao agentDao, AppointmentDao appointmentDao, CustomerDao customerDao, EstateDao estateDao, ClerkDao clerkDao) {
		this.agentDao = agentDao;
		this.appointmentDao = appointmentDao;
		this.customerDao = customerDao;
		this.estateDao = estateDao;
		this.clerkDao = clerkDao;
	}
	
	public String addAppointment(String icNum, LocalDate appointDate, LocalDate bookingDate, String agentId, String estateId, LocalTime startTime) {
		Customer customer = findCustomer(icNum);
		
		Agent agent = findAgent(agentId);
		
		Estate estate = findEstate(estateId);
		
		String appointmentId = appointmentDao.getNewAppointmentId();
		
		Appointment newAppointment = new Appointment(customer, bookingDate, appointDate, startTime, estate, appointmentId, agent);
		
		appointmentDao.addAppointment(newAppointment);
		
		appointmentDao.updateAppointmentFile();
		
		return appointmentId;
	}
	
	public void addCustomer(String icNum, String name, String phoneNum, String email) {
		Customer newCustomer = new Customer(icNum, name, phoneNum, email);
		
		customerDao.addCustomer(newCustomer);
		
		customerDao.updateCustomerFile();
	}
	
	public void deleteAppointment(Appointment selectedAppointment) {
		appointmentDao.deleteAppointment(selectedAppointment);
		
		appointmentDao.updateAppointmentFile();	
	}
	
	public Agent findAgent(String agentId) {
		return agentDao.findAgent(agentId);
	}
	
	public Appointment findAppointment(String appointmentId) {
		return appointmentDao.findAppointment(appointmentId);
	}
	
	public Appointment findAppointmentsOfCustomer(String appointmentId, String icNum) {
		return appointmentDao.findAppointmentsOfCustomer(appointmentId, icNum);
	}
	
	public Customer findCustomer(String icNum) {
		return customerDao.findCustomer(icNum);
	}
	
	public Estate findEstate(String estateId) {
		return estateDao.findEstate(estateId);
	}
	
	public List<Agent> getAllAgents() {
		return agentDao.getAllAgents();
	}
	
	public List<Appointment> getAllAppointments() {
		return appointmentDao.getAllAppointments();
	}
	
	public List<Estate> getAllEstates() {
		return estateDao.getAllEstates();
	}
	
	public List<Agent> getAvailableAgents(LocalDate appointmentDate, LocalTime startTime, String estateID) {
		Estate estate = findEstate(estateID);
		Appointment appointment = new Appointment();
		LocalTime endTime = appointment.computeEndTime(startTime, estate.getType());
		
		List<Agent> allAgents = agentDao.getAllAgents();
		
		List<Agent> unvailableAgents = appointmentDao.getUnavailableAgents(appointmentDate, startTime, endTime);
		
		List<Agent> availableAgents = new ArrayList<>();
		
		for (Agent agent : allAgents) {
			
			boolean available = true;
			
			for (Agent unvailableAgent : unvailableAgents) {
				if (agent.getId().equals(unvailableAgent.getId())) {
					available = false;
					break;
				}
			}
			
			if (available)
				availableAgents.add(agent);
		}
		
		return availableAgents;
	}
	
	public List<Appointment> getBookedSlots(LocalDate appointmentDate, Estate estate) {
		return appointmentDao.getBookedSlots(appointmentDate, estate);
	}
	
	public int getNumberOfAppointments() {
		return appointmentDao.getNumberOfAppointments();
	}
	
	public int getNumOfAptmtOfCustomer(String icNum) {
		return appointmentDao.getNumOfAptmtOfCustomer(icNum);
	}
	
	public void rescheduleAppointment(Appointment selectedAppointment, String appointmentId, LocalDate newAppointmentDate, LocalTime newStartTime,  Agent newAgent) {
		appointmentDao.rescheduleAppointment(selectedAppointment, appointmentId, newAppointmentDate, newStartTime,  newAgent);
		
		appointmentDao.updateAppointmentFile();	
	}
	
	public boolean checkClerk(String clerkID, String password) {
		return clerkDao.checkClerk(clerkID, password);
	}
}
