package com.contaazul.coverage.pullrequest.chunkblammer;

import org.eclipse.egit.github.core.CommitFile;

import com.contaazul.coverage.git.LinePositioner;
import com.contaazul.coverage.pullrequest.Cobertura;

public class DummyChunkBlammer implements ChunkBlammer {

	@Override
	public void blame(CommitFile file, Cobertura chunkCoverage,
			LinePositioner positioner) {
		// do nothing :)
	}

}
