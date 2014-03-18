package com.contaazul.coverage.cobertura;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.contaazul.coverage.cobertura.entity.Coverage;

public class Parser {
	private static final Logger logger = LoggerFactory.getLogger( Parser.class );
	private JAXBContext jax;

	public Parser() {
		this.jax = build();
	}

	public Coverage parse(File file) {
		try {
			logger.debug( String.format( "Parsing %s...", file.getAbsolutePath() ) );
			return (Coverage) jax.createUnmarshaller().unmarshal( file );
		} catch (Exception e) {
			throw new CoberturaException( "Cannot parse coverage.xml", e );
		}
	}

	private JAXBContext build() {
		try {
			return JAXBContext.newInstance( Coverage.class );
		} catch (JAXBException e) {
			throw new CoberturaException( "Cannot create Parser instance", e );
		}
	}
}
