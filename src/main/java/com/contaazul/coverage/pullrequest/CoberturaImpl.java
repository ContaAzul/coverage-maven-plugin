package com.contaazul.coverage.pullrequest;

public class CoberturaImpl implements Cobertura {
	private double coverage = 0;
	private int count = 0;
	private int lastLine = 0;

	@Override
	public void incrementCoverage(double coverage) {
		logger.debug( "Incrementing actual coverage from " + this.coverage + " with " + coverage );
		this.coverage += coverage;
		count++;
	}

	@Override
	public void incrementCoverage(int line, int coverage) {
		this.lastLine = line;
		incrementCoverage( coverage );
	}

	@Override
	public double getCoverage() {
		logger.debug( "Count " + count );
		if (count == 0)
			return 100;
		return this.coverage / count;
	}

	@Override
	public int getLastLine() {
		return lastLine;
	}

	@Override
	public boolean isLowerThan(int allowed) {
		return getCoverage() < allowed;
	}
}
