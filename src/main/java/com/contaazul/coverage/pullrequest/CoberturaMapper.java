package com.contaazul.coverage.pullrequest;

import java.util.List;

public final class CoberturaMapper {
	public static final Cobertura map(List<Cobertura> coverages) {
		Cobertura coverage = new CoberturaImpl();
		for (Cobertura cov : coverages)
			coverage.incrementCoverage( cov.getCoverage() );
		return coverage;
	}
}
