# Usuarios-Primac-API

## Project Overview

This is a RESTful API for managing users, clients, and agents in the Primac system. The API provides endpoints for CRUD operations on these entities and includes JWT authentication for secure access.

### Features

- Secure user registration with password encryption
- JWT-based authentication
- Role-based authorization
- Token validation for protected endpoints
- CRUD operations for Users, Clients, and Agents
- Docker and Docker Compose support for easy deployment

## API Endpoints

### Authentication Endpoints

#### Register a new user

```
POST /auth/register
```

Request body:
```json
{
  "username": "user123",
  "email": "user@example.com",
  "password": "password123",
  "role": "USER",
  "phone": 123456789,
  "street": "123 Main St",
  "city": "Anytown",
  "state": "LIMA"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "username": "user123",
  "email": "user@example.com",
  "role": "USER"
}
```

#### Login

```
POST /auth/login
```

Request body:
```json
{
  "username": "user123",
  "password": "password123"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "username": "user123",
  "email": "user@example.com",
  "role": "USER"
}
```

### User Endpoints

#### Get all users
```
GET /api/users
```

#### Get user by ID
```
GET /api/users/{id}
```

#### Create a new user
```
POST /api/users
```

#### Update a user
```
PUT /api/users/{id}
```

#### Delete a user
```
DELETE /api/users/{id}
```

### Client Endpoints

#### Get all clients
```
GET /api/clients
```

#### Get client by ID
```
GET /api/clients/{id}
```

#### Create a new client
```
POST /api/clients
```

#### Update a client
```
PUT /api/clients/{id}
```

#### Delete a client
```
DELETE /api/clients/{id}
```

### Agent Endpoints

#### Get all agents
```
GET /api/agents
```

#### Get agent by ID
```
GET /api/agents/{id}
```

#### Create a new agent
```
POST /api/agents
```

#### Update an agent
```
PUT /api/agents/{id}
```

#### Delete an agent
```
DELETE /api/agents/{id}
```

### Using JWT Tokens

After successful authentication, include the JWT token in the Authorization header for subsequent requests:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Security Configuration

- JWT tokens expire after 24 hours
- Passwords are encrypted using BCrypt
- All endpoints except `/auth/**` require authentication

### Implementation Details

The JWT authentication system consists of:

1. **JWT Utilities**: For token generation, validation, and parsing
2. **Authentication Filter**: Intercepts requests and validates JWT tokens
3. **Security Configuration**: Configures Spring Security with JWT authentication
4. **Authentication Controller**: Provides endpoints for login and registration
5. **Password Encryption**: All passwords are encrypted using BCrypt

### Database Configuration

The application uses MySQL database. Configuration details:

```
spring.datasource.url=jdbc:mysql://localhost:3306/primac
spring.datasource.username=admin
spring.datasource.password=admin
```

## Docker Deployment

This project includes Docker support for easy deployment. You can use Docker and Docker Compose to run the application and its dependencies.

### Prerequisites

- Docker
- Docker Compose

### Building and Running with Docker Compose

1. Clone the repository
2. Navigate to the project directory
3. Build and start the containers:

```bash
docker-compose up -d
```

This will:
- Build the Spring Boot application using the Dockerfile
- Start the application with MySQL database
- Expose the application on port 8080

### Accessing the Application

- API: http://localhost:8080
- MySQL Database: localhost:3306

### Database Configuration in Docker

The application is configured to use MySQL. Make sure your docker-compose.yml file includes the MySQL service configuration:

```yaml
services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: admin
      MYSQL_DATABASE: primac
      MYSQL_USER: admin
      MYSQL_PASSWORD: admin
    ports:
      - "3306:3306"
    volumes:
      - mysql-data:/var/lib/mysql

  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - mysql
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/primac?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
      SPRING_DATASOURCE_USERNAME: admin
      SPRING_DATASOURCE_PASSWORD: admin

volumes:
  mysql-data:
```

### Stopping the Containers

```bash
docker-compose down
```

To remove volumes when stopping:

```bash
docker-compose down -v
```

## Testing with Postman

This repository includes Postman collection and environment files to help you test the API endpoints.

### Importing the Postman Collection and Environment

1. Download [Postman](https://www.postman.com/downloads/) if you don't have it installed
2. Open Postman
3. Click on "Import" in the top left corner
4. Select the following files from the repository:
   - `Usuarios-Primac-API.postman_collection.json`
   - `Usuarios-Primac-API.postman_environment.json`
5. After importing, select the "Usuarios-Primac-API Environment" from the environment dropdown in the top right corner

### Using the Postman Collection

The collection is organized into folders for each category of endpoints:

1. **Authentication**: Contains endpoints for login and registration
2. **Users**: Contains CRUD endpoints for user management
3. **Clients**: Contains CRUD endpoints for client management
4. **Agents**: Contains CRUD endpoints for agent management

### Authentication Flow

1. Use the "Register" or "Login" request in the Authentication folder to get a JWT token
2. The token will be automatically saved to the environment variables
3. All other requests will use this token for authentication

### Customizing Environment Variables

You can modify the following environment variables according to your setup:

- `base_url`: The base URL of the API (default: http://localhost:8080)
- `username`: Default username for login
- `password`: Default password for login
- `user_id`, `client_id`, `agent_id`: IDs used in requests that require an entity ID

The collection is configured to automatically extract and save the JWT token from login/register responses, so you don't need to manually copy and paste it.
