package com.contaazul.coverage.github;

public interface Messages {

	final String BUILD = "The new lines added into **%s** have only %.2f%% "
			+ "test coverage, which is lower than %d%% minimum allowed.";

	final String PULL_REQUEST = "-1. Please add more tests!"
			+ "\n\n" + BUILD;

	final String COMMIT = "This chunk has %.2f%% of tests coverage, which is "
			+ "lower than %s%% minimum allowed";
}
