package com.contaazul.coverage.pullrequest.analyser;

import static com.contaazul.coverage.pullrequest.CoberturaUtils.addTo;
import static com.contaazul.coverage.pullrequest.CoberturaUtils.map;

import java.util.List;
import java.util.Map;

import org.eclipse.egit.github.core.CommitFile;

import com.contaazul.coverage.cobertura.LineCoverager;
import com.contaazul.coverage.git.LinePositioner;
import com.contaazul.coverage.pullrequest.ChunkBlammer;
import com.contaazul.coverage.pullrequest.Cobertura;
import com.google.common.collect.Lists;

public class FileAnalyser {
	private final ChunkAnalyser analyser;

	public FileAnalyser(ChunkBlammer blammer, LineCoverager coverager, LinePositioner positioner) {
		this.analyser = new ChunkAnalyser( blammer, coverager, positioner );
	}

	public Cobertura analyse(CommitFile file, final LinePositioner positioner, final LineCoverager coverager) {
		final List<Cobertura> fileCoverage = Lists.newArrayList();
		for (Map<Integer, Integer> chunk : positioner.getChunks())
			addTo( fileCoverage, analyser.analyse( chunk, file ) );
		return map( fileCoverage );
	}
}
