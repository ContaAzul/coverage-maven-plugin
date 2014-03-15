package com.contaazul.coverage.cobertura;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.contaazul.coverage.cobertura.entity.Coverage;

public class Parser {
	private JAXBContext jax;

	public Parser() {
		this.jax = build();
	}

	public Coverage parse(File file) {
		try {
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
