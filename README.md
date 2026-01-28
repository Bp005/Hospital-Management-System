# Hospital Management System (HMS) – Backend API



This is a  **Hospital Management System (HMS) backend REST API** that I am building using **Spring Boot**. I recently added frontedn part so it can be used as a full stack system.

I am developing this project mainly to **learn backend development** and understand how real-world backend applications are structured. This project helps me practice concepts like REST APIs, database integration, Spring Security, and JWT authentication.

**Work in Progress**: This project is still under development. I am currently working on **JWT authentication, role-based authorization (Doctor / Patient), and other security features**.

---

## 🎓 Why I Built This Project

I built this project to:

* Learn **Spring Boot** step by step
* Practice **CRUD operations** using JPA & Hibernate
* Understand **authentication & authorization** concepts
* Improve my backend skills for **internships and interviews**

This is a **learning-focused project**, so the code and features will keep improving over time.

---

## ⚙️ Tech Stack Used

* Java
* Spring Boot
* Spring Data JPA (Hibernate)
* Spring Security
* JWT (JSON Web Token) *(in progress)*
* MySQL
* Maven
* Postman (for API testing)

---

## 📂 Features Implemented So Far

* Patient CRUD APIs
* Doctor CRUD APIs
* Pagination support
* Basic authentication flow
* Password encryption using BCrypt

---

## 🔐 Authentication Status

* JWT authentication: 🚧 **In progress**
* Role-based access (Doctor / Patient): 🚧 **In progress**
* Password encryption: ✅ Implemented

---

## 🛠️ How to Run This Project on Your Computer

### 1️⃣ Prerequisites

Make sure you have the following installed:

* Java (17 or compatible version)
* Maven
* MySQL
* Git

---

### 2️⃣ Clone the Repository

```bash
git clone <your-github-repo-url>
cd hms-backend
```

---

### 3️⃣ Create Database

Create a MySQL database:

```sql
CREATE DATABASE hms_db;
```

---

### 4️⃣ Configure Database Connection

Edit `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hms_db
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

### 5️⃣ Run the Application

Using IDE (STS / IntelliJ):

* Right click on the project
* Run as **Spring Boot Application**

OR using terminal:

```bash
mvn spring-boot:run
```

The app will start on:

```
http://localhost:8080
```

---

## 🧪 Testing the APIs

You can test the APIs using **Postman**.

Example endpoints:

* `POST /auth/login` *(JWT login – in progress)*
* `GET /doctors`
* `GET /patients`

For protected APIs, send JWT token in headers:

```
Authorization: Bearer <token>
```

---

## 🚀 Future Plans

* Complete JWT authentication
* Implement role-based authorization
* Add validation & exception handling
* Add Swagger API documentation
* Deploy the application

---

## 👨‍💻 Author

**Bijay Poudel**

This project is created for **learning and practice purposes**. I am continuously improving it as I learn new backend concepts.

---


