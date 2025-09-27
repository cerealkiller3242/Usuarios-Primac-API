# Usuarios-Primac-API

## JWT Authentication System

This project has been enhanced with JWT (JSON Web Token) authentication for improved security in login and registration processes.

### Features

- Secure user registration with password encryption
- JWT-based authentication
- Role-based authorization
- Token validation for protected endpoints
- Docker and Docker Compose support for easy deployment

### Authentication Endpoints

#### Register a new user

```
POST /api/auth/register
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
POST /api/auth/login
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

### Using JWT Tokens

After successful authentication, include the JWT token in the Authorization header for subsequent requests:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Security Configuration

- JWT tokens expire after 24 hours
- Passwords are encrypted using BCrypt
- All endpoints except `/api/auth/**` and `/h2-console/**` require authentication

### Implementation Details

The JWT authentication system consists of:

1. **JWT Utilities**: For token generation, validation, and parsing
2. **Authentication Filter**: Intercepts requests and validates JWT tokens
3. **Security Configuration**: Configures Spring Security with JWT authentication
4. **Authentication Controller**: Provides endpoints for login and registration
5. **Password Encryption**: All passwords are encrypted using BCrypt

### Database Configuration

The application uses an H2 in-memory database for development. The H2 console is available at:

```
http://localhost:8080/h2-console
```

Connection details:
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

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
- Start the application with H2 in-memory database
- Expose the application on port 8080

### Accessing the Application

- API: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console

### Using PostgreSQL Instead of H2

The docker-compose.yml file includes a commented-out configuration for PostgreSQL. To use PostgreSQL:

1. Open the docker-compose.yml file
2. Uncomment the PostgreSQL service section
3. Uncomment the PostgreSQL volumes section
4. Comment out the current app service configuration
5. Uncomment the PostgreSQL app service configuration
6. Run Docker Compose:

```bash
docker-compose up -d
```

### Stopping the Containers

```bash
docker-compose down
```

To remove volumes when stopping:

```bash
docker-compose down -v
```
