package domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment implements Serializable, Comparable<Appointment> {

	private static final long serialVersionUID = 1L;

	private Customer customer;
	
	private LocalDate bookingDate;
	
	private LocalDate appointmentDate;
	
	private LocalTime startTime;
	
	private LocalTime endTime;
	
	private Estate estate;
	
	private String id;
	
	private Agent agent;
	
	public Appointment(Customer customer, LocalDate bookingDate, LocalDate appointmentDate, LocalTime startTime , Estate estate, String id, Agent agent) {
		this.customer= customer;
		this.bookingDate = LocalDate.now();
		this.appointmentDate = appointmentDate;
		this.startTime = startTime;
		this.endTime = computeEndTime(startTime, estate.getType());
		this.estate = estate;
		this.id = id;
		this.agent = agent;
	}
	public Appointment() {
		
	}

	public Customer getCustomer() {
		return customer;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public Estate getEstate() {
		return estate;
	}
	
	public String getId() {
		return id;
	}

	public Agent getAgent() {
		return agent;
	}
	
	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	
	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public LocalTime computeEndTime(LocalTime startTime, String estateType) {
		LocalTime endTime;
		
		if (estateType.equals("new house"))
			endTime = startTime.plusHours(1);
		else if (estateType.contentEquals("sample house"))
			endTime = startTime.plusMinutes(90);
		else
			endTime = startTime.plusHours(2);
		
		return endTime;
	}

	@Override
	public int compareTo(Appointment anotherAppointment) {
		
		// compare date, if date same compare startTime, if startTime same compare endTime,
		// if endTime same compare estate ID.
		
		
		// this appointment's date > another appointment's date
		if (this.appointmentDate.isAfter(anotherAppointment.getAppointmentDate()))
			return 1;
		
		// this appointment's date < another appointment's date
		else if (this.appointmentDate.isBefore(anotherAppointment.getAppointmentDate()))
			return -1;
		
		// this appointment and another appointment is on same day
		else {
			
			// this appointment's start time > another appointment's start time
			if (this.startTime.isAfter(anotherAppointment.getStartTime()))
				return 1;
			
			// this appointment's start time < another appointment's start time
			else if (this.startTime.isBefore(anotherAppointment.getStartTime()))
				return -1;
			
			// this appointment and another appointment start at the same time
			else {
				
				// this appointment's end time > another appointment's end time
				if (this.endTime.isAfter(anotherAppointment.getEndTime()))
					return 1;
				
				// this appointment's end time < another appointment's end time
				else if (this.endTime.isBefore(anotherAppointment.getEndTime()))
					return -1;
				
				// compare estate id
				else {
					String thisId = this.estate.getId();
					String anotherId = anotherAppointment.getEstate().getId();

					return thisId.compareTo(anotherId);
				}
			}			
		}
	}
}
