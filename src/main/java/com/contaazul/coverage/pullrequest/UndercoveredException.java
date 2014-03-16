package com.contaazul.coverage.pullrequest;

public class UndercoveredException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public static final String MSG = "The new lines added are with only %.2f%% total test coverage, which is lower than %d%% minimum allowed.";

	public UndercoveredException(Cobertura cobertura, int minCoverage) {
		super( String.format( MSG, cobertura.getCoverage(), minCoverage ) );
	}
}
