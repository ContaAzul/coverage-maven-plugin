package com.contaazul.coverage.pullrequest.analyser;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.contaazul.coverage.cobertura.LineCoverager;
import com.contaazul.coverage.pullrequest.Cobertura;
import com.contaazul.coverage.pullrequest.CoberturaImpl;

public class LineAnalyserTest {

	private LineAnalyser analyser;

	@Before
	public void init() {
		analyser = new LineAnalyser();
	}

	@Test
	public void testNullLineCoverage() throws Exception {
		final LineCoverager coverager = mock( LineCoverager.class );
		final Cobertura cobertura = spy( new CoberturaImpl() );
		when( coverager.getLineCoverage( anyInt() ) ).thenReturn( null );
		analyser.analyse( 1, coverager, cobertura );
		verify( cobertura, never() ).incrementCoverage( anyDouble() );
		verify( cobertura, never() ).incrementCoverage( anyInt(), anyDouble() );
		assertEquals( 100.0, cobertura.getCoverage(), 0.01 );
	}

	@Test
	public void testLineCoverage() throws Exception {
		final LineCoverager coverager = mock( LineCoverager.class );
		final Cobertura cobertura = spy( new CoberturaImpl() );
		when( coverager.getLineCoverage( anyInt() ) ).thenReturn( 90.0 );
		analyser.analyse( 1, coverager, cobertura );
		verify( cobertura, times( 1 ) ).incrementCoverage( anyDouble() );
		verify( cobertura, times( 1 ) ).incrementCoverage( anyInt(), anyDouble() );
		assertEquals( 1, cobertura.getLastLine() );
		assertEquals( 90.0, cobertura.getCoverage(), 0.01 );
	}
}
