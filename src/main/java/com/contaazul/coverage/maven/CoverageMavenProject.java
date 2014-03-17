package com.contaazul.coverage.maven;

import java.io.File;

import org.apache.maven.project.MavenProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.contaazul.coverage.cobertura.Parser;
import com.contaazul.coverage.cobertura.entity.Coverage;

public class CoverageMavenProject {
	private static final Logger logger = LoggerFactory
			.getLogger( CoverageMavenProject.class );
	private static final String COVERAGE_XML = "/site/cobertura/coverage.xml";

	private final MavenProject project;

	public CoverageMavenProject(MavenProject project) {
		this.project = project;
	}

	public Coverage getCoverage() {
		final Parser parser = new Parser();
		final File coverage = new File( getBuildDirectory() + COVERAGE_XML );
		if (!coverage.exists())
			return new Coverage();
		return parser.parse( coverage );
	}

	public String getSrcFolder() {
		final String projectFolder = project.getBasedir().getAbsolutePath();
		final String srcFolder = project.getBuild().getSourceDirectory();
		final String srcRelativeFolder = srcFolder.substring( projectFolder
				.length() + 1 );
		logger.debug( "Project Folder: " + projectFolder +
				"\nSource Folder: " + srcFolder +
				"\nRelative SRC folder: " + srcRelativeFolder );
		return srcRelativeFolder;
	}

	public String getBuildDirectory() {
		logger.debug( "Build dir: " + project.getBuild().getDirectory() );
		return project.getBuild().getDirectory();
	}
}
