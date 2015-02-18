package com.samples.web;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;



import com.samples.web.Models.*;

public class DBHandler {
	private static DBHandler instance = null;

	public static DBHandler getInstance() {
        if(instance == null) {
            instance = new DBHandler();
        }
        return instance;
    } 
    
	protected DBHandler() {

	}
	
	public void saveBook(Book book) {
		Session session = null;
        try {
		    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		    session = sessionFactory.openSession();
		
		    System.out.println("Inserting Record");
		    Transaction transaction = session.beginTransaction();
		
		    session.save(book);
		    transaction.commit();
		    
		    System.out.println("Done");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
        	if (session != null) {
        		session.close();
        	}
        }
	}

	public void saveCategory(Category category) {
		Session session = null;
        try {
		    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		    session = sessionFactory.openSession();
		
		    System.out.println("Inserting Record");
		    Transaction transaction = session.beginTransaction();
		
		    session.save(category);
		    transaction.commit();
		    
		    System.out.println("Done");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
        	if (session != null) {
        		session.close();
        	}
        }
		
	}
	
	public void updateCategory(Category category) {
		Session session = null;
        try {
		    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		    session = sessionFactory.openSession();
		
		    System.out.println("Inserting Record");
		    Transaction transaction = session.beginTransaction();
		
		    session.update(category);
		    transaction.commit();
		    
		    System.out.println("Done");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
        	if (session != null) {
        		session.close();
        	}
        }
		
	}
	
	public void updateBook(Book book) {
		Session session = null;
        try {
		    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		    session = sessionFactory.openSession();
		
		    System.out.println("Inserting Record");
		    Transaction transaction = session.beginTransaction();
		
		    session.update(book);
		    transaction.commit();
		    
		    System.out.println("Done");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
        	if (session != null) {
        		session.close();
        	}
        }
		
	}
	
	public void saveAuthor(Author author) {
		Session session = null;
        try {
		    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		    session = sessionFactory.openSession();
		
		    System.out.println("Inserting Record");
		    Transaction transaction = session.beginTransaction();
		
		    session.save(author);
		    transaction.commit();
		    
		    System.out.println("Done");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
        	if (session != null) {
        		session.close();
        	}
        }
		
	}
		
}
