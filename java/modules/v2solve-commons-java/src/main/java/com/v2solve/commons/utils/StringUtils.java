package com.v2solve.commons.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StringUtils 
{

	/**
	 * Returns true if the string is null, or whitespace, or zero length.
	 * @param appIdentifier
	 * @return
	 */
	public static boolean isNullOrZeroLength(String appIdentifier) 
	{
		if (appIdentifier == null || appIdentifier.trim().length()<=0)
			return true;
		else
			return false;
	}

	
	/**
	 * Captures the trace of the throwable in a string, and returns it back.
	 * @param e
	 * @return
	 */
	public static String traceString(Throwable e) 
	{
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}

	/**
	 * Truncates a string to the desired length (useful for DB inserts)
	 * @param trc
	 * @param length
	 * @param truncString - the string to be inserted at the end, if truncation.
	 * @return
	 */
	public static String truncateTo (String trc,int length,String truncString)
	{
		if (trc == null) return trc;
		
		if (length <= 0 ) 
			return "";
		
		int over = trc.length() - length;
		
		if (over <= 0) 
			return trc;
		
		String toRet = trc;
		
		int subindex = length - truncString.length();
		
		if (subindex <= 0)
		{
			// Lets try just with an 1.
			subindex = length - 1;
			
			if (subindex <= 0)
			{
				toRet = trc.substring(0,length);
			}
			else
			{
				toRet = trc.substring(0,subindex) + "-";
			}
		}
		else 
		{
			toRet = trc.substring(0,subindex) + truncString;
		}
		
		if (toRet != null && toRet.length() != length)
		{
			throw new RuntimeException("StringUtils.java: truncateTo: Program Logic Error, could not truncate the string: '" + trc + "'  to a length of " + length + " bytes! instead it is: " + toRet.length() + " in length: " + toRet);
		}
		
		return toRet;
	}
}
