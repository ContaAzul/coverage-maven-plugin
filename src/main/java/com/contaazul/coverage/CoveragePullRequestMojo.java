package com.contaazul.coverage;

import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import com.contaazul.coverage.github.GithubRepo;
import com.contaazul.coverage.pullrequest.PullRequestValidator;
import com.contaazul.coverage.pullrequest.PullRequestValidatorBuilder;

/**
 * 
 * @goal publish
 * @author carlos
 * 
 */
public class CoveragePullRequestMojo extends AbstractMojo {
	/**
	 * Set OAuth2 token
	 * 
	 * @parameter property="github.oauth2"
	 */
	private String oauth2;

	/**
	 * Github pull request ID
	 * 
	 * @parameter property="github.pullRequestId"
	 */
	private int pullRequestId;

	/**
	 * Github repository owner
	 * 
	 * @parameter property="github.repositoryOwner"
	 */
	private String repositoryOwner;

	/**
	 * Github repository name
	 * 
	 * @parameter property="github.repositoryName"
	 */
	private String repositoryName;

	/**
	 * The minimum % coverage accepted.
	 * 
	 * @parameter property="minCoverage"
	 */
	private int minimumCoverage;

	/**
	 * Maven project info.
	 * 
	 * @parameter property="project"
	 * @required
	 * @readonly
	 */
	private MavenProject project;

	/**
	 * The projects in the reactor.
	 * 
	 * @parameter property="reactorProjects"
	 * @readonly
	 */
	@SuppressWarnings("unused")
	private List<MavenProject> reactorProjects;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		PullRequestValidator validator = new PullRequestValidatorBuilder()
				.oauth2( oauth2 )
				.pullRequest( pullRequestId )
				.repository( new GithubRepo( repositoryName, repositoryOwner ) )
				.minCoverage( minimumCoverage )
				.project( project )
				.build();
		validator.validate();
	}
}
