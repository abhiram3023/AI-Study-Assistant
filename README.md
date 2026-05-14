# AI Study Assistant

AI Study Assistant is a full-stack web application developed using Spring Boot, MySQL, Thymeleaf, Bootstrap, and Groq AI API.

The application helps students:
- Track academic performance
- Store subjects and exam records
- Analyze weak subjects
- View progress charts
- Get AI-generated study recommendations
- Use an AI chatbot for study-related doubts

---

# Features

## User Authentication
- Signup and Login functionality
- Session-based authentication
- Secure access to dashboard

## Subject Management
- Add and manage subjects
- Store subject-wise exam records

## Exam Performance Tracking
- Add:
  - Exam Name
  - Subject
  - Total Marks
  - Scored Marks

## Smart Recommendations
The system analyzes:
- Weak subjects
- Strong subjects
- Overall performance
- Low-scoring exams

and generates personalized study recommendations.

## AI Chatbot
Integrated with Groq AI API for:
- Doubt solving
- Concept explanations
- Study guidance

## Analytics Dashboard
- Subject-wise progress chart
- Exam-wise performance graph
- Overall percentage analysis

---

# Tech Stack

## Frontend
- HTML5
- CSS3
- Bootstrap 5
- Thymeleaf

## Backend
- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate

## Database
- MySQL

## AI Integration
- Groq API

## Build Tool
- Maven

---

# Architecture

The project follows MVC (Model View Controller) Architecture.

## Controller Layer
Handles:
- HTTP requests
- Page navigation
- Form handling

## Service Layer
Contains:
- Business logic
- Recommendation system
- AI API integration

## Repository Layer
Handles database operations using Spring Data JPA.

## Model Layer
Contains entity classes mapped to database tables.

---

# Folder Structure

```text
src/
 ├── main/
 │   ├── java/com/aistudy/AIStudyAssistant/
 │   │   ├── controller/
 │   │   ├── service/
 │   │   ├── repository/
 │   │   ├── model/
 │   │   └── config/
 │   │
 │   └── resources/
 │       ├── static/
 │       ├── templates/
 │       └── application.properties
```

---

# Database Setup

## Step 1: Install MySQL

Download and install MySQL.

## Step 2: Create Database

Run:

```sql
CREATE DATABASE ai_learning_assistant;
```

## Step 3: Update application.properties

Open:

```text
src/main/resources/application.properties
```

Update:

```properties
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD

groq.api.key=YOUR_GROQ_API_KEY
```

---

# Groq API Setup

## Step 1: Create Groq Account

Visit:

https://console.groq.com

## Step 2: Generate API Key

- Create API key
- Copy the key

## Step 3: Add API Key

Paste in:

```properties
groq.api.key=YOUR_GROQ_API_KEY
```

---

# Installation & Run

## Clone Repository

```bash
git clone https://github.com/YOUR_USERNAME/AI-Study-Assistant.git
```

## Open Project

Open in:
- IntelliJ IDEA

## Install Dependencies

Maven automatically installs dependencies.

## Run Application

Run:

```text
AIStudyAssistantApplication.java
```

Application runs on:

```text
http://localhost:8080
```

---

# Screenshots

Add screenshots inside:

```text
Screenshots/
```

Example:

```md
![Home](Screenshots/home.png)
![Dashboard](Screenshots/dashboard.png)
![Chatbot](Screenshots/chatbot.png)
```

---

# Future Improvements

- Spring Security authentication
- Password encryption using BCrypt
- JWT authentication
- Email verification
- Study planner
- Attendance tracking
- PDF report generation
- AI-based performance prediction

---

# Author

Developed by Abhiram Majeti

---

# Forking the Project

## Fork

Click the Fork button on top-right of GitHub repository.

## Clone Fork

```bash
git clone https://github.com/YOUR_USERNAME/AI-Study-Assistant.git
```

---

# License

This project is developed for educational purposes.
