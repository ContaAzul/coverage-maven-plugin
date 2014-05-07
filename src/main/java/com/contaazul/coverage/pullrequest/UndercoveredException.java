package com.contaazul.coverage.pullrequest;

import static com.contaazul.coverage.github.Messages.BUILD;

import com.contaazul.coverage.maven.CoverageMavenProject;

public class UndercoveredException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UndercoveredException(CoverageMavenProject project,
			Cobertura cobertura, int minCoverage) {
		super(String.format(BUILD, project, cobertura.getCoverage(),
				minCoverage));
	}
}
