package com.v2solve.commons.base.restmodel;


import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * A Base Request which represents the base from all requests will originate for the API.
 * @author Saurin Magiawala
 *
 */
@Data
@NoArgsConstructor
public class BaseRequest implements java.io.Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Unique client id which represents the client on behalf of which the call is being made.
	 */
	String clientId;
	
	/**
	 * Additional groups that the client must be assumed to belong to 
	 *
	 */
	List<String> groups;

	/**
	 * data about how information returned should be paginated. - helpful in search and responses
	 */
	PagingInformation pagingInfo;
	
	
	/**
	 * data about how information returned should be sorted - helpful in searches.
	 */
	SortingInformation sortingInfo;
}
