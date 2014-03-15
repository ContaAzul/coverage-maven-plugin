package com.contaazul.coverage.pullrequest;

import com.contaazul.coverage.cobertura.entity.Coverage;
import com.contaazul.coverage.github.GithubService;

public class BuildBreakerPullRequestValidator extends AbstractPullRequestValidator {

	public BuildBreakerPullRequestValidator(GithubService gh, Coverage coverage, String srcFolder, int minCoverage) {
		super( gh, coverage, srcFolder, minCoverage );
	}

	@Override
	protected boolean breakOnLowCoverage() {
		return true;
	}
}
