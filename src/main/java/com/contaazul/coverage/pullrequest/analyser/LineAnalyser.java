package com.contaazul.coverage.pullrequest.analyser;

import com.contaazul.coverage.cobertura.LineCoverager;
import com.contaazul.coverage.pullrequest.Cobertura;

public class LineAnalyser {
	// TODO side-effects!
	public final void analyse(int line, LineCoverager coverager, Cobertura chunkCoverage) {
		final Integer lineCoverage = coverager.getLineCoverage( line );
		if (lineCoverage != null)
			chunkCoverage.incrementCoverage( line, lineCoverage );
	}
}
