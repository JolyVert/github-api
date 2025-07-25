package com.jolyvert.githubapi;

import com.jolyvert.githubapi.model.RepoResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GithubApiApplicationTests {

	@LocalServerPort
	private int port;

	private final TestRestTemplate rest = new TestRestTemplate();

	@Test
	void givenExistingGithubUser_whenGetRepos_thenReturnNonForkReposWithBranches() {
		String username = "octocat";
		String url = String.format("http://localhost:%d/users/%s/repos", port, username);

		ResponseEntity<RepoResponse[]> response = rest.getForEntity(url, RepoResponse[].class);

		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		RepoResponse[] repos = response.getBody();
		assertThat(repos).isNotNull().isNotEmpty();

		for (RepoResponse repo : repos) {
			assertThat(repo.ownerLogin()).isEqualTo(username);
			assertThat(repo.repositoryName()).isNotBlank();
			assertThat(repo.branches()).isNotEmpty();
			repo.branches().forEach(branch -> {
				assertThat(branch.name()).isNotBlank();
				assertThat(branch.lastCommitSha()).isNotBlank();
			});
		}
	}
}
