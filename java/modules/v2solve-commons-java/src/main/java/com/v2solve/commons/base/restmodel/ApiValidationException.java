package com.v2solve.commons.base.restmodel;

import com.v2solve.commons.exceptions.V2solveRuntimeException;

/**
 * A Validation exception which can/should be thrown when there is a validation error to perform the action in a context.
 * @author Saurin Magiawala
 *
 */
public class ApiValidationException extends V2solveRuntimeException 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ApiValidationException() {
		// 
	}

	public ApiValidationException(String message) {
		super(message);
		// 
	}

	public ApiValidationException(Throwable cause) {
		super(cause);
		// 
	}

	public ApiValidationException(String message, Throwable cause) {
		super(message, cause);
		// 
	}

	public ApiValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// 
	}

}
