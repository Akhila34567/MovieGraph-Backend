# 🎬 MovieGraph Backend

A Spring Boot REST API that manages movies, persons, genres, and their relationships using a graph database (CognoDB/Neo4j).

## 🚀 Features

- Movie CRUD
- Person CRUD
- Genre CRUD
- ACTED_IN relationship
- DIRECTED relationship
- HAS_GENRE relationship
- Movie recommendations based on shared genres
- Swagger API documentation
- Global exception handling
- Validation using Jakarta Validation

## 🛠️ Tech Stack

- Java 21
- Spring Boot 4.1.0
- Maven
- CognoDB (Neo4j)
- Neo4j Java Driver
- Swagger / OpenAPI
- Lombok

## 📂 Project Structure

```
src
├── controller
├── dto
├── exception
├── model
├── repository
├── service
└── config
```

## 📌 API Endpoints

### Movies

| Method | Endpoint |
|--------|----------|
| POST | /api/movies |
| GET | /api/movies |
| GET | /api/movies/{movieId} |
| PUT | /api/movies/{movieId} |
| DELETE | /api/movies/{movieId} |

### Persons

| Method | Endpoint |
|--------|----------|
| POST | /api/persons |
| GET | /api/persons |
| GET | /api/persons/{personId} |
| PUT | /api/persons/{personId} |
| DELETE | /api/persons/{personId} |

### Genres

| Method | Endpoint |
|--------|----------|
| POST | /api/genres |
| GET | /api/genres |
| GET | /api/genres/{genreId} |
| PUT | /api/genres/{genreId} |
| DELETE | /api/genres/{genreId} |

### Relationships

| Method | Endpoint |
|--------|----------|
| POST | /api/relationships/acted-in |
| POST | /api/relationships/directed |
| POST | /api/relationships/has-genre |

### Graph Queries

| Method | Endpoint |
|--------|----------|
| GET | /api/movies/{movieId}/actors |
| GET | /api/persons/{personId}/movies |
| GET | /api/movies/{movieId}/directors |
| GET | /api/persons/{personId}/directed-movies |
| GET | /api/movies/{movieId}/genres |
| GET | /api/genres/{genreId}/movies |

### Recommendations

| Method | Endpoint |
|--------|----------|
| GET | /api/recommendations/{movieId} |

## 📖 Swagger

After starting the application, open:

http://localhost:8081/swagger-ui/index.html

## ▶️ Running the Project

Clone the repository and navigate to the backend folder:

```bash
mvn clean install
mvn spring-boot:run
```

The application starts on:

```
http://localhost:8081
```

## 📌 Future Enhancements

- React Frontend
- User Authentication
- Docker Support
- Unit & Integration Tests
- CI/CD Pipeline

## 👩‍💻 Author

Akhila