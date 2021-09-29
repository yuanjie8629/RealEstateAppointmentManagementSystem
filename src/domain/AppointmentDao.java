package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentDao {
	public void addAppointment(Appointment newAppointment);
	
	public void deleteAppointment(Appointment selectedAppointment);
	
	public Appointment findAppointment(String appointmentId);
	
	public Appointment findAppointmentsOfCustomer(String appointmentId, String icNum);
	
	public List<Appointment> getAllAppointments();
	
	public List<Appointment> getBookedSlots(LocalDate appointmentDate, Estate estate);
	
	public List<Agent> getUnavailableAgents(LocalDate appointmentDate, LocalTime startTime, LocalTime endTime);
	
	public String getNewAppointmentId();
	
	public int getNumberOfAppointments();
	
	public int getNumOfAptmtOfCustomer(String icNum);
	
	public void readAppointmentFile();
	
	public void rescheduleAppointment(Appointment selectedAppointment, String appointmentId, LocalDate newAppointmentDate, LocalTime newStartTime,  Agent newAgent);
	
	public void updateAppointmentFile();
}
