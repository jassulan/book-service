---

# **📘 Book Service - Spring Boot REST API**  
A **Spring Boot** based **CRUD REST API** for managing books with **pagination, validation, logging, exception handling, and Swagger UI integration**.  
The project is pre-configured to use **H2** for development

---

## **📌 Features**
✅ **CRUD operations**: Create, Read, Update, Delete books.  
✅ **DTOs & Validation**: Uses `@Valid` to validate API requests.  
✅ **Pagination & Sorting**: Supports retrieving books with paging.  
✅ **Exception Handling**: Centralized error responses.  
✅ **Logging**: Uses `SLF4J` and `Logback` with structured logs.  
✅ **Internationalization**: Supports multiple languages for messages.  
✅ **Unit Tests**: JUnit + Mockito for service & controller testing.  
✅ **Swagger UI**: API documentation via **SpringDoc OpenAPI**.  
✅ **Database Support**: Uses **H2 (in-memory)** for development.  

---

## **🛠️ Tech Stack**
| Technology | Purpose |
|------------|---------|
| **Java 17** | Programming language |
| **Spring Boot 3** | Main framework |
| **Spring Data JPA** | Database access |
| **Spring Validation** | DTO validation |
| **H2 Database** | In-memory DB for dev |
| **JUnit 5 & Mockito** | Unit testing |
| **Lombok** | Reduces boilerplate code |
| **SLF4J & Logback** | Logging |
| **SpringDoc OpenAPI** | API documentation |

---

## **📂 Project Structure**
```
📦 book-service
 ┣ 📂 src
 ┃ ┣ 📂 main
 ┃ ┃ ┣ 📂 java/com/example/bookservice
 ┃ ┃ ┃ ┣ 📂 controller        # REST Controllers
 ┃ ┃ ┃ ┣ 📂 dto               # DTOs (Request & Response)
 ┃ ┃ ┃ ┣ 📂 model             # JPA Entities
 ┃ ┃ ┃ ┣ 📂 repository        # Spring Data JPA Repositories
 ┃ ┃ ┃ ┣ 📂 service           # Business Logic Layer
 ┃ ┃ ┃ ┣ 📜 BookServiceApplication.java # Main Spring Boot Application
 ┃ ┣ 📂 test/java/com/example/bookservice
 ┃ ┃ ┣ 📂 service            # Unit tests for Service
 ┃ ┃ ┣ 📂 controller         # Unit tests for Controller
 ┃ ┣ 📜 application.yml      # Spring Boot Config
 ┃ ┣ 📜 schema.sql           # Database Schema (Optional)
 ┃ ┣ 📜 data.sql             # Sample Data (Optional)
 ┣ 📜 pom.xml                # Dependencies
 ┣ 📜 README.md              # Documentation
```

---

## **🚀 How to Run the Project**
### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/jassulan/book-service.git
cd book-service
```

### **3️⃣ Run the Application**
#### **Using Maven**
```sh
mvn spring-boot:run
```
#### **Using Java**
```sh
./mvnw clean package
java -jar target/book-service-0.0.1-SNAPSHOT.jar
```

### **4️⃣ Test the API**
#### **Swagger UI**
Once running, access **Swagger UI** at:  
👉 **`http://localhost:8080/swagger-ui`**  

```

---

## **📜 License**
This project is licensed under the **MIT License**.
