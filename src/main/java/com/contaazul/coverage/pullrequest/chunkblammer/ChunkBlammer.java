package com.contaazul.coverage.pullrequest.chunkblammer;

import org.eclipse.egit.github.core.CommitFile;

import com.contaazul.coverage.git.LinePositioner;
import com.contaazul.coverage.pullrequest.Cobertura;

public interface ChunkBlammer {
	public void blame(CommitFile file, Cobertura chunkCoverage,
			LinePositioner positioner);
}