package com.v2solve.commons.jpautils;

import com.v2solve.commons.exceptions.V2solveRuntimeException;

public class DatalogicValidationException extends V2solveRuntimeException 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DatalogicValidationException() {
		// 
	}

	public DatalogicValidationException(String message) {
		super(message);
		// 
	}

	public DatalogicValidationException(Throwable cause) {
		super(cause);
		// 
	}

	public DatalogicValidationException(String message, Throwable cause) {
		super(message, cause);
		// 
	}

	public DatalogicValidationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// 
	}
}
