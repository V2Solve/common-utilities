package com.v2solve.commons.base.restmodel;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * This class represents a Request Status information.  It can be extended by other classes, but has common
 * structure such as a success message/success code, and error message/error code if operations failed.
 * @author Saurin Magiawala
 *
 */
@Data
@NoArgsConstructor
public class RequestStatusInformation implements java.io.Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String statusCode;
	String statusMessage;
	
	public static final String standardSuccessCode = "Succeeded";
	public static final String standardFailureCode = "Failed";
	public static final String standardSuccessMessage = "Operation Succeeded";
	public static final String standardFailureMessage = "Operation Failed";
	
	public static RequestStatusInformation SUCCESS = new RequestStatusInformation(standardSuccessCode,standardSuccessMessage);
	public static RequestStatusInformation FAILURE = new RequestStatusInformation(standardFailureCode,standardFailureMessage);
	

	public static RequestStatusInformation success (String successMessage)
	{
		return new RequestStatusInformation(standardSuccessCode, successMessage);
	}
	
	public static RequestStatusInformation failure (String failureMessage)
	{
		return new RequestStatusInformation(standardFailureCode, failureMessage);
	}
	
	public RequestStatusInformation (String statusCode,String statusMessage)
	{
		this.statusCode =statusCode;
		this.statusMessage = statusMessage;
	}
	
}
