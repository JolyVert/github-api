package com.jolyvert.githubapi.model;

public record GithubRepo(String name, boolean fork, GithubOwner owner) {}
