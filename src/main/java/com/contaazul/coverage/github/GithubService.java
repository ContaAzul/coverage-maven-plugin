package com.contaazul.coverage.github;

import java.util.List;

import org.eclipse.egit.github.core.CommitFile;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.eclipse.egit.github.core.service.RepositoryService;

public interface GithubService {

	RepositoryService getRepositoryService();

	Repository getRepository();

	List<CommitFile> getPullRequestCommitFiles();

	PullRequestService getPullRequestService();

	List<CommitFile> getFiles();

	void createComment(PullRequestCommitComment comment);

	void createComment(String comment);

	IssueService getIssueService();

}
