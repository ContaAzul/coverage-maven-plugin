package com.contaazul.coverage.cobertura;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.contaazul.coverage.cobertura.entity.Clazz;
import com.contaazul.coverage.cobertura.entity.Line;

public class LineCoveragerImpl implements LineCoverager {
	private static final Logger logger = LoggerFactory.getLogger( LineCoverager.class );
	private final Clazz clazz;

	public LineCoveragerImpl(Clazz clazz) {
		if (clazz == null)
			throw new CoveragerException( "Clazz cant be null." );
		this.clazz = clazz;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.contaazul.coverage.cobertura.LineCoverager#getLineCoverage(int)
	 */
	@Override
	public Double getLineCoverage(int lineNumber) {
		Line line = findLine( lineNumber );
		return getCoverageOf( line );
	}

	private Line findLine(int lineNumber) {
		for (Line line : clazz.getLines())
			if (line.getNumber() == lineNumber)
				return line;
		return null;
	}

	private Double getCoverageOf(Line line) {
		if (line == null)
			return null;
		if (line.isBranch() && line.getConditionCoverage() != null)
			return getConditionCoverage( line );
		return line.getHits() > 0 ? 100.0 : 0.0;
	}

	private double getConditionCoverage(Line line) {
		final Pattern regex = Pattern.compile( "%.*" );
		final String value = regex.matcher( line.getConditionCoverage() ).replaceAll( "" );
		logger.debug( "Value parsed " + value );
		return Double.parseDouble( value );
	}
}
