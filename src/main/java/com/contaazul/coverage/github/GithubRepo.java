package com.contaazul.coverage.github;

import java.io.IOException;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;

public class GithubRepo {
	private final String name;
	private final String owner;
	private Repository repo;

	public GithubRepo(String name, String owner) {
		super();
		this.name = name;
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public String getOwner() {
		return owner;
	}

	public Repository connect(RepositoryService service) {
		try {
			if (repo == null)
				repo = service.getRepository( owner, name );
			return repo;
		} catch (IOException e) {
			throw new GitHubException( "Failed to get repository", e );
		}
	}

	@Override
	public String toString() {
		return "GithubRepo [name=" + name + ", owner=" + owner + "]";
	}
}
