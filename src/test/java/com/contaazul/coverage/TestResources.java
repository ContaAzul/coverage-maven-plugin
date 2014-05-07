package com.contaazul.coverage;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestResources {
	private static Logger logger = LoggerFactory.getLogger(TestResources.class);
	public static final String XML = "/coverage.xml";
	public static final String COVERAGEXML_RESOURCE = "src/test/resources"
			+ XML;
	public static final String TARGET_FOLDER = "target/tmp/target/site/cobertura";
	public static final String COVERAGE_XML_FILE = TARGET_FOLDER + XML;

	public static void setup() {
		File folder = setupFolder();
		setupXml(folder);
	}

	private static void setupXml(File folder) {
		File tmpCoverageXml = new File(folder.getAbsolutePath() + XML);
		if (!tmpCoverageXml.exists())
			copyXml(tmpCoverageXml);
	}

	private static void copyXml(File tmpCoverageXml) {
		try {
			Files.copy(new File(COVERAGEXML_RESOURCE), tmpCoverageXml);
		} catch (IOException e) {
			logger.error("Failed to setup files", e);
		}
	}

	private static File setupFolder() {
		File folder = new File(TARGET_FOLDER);
		if (!folder.exists())
			folder.mkdirs();
		return folder;
	}
}
