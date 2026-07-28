# LearnLink — Learning Management System (LMS)

LearnLink is a full-stack Learning Management System built with **Spring Boot**, **Spring Data JPA**, and **MySQL**. It gives students a secure, intuitive platform to explore courses, enroll, and track their progress — while giving administrators the tools to manage content and users efficiently.

The project follows a **layered architecture** and **RESTful design principles**, showcasing backend development best practices, clean database design, and enterprise-grade application structure.

---

## Key Features

### Student Module
- Secure user registration and authentication
- Browse available courses
- Enroll in courses of interest
- Track learning progress and enrollment history

### Admin Module
- Create, update, and delete courses
- Manage student accounts
- Monitor course enrollments across the platform

### Authentication & Security
- Secure login system
- Role-based access control (Admin & Student)

---

##Technology Stack

| Layer | Technologies |
|---|---|
| **Backend** | Java, Spring Boot, Spring MVC, Spring Data JPA (Hibernate), REST APIs |
| **Database** | MySQL |
| **Frontend** | HTML, CSS, JavaScript |

---

##Application Architecture

LearnLink follows a layered architecture for better scalability, maintainability, and separation of concerns:

- **Controller Layer** – Handles incoming HTTP requests and exposes API endpoints
- **Service Layer** – Implements business logic and application workflows
- **Repository Layer** – Manages database interactions via Spring Data JPA
- **MySQL Database** – Persists users, courses, and enrollment data

---

## ⚙️ How It Works

Every request in LearnLink flows through the layers in a fixed, one-directional path — no layer skips ahead or reaches back:

1. **Browser / Frontend** – The user (student or admin) triggers an action (e.g. clicking "Enroll"), which sends an HTTP request such as `POST /api/enrollments`.
2. **Security Filter** – Spring Security intercepts the request first, verifies the login session, and checks whether the user's role (Admin/Student) is permitted to access that endpoint. Unauthorized requests are rejected here (401/403).
3. **Controller Layer** – A `@RestController` maps the URL and HTTP method to the right handler method, extracts the request data, and passes it to the service layer. It contains no business logic itself.
4. **Service Layer** – This is where the actual rules live: is the course full? Is the student already enrolled? Is registration still open? It orchestrates one or more repository calls to fulfill the request.
5. **Repository Layer** – Spring Data JPA repositories translate method calls into SQL, and Hibernate maps the results back into Java objects (`User`, `Course`, `Enrollment` entities).
6. **MySQL Database** – The actual data is stored here, with the `Enrollments` table serving as the join table for the many-to-many relationship between `Users` and `Courses`.

The response then travels back up the same path — Repository → Service → Controller — gets serialized into JSON, and is returned to the browser, which updates the UI accordingly.

This strict separation means the Controller never touches SQL, and the Repository never knows about HTTP — keeping each layer independently testable and replaceable.

---

## Database Design

### Core Entities
- **Users**
- **Courses**
- **Enrollments**

### Relationships
- A **User** can enroll in multiple **Courses**
- A **Course** can have multiple enrolled **Users**

This many-to-many relationship is resolved through a dedicated **Enrollments** join table.

---

##  Skills & Concepts Demonstrated

- REST API development with Spring Boot
- Layered software architecture
- Database integration using JPA/Hibernate
- CRUD operations
- Data validation and exception handling
- Object-relational mapping (ORM)
- Backend application design following industry best practices

---

## Future Enhancements

- [ ] JWT-based authentication and authorization
- [ ] Payment gateway integration
- [ ] Video lecture streaming
- [ ] Analytics dashboard for administrators
- [ ] Cloud deployment (AWS)
- [ ] Docker containerization and CI/CD pipeline

---

##  Project Status

This project is under **active development**, with ongoing work focused on performance optimization, enhanced security, and new feature additions.

---

## Contributing

If you find this project useful or interesting:
- ⭐ Star the repository
- 🍴 Fork it
- 🔧 Contribute via pull requests

Contributions, issues, and feature requests are always welcome!
