# Todo App with Authentication
Basic Todo App backend made with Spring Boot 3.0 and MySQL.


## Features
 * User registration and login with JWT Authenticaiton.
 * Password encryption with BCrypt.
 * Role based authorization with Spring Security.
 * Refresh & Access Tokens

## Technologies
* Spring Boot 3.0
* Spring Security
* JSON Web Tokens (JWT)
* BCrypt
* Maven
* MySQL

## Routes
* **POST** `/api/v1/auth/register` - Register a new user
* **POST** `/api/v1/auth/login` - Login a user
* **POST** `/api/v1/auth/refresh` - Refresh Access token
* **POST** `/api/v1/auth/logout` - Logout a user
* **GET** `/api/v1/user/confirm` - Confirm user email
* **POST** `/api/v1/auth/resetpassword` - Request a password reset.
* **POST** `/api/v1/auth/changepassword` - Reset password with token.

## Todo
 * Unit Testing
 * Fix route permissions
 * ~~Email Verification for registration~~
 * ~~Password reset route~~
 * 2fa for login
 * Make auth service doesnt access the user repository directly
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