package com.contaazul.coverage.pullrequest;

import com.contaazul.coverage.github.GithubService;

public class NonBuildBreakerPullRequestValidator extends AbstractPullRequestValidator {

	public NonBuildBreakerPullRequestValidator(GithubService gh, int minCoverage) {
		super( gh, minCoverage );
	}

	@Override
	protected boolean breakOnLowCoverage() {
		return false;
	}
}
