package com.v2solve.commons.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Utility methods which use reflection to accomplish tasks.
 * @author Saurin Magiawala
 *
 */
public class ReflectUtils 
{
	
	/**
	 * Creates a copy of type T using default constructor, and copies properties from the src into the dest,
	 * returning the new copy.
	 * @param <T>
	 * @param src
	 * @param clzz
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static <T> T createCopy (T src,Class<T> clzz) 
	throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		T dest = clzz.getConstructor().newInstance();
		copy(src,dest);
		return dest;
	}
	
	
	/**
	 * Copies over data from the src object to the destination object, but only copies top level properties.
	 * Also, will only copy properties that match in both objects. It will try to use get/set methods to copy properties.
	 * @param src
	 * @param dest
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static void copy (Object src,Object dest) 
	throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		// Lets get all the fields from the src object..
		Field fields [] = src.getClass().getDeclaredFields();
		
		for (Field f: fields)
		{
			copyProperty(f.getName(), src, dest);
		}
	}
	
	/**
	 * Transfers the value of a property from the src to dest provided a get method with that property exists on src
	 * and a set method exists in the destination which takes that parameter (returned by get). If success it returns true
	 * otherwise false;
	 * @param propertyName
	 * @param src
	 * @param dest
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static boolean copyProperty (String propertyName,Object src,Object dest) 
	throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		boolean copied = false;
		
		Method mget = getGetPropertyMethodOnObject(src, propertyName, null);
		
		 if (mget != null)
		 {
			// Okay so got a get method..
			Class<?> retType = mget.getReturnType();
			// Lets try to get a set method on the destination..
			Method mset = getSetPropertyMethodOnObject(dest, propertyName, retType);
			if (mset != null)
			{
				// Cool got a set method which matches that of the get method..
				Object valueToXfer = mget.invoke(src, (Object[])null);
				// Lets xfer it..
				mset.invoke(dest, valueToXfer);
				copied = true;
			}
		 }
		
		return copied;
	}
	
	
	/**
	 * Goes through the methods of the dest object class, and if a method matches setPropertyName(paramType)
	 * then it returns that method, otherwise returns null
	 * @param dest
	 * @param propertyName
	 * @param paramType
	 * @return
	 */
	public static Method getSetPropertyMethodOnObject (Object dest,String propertyName,Class<?> paramType)
	{
		String getMethodName = "set" + propertyName.substring(0,1).toUpperCase() + propertyName.substring(1);
		Method [] ms = dest.getClass().getMethods();
		for (Method m: ms)
		{
			if (m.getName().equals(getMethodName))
			{
				// Okay at least the method is the present, now lets check the parameter type..
				Class<?> [] pTypes =  m.getParameterTypes();
				
				if (pTypes == null || pTypes.length > 1)
					continue;
				
				// Okay only one parameter.. lets check the type.
				
				if (pTypes[0].equals(paramType))
				{
					// Okay this is the method..
					return m;
				}
			}
		}
		
		// Method not found.
		return null;
	}
	
	
	/**
	 * Iterates through the methods of the object dest, and if a method matches the signature getPropertyName () with 
	 * return type of returnType (unless it is not passed) it returns that method. If nothing matches returns a null;
	 * @param dest
	 * @param propertyName
	 * @param returnType
	 * @return
	 */
	public static Method getGetPropertyMethodOnObject (Object dest,String propertyName,Class<?> returnType)
	{
		String getMethodName = "get" + propertyName.substring(0,1).toUpperCase() + propertyName.substring(1);
		Method [] ms = dest.getClass().getMethods();
		for (Method m: ms)
		{
			if (m.getName().equals(getMethodName))
			{
				if (returnType != null)
				{
					// Okay at least the method is the present, now lets check the return type..
					Class<?> mRetType = m.getReturnType();
				
					if (mRetType.equals(returnType))
					{
						// Okay this is the method..
						return m;
					}
				}
				else
				{
					return m;
				}
				
			}
		}
		
		// Method not found.
		return null;
	}
	
}
