package com.v2solve.commons.exceptions;

/**
 * A common base exception so that it can be distinguished from other Runtime Exceptions in java.
 * @author Saurinya
 *
 */
public class V2solveRuntimeException extends RuntimeException 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public V2solveRuntimeException() {
		
	}

	public V2solveRuntimeException(String message) {
		super(message);
		
	}

	public V2solveRuntimeException(Throwable cause) {
		super(cause);
		
	}

	public V2solveRuntimeException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public V2solveRuntimeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}