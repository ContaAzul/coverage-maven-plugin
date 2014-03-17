package com.contaazul.coverage.pullrequest;

import com.contaazul.coverage.maven.CoverageMavenProject;

public interface PullRequestValidator {
	void validate(CoverageMavenProject project);
}
