package com.v2solve.commons.base.restmodel;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a request/response data structure for paging information to be passed in API calls
 * and returned as appropriate.
 * @author Saurin Magiawala
 *
 */
@Data
@NoArgsConstructor
public class PagingInformation implements java.io.Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// may be set in request or response
	int currentPage;
	
	// usually set in requests..
	int pageSize;
	
	// may be set in request or response..
	int noOfPages;
	
	// may be set in the response
	int totalNoOfRecords;
	
	// may be set in the response.
	int recordsReturned;
}
