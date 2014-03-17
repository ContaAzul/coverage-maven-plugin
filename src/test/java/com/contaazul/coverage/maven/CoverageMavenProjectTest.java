package com.contaazul.coverage.maven;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.File;

import org.apache.maven.model.Build;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.contaazul.coverage.cobertura.entity.Coverage;

public class CoverageMavenProjectTest {

	@Mock
	private MavenProject project;
	@Mock
	private Build build;

	@Before
	public void init() {
		initMocks( this );
	}

	@Test
	public void getCoverage() throws Exception {
		when( build.getSourceDirectory() ).thenReturn( "target/tmp/src/main/java" );
		when( build.getDirectory() ).thenReturn( "target/tmp/target" );
		when( project.getBasedir() ).thenReturn( new File( "target/tmp" ) );
		when( project.getBuild() ).thenReturn( build );
		Coverage coverage = new CoverageMavenProject( project ).getCoverage();
		assertFalse( coverage.getPackages().isEmpty() );
	}

	@Test
	public void getCoverageNoCoverageXml() throws Exception {
		when( build.getSourceDirectory() ).thenReturn( "target/tmpp/src/main/java" );
		when( build.getDirectory() ).thenReturn( "target/tmpp/target" );
		when( project.getBasedir() ).thenReturn( new File( "target/tmpp" ) );
		when( project.getBuild() ).thenReturn( build );
		Coverage coverage = new CoverageMavenProject( project ).getCoverage();
		assertTrue( coverage.getPackages().isEmpty() );
	}
}
