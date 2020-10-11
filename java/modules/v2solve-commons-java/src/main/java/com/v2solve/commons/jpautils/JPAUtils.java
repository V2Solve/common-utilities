package com.v2solve.commons.jpautils;


import java.lang.reflect.InvocationTargetException;
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
	
}
