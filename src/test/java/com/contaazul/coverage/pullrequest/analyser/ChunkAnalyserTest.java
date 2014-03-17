package com.contaazul.coverage.pullrequest.analyser;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.egit.github.core.CommitFile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.contaazul.coverage.cobertura.LineCoverager;
import com.contaazul.coverage.git.LinePositioner;
import com.contaazul.coverage.pullrequest.ChunkBlammer;
import com.contaazul.coverage.pullrequest.Cobertura;
import com.google.common.collect.Maps;

public class ChunkAnalyserTest {

	@Mock
	private ChunkBlammer blammer;
	@Mock
	private LineCoverager coverager;
	@Mock
	private LinePositioner positioner;

	private ChunkAnalyser analyser;

	@Before
	public void init() {
		initMocks( this );
		this.analyser = new ChunkAnalyser( blammer, coverager, positioner );
	}

	@Test
	public void testAnalyseEmptyChunk() throws Exception {
		CommitFile cf = mock( CommitFile.class );
		Cobertura cov = analyser.analyse( new HashMap<Integer, Integer>(), cf );
		assertEquals( 100.0, cov.getCoverage(), 0.01 );
	}

	@Test
	public void testAnalyse() throws Exception {
		CommitFile cf = mock( CommitFile.class );
		Map<Integer, Integer> chunk = Maps.newHashMap();
		chunk.put( 1, 10 );
		chunk.put( 2, 80 );
		chunk.put( 3, 100 );
		chunk.put( 4, 56 );
		doReturn( 10 ).when( coverager ).getLineCoverage( eq( 1 ) );
		doReturn( 80 ).when( coverager ).getLineCoverage( eq( 2 ) );
		doReturn( 100 ).when( coverager ).getLineCoverage( eq( 3 ) );
		doReturn( 56 ).when( coverager ).getLineCoverage( eq( 4 ) );
		Cobertura cov = analyser.analyse( chunk, cf );
		assertEquals( 61.5, cov.getCoverage(), 0.01 );
	}
}
