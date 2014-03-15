package com.contaazul.coverage;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.File;
import java.io.IOException;

import org.apache.maven.model.Build;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.contaazul.coverage.cobertura.CoverageException;
import com.contaazul.coverage.github.GithubRepo;
import com.contaazul.coverage.pullrequest.PullRequestValidator;
import com.contaazul.coverage.pullrequest.PullRequestValidatorBuilder;
import com.google.common.io.Files;

public class PullRequestValidatorBuilderTest {

	@Mock
	private MavenProject project;
	@Mock
	private Build build;

	private GithubRepo repo;

	@Before
	public void setup() throws IOException {
		initMocks( this );
		repo = new GithubRepo( "caarlos0", "coverage-maven-plugin" );
		File folder = new File( "target/tmp/target/site/cobertura" );
		folder.mkdirs();
		Files.copy( new File( "src/test/resources/coverage.xml" ), new File( folder.getAbsolutePath()
				+ "/coverage.xml" ) );

		when( build.getDirectory() ).thenReturn( new File( "target/tmp/target/" ).getAbsolutePath() );
		when( build.getSourceDirectory() ).thenReturn( new File( "target/tmp/src" ).getAbsolutePath() );
		when( project.getBasedir() ).thenReturn( new File( "target/tmp" ) );
		when( project.getBuild() ).thenReturn( build );
	}

	@Test(expected = CoverageException.class)
	public void buildInvalid() throws Exception {
		new PullRequestValidatorBuilder()
				.build();
	}

	@Test
	public void testValid() throws Exception {
		PullRequestValidator validator = new PullRequestValidatorBuilder()
				.minCoverage( 70 )
				.oauth2( "not_really_an_oauth_token" )
				.pullRequest( 2 )
				.repository( repo )
				.project( project )
				.build();
		assertNotNull( validator );

	}
}
