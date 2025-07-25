package com.jolyvert.githubapi.controller;

import com.jolyvert.githubapi.exception.UserNotFoundException;
import com.jolyvert.githubapi.service.GithubService;
import com.jolyvert.githubapi.model.RepoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GithubController {
    private final GithubService service;
    public GithubController(GithubService service) { this.service = service; }

    @GetMapping("/users/{username}/repos")
    public ResponseEntity<?> getRepos(@PathVariable String username) {
        try {
            var list = service.getNonForkRepos(username);
            return ResponseEntity.ok(list);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404)
                    .body(new ErrorResponse(404, e.getMessage()));
        }
    }

    record ErrorResponse(int status, String message) {}
}
