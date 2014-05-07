package com.contaazul.coverage.pullrequest.analyser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.egit.github.core.CommitFile;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.contaazul.coverage.cobertura.LineCoverager;
import com.contaazul.coverage.git.LinePositioner;
import com.contaazul.coverage.pullrequest.ChunkBlammer;
import com.contaazul.coverage.pullrequest.Cobertura;

public class FileAnalyserTest {
	@Mock
	private ChunkBlammer blammer;
	@Mock
	private LineCoverager coverager;
	@Mock
	private LinePositioner positioner;
	@Mock
	private CommitFile commitFile;

	private FileAnalyser analyser;

	@Before
	public void init() {
		initMocks(this);
		this.analyser = new FileAnalyser(blammer, coverager, positioner);
	}

	@Test
	public void testNullFileCoverage() throws Exception {
		when(positioner.getChunks()).thenReturn(
				new ArrayList<Map<Integer, Integer>>());
		Cobertura cov = analyser.analyse(commitFile, positioner, coverager);
		assertEquals(cov.getCoverage(), 100.0, 0.01);
	}

	@Test
	public void testNotNullFileCoverage() throws Exception {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, 10);
		when(coverager.getLineCoverage(1)).thenReturn(10.0);
		when(positioner.getChunks()).thenReturn(Arrays.asList(map));
		Cobertura cov = analyser.analyse(commitFile, positioner, coverager);
		assertEquals(cov.getCoverage(), 10.0, 0.01);
	}
}
