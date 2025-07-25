package com.jolyvert.githubapi.model;

import java.util.List;

public record RepoResponse(
        String repositoryName,
        String ownerLogin,
        List<Branch> branches
) {
    public record Branch(String name, String lastCommitSha) {}
}

