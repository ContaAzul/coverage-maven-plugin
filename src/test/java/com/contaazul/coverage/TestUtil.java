package com.contaazul.coverage;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

public class TestUtil {
	private static final String XML = "/coverage.xml";
	private static final String COVERAGEXML_RESOURCE = "src/test/resources" + XML;
	private static final String TARGET_FOLDER = "target/tmp/target/site/cobertura";

	public static void createTmpData() throws IOException {
		File folder = new File( TARGET_FOLDER );
		if (!folder.exists())
			folder.mkdirs();
		File tmpCoverageXml = new File( folder.getAbsolutePath() + XML );
		if (!tmpCoverageXml.exists())
			Files.copy( new File( COVERAGEXML_RESOURCE ), tmpCoverageXml );
	}
}
