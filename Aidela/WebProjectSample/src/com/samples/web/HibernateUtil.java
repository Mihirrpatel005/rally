package com.samples.web;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
 
public class HibernateUtil {
 
    private static SessionFactory sessionFactory = null;
    private static ServiceRegistry serviceRegistry;

    private static void createSessionFactory() {
    	try {
            Configuration configuration = new Configuration();
            configuration.configure();
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                                  configuration.getProperties()).build();
            
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    	} catch (Throwable ex) {
    		System.err.println("Failed to create sessionFactory object." + ex);
    		//throw new ExceptionInInitializerError(ex);
    	}
    }

    public static SessionFactory getSessionFactory() {
    	if (sessionFactory == null) {
    		createSessionFactory();
    	}
        return sessionFactory;
    }
 
    public static void shutdown() {
    	getSessionFactory().close();
    }
 
}