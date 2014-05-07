package com.contaazul.coverage.github;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class GithubRepoTest {

	private GithubRepo repo;
	@Mock
	private RepositoryService service;
	@Mock
	private Repository ghRepo;

	@Before
	public void init() throws IOException {
		initMocks(this);
		when(service.getRepository("caarlos0", "coverage-maven-plugin"))
				.thenReturn(ghRepo);
		repo = spy(new GithubRepo("caarlos0", "coverage-maven-plugin"));
	}

	@Test
	public void testSameInstance() throws Exception {
		Repository connectedRepo = repo.connect(service);
		assertEquals(connectedRepo, repo.connect(service));
	}

	@Test(expected = GitHubException.class)
	public void testConnectException() throws Exception {
		when(service.getRepository(anyString(), anyString())).thenThrow(
				IOException.class);
		repo.connect(service);
	}
}
