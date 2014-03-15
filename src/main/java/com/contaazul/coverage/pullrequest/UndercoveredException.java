package com.contaazul.coverage.pullrequest;

public class UndercoveredException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private static final String message = "The new lines added are wt=ith only %.2f%% total test coverage, which is lower than %d%% minimum allowed ";

	public UndercoveredException(Cobertura cobertura, int minCoverage) {
		super( String.format( message, cobertura.getCoverage(), minCoverage ) );
	}
}
