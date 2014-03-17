package com.contaazul.coverage.pullrequest;

import static com.contaazul.coverage.pullrequest.CoberturaMapper.map;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.egit.github.core.CommitFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.contaazul.coverage.cobertura.ClazzMapper;
import com.contaazul.coverage.cobertura.ClazzMapperImpl;
import com.contaazul.coverage.cobertura.LineCoverager;
import com.contaazul.coverage.cobertura.LineCoveragerImpl;
import com.contaazul.coverage.cobertura.entity.Clazz;
import com.contaazul.coverage.cobertura.entity.Coverage;
import com.contaazul.coverage.git.LinePositioner;
import com.contaazul.coverage.git.OneToOneLinePositioner;
import com.contaazul.coverage.git.PatchLinePositioner;
import com.contaazul.coverage.github.GithubService;
import com.contaazul.coverage.github.PullRequestComment;
import com.contaazul.coverage.github.PullRequestCommitComment;
import com.contaazul.coverage.github.PullRequestSHARetriever;
import com.google.common.collect.Lists;

// XXX this class has too much responsibility.
public abstract class AbstractPullRequestValidator implements PullRequestValidator {
	private static final String MESSAGE = "The new lines added are with %.2f%% of %d%% minimum allowed code coverage.";
	private static final Logger logger = LoggerFactory.getLogger( PullRequestValidator.class );
	private final int minCoverage;
	private final GithubService gh;
	private ClazzMapper mapper;
	private final PullRequestSHARetriever shas;

	public AbstractPullRequestValidator(GithubService gh, Coverage coverage, String srcFolder, int minCoverage) {
		this.gh = gh;
		this.minCoverage = minCoverage;
		this.mapper = new ClazzMapperImpl( coverage, srcFolder );
		this.shas = new PullRequestSHARetriever( gh );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.contaazul.coverage.IPullRequestValidator#validate()
	 */
	@Override
	public void validate() {
		final List<Cobertura> coberturas = Lists.newArrayList();
		for (CommitFile file : gh.getPullRequestCommitFiles())
			add( coberturas, getCobertura( file ) );
		checkTotalCoverage( map( coberturas ) );
	}

	private void add(final List<Cobertura> coberturas, Cobertura cobertura) {
		if (cobertura.isCounted())
			coberturas.add( cobertura );
	}

	private Cobertura getCobertura(CommitFile file) {
		logger.debug( "File: " + file.getFilename() );
		if (file.getPatch() == null)
			return nullCobertura();

		final LinePositioner positioner = createLinePositioner( file );
		final LineCoverager coverager = createLineCoverager( file );
		if (positioner == null || coverager == null)
			return nullCobertura();

		return analyseFile( file, positioner, coverager );
	}

	private Cobertura analyseFile(CommitFile file, final LinePositioner positioner, final LineCoverager coverager) {
		final List<Cobertura> fileCoverage = Lists.newArrayList();
		for (Map<Integer, Integer> chunk : positioner.getChunks())
			add( fileCoverage, analyseChunk( chunk, file, coverager, positioner ) );
		return map( fileCoverage );
	}

	private Cobertura analyseChunk(Map<Integer, Integer> chunk, CommitFile file, LineCoverager coverager,
			LinePositioner positioner) {
		logger.debug( "Analysing chunk " + chunk );
		Cobertura chunkCoverage = getChunkCoverage( chunk, coverager );
		if (chunkCoverage.isLowerThan( minCoverage ))
			blameChunk( file, chunkCoverage.getCoverage(), positioner.toPosition( chunkCoverage.getLastLine() ) );
		return chunkCoverage;
	}

	private Cobertura getChunkCoverage(Map<Integer, Integer> chunk, LineCoverager coverager) {
		final Cobertura chunkCoverage = new CoberturaImpl();
		for (Entry<Integer, Integer> line : chunk.entrySet())
			analyseLine( coverager, chunkCoverage, line.getKey() );
		return chunkCoverage;
	}

	private void analyseLine(LineCoverager coverager, Cobertura chunkCoverage, int line) {
		final Integer lineCoverage = coverager.getLineCoverage( line );
		if (lineCoverage != null)
			chunkCoverage.incrementCoverage( line, lineCoverage );
	}

	private void checkTotalCoverage(Cobertura cobertura) {
		logger.info( String.format( MESSAGE, cobertura.getCoverage(), minCoverage ) );
		if (cobertura.isLowerThan( minCoverage ))
			blameLowCoverage( cobertura );
	}

	private void blameLowCoverage(Cobertura cobertura) {
		gh.createComment( PullRequestComment.MSG + "\n\n"
				+ String.format( UndercoveredException.MSG, cobertura.getCoverage(), minCoverage ) );
		if (breakOnLowCoverage())
			throw new UndercoveredException( cobertura, minCoverage );
	}

	protected abstract boolean breakOnLowCoverage();

	private Cobertura nullCobertura() {
		logger.debug( "Null Cobertura" );
		return new NullCobertura();
	}

	private void blameChunk(CommitFile cf, double coverage, int position) {
		logger.debug( "Blamming chunk on " + cf.getFilename() + " for the coverage " + coverage + " in position "
				+ position );
		String sha = shas.get( cf );
		String filename = cf.getFilename();
		PullRequestCommitComment comment = new PullRequestCommitComment( coverage, minCoverage, sha,
				filename, position );
		gh.createComment( comment );
	}

	private LineCoverager createLineCoverager(CommitFile commitFile) {
		logger.debug( "filename: " + commitFile.getFilename() );
		Clazz clazz = mapper.getClazz( commitFile.getFilename() );
		if (clazz == null)
			return null;
		return new LineCoveragerImpl( clazz );
	}

	private LinePositioner createLinePositioner(CommitFile cf) {
		if ("added".equals( cf.getStatus() ))
			return new OneToOneLinePositioner();
		else
			return new PatchLinePositioner( cf.getPatch() );
	}
}
