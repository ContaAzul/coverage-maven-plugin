package com.contaazul.coverage.github;

import java.util.Map;

import org.eclipse.egit.github.core.CommitFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class PullRequestSHARetriever {
	private static final Logger logger = LoggerFactory
			.getLogger(PullRequestSHARetriever.class);
	private final GithubService service;
	private Map<String, String> shas;

	public PullRequestSHARetriever(GithubService service) {
		super();
		this.service = service;
	}

	private Map<String, String> getFilesSha() {
		if (shas != null)
			return shas;
		shas = Maps.newHashMap();
		for (CommitFile commitFile : service.getFiles())
			addSHA(commitFile);
		return shas;
	}

	private void addSHA(CommitFile commitFile) {
		String file = commitFile.getFilename();
		String sha = commitFile.getBlobUrl().replaceAll(".*blob/", "")
				.replaceAll("/.*", "");
		logger.debug("FILENAME: " + file + " SHA " + sha);
		shas.put(file, sha);
	}

	public String get(CommitFile cf) {
		return get(cf.getFilename());
	}

	private String get(String filename) {
		return getFilesSha().get(filename);
	}
}
