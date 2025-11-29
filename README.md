# SuperDrive Cloud Storage

A secure cloud storage web application with personal information management features, built with Spring Boot and modern web technologies.

## Overview

SuperDrive is a full-stack cloud storage application that allows users to securely store files, manage notes, and save website credentials. The application features user authentication, encrypted password storage, and a clean, responsive interface built with Bootstrap and Thymeleaf.

## Features

### 🔐 User Authentication & Security
- Secure user registration and login
- Password hashing with salted SHA-512
- Spring Security integration for access control
- Session management and logout functionality

### 📁 File Storage
- Upload files with size validation
- Download previously uploaded files
- Delete files with confirmation
- Duplicate filename prevention
- Secure file storage per user

### 📝 Note Management
- Create and organize personal notes
- Edit existing notes in real-time
- Delete notes with confirmation
- Rich text support with proper formatting

### 🔑 Credential Management
- Store website credentials securely
- Encrypted password storage using AES encryption
- View, edit, and delete saved credentials
- Password visibility toggle (shows encrypted/decrypted)
- Secure key generation for encryption

## Technology Stack

### Backend
- **Spring Boot** - Application framework
- **Spring Security** - Authentication and authorization
- **MyBatis** - Database ORM
- **H2 Database** - In-memory database for development
- **Maven** - Dependency management

### Frontend
- **Thymeleaf** - Server-side templating engine
- **Bootstrap 4** - Responsive UI framework
- **JavaScript** - Client-side interactivity

### Testing
- **JUnit** - Unit testing framework
- **Selenium WebDriver** - End-to-end testing
- **Spring Boot Test** - Integration testing support

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/udacity/jwdnd/course1/cloudstorage/
│   │       ├── config/              # Security configuration
│   │       ├── controller/          # MVC controllers
│   │       ├── mapper/              # MyBatis mappers
│   │       ├── model/               # Data models
│   │       └── services/            # Business logic
│   └── resources/
│       ├── application.properties   # Application configuration
│       ├── schema.sql              # Database schema
│       ├── static/                 # CSS, JS assets
│       └── templates/              # Thymeleaf templates
└── test/
    └── java/                       # Test cases and page objects
```

## Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher
- Modern web browser (Chrome, Firefox, Safari, Edge)

### Installation

1. Clone the repository:
```bash
git clone https://github.com/Slivinskiy/SuperDrive.git
cd SuperDrive
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

4. Access the application:
```
http://localhost:8080
```

## Usage

1. **Sign Up**: Create a new account with a unique username
2. **Login**: Access your personal dashboard
3. **Upload Files**: Use the Files tab to upload and manage documents
4. **Create Notes**: Use the Notes tab to create and organize notes
5. **Save Credentials**: Use the Credentials tab to securely store website passwords
6. **Logout**: Securely end your session when finished

## Security Features

### Password Hashing
User passwords are hashed using SHA-512 with a randomly generated salt before storage. This ensures that even if the database is compromised, user passwords remain secure.

### Credential Encryption
Stored website credentials use AES encryption with unique encryption keys per credential. Passwords are encrypted before storage and decrypted only when explicitly viewed by the authorized user.

### Access Control
Spring Security ensures that:
- Unauthenticated users can only access login and signup pages
- All other pages require valid authentication
- Users can only access their own data
- Sessions are properly managed and secured

## Testing

The application includes comprehensive Selenium tests covering:

### User Authentication Tests
- Unauthorized access restrictions
- User signup and login flow
- Session management and logout

### Feature Tests
- File upload, download, and deletion
- Note creation, editing, and deletion
- Credential creation, viewing (encrypted/decrypted), editing, and deletion

### Running Tests
```bash
mvn test
```

## Database Schema

The application uses an H2 in-memory database with the following tables:
- **USERS** - User account information
- **FILES** - Uploaded file metadata and content
- **NOTES** - User notes
- **CREDENTIALS** - Encrypted website credentials

Schema is automatically initialized from `schema.sql` on application startup.

## Configuration

Key configuration options in `application.properties`:
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Udacity Java Web Developer Nanodegree Program
- Spring Boot and Spring Security communities
- Bootstrap team for the excellent UI framework
