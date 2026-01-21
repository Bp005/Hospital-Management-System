# Hospital Management System - All-in-One Setup

This is a Spring Boot Hospital Management System with Doctors, Patients, Authentication, and dummy data.

---

# 1. Clone Repo
git clone <your-repo-url>
cd hospital-management-system

---

# 2. Database Setup

 H2 (default, dev/test): nothing extra needed

 MySQL (optional, prod):
Create DB:
CREATE DATABASE hms;
 Update application.properties:
 spring.datasource.url=jdbc:mysql://localhost:3306/hms
 spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

---

# 3. Build and Run
mvn clean install
mvn spring-boot:run

# App URL: http://localhost:8080

---

# 4. API Endpoints

 Authentication
 POST /auth/register  → register user/doctor/patient
#POST /auth/login     → login and get JWT token

# Doctors
 GET /api/v1/doctors           → list doctors
 POST /api/v1/doctors          → add doctor
 PUT /api/v1/doctors/{id}      → update doctor
 DELETE /api/v1/doctors/{id}   → delete doctor

# Patients
 GET /api/v1/patients          → list patients
 POST //api/v1/patients         → add patient
 PUT /api/v1/patients/{id}     → update patient
 DELETE /api/v1/patients/{id}  → delete patient

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

 Use POST /register with above JSON to create test users

---

## 6. Test API with Postman

1. Open Postman.  
2. **Register Doctor:**  
   - Method: POST  
   - URL: http://localhost:8080/auth/register  
   - Body: raw JSON (paste doctor JSON above)  
   - Headers: Content-Type → application/json  

3. **Register Patient:**  
   - Method: POST  
   - URL: http://localhost:8080/auth/register  
   - Body: raw JSON (paste patient JSON above)  
   - Headers: Content-Type → application/json  

4. **Login:**  
   - Method: POST  
   - URL: http://localhost:8080/auth/login  
   - Body: raw JSON (use doctor or patient email/password)  
   - Headers: Content-Type → application/json  
   - Response: JWT token for authorization  

5. **Access Protected APIs:**  
   - Add Header: Authorization → Bearer <JWT_TOKEN>  
   - Example: GET `/doctors` or GET `/patients`

