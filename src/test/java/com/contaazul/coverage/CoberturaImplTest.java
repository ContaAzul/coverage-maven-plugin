package com.contaazul.coverage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.contaazul.coverage.pullrequest.Cobertura;
import com.contaazul.coverage.pullrequest.CoberturaImpl;

public class CoberturaImplTest {
	private Cobertura cobertura = null;

	@Before
	public void init() {
		cobertura = new CoberturaImpl();
	}

	@Test
	public void testWithoutIncrement() throws Exception {
		assertEquals( 100.0, cobertura.getCoverage(), 0.0001 );
		assertEquals( 0, cobertura.getLastLine() );
		assertTrue( cobertura.isCounted() );
	}

	@Test
	public void testIncrement() throws Exception {
		cobertura.incrementCoverage( 80.0 );
		cobertura.incrementCoverage( 20.0 );
		assertEquals( 50.0, cobertura.getCoverage(), 0.0001 );
		assertEquals( 0, cobertura.getLastLine() );
		assertTrue( cobertura.isCounted() );
	}

	@Test
	public void testIncrementWithLineNumber() throws Exception {
		cobertura.incrementCoverage( 2, 100.0 );
		cobertura.incrementCoverage( 5, 50.0 );
		assertEquals( 75.0, cobertura.getCoverage(), 0.001 );
		assertEquals( 5, cobertura.getLastLine() );
		assertTrue( cobertura.isCounted() );
	}
}
