package com.contaazul.coverage.pullrequest;

import com.contaazul.coverage.cobertura.CoverageException;
import com.contaazul.coverage.github.GithubRepo;
import com.contaazul.coverage.github.GithubService;
import com.contaazul.coverage.github.GithubServiceImpl;

public class PullRequestValidatorBuilder {
	private int minCoverage = 95;
	private String oauth2;
	private int pullRequestId = -1;
	private GithubRepo repo;
	private boolean breakOnLowCov = true;

	public PullRequestValidatorBuilder() {
	}

	public PullRequestValidatorBuilder minCoverage(int minCoverage) {
		this.minCoverage = minCoverage;
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

	public PullRequestValidatorBuilder breakOnLowCov(boolean breakOnLowCov) {
		this.breakOnLowCov = breakOnLowCov;
		return this;
	}

	public PullRequestValidator build() {
		validate();
		final GithubService gh = new GithubServiceImpl(repo, oauth2,
				pullRequestId);
		return create(gh);
	}

	private PullRequestValidator create(final GithubService gh) {
		if (breakOnLowCov)
			return new BuildBreakerPullRequestValidator(gh, minCoverage);
		else
			return new NonBuildBreakerPullRequestValidator(gh, minCoverage);
	}

	private void validate() {
		if (repo == null || oauth2 == null || pullRequestId < 1)
			throw new CoverageException("Can't build GithubService.");
	}
}
