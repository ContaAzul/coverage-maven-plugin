package com.contaazul.coverage.cobertura;

public class CoberturaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CoberturaException(String msg, Exception e) {
		super(msg, e);
	}
}
