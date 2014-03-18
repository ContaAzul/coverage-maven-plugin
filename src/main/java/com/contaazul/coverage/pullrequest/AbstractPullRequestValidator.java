package com.contaazul.coverage.pullrequest;

import static com.contaazul.coverage.github.Messages.BUILD;
import static com.contaazul.coverage.github.Messages.PULL_REQUEST;
import static com.contaazul.coverage.pullrequest.CoberturaUtils.addTo;
import static com.contaazul.coverage.pullrequest.CoberturaUtils.map;

import java.util.List;

import org.eclipse.egit.github.core.CommitFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.contaazul.coverage.cobertura.ClazzMapper;
import com.contaazul.coverage.cobertura.ClazzMapperImpl;
import com.contaazul.coverage.cobertura.LineCoverager;
import com.contaazul.coverage.cobertura.LineCoveragerImpl;
import com.contaazul.coverage.cobertura.entity.Clazz;
import com.contaazul.coverage.git.LinePositioner;
import com.contaazul.coverage.git.PatchLinePositioner;
import com.contaazul.coverage.github.GithubService;
import com.contaazul.coverage.maven.CoverageMavenProject;
import com.contaazul.coverage.pullrequest.analyser.FileAnalyser;
import com.google.common.collect.Lists;

// XXX this class has too much responsibility.
public abstract class AbstractPullRequestValidator implements PullRequestValidator {
	private static final Logger logger = LoggerFactory.getLogger( PullRequestValidator.class );
	private final int minCoverage;
	private final GithubService gh;
	private final ChunkBlammer blammer;

	public AbstractPullRequestValidator(GithubService gh, int minCoverage) {
		this.gh = gh;
		this.minCoverage = minCoverage;
		this.blammer = new ChunkBlammer( gh, minCoverage );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.contaazul.coverage.IPullRequestValidator#validate()
	 */
	@Override
	public void validate(CoverageMavenProject project) {
		final ClazzMapper mapper = new ClazzMapperImpl( project );
		final List<Cobertura> coberturas = Lists.newArrayList();
		for (CommitFile file : gh.getPullRequestCommitFiles())
			addTo( coberturas, getCobertura( mapper, file ) );
		checkTotalCoverage( map( coberturas ), project );
	}

	private Cobertura getCobertura(ClazzMapper mapper, CommitFile file) {
		logger.debug( "File: " + file.getFilename() );
		if (file.getPatch() == null)
			return nullCobertura();

		final LinePositioner positioner = createLinePositioner( file );
		final LineCoverager coverager = createLineCoverager( mapper, file );
		if (positioner == null || coverager == null)
			return nullCobertura();

		return new FileAnalyser( blammer, coverager, positioner )
				.analyse( file, positioner, coverager );
	}

	private void checkTotalCoverage(Cobertura cobertura, CoverageMavenProject project) {
		logger.info( String.format( BUILD, project.getArtifactId(), cobertura.getCoverage(), minCoverage ) );
		if (cobertura.isLowerThan( minCoverage ))
			blameLowCoverage( cobertura, project );
	}

	private void blameLowCoverage(Cobertura cobertura, CoverageMavenProject project) {
		gh.createComment( format( cobertura, project ) );
		if (breakOnLowCoverage())
			throw new UndercoveredException( project, cobertura, minCoverage );
	}

	private String format(Cobertura cobertura, CoverageMavenProject project) {
		return String.format( PULL_REQUEST, project.getArtifactId(), cobertura.getCoverage(),
				minCoverage );
	}

	protected abstract boolean breakOnLowCoverage();

	private Cobertura nullCobertura() {
		logger.debug( "Null Cobertura" );
		return new NullCobertura();
	}

	private LineCoverager createLineCoverager(ClazzMapper mapper, CommitFile commitFile) {
		logger.debug( "filename: " + commitFile.getFilename() );
		Clazz clazz = mapper.getClazz( commitFile.getFilename() );
		if (clazz == null)
			return null;
		return new LineCoveragerImpl( clazz );
	}

	private LinePositioner createLinePositioner(CommitFile cf) {
		return new PatchLinePositioner( cf.getPatch() );
	}
}
