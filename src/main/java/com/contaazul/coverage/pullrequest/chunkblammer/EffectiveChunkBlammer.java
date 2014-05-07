package com.contaazul.coverage.pullrequest;

import org.eclipse.egit.github.core.CommitFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.contaazul.coverage.git.LinePositioner;
import com.contaazul.coverage.github.GithubService;
import com.contaazul.coverage.github.PullRequestCommitComment;
import com.contaazul.coverage.github.PullRequestSHARetriever;
import com.contaazul.coverage.pullrequest.analyser.ChunkAnalyser;

public class ChunkBlammer {
	private static final Logger logger = LoggerFactory
			.getLogger(ChunkAnalyser.class);
	private final int minCoverage;
	private final PullRequestSHARetriever shas;
	private final GithubService gh;

	public ChunkBlammer(GithubService gh, int minCoverage) {
		super();
		this.minCoverage = minCoverage;
		this.gh = gh;
		this.shas = new PullRequestSHARetriever(gh);
	}

	public void blame(CommitFile file, Cobertura chunkCoverage,
			LinePositioner positioner) {
		if (chunkCoverage.isLowerThan(minCoverage))
			blameChunk(file, chunkCoverage.getCoverage(),
					positioner.toPosition(chunkCoverage.getLastLine()));
	}

	private void blameChunk(CommitFile cf, double coverage, int position) {
		logger.debug("Blamming chunk on " + cf.getFilename()
				+ " for the coverage " + coverage + " in position "
				+ position);
		gh.createComment(new PullRequestCommitComment(coverage, minCoverage,
				shas.get(cf),
				cf.getFilename(), position));
	}

}
