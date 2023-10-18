package com.fabrick.exception;

public class ApiException extends RuntimeException{

	public ApiException(Exception e) {
		super(e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
