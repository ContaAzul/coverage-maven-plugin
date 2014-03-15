package com.contaazul.coverage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;

import org.junit.Test;

import com.contaazul.coverage.cobertura.CoberturaException;
import com.contaazul.coverage.cobertura.Parser;
import com.contaazul.coverage.cobertura.entity.Coverage;

public class ParserTest {

	@Test
	public void testCreateInstance() throws Exception {
		assertNotNull( new Parser() );
	}

	@Test(expected = CoberturaException.class)
	public void testParseNullFile() throws Exception {
		assertNull( new Parser().parse( null ) );
	}

	@Test
	public void testParse() throws Exception {
		Coverage coverage = new Parser().parse( new File( "src/test/resources/coverage.xml" ) );

		assertNotNull( coverage );
		assertNotNull( coverage.getPackages() );
		assertFalse( coverage.getPackages().isEmpty() );
	}
}
