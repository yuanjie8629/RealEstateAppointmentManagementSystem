# RealEstateAppointmentManagementSystem

## Overview
<p>This is a real estate appointment management system developed using Java. The objective of the assignments is to allow reinforce learning in analysing the issues of the requirements, design, and implementation of the reservation system. Furthermore, high-level and low-level designs of the system are created.</p>
<p>FYI, this system is designed using controller with DAO design pattern.</p>
<p>The functions of the system are as follows:</p>

1. Login Clerk and Customer Account
2. View Appointments
3. Search for Appointment
4. Create New Customer account
5. View Booked Slots
6. Make Appointment
7. Reschedule Appointment
8. Cancel Appointment

#### System Assumptions
1.	The customer is a stakeholder and will not be using the system.
2.	The clerk is the direct user of the system.
3.	The clerk will have to log in to the admin account before accessing the system.
4.	Customer can only perform make an appointment, cancel an appointment and reschedule an appointment through a clerk.
5.	The clerk will have to log in to a customer account by customer IC number before creating an appointment or making any changes to an existing appointment. If the customer is new, the clerk will create a new account of the customer.
6.	This system is a centralised appointment system, all the appointments will be handled by head office.
7.	The customer is required to contact the clerk via the office phone number or approaching the clerk in the head office to make an appointment.
8.	Customer will recognize the estate by estate ID that they want to check out from the real estate website or advertisements before contacting or approaching a clerk for making an appointment.
9.	A reminder will be generated and sent to the customer’s phone number after an appointment is made.
10.	Customer is required to provide IC number and the appointment ID if they wish to cancel or reschedule an appointment so that the clerk can search for that appointment. The appointment ID can be found in the reminder sent to the customer’s phone number.
11.	There are three types of real estate in this system, which includes new estate, sample estate and old estate. The duration of appointment for each type of real estate is set to one hour for the new estate, one and half hour for sample estate and two hours for the old estate.
12.	This system is only used for appointment management. Therefore, the information of each real estate excluding its id and type will not be stored in this system.

## Tools
1. Java
2. Eclipse IDE
3. Enterprise Architect (System Modelling)

## Usage
Import the files into your project.<br>
Run the project with Java compiler.

Enter the system using clerk id and password below:<br>
clerk id: CK0001<br>
password: admin1

## Application Design
### Use Case Diagram
<img src="previews/UseCaseDiagram.png">

### Analysis Class Diagram
<img src="previews/AnalysisClassDiagram.png">

### Design Class Diagram
<img src="previews/DesignClassDiagram.png">

### Package Diagram
<img src="previews/PackageDiagram.png">

## Test
Manual testing has been performed.

## Previews
1. Login Clerk Account <br> <img src="previews/LoginClerk.png"><br><br>
2. MainMenu <br> <img src="previews/MainMenu.png"><br><br>
3. View Appointments <br> <img src="previews/ViewAppointments.png"><br><br>
4. Search for Appointment <br> <img src="previews/SearchForAppointment.png"><br><br>
5. Login Customer Account <br> <img src="previews/LoginCustomer.png"><br><br>
6. Create New Customer Account <br> <img src="previews/CreateCustomerAccount.png"><br><br>
7. View Booked Slots <br> <img src="previews/ViewBookedSlots.png"><br><br>
8. Make Appointment <br> <img src="previews/MakeAppointment.png"><br><br>
9. Cancel Appointment <br> <img src="previews/CancelAppointment.png"><br><br>
10. Reschedule Appointment <br> <img src="previews/RescheduleAppointment.png"><br><br>

## Contributors
1. Lean Wei Liang
2. Pang Jia Jin
3. Seow Kai Sheng
4. Tan Yuan Jie
5. Wo Zi Rock
