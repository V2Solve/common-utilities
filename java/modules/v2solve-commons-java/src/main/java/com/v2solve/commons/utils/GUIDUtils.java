package com.v2solve.commons.utils;

import java.util.UUID;

/**
 * Some Utitlity to deal with GUIDs
 * @author Saurinya
 *
 */
public class GUIDUtils 
{
	public static String getGUID ()
	{
		return UUID.randomUUID().toString();
	}
}
