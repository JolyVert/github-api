package com.jolyvert.githubapi.controller;


import com.jolyvert.githubapi.model.RepoResponse;
import com.jolyvert.githubapi.service.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class Controller {
    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @GetMapping("/{username}/repos")
    public ResponseEntity<List<RepoResponse>> getUserRepos(@PathVariable String username) {
        List<RepoResponse> repos = service.getNonForkReposWithBranches(username);
        return ResponseEntity.ok(repos);
    }
}