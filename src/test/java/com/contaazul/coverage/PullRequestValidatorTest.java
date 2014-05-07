package com.contaazul.coverage;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.eclipse.egit.github.core.CommitFile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.contaazul.coverage.cobertura.entity.Clazz;
import com.contaazul.coverage.cobertura.entity.Coverage;
import com.contaazul.coverage.cobertura.entity.Line;
import com.contaazul.coverage.cobertura.entity.Package;
import com.contaazul.coverage.github.GithubService;
import com.contaazul.coverage.maven.CoverageMavenProject;
import com.contaazul.coverage.pullrequest.BuildBreakerPullRequestValidator;
import com.contaazul.coverage.pullrequest.PullRequestValidator;
import com.contaazul.coverage.pullrequest.UndercoveredException;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class PullRequestValidatorTest {

	private PullRequestValidator validator;

	@Mock
	private GithubService gh;
	@Mock
	private Coverage cov;
	@Mock
	private Package pack;
	@Mock
	private Clazz clazz;

	private String patch;

	@Before
	public void init() throws IOException {
		initMocks(this);
		final File patchFile = new File("src/test/resources/2.patch");
		patch = Files.toString(patchFile, Charsets.UTF_8);

		when(cov.getPackages()).thenReturn(Arrays.asList(pack));
		when(pack.getClasses()).thenReturn(Arrays.asList(clazz));

	}

	@Test(expected = UndercoveredException.class)
	public void testValidateInvalid() throws Exception {
		when(clazz.getLines()).thenReturn(
				Arrays.asList(new Line(10, 20, false, null, null)));
		validator = new BuildBreakerPullRequestValidator(gh, 110, true);
		CoverageMavenProject project = mock(CoverageMavenProject.class);
		when(project.getCoverage()).thenReturn(cov);
		when(project.getSrcFolder()).thenReturn("src/main/java");
		validator.validate(project);
	}

	@Test
	public void testValidate() throws Exception {
		when(pack.getName()).thenReturn("com.contaazul.coverage.pullrequest");
		when(clazz.getLines()).thenReturn(
				Arrays.asList(new Line(10, 20, false, null, null)));
		when(clazz.getName())
				.thenReturn(
						"com.contaazul.coverage.pullrequest.PullRequestValidatorImpl.java");

		CommitFile f = new CommitFile();
		f.setPatch(patch);
		f.setFilename("src/main/java/com/contaazul/coverage/pullrequest/PullRequestValidatorImpl.java");
		when(gh.getPullRequestCommitFiles()).thenReturn(Arrays.asList(f));
		validator = new BuildBreakerPullRequestValidator(gh, 0, true);
		CoverageMavenProject project = mock(CoverageMavenProject.class);
		when(project.getCoverage()).thenReturn(cov);
		when(project.getSrcFolder()).thenReturn("src/main/java");
		validator.validate(project);
	}
}
