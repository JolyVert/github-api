package com.jolyvert.githubapi.model;

import java.util.List;

public record RepoResponse(String repositoryName, String ownerLogin, List<BranchResponse> branches) {}
