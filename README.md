# Todo App with Authentication
Basic Todo App backend made with Spring Boot 3.0 and MySQL.


## Features
 * User registration and login with JWT Authenticaiton.
 * Password encryption with BCrypt.
 * Role based authorization with Spring Security.

## Technologies
* Spring Boot 3.0
* Spring Security
* JSON Web Tokens (JWT)
* BCrypt
* Maven
* MySQL

## Routes
* POST /api/auth/register - Register a new user (returns JWT token)
* POST /api/auth/login - Login a user (returns JWT token)

## Todo
 * Unit Testing
 * ~~Email Verification for registration~~
 * Password reset route
 * ~~Use template engine for email templates instead of hard coded html strings~~
 * i18n localization
 * Documentation with Swagger and Javadoc
 * Dockerize the application
 * Default /error route for error handling
 * Rate limiting
    * Email confirmation rate limiting per user

## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+
* MySQL

To build and run the project, follow these steps:

* Clone the repository: `git clone https://github.com/notbozho/spring-boot-todoapp.git`
* Navigate to the project directory: cd spring-boot-todoapp
* Create database with name todoapp
* Add username and password to application.properties
* Build the project: mvn clean install
* Run the project: mvn spring-boot:run

The application will be available at http://localhost:8080.

---
Made with 💖 by **@notbozho**.