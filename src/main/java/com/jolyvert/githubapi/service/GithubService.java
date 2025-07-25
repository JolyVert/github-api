package com.jolyvert.githubapi.service;

import com.jolyvert.githubapi.exception.UserNotFoundException;
import com.jolyvert.githubapi.model.GithubBranch;
import com.jolyvert.githubapi.model.GithubRepo;
import com.jolyvert.githubapi.model.RepoResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class GithubService {
    private static final String API = "https://api.github.com";
    private final RestTemplate rest = new RestTemplate();

    public List<RepoResponse> getNonForkRepos(String username) {
        try {
            var repos = Arrays.stream(rest.getForObject(API + "/users/" + username + "/repos", GithubRepo[].class))
                    .filter(r -> !r.fork())
                    .map(r -> new RepoResponse(
                            r.name(),
                            r.owner().login(), 
                            Arrays.stream(rest.getForObject(API + "/repos/" + username + "/" + r.name() + "/branches", GithubBranch[].class))
                                    .map(b -> new RepoResponse.Branch(b.name(), b.commit().sha()))
                                    .toList()
                    ))
                    .toList();
            return repos;
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("User '" + username + "' not found");
        }
    }
}
