package com.jolyvert.githubapi.service;


import com.jolyvert.githubapi.exception.UserNotFoundException;
import com.jolyvert.githubapi.model.BranchResponse;
import com.jolyvert.githubapi.model.RepoResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class Service {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<RepoResponse> getNonForkReposWithBranches(String username) {
        String url = "https://api.github.com/users/" + username + "/repos";

        JsonNode repos;
        try {
            repos = objectMapper.readTree(restTemplate.getForObject(url, String.class));
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException(404, "User not found");
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch GitHub repositories", e);
        }

        List<RepoResponse> result = new ArrayList<>();
        for (JsonNode repo : repos) {
            if (!repo.get("fork").asBoolean()) {
                String repoName = repo.get("name").asText();
                String ownerLogin = repo.get("owner").get("login").asText();
                String branchesUrl = "https://api.github.com/repos/" + ownerLogin + "/" + repoName + "/branches";

                try {
                    JsonNode branches = objectMapper.readTree(restTemplate.getForObject(branchesUrl, String.class));
                    List<BranchResponse> branchList = new ArrayList<>();

                    for (JsonNode branch : branches) {
                        branchList.add(new BranchResponse(
                                branch.get("name").asText(),
                                branch.get("commit").get("sha").asText()
                        ));
                    }

                    result.add(new RepoResponse(repoName, ownerLogin, branchList));
                } catch (Exception e) {
                    // Log and skip failing repo branch fetch
                }
            }
        }

        return result;
    }
}
