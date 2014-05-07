package com.contaazul.coverage.pullrequest.analyser;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.egit.github.core.CommitFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.contaazul.coverage.cobertura.LineCoverager;
import com.contaazul.coverage.git.LinePositioner;
import com.contaazul.coverage.pullrequest.ChunkBlammer;
import com.contaazul.coverage.pullrequest.Cobertura;
import com.contaazul.coverage.pullrequest.CoberturaImpl;

public class ChunkAnalyser {
	private static final Logger logger = LoggerFactory
			.getLogger(ChunkAnalyser.class);
	private final LineAnalyser analyser;
	private final ChunkBlammer blammer;
	private final LineCoverager coverager;
	private final LinePositioner positioner;

	public ChunkAnalyser(ChunkBlammer blammer, LineCoverager coverager,
			LinePositioner positioner) {
		this.analyser = new LineAnalyser();
		this.blammer = blammer;
		this.coverager = coverager;
		this.positioner = positioner;
	}

	public final Cobertura analyse(Map<Integer, Integer> chunk, CommitFile file) {
		logger.debug("Analysing chunk " + chunk);
		Cobertura chunkCoverage = getChunkCoverage(chunk, coverager);
		blammer.blame(file, chunkCoverage, positioner);
		return chunkCoverage;
	}

	private Cobertura getChunkCoverage(Map<Integer, Integer> chunk,
			LineCoverager coverager) {
		final Cobertura chunkCoverage = new CoberturaImpl();
		for (Entry<Integer, Integer> line : chunk.entrySet())
			analyser.analyse(line.getKey(), coverager, chunkCoverage);
		return chunkCoverage;
	}

}
