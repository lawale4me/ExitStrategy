/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scantranx.core;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author PHI
 */
public class RepositoryManager
{
    private static EntityManagerFactory factory;    
    static 
    {
        factory = Persistence.createEntityManagerFactory("ExitStrategyPU");
    }
    
    public static EntityManager getManager()
    {
        return factory.createEntityManager();
    }
}
