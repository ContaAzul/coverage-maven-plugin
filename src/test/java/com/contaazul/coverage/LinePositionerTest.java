package com.contaazul.coverage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.contaazul.coverage.git.LinePositioner;
import com.contaazul.coverage.git.PatchLinePositioner;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class LinePositionerTest {

	private LinePositioner positioner;

	@Before
	public void init() throws IOException {
		final File patchFile = new File("src/test/resources/2.patch");
		final String patch = Files.toString(patchFile, Charsets.UTF_8);
		positioner = new PatchLinePositioner(patch);

	}

	@Test
	public void getChunks() throws Exception {
		assertFalse(positioner.getChunks().isEmpty());
		assertEquals(7, positioner.getChunks().size());
	}

	@Test
	public void toPosition() throws Exception {
		assertEquals(10, positioner.toPosition(7));
	}

	@Test
	public void toPositionInvalidLine() throws Exception {
		assertEquals(-1, positioner.toPosition(213));
	}
}
