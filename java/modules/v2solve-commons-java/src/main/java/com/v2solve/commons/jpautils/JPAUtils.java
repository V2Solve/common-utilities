package com.v2solve.commons.jpautils;


import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class JPAUtils 
{
	
	/**
	 * Creates an Object using the Entity Manager.
	 * @param <T>
	 * @param em
	 * @param object
	 */
	public static <T> void createObject(EntityManager em, T object) 
	{
		em.persist(object);
	}

	/**
	 * Deletes the object identified by the key id and of the particular entity class of type T
	 * @param <T>
	 * @param em
	 * @param id
	 * @param clzz
	 * @return
	 */
	public static <T> T deleteObject(EntityManager em, Object id, Class<T> clzz) 
	{
		// Lets find the object first..
		T object = em.find(clzz, id);
		
		if (object != null)
		{
			em.remove(object);
			em.flush();
		}
		
		return object;
	}


	/**
	 * Builds an In Value expression, setting the values inside the expression.
	 * @param <T>
	 * @param inExpression
	 * @param collection
	 * @return
	 */
    public static<T> In<T> buildInvalues (In<T> inExpression,List<T> collection)
    {
    	if (collection != null)
    	{
    		for (T obj : collection)
    		{
    			inExpression = inExpression.value(obj);
    		}
    	}
    	
    	return inExpression;
    }	


	public static void updateObject(EntityManager em, Object existingObject) 
	{
		if (em.contains(existingObject))
			em.merge(existingObject);
		else
			em.persist(existingObject);
		em.flush();
		
	}

	public static <T> T findByPrimaryKey(EntityManager em, Class<T> clzz, Object id) 
	{
		return em.find(clzz, id);
	}

	/**
	 * Finds all objects of the entity represented by the clzz, with a property identifier, having value idValue
	 * @param <T>
	 * @param em
	 * @param clzz
	 * @param identifier
	 * @param idValue
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static <T> List<T> findObjects (EntityManager em,Class<T> clzz, String identifier,Object idValue) 
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clzz);
		Root<T> root = cq.from(clzz);
		
		Path<?> identifierPath = root.get(identifier);
		Predicate whereClause = cb.equal(identifierPath, idValue);
		cq.where(whereClause);
		TypedQuery<T> tq = em.createQuery(cq);
		
		List<T> listOfObjs = tq.getResultList();
		return listOfObjs;
	}

	/**
	 * Returns all records in the table..
	 * @param <T>
	 * @param em
	 * @param clzz
	 * @return - All records.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static <T> List<T> findAll (EntityManager em,Class<T> clzz) 
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clzz);
		cq.from(clzz);
		TypedQuery<T> tq = em.createQuery(cq);
		List<T> listOfObjs = tq.getResultList();
		return listOfObjs;
	}

	
	/**
	 * Creates in clause based on values found in the limitData if any and returns the predicate duly 'ANDED'
	 * @param <T> - The type of RootObject
	 * @param <K> - the type of limiting property list..
	 * @param cb - CriteriaBuilder.
	 * @param root - the Select Root. 
	 * @param limitData - if there is any criteria using which the records should be limited.
	 * @param currentPredicate - if there is already a current predicate.
	 * @return
	 */
	public static <T,K> Predicate addLimitingClauseForProperties (CriteriaBuilder cb,Root<T> root,HashMap<String, List<K>> limitData,Predicate currentPredicate)
	{
		Predicate finalPredicate = null;
		
		if (limitData != null && !limitData.isEmpty())
		{
			for (String property: limitData.keySet())
			{
				Path<K> propPath = root.get(property);
				List<K> values = limitData.get(property);
				if (values != null && !values.isEmpty())
				{
					In<K> inClause = cb.in(propPath);
					Predicate inObjs = buildInvalues(inClause, values);
					if (finalPredicate == null)
						finalPredicate = inObjs;
					else
						finalPredicate = cb.and(finalPredicate,inObjs);
				}
			}
		}
			
		if (currentPredicate != null)
				finalPredicate = cb.and(currentPredicate,finalPredicate);
			else
				currentPredicate = finalPredicate;
		
		return currentPredicate;
	}

	/**
	 * Finds a Unique Object of the entity represented by the clzz, with a property identifier, having value idValue.
	 * It throws an exception if 0 instances found, or if multiple instances found. The instance found must exactly be 1.
	 * @param <T>
	 * @param em
	 * @param clzz
	 * @param identifier
	 * @param idValue
	 * @return
	 */
	public static <T> T findObject (EntityManager em,Class<T> clzz, String identifier,Object idValue) 
	{
		List<T> listOfObjs = JPAUtils.findObjects(em, clzz, identifier, idValue);
		if (listOfObjs == null || listOfObjs.isEmpty())
			throw new DatalogicValidationException("Object with identifier: " + idValue + " not found.");
		
		if (listOfObjs.size() > 1)
			throw new DatalogicValidationException("Multiple Objects with identifier: " + idValue + " found.");
		
		T obj = listOfObjs.get(0);
		
		return obj;
	}
	
	/**
	 * Finds a Unique Object of the entity represented by the clzz, with a property identifier, having value idValue.
	 * It returns null, if it does not find an object. 
	 * @param <T>
	 * @param em
	 * @param clzz
	 * @param identifier
	 * @param idValue
	 * @return
	 */
	public static <T> T findObjectReturnNull (EntityManager em,Class<T> clzz, String identifier,String idValue) 
	{
		List<T> listOfObjs = JPAUtils.findObjects(em, clzz, identifier, idValue);
		if (listOfObjs == null || listOfObjs.isEmpty())
			return null;
		if (listOfObjs.size() > 1)
			throw new DatalogicValidationException("Multiple Objects with identifier: " + idValue + " found.");

		T obj = listOfObjs.get(0);
		return obj;
	}

}
