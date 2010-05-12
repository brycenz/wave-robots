package com.wavenz.robots.mvc.model;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

/**
 * @author Bryce Ewing
 * @version 0.1
 */
public abstract class AbstractJdoProxy<N> extends AbstractProxy<N> {
    private static final PersistenceManagerFactory persistenceManagerFactory = JDOHelper.getPersistenceManagerFactory("transactions-optional");

    protected AbstractJdoProxy(Class proxyClass) {
        super(proxyClass);
    }

    protected PersistenceManager getPersistenceManager() {
        return persistenceManagerFactory.getPersistenceManager();
    }
}
