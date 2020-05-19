package com.pozpl.nerannotator.shared.exceptions;

public class NerWebException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String[] errors;

	public NerWebException(String[] errors) {
		super();
		this.errors = errors;
	}

	public NerWebException(Exception e) {
		super(e);
	}

	public NerWebException(String msg, Exception e) {
		super(msg, e);
	}

	public NerWebException(String msg) {
		super(msg);
	}

	public String[] getErrors() {
		return errors;
	}

}
