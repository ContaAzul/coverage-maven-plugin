package com.contaazul.coverage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.contaazul.coverage.pullrequest.Cobertura;
import com.contaazul.coverage.pullrequest.NullCobertura;

public class NullCoberturaTest {
	private Cobertura cobertura;

	@Before
	public void init() {
		cobertura = new NullCobertura();
	}

	@Test
	public void testNullCobertura() throws Exception {
		cobertura.incrementCoverage(10.0);
		cobertura.incrementCoverage(10, 30.0);
		assertEquals(0.0, cobertura.getCoverage(), 0.0001);
		assertEquals(-1, cobertura.getLastLine());
		assertTrue(cobertura.isLowerThan(42));
		assertFalse(cobertura.isCounted());
	}
}
