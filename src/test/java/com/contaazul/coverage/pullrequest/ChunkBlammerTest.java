package com.contaazul.coverage.pullrequest;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.eclipse.egit.github.core.CommitFile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.contaazul.coverage.git.LinePositioner;
import com.contaazul.coverage.github.GithubService;
import com.contaazul.coverage.github.PullRequestCommitComment;
import com.contaazul.coverage.pullrequest.chunkblammer.ChunkBlammer;
import com.contaazul.coverage.pullrequest.chunkblammer.DummyChunkBlammer;
import com.contaazul.coverage.pullrequest.chunkblammer.EffectiveChunkBlammer;

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
		blammer = new EffectiveChunkBlammer(gh, 70);
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
	
	@Test
	public void testWhenCommentingDisabled() throws Exception {
		blammer = new DummyChunkBlammer();
		blammer.blame(file, chunkCoverage, positioner);
		verify(gh, never()).createComment(any(PullRequestCommitComment.class));
	}
}
