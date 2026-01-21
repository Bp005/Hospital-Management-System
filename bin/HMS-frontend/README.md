# Hospital Management System - All-in-One Setup

This is a Spring Boot Hospital Management System with Doctors, Patients, Authentication, and dummy data.

---

# 1. Clone Repo
git clone <your-repo-url>
cd hospital-management-system

---

# 2. Database Setup

# H2 (default, dev/test): nothing extra needed

# MySQL (optional, prod):
# Create DB:
CREATE DATABASE hms;
# Update application.properties:
# spring.datasource.url=jdbc:mysql://localhost:3306/hms
# spring.datasource.username=root
# spring.datasource.password=yourpassword
# spring.jpa.hibernate.ddl-auto=update

---

# 3. Build and Run
mvn clean install
mvn spring-boot:run

# App URL: http://localhost:8080

---

# 4. API Endpoints

# Authentication
# POST /register  → register user/doctor/patient
# POST /login     → login and get JWT token

# Doctors
# GET /doctors           → list doctors
# POST /doctors          → add doctor
# PUT /doctors/{id}      → update doctor
# DELETE /doctors/{id}   → delete doctor

# Patients
# GET /patients          → list patients
# POST /patients         → add patient
# PUT /patients/{id}     → update patient
# DELETE /patients/{id}  → delete patient

---

# 5. Dummy Data

# Doctor
{
  "name": "Dr. Sita Sharma",
  "email": "sita.sharma@hospital.com",
  "specialization": "Cardiology",
  "contact": "9841234567",
  "password": "password123"
}

# Patient
{
  "name": "Ram Thapa",
  "age": 35,
  "gender": "Male",
  "contact": "9812345678",
  "address": "Kathmandu, Nepal",
  "password": "password123"
}

# Use POST /register with above JSON to create test users

---

# 6. Test APIs with curl

# Register Doctor
curl -X POST http://localhost:8080/register -H "Content-Type: application/json" -d '{"name":"Dr. Sita Sharma","email":"sita.sharma@hospital.com","specialization":"Cardiology","contact":"9841234567","password":"password123"}'

# Register Patient
curl -X POST http://localhost:8080/register -H "Content-Type: application/json" -d '{"name":"Ram Thapa","age":35,"gender":"Male","contact":"9812345678","address":"Kathmandu, Nepal","password":"password123"}'

# Login
curl -X POST http://localhost:8080/login -H "Content-Type: application/json" -d '{"email":"sita.sharma@hospital.com","password":"password123"}'
