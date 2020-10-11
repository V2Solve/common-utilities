package com.v2solve.commons.base.restmodel;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A base response class which all the response will extend.
 * @author Saurin Magiawala
 *
 */
@Data
@NoArgsConstructor
public class BaseResponse implements java.io.Serializable 
{	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// will specific what the status is. (error, or success depending on the code)
	RequestStatusInformation status;
	
	// In case of paging enabled the number of records returned could be held in here..
	PagingInformation pageInfo;
	
	
	public BaseResponse (RequestStatusInformation rsi)
	{
		this.status = rsi;
	}
	
	public BaseResponse (RequestStatusInformation rsi,PagingInformation pageInfo)
	{
		this.status = rsi;
		this.pageInfo = pageInfo;
	}
	
}
