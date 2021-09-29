package domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppointmentDaoImpl implements AppointmentDao {

	List<Appointment> appointmentList;
	
	public AppointmentDaoImpl() {
		this.appointmentList = new ArrayList<>();
	}
	
	@Override
	public void addAppointment(Appointment newAppointment) {
		appointmentList.add(newAppointment);
		
		Collections.sort(appointmentList);
	}

	@Override
	public void deleteAppointment(Appointment selectedAppointment) {
		appointmentList.remove(selectedAppointment);
		
		Collections.sort(appointmentList);
	}

	@Override
	public Appointment findAppointment(String appointmentId) {
		Appointment selectedAppointment = null;
		
		for (Appointment appointment : appointmentList) {
			if (appointment.getId().equals(appointmentId))
				selectedAppointment = appointment;
		}
		
		return selectedAppointment;
	}

	@Override
	public Appointment findAppointmentsOfCustomer(String appointmentId, String icNum) {
		Appointment selectedAppointment = null;
		
		for (Appointment appointment : appointmentList) {
			if (appointment.getId().equals(appointmentId) && appointment.getCustomer().getIcNum().equals(icNum))
				selectedAppointment = appointment;
		}
		
		return selectedAppointment;
	}

	@Override
	public List<Appointment> getAllAppointments() {
		return appointmentList;
	}

	@Override
	public List<Appointment> getBookedSlots(LocalDate appointmentDate, Estate estate) {
		List<Appointment> sameEstateNDayAppointments = new ArrayList<>();
		
		for (Appointment appointment : appointmentList) {
			Estate anEstate = appointment.getEstate(); 
			LocalDate date = appointment.getAppointmentDate();
			
			if (anEstate.getId().equals(estate.getId())) {
				if (date.equals(appointmentDate))
					sameEstateNDayAppointments.add(appointment);
			}
		}
		
		return sameEstateNDayAppointments;
	}

	@Override
	public String getNewAppointmentId() {
		String newId;
		
		if (appointmentList.size() == 0) // no appointment made yet, appointment ID = first ID
			newId =  "AP0001";
		else {
			Appointment lastAppointmentMade = appointmentList.get(0);
			
			for (int i = 1; i < appointmentList.size(); i++) {
				String lastAppointmentID = lastAppointmentMade.getId();
				
				Appointment currentAppointment = appointmentList.get(i);
				String currentAppointmentID = currentAppointment.getId();
				
				// if current appointments ID > last appointment ID, current appointment will be the last appointment
				// e.g. AP0001.compareTo(AP0005) will return -1, and AP0005 will be latest appointment made
				// AP0005.compareTo(AP0001) will return 1, so AP0005 continue to be latest appointment made
				if (lastAppointmentID.compareTo(currentAppointmentID) < 0) {
					lastAppointmentMade = currentAppointment;
				}
			}
			
			// convert id to integer, increment by 1, add "AP" and pad the left side with 0s
			String lastId = lastAppointmentMade.getId();
			
			int idIntPart = Integer.parseInt(lastId.substring(2));
			idIntPart++;
			
			newId = String.format("AP%04d", idIntPart);
		}
		
		return newId;
	}

	@Override
	public int getNumberOfAppointments() {
		return appointmentList.size();
	}
	
	@Override
	public int getNumOfAptmtOfCustomer(String icNum) {
		int count = 0;
		
		for (Appointment appointment : appointmentList) {
			if (appointment.getCustomer().getIcNum().equals(icNum))
				count++;
		}
		
		return count;
	}

	@Override
	public List<Agent> getUnavailableAgents(LocalDate appointmentDate, LocalTime startTime, LocalTime endTime) {
		List<Appointment> sameTimeAppointments = new ArrayList<>();
		
		for (Appointment appointment : appointmentList) {
			if (appointment.getAppointmentDate().isEqual(appointmentDate)) {
				
				boolean clashTime = (appointment.getStartTime().isAfter(startTime) && appointment.getStartTime().isBefore(endTime)) ||
						(appointment.getEndTime().isAfter(startTime) && appointment.getEndTime().isBefore(endTime) || appointment.getStartTime().equals(startTime) || appointment.getEndTime().equals(endTime));
				
				if (clashTime)
					sameTimeAppointments.add(appointment);
			}
		}
		
		List<Agent> unavailableAgents = new ArrayList<>(); 
		
		for (Appointment appointment : sameTimeAppointments) {
			unavailableAgents.add(appointment.getAgent());
		}
		
		return unavailableAgents;
	}

	@Override
	public void readAppointmentFile() {
		String fileName = "appointments";
		
		try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
 
            boolean moreObject = true;
            while (moreObject) {
            	Object obj = null;
            	
				try {
					obj = objectIn.readObject();
				} catch(ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                } catch (IOException e) {
        			break;
        		}
				
				appointmentList.add((Appointment) obj);
			}
            
            objectIn.close();
 
        } catch (FileNotFoundException exc) {
			System.out.println("\"" + fileName + "\" not found.");
		} catch (Exception e) {
            System.out.println("\nError reading appointment records \"appointments\" file.\n");
        }
	}
	
	@Override
	public void rescheduleAppointment(Appointment selectedAppointment, String appointmentId, LocalDate newAppointmentDate, LocalTime newStartTime,  Agent newAgent) {
		selectedAppointment.setAppointmentDate(newAppointmentDate);
		selectedAppointment.setStartTime(newStartTime);
		selectedAppointment.setEndTime(selectedAppointment.computeEndTime(newStartTime, selectedAppointment.getEstate().getType()));
		selectedAppointment.setAgent(newAgent);
		
		Collections.sort(appointmentList);
	}

	@Override
	public void updateAppointmentFile() {
		String fileName = "appointments";
		
		try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
 
            for (Appointment appointment : appointmentList) {
	            objectOut.writeObject(appointment);
            }
            
            objectOut.close();
 
        } catch (FileNotFoundException exc) {
			System.out.println("\"" + fileName + "\" not found.");
		} catch (IOException e) {
			System.out.println("\nError updating appointment records to \"appointments.txt\" file.\n");
		} catch (Exception e) {
            System.out.println("\nError updating appointment records to \"appointments.txt\" file.\n");
        }
	}
}
