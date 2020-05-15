package com.pozpl.nerannotator.service.exceptions;

public class NerServiceException extends Exception {

	private String externalMsg;

	private static final long serialVersionUID = 1L;

	public NerServiceException(Exception e) {
		super(e);
	}

	public NerServiceException(Throwable e){
		super(e);
	}

	public NerServiceException(String msg) {
		super(msg);
		this.externalMsg = msg;
	}

	public NerServiceException(String msg, String externalMsg) {
		super(msg);
		this.externalMsg = externalMsg;
	}

	public NerServiceException(String msg, Exception e) {
		super(msg, e);
	}

	public String getExternalMsg() {
		return externalMsg;
	}
}