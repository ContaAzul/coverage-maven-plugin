package com.contaazul.coverage.github;

import java.io.IOException;
import java.util.List;

import org.eclipse.egit.github.core.CommitFile;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GithubServiceImpl implements GithubService {
	private static final Logger logger = LoggerFactory.getLogger( GithubServiceImpl.class );

	private final GitHubClient client;
	private final int pullRequestId;
	private final GithubRepo repo;
	private PullRequestService prService;
	private RepositoryService repoService;

	public GithubServiceImpl(GithubRepo repo, String oauth2, int pullRequestId) {
		this.repo = repo;
		this.pullRequestId = pullRequestId;
		client = new GitHubClient().setOAuth2Token( oauth2 );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.contaazul.coverage.github.GithubService#getRepositoryService()
	 */
	@Override
	public RepositoryService getRepositoryService() {
		if (repoService == null)
			repoService = new RepositoryService( client );
		return repoService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.contaazul.coverage.github.GithubService#getRepository()
	 */
	@Override
	public Repository getRepository() {
		return repo.connect( getRepositoryService() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.contaazul.coverage.github.GithubService#getPullRequestCommitFiles()
	 */
	@Override
	public List<CommitFile> getPullRequestCommitFiles() {
		try {
			return getPullRequestService().getFiles( getRepository(), pullRequestId );
		} catch (IOException e) {
			throw new GitHubException( "Failed to get pull request commits", e );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.contaazul.coverage.github.GithubService#getPullRequestService()
	 */
	@Override
	public PullRequestService getPullRequestService() {
		if (prService == null)
			prService = new PullRequestService( client );
		return prService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.contaazul.coverage.github.GithubService#getFiles()
	 */
	@Override
	public List<CommitFile> getFiles() {
		try {
			return getPullRequestService().getFiles( getRepository(), pullRequestId );
		} catch (IOException e) {
			throw new GitHubException( "Failed to get pull request files", e );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.contaazul.coverage.github.GithubService#createComment(com.contaazul
	 * .coverage.github.PullRequestComment)
	 */
	@Override
	public void createComment(PullRequestComment comment) {
		if (comment.isValid())
			comment( comment );
		else
			logger.debug( "Comment is invalid: " + comment );
	}

	private void comment(PullRequestComment comment) {
		logger.debug( "Commenting: " + comment );
		try {
			getPullRequestService()
					.createComment( getRepository(), pullRequestId, comment.get() );
		} catch (Exception e) {
			logger.error( "Failed to comment on PullRequest " + pullRequestId + ": " + comment, e );
		}
	}

}
