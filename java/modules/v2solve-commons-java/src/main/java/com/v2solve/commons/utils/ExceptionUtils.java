package com.v2solve.commons.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Some Exception Utils..
 * @author Saurinya
 *
 */
public class ExceptionUtils 
{

	public static String getTrace (Throwable e)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}
}
