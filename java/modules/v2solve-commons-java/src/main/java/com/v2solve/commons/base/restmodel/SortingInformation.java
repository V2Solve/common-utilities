package com.v2solve.commons.base.restmodel;

import java.util.List;

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
public class SortingInformation implements java.io.Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * List of Sorts that need to be applied to a query..
	 */
	List<SortInfo> sorts;
}
