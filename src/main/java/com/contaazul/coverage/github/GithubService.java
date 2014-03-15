package com.contaazul.coverage.github;

import java.util.List;

import org.eclipse.egit.github.core.CommitFile;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.eclipse.egit.github.core.service.RepositoryService;

public interface GithubService {

	public abstract RepositoryService getRepositoryService();

	public abstract Repository getRepository();

	public abstract List<CommitFile> getPullRequestCommitFiles();

	public abstract PullRequestService getPullRequestService();

	public abstract List<CommitFile> getFiles();

	public abstract void createComment(PullRequestComment comment);

}
