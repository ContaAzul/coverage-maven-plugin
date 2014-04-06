package com.contaazul.coverage.github;

import java.io.IOException;

public class GitHubException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public GitHubException(String msg, IOException e) {
		super( msg, e );
	}
}
