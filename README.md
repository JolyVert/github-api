# GitHub Repositories API

This Spring Boot application exposes an endpoint to list non-fork GitHub repositories for a given user, including branch names and latest commit SHAs.

## Requirements
- Java 21
- Spring Boot 3.5

## Usage
1. Clone the repository
2. Configure GitHub token (if needed) via `application.properties`
3. Run `./mvnw spring-boot:run`
4. API: `GET /users/{username}/repos`

## Response
- 200 OK: List of repositories
- 404 Not Found: `{ "status": 404, "message": "User not found" }`

## Integration Test
A single full-stack integration test covers the happy path scenario, performing a real call through the service stack.
