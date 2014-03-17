package com.contaazul.coverage.pullrequest.analyser;

import org.mockito.Mock;

import com.contaazul.coverage.cobertura.LineCoverager;
import com.contaazul.coverage.git.LinePositioner;
import com.contaazul.coverage.pullrequest.ChunkBlammer;

public class ChunkAnalyserTest {

	@Mock
	private ChunkBlammer blammer;
	@Mock
	private LineCoverager coverager;
	@Mock
	private LinePositioner positioner;

}
