package com.contaazul.coverage.pullrequest;

import java.util.List;

public final class CoberturaUtils {
	public static final Cobertura map(List<Cobertura> coverages) {
		Cobertura coverage = new CoberturaImpl();
		for (Cobertura cov : coverages)
			coverage.incrementCoverage( cov.getCoverage() );
		return coverage;
	}

	public static final void addTo(final List<Cobertura> coberturas, Cobertura cobertura) {
		if (cobertura.isCounted())
			coberturas.add( cobertura );
	}
}
