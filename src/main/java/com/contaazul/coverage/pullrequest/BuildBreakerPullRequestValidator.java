package com.contaazul.coverage.pullrequest;

import com.contaazul.coverage.github.GithubService;

public class BuildBreakerPullRequestValidator extends
		AbstractPullRequestValidator {

	public BuildBreakerPullRequestValidator(GithubService gh, int minCoverage, boolean commentChunks) {
		super(gh, minCoverage, commentChunks);
	}

	@Override
	protected boolean breakOnLowCoverage() {
		return true;
	}
}
