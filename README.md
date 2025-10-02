# Blood Donation System

This is a Spring Boot project for managing blood donations.

A comprehensive Spring Boot web application for managing blood donation operations with **role-based authentication** and **complete CRUD functionality**. Features include donor management, appointment scheduling, communication, coordination, and role-specific dashboards.

## Features

### 🔐 **Authentication & Authorization**
- **User Registration**: Create accounts with role-based access
- **Secure Login**: Username/password authentication
- **Role-Based Dashboards**: Different interfaces for each user type
- **Session Management**: Secure session handling with logout functionality

### 👥 **User Roles & Dashboards**
- **Admin Dashboard**: Complete system management and user oversight
- **Receptionist Dashboard**: Donor management and appointment scheduling
- **Doctor Dashboard**: Medical reviews and consultation management
- **Nurse Dashboard**: Donation procedures and patient care
- **Blood Bank Manager Dashboard**: Inventory and distribution management

### 🩸 **Core Functionality**
- **Donor Registration**: Public donor registration form with comprehensive health information
- **Donor Management**: Add, edit, delete, and view donor profiles with enhanced details
- **Appointment Management**: Schedule, update, and track blood donation appointments
- **Communication**: Send messages to donors and track communication history
- **Coordination**: Send information to medical staff (nurses, doctors, blood bank managers)
- **Reporting**: Generate reports for donor attendance, donation history, and feedback
- **Enhanced Navigation**: Consistent navigation panels across all functional pages

## Technology Stack

- **Backend**: Spring Boot 3.5.6, Spring Data JPA, Spring Web
- **Frontend**: Thymeleaf templates with HTML/CSS
- **Database**: H2 (file-based for persistence)
- **Build Tool**: Maven
- **Java Version**: 21

## Database Configuration

The application is configured to use H2 file-based database for permanent data storage:
- Database file: `./data/blooddonation.mv.db`
- H2 Console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:file:./data/blooddonation`
- Username: `sa`
- Password: `password`

## 🚀 **Quick Start**

### **Prerequisites**
- Java 17 or higher
- Maven (or use included Maven wrapper)

### **Installation & Setup**
1. **Clone the repository**
2. **Navigate to project directory**
3. **Run the application**:
   ```bash
   ./mvnw spring-boot:run
   ```
   Or if Maven is installed:
   ```bash
   mvn spring-boot:run
   ```

### **Access the Application**
1. **Open browser**: `http://localhost:8080`
2. **You'll be redirected to login page**
3. **Use the credentials below to login**

## 🔑 **Login Credentials**

The system comes with pre-configured users for testing:

| Role | Username | Password | Dashboard Access |
|------|----------|----------|------------------|
| **Admin** | `admin` | `admin123` | Complete system management |
| **Receptionist** | `receptionist` | `recep123` | Donor & appointment management |
| **Doctor** | `doctor` | `doc123` | Medical reviews & consultations |
| **Nurse** | `nurse` | `nurse123` | Donation procedures & patient care |
| **Manager** | `manager` | `manager123` | Inventory & distribution management |

### **Registration**
- **Staff Registration**: `http://localhost:8080/register` - For system users (Admin, Receptionist, etc.)
- **Donor Registration**: `http://localhost:8080/donor/register` - For blood donors (Public access)
- Choose appropriate role during staff registration
- All roles have different access levels and dashboard views

## 🌐 **Application URLs**

### **Authentication**
- **Login**: `http://localhost:8080/login`
- **Register**: `http://localhost:8080/register`
- **Logout**: `http://localhost:8080/logout`

### **Role-Based Dashboards**
- **Admin Dashboard**: `http://localhost:8080/admin/dashboard`
- **Receptionist Dashboard**: `http://localhost:8080/receptionist/dashboard`
- **Doctor Dashboard**: `http://localhost:8080/doctor/dashboard`
- **Nurse Dashboard**: `http://localhost:8080/nurse/dashboard`
- **Manager Dashboard**: `http://localhost:8080/manager/dashboard`

### **Functional Modules**
- **Donor Registration**: `http://localhost:8080/donor/register` (Public access)
- **Donor Management**: `http://localhost:8080/donors`
- **Appointment Management**: `http://localhost:8080/appointments`
- **Communication**: `http://localhost:8080/communications`
- **Coordination**: `http://localhost:8080/coordination`
- **Reports**: `http://localhost:8080/reports`

### **Database Access**
- **H2 Console**: `http://localhost:8080/h2-console`

## 🩸 **Donor Registration Feature**

### **Public Donor Registration**
- **URL**: `http://localhost:8080/donor/register`
- **Access**: No login required - open to the public
- **Features**:
  - Comprehensive registration form with personal, contact, and medical information
  - Blood type selection with visual interface
  - Age eligibility validation (17-65 years)
  - Weight requirements check (minimum 110 lbs)
  - Emergency contact information
  - Email uniqueness validation

### **Registration Process**
1. **Fill Form**: Complete all required fields including personal and medical information
2. **Validation**: System validates age, weight, and email uniqueness
3. **Success Page**: Redirected to beautiful success page with registration details
4. **Database Storage**: All information stored with enhanced donor profile

### **Enhanced Donor Profiles**
The donor profiles now include:
- First Name, Last Name, Full Name
- Date of Birth and calculated Age
- Gender information
- Weight and Height (optional)
- Emergency contact details
- Registration timestamp
- Enhanced display in donor management interface

## 🧭 **Navigation System**

### **Consistent Navigation Panels**
All functional pages now include comprehensive navigation panels with:
- **Color-coded navigation bars** matching each page's theme
- **Active page highlighting** to show current location
- **Quick access links** to all major functions
- **Back to Dashboard** buttons for easy navigation
- **Responsive design** that works on all screen sizes

### **Navigation Features**
- **Staff Pages**: Full navigation panel with all system functions
- **Public Pages**: Top navigation bar with essential links
- **Edit Pages**: Context-aware navigation with current section highlighted
- **Mobile Responsive**: Navigation adapts to smaller screens

### **Pages with Navigation**
- ✅ Donor Profile Handling
- ✅ Appointment Management  
- ✅ Donor Communication
- ✅ Coordination
- ✅ Reporting & Tracking
- ✅ Donor Edit
- ✅ Appointment Edit
- ✅ Donor Registration (Public)
- ✅ Success Page (Public)

## Database Operations

The application supports full CRUD operations:

### Donors
- **Create**: Add new donors with name, email, phone, address, and blood type
- **Read**: View all donors in a table format
- **Update**: Edit existing donor information
- **Delete**: Remove donors from the system

### Appointments
- **Create**: Schedule new appointments with donor name, date, and time
- **Read**: View all appointments with status information
- **Update**: Modify appointment details including status and notes
- **Delete**: Cancel appointments

### Communications
- **Create**: Send messages to specific donors
- **Read**: View communication history
- **Delete**: Remove communication records

### Coordination
- **Create**: Send information to medical staff
- **Read**: View coordination history
- **Delete**: Remove coordination records

## Sample Data

The application automatically initializes with sample data including:
- 5 sample donors with different blood types
- 3 sample appointments with various statuses

## Data Persistence

- Data is stored in H2 file-based database
- Database file is created automatically in `./data/` directory
- Data persists between application restarts
- Tables are created automatically using JPA entities

## Project Structure

```
src/
├── main/
│   ├── java/com/bloodDonation/BloodDonationSystem/
│   │   ├── controller/          # Web controllers
│   │   ├── model/              # JPA entities
│   │   ├── repository/         # Data repositories
│   │   ├── config/             # Configuration classes
│   │   └── BloodDonationSystemApplication.java
│   └── resources/
│       ├── templates/          # Thymeleaf HTML templates
│       ├── static/            # Static resources
│       └── application.properties
└── test/                      # Test classes
```

## Testing the Application

1. Start the application
2. Navigate to `http://localhost:8080`
3. Use the navigation menu to access different features
4. Try adding, editing, and deleting records
5. Check the H2 console to verify data persistence
=======
# Blood-Donation-System
Blood Donation System Where Donors register, manage Donors and Staff, Keeping track on Blood Stock real time managing entire blood donation process smooth and easy
>>>>>>> d363ba6a57deadf5dcdda449cdf3918c464019ab
