package com.contaazul.coverage.github;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.github.core.Comment;
import org.eclipse.egit.github.core.CommitComment;
import org.eclipse.egit.github.core.CommitFile;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.PullRequestService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class GithubServiceTest {
	private GithubService gh;
	@Mock
	private GitHubClient client;
	private GithubRepo repo;

	@Before
	public void init() {
		initMocks( this );
		repo = new GithubRepo( "caarlos0", "not-a-real-repo" );
		gh = spy( new GithubServiceImpl( repo, "not a real oauth ", 42 ) );
	}

	@Test
	public void getRepositoryService() throws Exception {
		RepositoryService service = gh.getRepositoryService();
		RepositoryService service2 = gh.getRepositoryService();
		assertNotNull( service );
		assertEquals( service, service2 );
	}

	@Test
	public void getIssueService() throws Exception {
		IssueService service = gh.getIssueService();
		IssueService service2 = gh.getIssueService();
		assertNotNull( service );
		assertEquals( service, service2 );
	}

	@Test
	public void getPullRequestService() throws Exception {
		PullRequestService service = gh.getPullRequestService();
		PullRequestService service2 = gh.getPullRequestService();
		assertNotNull( service );
		assertEquals( service, service2 );
	}

	@Test
	public void getPRService() throws Exception {
		assertNotNull( gh.getPullRequestService() );
	}

	@Test
	public void connectToRepository() throws Exception {
		RepositoryService service = mock( RepositoryService.class );
		doReturn( service ).when( gh ).getRepositoryService();
		doReturn( new Repository() ).when( service ).getRepository( anyString(), anyString() );
		Repository repo = gh.getRepository();
		assertNotNull( repo );
	}

	@Test
	public void getPullRequestCommitFiles() throws Exception {
		PullRequestService service = mock( PullRequestService.class );
		doReturn( new ArrayList<CommitFile>() ).when( service )
				.getFiles( any( IRepositoryIdProvider.class ), anyInt() );
		doReturn( service ).when( gh ).getPullRequestService();
		doReturn( new Repository() ).when( gh ).getRepository();
		List<CommitFile> files = gh.getPullRequestCommitFiles();
		assertTrue( files.isEmpty() );
	}

	@Test(expected = GitHubException.class)
	public void getPullRequestFilesException() throws Exception {
		when( gh.getRepository() ).thenThrow( new IOException() );
		assertTrue( gh.getPullRequestCommitFiles().isEmpty() );
	}

	@Test(expected = GitHubException.class)
	public void getFilesException() throws Exception {
		when( gh.getRepository() ).thenThrow( new IOException() );
		assertTrue( gh.getFiles().isEmpty() );
	}

	@Test
	public void createComment() throws Exception {
		IssueService issue = mock( IssueService.class );
		doReturn( issue ).when( gh ).getIssueService();
		doReturn( new Repository() ).when( gh ).getRepository();
		when( issue.createComment( any( IRepositoryIdProvider.class ), anyInt(), anyString() ) )
				.thenReturn( new Comment() );
		gh.createComment( "not a real comment" );
	}

	@Test(expected = GitHubException.class)
	public void createCommentThrowsException() throws Exception {
		when( gh.getRepository() ).thenThrow( new IOException() );
		gh.createComment( "not a real comment" );
	}

	@Test
	public void createPRComment() throws Exception {
		mockPRComment();
		gh.createComment( new PullRequestCommitComment( 10, 100, "not a sha", "not/a/path", 42 ) );
	}

	@Test
	public void createInvalidPRComment() throws Exception {
		mockPRComment();
		gh.createComment( new PullRequestCommitComment( 10, 100, null, null, -1 ) );
	}

	@Test(expected = GitHubException.class)
	public void createPRCommentException() throws Exception {
		PullRequestService pr = mock( PullRequestService.class );
		doReturn( pr ).when( gh ).getPullRequestService();
		when( gh.getRepository() ).thenThrow( new IOException() );
		gh.createComment( new PullRequestCommitComment( 10, 100, "not a sha", "not/a/path", 42 ) );
	}

	private void mockPRComment() throws IOException {
		PullRequestService pr = mock( PullRequestService.class );
		doReturn( pr ).when( gh ).getPullRequestService();
		doReturn( new Repository() ).when( gh ).getRepository();
		when( pr.createComment( any( IRepositoryIdProvider.class ), anyInt(), any( CommitComment.class ) ) )
				.thenReturn( new CommitComment() );
	}
}
