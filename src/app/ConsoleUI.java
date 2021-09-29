package app;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import domain.*;

public class ConsoleUI {

	Controller controller;
	
	Scanner scanner;
	
	Customer customer;
	
	public ConsoleUI(Controller controller, Scanner scanner) {
		this.controller = controller;
		this.scanner = scanner;
	}
	
	public void start() {
		loginClerkAccount();
		boolean quit = false;
		do {
			System.out.println("+-----------------------------------+");
			System.out.println("|            Real Estate            |");
			System.out.println("|          Management System        |");
			System.out.println("|             Admin Only            |");
			System.out.println("+-----------------------------------+");
			System.out.println("Select an option:");
			System.out.println("[ 1 ] View All Appointments");
			System.out.println("[ 2 ] Search Appointment");
			System.out.println("[ 3 ] Login Customer Account");
			System.out.println("[ 4 ] Create Customer Account");
			System.out.println("[ 5 ] View Booked Slots By Date");
			System.out.println("[ 6 ] Quit");
			
			int option = 0;
			boolean inputInvalid;
			do {
				try {
					System.out.print(">>");
					
					String input = scanner.nextLine();
					option = Integer.parseInt(input);
				
					if (option < 1 || option > 5) 
						throw new NumberFormatException();
					
					inputInvalid = false;
				} catch(NumberFormatException exc) {
					inputInvalid = true;
					System.out.println("\nNot a valid option! choose between 1 - 5\n");
				}
			}while(inputInvalid);
			
			switch(option) {
			
			case 1: viewAllAppointments();
					break;
			case 2: searchAppointment();
					break;
			case 3: loginCustomerAccount();
					break;
			case 4: createCustomerAccount();
					break;
			case 5: viewBookedSlotsByDate();
					break;
			case 6: quit = true;
					break;
			}
		}while(!quit);
	}
	
	public void loginClerkAccount() {
		System.out.println("+-----------------------------------+");
		System.out.println("|            Real Estate            |");
		System.out.println("|          Management System        |");
		System.out.println("|             Admin Only            |");
		System.out.println("+-----------------------------------+");
		String clerkID = null, password = null;
		for (int i = 0; i < 3; i++) {
			System.out.print("Please enter clerk ID (CK0001):  ");
			clerkID = scanner.nextLine();
			System.out.print("Please enter password (admin1):  ");
			password = scanner.nextLine();
			if (controller.checkClerk(clerkID, password)) {
				System.out.println("\nLogin successful.\n");
				break;
			}
			else {
				System.out.println("\nLogin Failed! " + (2 - i) + " attempt(s) more.\n");
			}
			
			if (i == 2) {
				System.out.println("\nSystem terminate due to too many login attempts.");
				System.exit(0);
			}
		}
	}
	
	public void cancelAppointment(String icNum) {
		String appointmentID;
		
		int count = controller.getNumOfAptmtOfCustomer(icNum);
		
		if(count == 0)
			System.out.println("\nNo appointment has been made yet.\n");
		else {
			boolean inputInvalid;
			do {
				try {
					System.out.print("Please enter Appointment ID you want to cancel (e.g. AP0001): ");
					appointmentID=scanner.nextLine();
					
					Appointment selectedAppointment = controller.findAppointmentsOfCustomer(appointmentID, icNum);
					
					if (selectedAppointment == null) 
						throw new IllegalArgumentException("\nNo appointment found.\n");
					
					controller.deleteAppointment(selectedAppointment);
					
					System.out.println("\nAppointment cancelled.\n");
					
					inputInvalid = false;
				}catch(IllegalArgumentException e) {
					inputInvalid= true;
					System.out.println(e.getMessage());
				}
			}while(inputInvalid);
		}
	}
	
	public void createAppointment() {
		LocalDate bookingDate = LocalDate.now();
		
		// get estate
		Estate estate = null;
		String estateID = null;
		boolean inputInvalid= true;
		do {
			try {
				System.out.print("Please enter Estate ID (e.g. ES0001) : ");
				estateID = scanner.nextLine();
				estate = controller.findEstate(estateID);
				
				if (estate == null) 
					throw new IllegalArgumentException("\nNot a valid Estate ID!\n");
				
				inputInvalid = false;
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}while(inputInvalid);
		
		inputInvalid = true;
		
		
		// get appointment date
		LocalDate appointmentDate = null;
		int day = 0, month = 0, year = 0;
		
		boolean dateInvalid;
		do {
			try {
				// get day
				do {
					try {
						System.out.print("Please enter appointment day (1 - 31): ");
						day=Integer.parseInt(scanner.nextLine());
						
						if(day < 1 || day > 31) 
							throw new NumberFormatException();
						
						inputInvalid = false;
					} catch(NumberFormatException e) {
						inputInvalid = true;
						System.out.println("\nNot a valid date! Only accept day between 1 - 31\n");
					} 
				} while(inputInvalid);
				
				// get month
				do {
					try {
						System.out.print("Please enter appointment month (1 - 12): ");
						month=Integer.parseInt(scanner.nextLine());
						
						if(month < 1 || month > 12) 
							throw new NumberFormatException();
						
						inputInvalid = false;
					} catch(NumberFormatException e) {
						inputInvalid = true;
						System.out.println("\nNot a valid month! Only accept month between 1 - 12\\n");
					}
				} while(inputInvalid);
				
				// get year
				do {
					try {
						System.out.print("Please enter appointment year (e.g. 2021): ");
						year=Integer.parseInt(scanner.nextLine());
						
						if (year < 2021 || year > 2025) 
							throw new NumberFormatException();
						
						inputInvalid=false;
					} catch(NumberFormatException e) {
						inputInvalid = true;
						System.out.println("\nNot a valid year! Only accept year between 2021 - 2025\n");
					}
				} while(inputInvalid);
				
				// this will throw DateTimeException is the date is not valid
				appointmentDate = LocalDate.of(year, month, day);
				
				dateInvalid = false;
			} catch(DateTimeException e) {
				dateInvalid = true;
				System.out.println("\nNot a valid date!\n");
			}
		} while(dateInvalid);
		
		
		// show booked slot for the estate on the specified date
		List<Appointment> sameEstateNDateAppointments = controller.getBookedSlots(appointmentDate, estate);
		
		if (sameEstateNDateAppointments.size() != 0) {
			System.out.println("\nBooked slot for estate " + estate.getId() + " on " + appointmentDate + ":");
			System.out.println(String.format("%-3s%-11s%-11s", "no","Start time", "End time"));
			
			for(int i=0; i < sameEstateNDateAppointments.size(); i++) {
				LocalTime bookedStartTime = sameEstateNDateAppointments.get(i).getStartTime();
				LocalTime bookedEndTime = sameEstateNDateAppointments.get(i).getEndTime();
				
				String row = String.format("%-3s%-11s%-11s", i + 1, bookedStartTime, bookedEndTime);
				
				System.out.println(row);
			}
			System.out.println();
		}
		
		
		// get time
		LocalTime startTime = null;
		String time;
		int hour = 0, minute = 0;
		boolean repeat = false;
		do {
			do {
				try {
					System.out.print("Please enter appointment time (e.g. 13:00): ");
					time = scanner.nextLine();
					
					if (!time.matches("[0-9]{1,2}:[0-9]{1,2}")) 
						throw new IllegalArgumentException("\nNot a valid Time! Only accept time in 24hour format (only 8am to 6pm allowed).\n");
					
					String[]hourAndMinute = time.split(":");
					hour = Integer.parseInt(hourAndMinute[0]);
					minute = Integer.parseInt(hourAndMinute[1]);
					
					if(hour < 8 || hour > 18) 
						throw new IllegalArgumentException("\nNot a valid range of operational hour, please enter within range between 8:00 - 18:00!\n");
					
					if(minute < 0 || minute > 59)
						throw new DateTimeException("\nNot a valid range of operational minute, please enter within range between 0 - 59!\n");
					
					if (hour == 18 && minute != 0)
						throw new IllegalArgumentException("\nNot a valid range of operational hour, please enter within range between 8:00 - 18:00!\n");
					
					inputInvalid = false;
				} catch(NumberFormatException e) {
					inputInvalid = true;
					System.out.println("\nNot a valid time!\n");
				} catch (DateTimeException e) {
					inputInvalid = true;
					System.out.println(e.getMessage());
				} catch (IllegalArgumentException e) {
					inputInvalid = true;
					System.out.println(e.getMessage());
				}
			} while(inputInvalid);
			startTime = LocalTime.of(hour, minute);
			
			
			// check if time is available, if not available, loop to get another time
			if (sameEstateNDateAppointments.size() != 0) {
				Appointment endTimeCalculator = new Appointment();
				LocalTime endTime = endTimeCalculator.computeEndTime(startTime, estate.getType());
						
				for (Appointment appointment : sameEstateNDateAppointments) {
					LocalTime bookedStartTime = appointment.getStartTime();
					LocalTime bookedEndTime = appointment.getEndTime();
					
					boolean clashTime = (startTime.isAfter(bookedStartTime) && startTime.isBefore(bookedEndTime)) || (endTime.isAfter(bookedStartTime) && endTime.isBefore(bookedEndTime) || startTime.equals(bookedStartTime) || endTime.equals(bookedEndTime));
					
					if (clashTime) {
						repeat = true;
						System.out.println("\nThe time is not available, pick another time.\n");
						break;
					}
				}
			}
		
		}while(repeat);
		
		List<Agent> agentList=controller.getAvailableAgents(appointmentDate, startTime, estateID);
		
		if(agentList.size() == 0)
			System.out.println("\nNo agent available! Please try to pick another time.");
		else {
			System.out.println("\nAvailable agents:");
			
			for(int i = 0; i < agentList.size(); i++) {
				System.out.println(i+1 + ". " + agentList.get(i).getName());
			}
			 String agentID = null;
			 do {
				 try {
					 System.out.print("Please enter Agent No. : ");
					 int options = Integer.parseInt(scanner.nextLine());
					 agentID = agentList.get(options-1).getId();
				 } catch (IndexOutOfBoundsException | NumberFormatException e) {
					 System.out.println("Not valid Agent No.! Only accept number from 1 to " + agentList.size() + ".");
				 }
			 }while(agentID == null);
			 
			
			String appointmentId = controller.addAppointment(customer.getIcNum(),appointmentDate,bookingDate,agentID,estateID,startTime);
		
			System.out.println("\nNew appointment made.");
			System.out.println("Appointment ID: " + appointmentId + "\n\n");
		}
	}
	
	
	public void createCustomerAccount() {
		String icNo = null,name = null,phoneNum = null,email = null;
		boolean inputInvalid;
		
		do {
			try {
				System.out.print("Please enter your IC Number (e.g. 001132221123): ");
				icNo=scanner.nextLine();
				if (!icNo.matches("[0-9]{12}"))
					throw new IllegalArgumentException("\nNot a valid IC Number! Please enter your IC Number following format (e.g. 001132221123)\n");
				
				inputInvalid = false;
			} catch(IllegalArgumentException e) {
				inputInvalid = true;
				System.out.println(e.getMessage());
			}
		}while(inputInvalid);
		
		do {
			try {			
				System.out.print("Please enter your Name (e.g. Wo Zi Rock): ");
				name=scanner.nextLine();
				if (!name.matches("[a-zA-Z\\s]{5,30}")) 
					throw new IllegalArgumentException("\nNot a valid Name! Only 5-30 characters (alphabet/space) allowed.\n");
				
				inputInvalid = false;
			} catch(IllegalArgumentException e) {
				inputInvalid = true;
				System.out.println(e.getMessage());
			}
		}while(inputInvalid);
		
		do {
			try {
				System.out.print("Please enter your Phone Number (e.g. 01234567890): ");
				phoneNum=scanner.nextLine();
				
				if (!phoneNum.matches("[0-9]{10,11}"))
					throw new IllegalArgumentException("\nNot a valid Phone Number!\n");
				
				inputInvalid = false;
			} catch(IllegalArgumentException e) {
				inputInvalid = true;
				System.out.println(e.getMessage());
			}
		}while(inputInvalid);
		
		do {
			try {
				String emailRegex="[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
				System.out.print("Please enter your email: ");
				email=scanner.nextLine();
				
				if (!email.matches(emailRegex)) 
					throw new IllegalArgumentException("\nNot a valid Email! Please enter your email following format: xxx@xxxxx.xxx\n");
				
				inputInvalid = false;
			} catch(IllegalArgumentException e) {
				inputInvalid = true;
				System.out.println(e.getMessage());
			}
		}while(inputInvalid);
		
		controller.addCustomer(icNo, name, phoneNum, email);
		
		System.out.println("\nNew customer registered.\n");
	}
	
	public void loginCustomerAccount() {
		String icNo = null;
		
		boolean inputInvalid;
		do {
			try {
				System.out.print("Please enter your IC Number (e.g. 000214209161): ");
				icNo=scanner.nextLine();
				
				if (!icNo.matches("[0-9]{12}"))
					throw new IllegalArgumentException("\nNot a valid IC Number!\n");

				inputInvalid = false;
			} catch (IllegalArgumentException e) {
				inputInvalid = true;
				System.out.println(e.getMessage());
			}
		} while(inputInvalid);
		
		customer = controller.findCustomer(icNo);
			
		if (customer == null)
			System.out.println(("\nCustomer not registered.\n"));
		else {
			
			System.out.println("\nLogin successful.\n");
			
			boolean logout = false;
			do {
				System.out.println("+-----------------------------------+");
				System.out.println("|            Real Estate            |");
				System.out.println("|          Management System        |");
				System.out.println("|             Admin Only            |");
				System.out.println("+-----------------------------------+");
				System.out.println("Select an option:");
				System.out.println("[ 1 ] Make Appointment"); 
				System.out.println("[ 2 ] Cancel Appointment");
				System.out.println("[ 3 ] Reschedule Appointment");
				System.out.println("[ 4 ] Log out");
				
				int option = 0;
				do {
					try {
						System.out.print(">>");
						
						String input = scanner.nextLine();
						option = Integer.parseInt(input);
					
						if (option < 1 || option > 4) 
							throw new NumberFormatException();
						
						inputInvalid = false;
					}catch(NumberFormatException exc) {
						inputInvalid = true;
						System.out.println("\nNot a valid option! Please choose between 1 - 4 only.\n");
					}
				}while(inputInvalid);
				
				switch(option) {
					case 1: createAppointment();
							break;
					case 2: cancelAppointment(icNo);
							break;
					case 3: rescheduleAppointment(icNo);
							break;
					case 4: logout = true;
							break;
				}

			} while(!logout);
		}
	}

	public void rescheduleAppointment(String icNum) {
		String appointmentID = null;
		Appointment selectedAppointment = null;
		
		int count = controller.getNumOfAptmtOfCustomer(icNum);
		
		if(count == 0)
			System.out.println("\nNo appointment has been made yet.\n");
		else {
			boolean inputInvalid;
			do {
				try {
					System.out.print("Please enter the appointment ID (e.g. AP0001): ");
					appointmentID = scanner.nextLine();
					
					selectedAppointment = controller.findAppointmentsOfCustomer(appointmentID, icNum);
					
					if(selectedAppointment == null) 
						throw new IllegalArgumentException("\nNo appointment found!\n");
					
					inputInvalid = false;
				} catch(IllegalArgumentException e) {
					inputInvalid = true;
					System.out.println(e.getMessage());
				}
			} while(inputInvalid);
			
			
			// show original appointment schedule
			System.out.println();
			System.out.println(String.format("%-16s%-26s%-12s%-12s", "Appointment ID", "Appointment date (y-m-d)", "Start time", "End time"));
			System.out.println("-----------------------------------------------------------------------------");
			
			String appointmentId = selectedAppointment.getId();
			LocalDate oriDate = selectedAppointment.getAppointmentDate();
			LocalTime oriStartTime = selectedAppointment.getStartTime();
			LocalTime oriEndTime = selectedAppointment.getEndTime();
			
			System.out.println(String.format("%-16s%-26s%-12s%-12s", appointmentId, oriDate, oriStartTime, oriEndTime));
			
			System.out.println();
			
			
			
			// get new appointment date
			LocalDate newAppointmentDate = null;
			int newDate = 0,newMonth = 0,newYear = 0;
			
			boolean dateInvalid;
			do {
				try {
					//ask day
					do {
						try {
							System.out.print("Please enter new appointment day (1 - 31): ");
							newDate=Integer.parseInt(scanner.nextLine());
							
							if(newDate < 1 || newDate > 31) 
								throw new NumberFormatException();
							
							inputInvalid = false;
						} catch(NumberFormatException e) {
							inputInvalid = true;
							System.out.println("\nNot a valid date! Only accept day between 1 - 31\n");
						}
					} while(inputInvalid);
					
					// ask month
					do {
						try {
							System.out.print("Please enter new appointment month (1 - 12): ");
							newMonth=Integer.parseInt(scanner.nextLine());
							
							if(newMonth < 1 || newMonth > 12) 
								throw new NumberFormatException();
							
							inputInvalid = false;
						} catch(NumberFormatException e) {
							inputInvalid = true;
							System.out.println("\nNot a valid month! Only accept month between 1 - 12\n");
						}
					} while(inputInvalid);
					
					// ask year
					do {
						try {
							System.out.print("Please enter new appointment year (e.g. 2021): ");
							newYear=Integer.parseInt(scanner.nextLine());
							
							if (newYear < 2021 || newYear > 2025) 
								throw new NumberFormatException();
							
							inputInvalid=false;
						} catch(NumberFormatException e) {
							inputInvalid = true;
							System.out.println("\nNot a valid year! Only accept year between 2021 - 2025\n");
						}
					} while(inputInvalid);
	
					// this will throw DateTimeException is the date is not valid
					newAppointmentDate = LocalDate.of(newYear, newMonth, newDate);
					
					dateInvalid = false;
				} catch(DateTimeException e) {
					dateInvalid = true;
					System.out.println("\nNot a valid date!\n");
				}
			} while(dateInvalid);
			
			
			// get booked slots for the estate on the specified date
			Estate estate = selectedAppointment.getEstate();
			
			List<Appointment> sameEstateNDateAppointments = controller.getBookedSlots(newAppointmentDate, estate);
			
			
			// remove the original booked slot
			boolean rescheduledToSameDate = false;
			int indexToRemove = 0;
			for(int i = 0; i < sameEstateNDateAppointments.size(); i++) {
				LocalTime bookedStartTime = sameEstateNDateAppointments.get(i).getStartTime();
				
				if (oriStartTime.equals(bookedStartTime)) {
					rescheduledToSameDate = true;
					indexToRemove = i;
				}
			}
			
			if (rescheduledToSameDate)
				sameEstateNDateAppointments.remove(indexToRemove);
			
			
			// show the booked slots
			if (sameEstateNDateAppointments.size() != 0) {
				System.out.println("\nBooked slot for estate " + estate.getId() + " on " + newAppointmentDate + ":");
				System.out.println(String.format("%-3s%-11s%-11s", "no","Start time", "End time"));
				
				for(int i=0; i < sameEstateNDateAppointments.size(); i++) {
					LocalTime bookedStartTime = sameEstateNDateAppointments.get(i).getStartTime();
					LocalTime bookedEndTime = sameEstateNDateAppointments.get(i).getEndTime();
					
					String row = String.format("%-3s%-11s%-11s", i + 1, bookedStartTime, bookedEndTime);
					
					System.out.println(row);
				}
				System.out.println();
			}
			
			
			// get new time
			LocalTime newStartTime = null;
			String newTime;
			int newHour = 0, newMinute = 0;
			boolean repeat = false;
			do {
				do {
					try {
						System.out.print("Please enter new appointment time (e.g. 13:00): ");
						newTime = scanner.nextLine();
						
						if (!newTime.matches("[0-9]{1,2}:[0-9]{1,2}")) 
							throw new IllegalArgumentException("\nNot a valid Time! Only accept time in 24hour format (only 8am to 6pm allowed).\n");
						
						String[]hourAndMinute = newTime.split(":");
						newHour = Integer.parseInt(hourAndMinute[0]);
						newMinute = Integer.parseInt(hourAndMinute[1]);
						
						if(newHour < 8 || newHour > 18) 
							throw new IllegalArgumentException("\nNot a valid range of hour, please enter within range between 8:00 - 18:00!\n");
						
						if(newMinute < 0 || newMinute > 59)
							throw new DateTimeException("\nNot a valid range of minute, please enter within range between 0 - 59!\n");
						
						if (newHour == 18 && newMinute != 0)
							throw new IllegalArgumentException("\nNot a valid range of operational hour, please enter within range between 8:00 - 18:00!\n");
						
						inputInvalid = false;
					} catch(NumberFormatException e) {
						inputInvalid = true;
						System.out.println("\nNot a valid time!\n");
					} catch (DateTimeException e) {
						inputInvalid = true;
						System.out.println(e.getMessage());
					} catch (IllegalArgumentException e) {
						inputInvalid = true;
						System.out.println(e.getMessage());
					}
				}while(inputInvalid);
				
				newStartTime = LocalTime.of(newHour, newMinute);
				
				
				// check if new time is available, if not available, loop to get another time
				if (sameEstateNDateAppointments.size() != 0) {
					Appointment endTimeCalculator = new Appointment();
					LocalTime newEndTime = endTimeCalculator.computeEndTime(newStartTime, estate.getType());
							
					for(Appointment appointment : sameEstateNDateAppointments) {
						LocalTime bookedStartTime = appointment.getStartTime();
						LocalTime bookedEndTime = appointment.getEndTime();
						
						boolean clashTime = (newStartTime.isAfter(bookedStartTime) && newStartTime.isBefore(bookedEndTime)) || (newEndTime.isAfter(bookedStartTime) && newEndTime.isBefore(bookedEndTime) || newStartTime.equals(bookedStartTime) || newEndTime.equals(bookedEndTime));
						
						if (clashTime) {
							repeat = true;
							System.out.println("\nThe time is not available, pick another time.\n");
							break;
						}
					}
				}
			}while(repeat);
			
			
			// assign new agent
			List<Agent> agentList = controller.getAvailableAgents(newAppointmentDate,newStartTime,selectedAppointment.getEstate().getId());
			
			if(agentList.size() == 0)
				System.out.println("\nNo agent available! Please try to pick another time.");
			else {
				System.out.println("\nAvailable agents:");
				
				for(int i = 0; i < agentList.size(); i++) {
					System.out.println(i+1 + ". " + agentList.get(i).getName());
				}
				
				int options = 0;
				do {
					System.out.print("Please enter Agent No. : ");
					try {
						options = Integer.parseInt(scanner.nextLine());
						
						if (options < 1 || options > agentList.size())
							throw new IllegalArgumentException("\nNot a valid option!");
						
						inputInvalid = false;
					} catch(NumberFormatException e) {
						inputInvalid = true;
						System.out.println("\nNot a valid option!");
					} catch(IllegalArgumentException e) {
						inputInvalid = true;
						System.out.println(e.getMessage());
					}
				} while(inputInvalid);
				
				Agent newAgent = agentList.get(options-1);
				controller.rescheduleAppointment(selectedAppointment, appointmentID, newAppointmentDate, newStartTime,  newAgent);
				
				System.out.println("\nAppointment rescheduled.");
			}
		}
	}
	
	
	public void viewAllAppointments() {
		int numberOfAppointments = controller.getNumberOfAppointments();
		
		if(numberOfAppointments == 0)
			System.out.println("\nNo appointment has been made yet.\n");
		else {
			List<Appointment> appointmentList=controller.getAllAppointments();	
			
			System.out.println();
			System.out.println(String.format("%-3s%-16s%-14s%-32s%-26s%-12s%-12s%-9s%-9s","No", "Appointment ID", "IC Number", "Name", "Appointment date (y-m-d)", "Start time", "End time", "Agent ID", "Estate ID"));
			System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
			
			for(int i=0; i < appointmentList.size(); i++ ) {
				String appointmentId = appointmentList.get(i).getId();
				String customerIc = appointmentList.get(i).getCustomer().getIcNum();
				String customerName = appointmentList.get(i).getCustomer().getName();
				LocalDate date = appointmentList.get(i).getAppointmentDate();
				LocalTime startTime = appointmentList.get(i).getStartTime();
				LocalTime endTime = appointmentList.get(i).getEndTime();
				String agentId = appointmentList.get(i).getAgent().getId();
				String estateId = appointmentList.get(i).getEstate().getId();
				
				String row = String.format("%-3s%-16s%-14s%-32s%-26s%-12s%-12s%-9s%-9s", i + 1, appointmentId, customerIc, customerName, date, startTime, endTime, agentId, estateId);
				
				System.out.println(row);
			}
			System.out.println();
		}
	}
	
	
	public void searchAppointment() {
		Appointment selectedAppointment;
		String appointmentID;
		
		boolean inputInvalid = true;
		do {
			try {
				System.out.print("Please enter the appointment ID (e.g. AP0001): ");
				appointmentID=scanner.nextLine();
				selectedAppointment = controller.findAppointment(appointmentID);
				
				if(selectedAppointment == null) 
					throw new IllegalArgumentException("\nNo appointment found!\n");
				
				System.out.println();
				System.out.println(String.format("%-16s%-14s%-32s%-26s%-12s%-12s%-9s%-9s", "Appointment ID", "IC Number", "Name", "Appointment date (y-m-d)", "Start time", "End time", "Agent ID", "Estate ID"));
				System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
				
				String appointmentId = selectedAppointment.getId();
				String customerIc = selectedAppointment.getCustomer().getIcNum();
				String customerName = selectedAppointment.getCustomer().getName();
				LocalDate date = selectedAppointment.getAppointmentDate();
				LocalTime startTime = selectedAppointment.getStartTime();
				LocalTime endTime = selectedAppointment.getEndTime();
				String agentId = selectedAppointment.getAgent().getId();
				String estateId = selectedAppointment.getEstate().getId();
				
				String row = String.format("%-16s%-14s%-32s%-26s%-12s%-12s%-9s%-9s", appointmentId, customerIc, customerName, date, startTime, endTime, agentId, estateId);
				
				System.out.println(row);
				
				System.out.println();
				
				inputInvalid= false;
			}catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}while(inputInvalid);
	}
	
	public void viewBookedSlotsByDate() {
		// get estate
				Estate estate = null;
				String estateID = null;
				boolean inputInvalid= true;
				do {
					try {
						System.out.print("Please enter Estate ID (e.g. ES0001) : ");
						estateID = scanner.nextLine();
						estate = controller.findEstate(estateID);
						
						if (estate == null) 
							throw new IllegalArgumentException("\nNot a valid Estate ID!\n");
						
						inputInvalid = false;
					} catch(IllegalArgumentException e) {
						System.out.println(e.getMessage());
					}
				}while(inputInvalid);
				
				inputInvalid = true;
				
				
				// get appointment date
				LocalDate appointmentDate = null;
				int day = 0, month = 0, year = 0;
				
				boolean dateInvalid;
				do {
					try {
						// get day
						do {
							try {
								System.out.print("Please enter appointment day (1 - 31): ");
								day=Integer.parseInt(scanner.nextLine());
								
								if(day < 1 || day > 31) 
									throw new NumberFormatException();
								
								inputInvalid = false;
							} catch(NumberFormatException e) {
								inputInvalid = true;
								System.out.println("\nNot a valid date! Only accept day between 1 - 31\n");
							} 
						} while(inputInvalid);
						
						// get month
						do {
							try {
								System.out.print("Please enter appointment month (1 - 12): ");
								month=Integer.parseInt(scanner.nextLine());
								
								if(month < 1 || month > 12) 
									throw new NumberFormatException();
								
								inputInvalid = false;
							} catch(NumberFormatException e) {
								inputInvalid = true;
								System.out.println("\nNot a valid month! Only accept month between 1 - 12\\n");
							}
						} while(inputInvalid);
						
						// get year
						do {
							try {
								System.out.print("Please enter appointment year (e.g. 2021): ");
								year=Integer.parseInt(scanner.nextLine());
								
								if (year < 2021 || year > 2025) 
									throw new NumberFormatException();
								
								inputInvalid=false;
							} catch(NumberFormatException e) {
								inputInvalid = true;
								System.out.println("\nNot a valid year! Only accept year between 2021 - 2025\n");
							}
						} while(inputInvalid);
						
						// this will throw DateTimeException is the date is not valid
						appointmentDate = LocalDate.of(year, month, day);
						
						dateInvalid = false;
					} catch(DateTimeException e) {
						dateInvalid = true;
						System.out.println("\nNot a valid date!\n");
					}
				} while(dateInvalid);
				
				
				// show booked slot for the estate on the specified date
				List<Appointment> sameEstateNDateAppointments = controller.getBookedSlots(appointmentDate, estate);
				
				if (sameEstateNDateAppointments.size() != 0) {
					System.out.println("\nBooked slots for estate " + estate.getId() + " on " + appointmentDate + ":");
					System.out.println(String.format("%-3s%-11s%-11s", "no","Start time", "End time"));
					
					for(int i=0; i < sameEstateNDateAppointments.size(); i++) {
						LocalTime bookedStartTime = sameEstateNDateAppointments.get(i).getStartTime();
						LocalTime bookedEndTime = sameEstateNDateAppointments.get(i).getEndTime();
						
						String row = String.format("%-3s%-11s%-11s", i + 1, bookedStartTime, bookedEndTime);
						
						System.out.println(row);
					}
					System.out.println();
				}
				else
					System.out.println("\nThere are no booked slots for estate " + estate.getId() + " on " + appointmentDate + ".\n");
	}
}
	

