# GitHub-API

This Spring Boot app fetches non-fork repositories of a GitHub user along with their branches and last commit SHAs.

## Endpoint

`GET /users/{username}/repos`

### Response Example
```
[
  {
    "repositoryName": "Hello-World",
    "ownerLogin": "octocat",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "6dcb09b...
      }
    ]
  }
]
```

### Error
```json
{
  "status": 404,
  "message": "User not found"
}
```

### Test
Run with:
```bash
./mvnw test
