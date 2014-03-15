package com.contaazul.coverage.pullrequest;

import org.apache.maven.project.MavenProject;

import com.contaazul.coverage.cobertura.CoverageException;
import com.contaazul.coverage.cobertura.entity.Coverage;
import com.contaazul.coverage.github.GithubRepo;
import com.contaazul.coverage.github.GithubService;
import com.contaazul.coverage.github.GithubServiceImpl;
import com.contaazul.coverage.maven.CoverageMavenProject;

public class PullRequestValidatorBuilder {
	private int minCoverage = 95;
	private CoverageMavenProject project;
	private String oauth2;
	private int pullRequestId = -1;
	private GithubRepo repo;

	public PullRequestValidatorBuilder() {
	}

	public PullRequestValidatorBuilder minCoverage(int minCoverage) {
		this.minCoverage = minCoverage;
		return this;
	}

	public PullRequestValidatorBuilder project(MavenProject project) {
		this.project = new CoverageMavenProject( project );
		return this;
	}

	public PullRequestValidatorBuilder oauth2(String oauth2) {
		this.oauth2 = oauth2;
		return this;
	}

	public PullRequestValidatorBuilder pullRequest(int pullRequestId) {
		this.pullRequestId = pullRequestId;
		return this;
	}

	public PullRequestValidatorBuilder repository(GithubRepo githubRepo) {
		this.repo = githubRepo;
		return this;
	}

	public PullRequestValidator build() {
		validate();
		final GithubService gh = new GithubServiceImpl( repo, oauth2, pullRequestId );
		final String src = project.getSrcFolder();
		final Coverage coverage = project.getCoverage();
		return new PullRequestValidatorImpl( gh, coverage, src, minCoverage );
	}

	private void validate() {
		if (repo == null || oauth2 == null || pullRequestId < 1)
			throw new CoverageException( "Can't build GithubService." );
		if (project == null)
			throw new CoverageException( "Can't get Project data." );
	}
}
