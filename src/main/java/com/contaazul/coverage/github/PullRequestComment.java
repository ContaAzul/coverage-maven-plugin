package com.contaazul.coverage.github;

import org.eclipse.egit.github.core.CommitComment;

public class PullRequestComment {
	private double coverage;
	private int minCoverage;
	private String sha;
	private String path;
	private int position;
	private final String messageTemplate = "This chunk has %.2f%% of tests coverage, which is lower than %s%% minimum allowed";

	public PullRequestComment(double coverage, int minCoverage, String sha, String path, int position) {
		super();
		this.coverage = coverage;
		this.minCoverage = minCoverage;
		this.sha = sha;
		this.path = path;
		this.position = position;
	}

	public CommitComment get() {
		CommitComment comment = new CommitComment();
		comment.setBody( getMessageBody() );
		comment.setCommitId( sha );
		comment.setPath( path );
		comment.setPosition( position );
		return comment;
	}

	public boolean isValid() {
		return sha != null && path != null && position > -1;
	}

	@Override
	public String toString() {
		return "PullRequestComment [coverage=" + coverage + ", minCoverage=" + minCoverage + ", sha=" + sha + ", path="
				+ path + ", position=" + position + ", getMessageBody()=" + getMessageBody() + "]";
	}

	private String getMessageBody() {
		return String.format( messageTemplate, coverage, minCoverage );
	}
}
