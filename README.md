# A* Documentation

## Overview
This documentation provides an overview of the A* application, including its tech stack, architecture, and project structure.

## Tech Stack
- **Backend:** Java with Spring Boot
- **Build Tool:** Gradle
- **Database:** PostgreSQL
- **Frontend:** React with TypeScript

## Architecture
The application follows a layered architecture, separating concerns into distinct layers:
1. **Presentation Layer (Frontend):** This layer is responsible for the user interface and user interactions. It communicates with the backend through RESTful APIs.
2. **Business Logic Layer (Backend):** This layer contains the core business logic of the application, including services, controllers, DTOs, HTTP interceptors, and configurations.
3. **Data Access Layer (Database):** This layer manages the persistence of data using PostgreSQL. It includes entities, and repositories.
4. **Security Layer:** The application handles authentication and authorization using JWT tokens and role-based access using HTTP interceptors for every request.

## Project Structure
1. **Backend:**
   - `src/main/java/com/logistics/company/`: Contains the main application code.
     - `annotations/`: Contains the custom `AuthGuard` annotation, used for role-based authorization.
     - `configs`: Contains a custom implementation of the `WebMvcConfig` that adds `CORS` rules, and interceptors.
     - `controllers/`: Contains REST controllers for handling HTTP requests.
     - `dtos/`: Contains Data Transfer Objects for transferring data between layers.
     - `exceptions/`: Contains a global HTTP exception handler for handling exceptions across the application, as well as custom exceptions for specific error cases.
     - `interceptors/`: Contains HTTP interceptors for handling authentication and authorization.
     - `models/`: Contains entity classes that represent the database tables.
     - `repository/`: Contains repository interfaces for database access.
     - `service/`: Contains service classes that implement business logic.
     - `util/`: Contains utility classes for common functionalities like validation and DTO mappings.

## Database
The application uses PostgreSQL as its database.

- **Database Schema**
  
  ![Home Page](/docs/db.png)