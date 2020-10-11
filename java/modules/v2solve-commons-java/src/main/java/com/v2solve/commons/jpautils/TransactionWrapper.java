package com.v2solve.commons.jpautils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


/**
 * This is a helper class for controlling transaction in a method.
 * You start a transaction by creating an instance of this class. If you successfully updated the database, call the success method on it
 * and then in some finally block call the commit on it. If you call commit without marking it success, then it assumes a failure and rolls back,
 * during the commit call, instead of committing.
 * 
 * @author Saurin Magiawala
 *
 */
public class TransactionWrapper 
{
	EntityManager em = null;
	EntityTransaction t = null;
	boolean toFlush = false;
	boolean flushed = false;
	boolean success = false;
	
	/**
	 * It starts a new transaction, if one is not present, if one is present, it joins it.
	 * @param em
	 */
	public TransactionWrapper(EntityManager em) 
	{
		this.em = em;
		t = em.getTransaction();
		if (!t.isActive())
		{
			t.begin();
			toFlush = true;
		}
	}
	
	/**
	 * Marks the transaction as success.
	 * It will be committed the next time commit is called.
	 * If this method is not called before commit is called, it always assumes that the transaction failed,
	 * and so will rollback during the commit.
	 */
	public void success ()
	{
		success = true;
	}

	
	/**
	 * Marks the transaction as successfull and also commits it.
	 */
	public void successAndCommit ()
	{
		success = true;
		commit ();
	}
	
	
	/**
	 * If marked as success, this will commit the transaction,
	 * otherwise this will rollback the transaction. If already done once,
	 * it will simply exit and not do it anymore.
	 */
	public void commit() 
	{
		if (!flushed)
		{
			if (toFlush)
			{
				if (success)
					t.commit();
				else
					t.rollback();
				flushed = true;
			}
		}
	}
}
