package com.contaazul.coverage.pullrequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import org.eclipse.egit.github.core.CommitFile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.contaazul.coverage.git.LinePositioner;
import com.contaazul.coverage.github.GithubService;
import com.contaazul.coverage.github.PullRequestCommitComment;

public class ChunkBlammerTest {
	@Mock
	private GithubService gh;
	@Mock
	private LinePositioner positioner;
	private ChunkBlammer blammer;
	private CommitFile file;
	private Cobertura chunkCoverage;

	@Before
	public void init() {
		initMocks(this);
		blammer = new ChunkBlammer(gh, 70);
		file = new CommitFile();
		chunkCoverage = new CoberturaImpl();
	}

	@Test
	public void testLowCoverage() throws Exception {
		chunkCoverage.incrementCoverage(1.0);
		blammer.blame(file, chunkCoverage, positioner);
		verify(gh).createComment(any(PullRequestCommitComment.class));
	}

	@Test
	public void testHishCoverage() throws Exception {
		chunkCoverage.incrementCoverage(100.0);
		blammer.blame(file, chunkCoverage, positioner);
		verify(gh, never()).createComment(any(PullRequestCommitComment.class));
	}
}
