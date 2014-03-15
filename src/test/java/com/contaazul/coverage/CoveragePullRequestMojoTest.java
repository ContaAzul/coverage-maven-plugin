package com.contaazul.coverage;

import org.junit.Test;

import com.contaazul.coverage.cobertura.CoverageException;

public class CoveragePullRequestMojoTest {

	@Test(expected = CoverageException.class)
	public void testIt() throws Exception {
		CoveragePullRequestMojo mojo = new CoveragePullRequestMojo();
		mojo.execute();
	}
}
