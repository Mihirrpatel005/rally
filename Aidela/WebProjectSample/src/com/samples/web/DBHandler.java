package com.samples.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.samples.web.Models.*;


public class DBHandler {
	
	final static Logger logger = Logger.getLogger(DBHandler.class);
	
	private static DBHandler instance = null;

	public static DBHandler getInstance() {
        if(instance == null) {
            instance = new DBHandler();
        }
        return instance;
    } 
    
	protected DBHandler() {
		// Initialize session factory
		HibernateUtil.getSessionFactory();
	}

	
	/* Public methods */
	
	@SuppressWarnings("unchecked")
	public List<Category> getCategoryList() {
		Session session = null;
		List<Category> result = new ArrayList<Category>();
		
        try {
		    session = HibernateUtil.getSessionFactory().openSession();
		    result = (List<Category>)session.createCriteria(Category.class).list();
		    
        } catch (Exception e) {
        	logger.error(e. getMessage());
        } finally {
        	if (session != null) {
        		session.close();
        	}
        }	
        
        return result;
    }
	
	@SuppressWarnings("unchecked")
	public List<Book> getBookListForCategory(Category category) {  
		Session session = null;
		List<Book> result = new ArrayList<Book>();
		
		try {
		    session = HibernateUtil.getSessionFactory().openSession();
		    
		    Query query = session.createSQLQuery("select b.*, bd.description "
		    								   + "from book b left join bookdescription bd on b.idBook = bd.book_idBook "
		    								   + "where b.category_idCategory = :id ").addEntity(Book.class);
		    query.setParameter("id", category.id);
		    result = (List<Book>) query.list();

		    for (Book book : result) {
		    	Hibernate.initialize(book.getAuthors());
		    }
		    
        } catch (Exception e) {
        	logger.error(e. getMessage());
        } finally {
        	if (session != null) {
        		session.close();
        	}
        }

	    return result;  
	  }  
	
	public void saveBook(Book book) {
		Session session = null;
        try {
		    session =  HibernateUtil.getSessionFactory().openSession();

			Object queryResult = session.createCriteria(Book.class)
								        .add(Restrictions.eq("ISBN", book.getISBN())).uniqueResult();
			
			Transaction transaction = session.beginTransaction();
			
			if (queryResult != null) {
				logger.warn("Database already contains book with ISBN:" + book.getISBN());
				logger.warn("Updating book with ISBN:" + book.getISBN());
				session.update(book);
			} else {
				logger.info("Saving book with ISBN:" + book.getISBN());
			    session.save(book);
			}   
			
			transaction.commit();
			
        } catch (Exception e) {
        	logger.error(e.getMessage());
        } finally {
        	if (session != null) {
        		session.close();
        	}
        }	
	}

	public void saveCategory(Category category) {
		Session session = null;
        try {
        	session =  HibernateUtil.getSessionFactory().openSession();

			Object queryResult = session.createCriteria(Category.class)
										.add(Restrictions.eq("Name", category.getName())).uniqueResult();
			
			Transaction transaction = session.beginTransaction();
			
			if (queryResult != null) {
				logger.warn("Database already contains category with name " + category.getName());
				logger.warn("Updating category with name " + category.getName());
			    session.update(category);
			} else {
				logger.info("Saving category with name " + category.getName());
			    session.save(category);
			}
			
			transaction.commit();
			
        } catch (Exception e) {
        	logger.error(e.getMessage());
        } finally {
        	if (session != null) {
        		session.close();
        	}
        }
	}
	
	public void saveAuthor(Author author) {
		Session session = null;
        try {
		    session =  HibernateUtil.getSessionFactory().openSession();

			Object queryResult = session.createCriteria(Author.class)
										.add(Restrictions.eq("FirstName", author.FirstName))
			                            .add(Restrictions.eq("LastName", author.LastName)).uniqueResult();
			
			if (queryResult != null) {
				logger.warn("Database already contains author with name " + author.getFullName());
			} else {
				logger.info("Saving author with name " + author.getFullName());
				
				Transaction transaction = session.beginTransaction();
			    session.save(author);
			    transaction.commit();
			}   
        } catch (Exception e) {
        	logger.error(e.getMessage());
        } finally {
        	if (session != null) {
        		session.close();
        	}
        }	
	}
		
}
