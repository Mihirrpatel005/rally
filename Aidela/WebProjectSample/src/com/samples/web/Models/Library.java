package com.samples.web.Models;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.samples.web.DBHandler;


public class Library {
	
	final static Logger logger = Logger.getLogger(Library.class);
	
	private List<Category> Categories;
	private DBHandler dbHandler = DBHandler.getInstance();
	
	private static Library instance = null;
	
	public static Library getInstance() {
        if(instance == null) {
            instance = new Library();
        }
        return instance;
    } 
    
	
	/* Constructors */
	
	@SuppressWarnings("serial")
	protected Library() {
		
		Categories = dbHandler.getCategoryList();

		// Uncomment when running for first time, to populate database
		addCategory("Fiction");
		addCategory("Non-Fiction");
		addCategory("Philosophy");
		addCategory("Biography");
		addCategory("Travel");
		
		Book book1 = new Book("47 Ronin", "9781622313563", " Highbridge Company", "2012", 1);
		book1.setAuthors(new ArrayList<Author>() {{
		    add(new Author("John", "Allyn"));
		    add(new Author("Stephen", "Turnbull"));
		}});
		addBook(book1, "Fiction");
		
		Book book2 = new Book("Anna Karenina", "9781598870800", "Penguin Classics", "2004", 1);
		book2.setAuthors(new ArrayList<Author>() {{
		    add(new Author("Leo", "Tolstoy"));
		}});
		book2.setDescription("sample description");
		addBook(book2, "Fiction");
		
		Book book3 = new Book("The Reaper: Autobiography of One of the Deadliest Special Ops Snipers",
								"9781250045447", "St. Martin's Press", "2015", 1, "1st");
		book3.setAuthors(new ArrayList<Author>() {{
		    add(new Author("Nicholas", "Irving"));
		}});
		addBook(book3, "Biography"); 
		
	}
	
	
	/* Public methods */
	
	public boolean containsCategoryWithName(String name) {
		for (Category category : Categories) {
			if (category.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	public Category getCategoryWithName(String name) {
		for (Category category : Categories) {
			if (category.getName().equalsIgnoreCase(name)) {
				if (!category.isLoaded()) {
					category.populateWithBookList(dbHandler.getBookListForCategory(category));
				}
				
				return category;
			}
		}
		logger.warn("Library doesn't contain category with name " + name);
		return null;
	}
	
	public Book getBookWithISBN(String isbn) {
		// Note: This will return null if getCategoryWithName wasn't requested prior
		// which shouldn't happen with currently implemented workflow
		for (Category category : Categories) {
			if (category.containsBookWithISBN(isbn)) {
				return category.getBookWithISBN(isbn);
			}
		}
		logger.warn("Library doesn't contain book with ISBN:" + isbn);
		return null;
	}

	public boolean addCategory(String name) {
		if(getCategoryWithName(name) == null) {
			Category category = new Category(name);
			Categories.add(category);
			dbHandler.saveCategory(category);
			return true;
		} else {
			logger.warn("Category with name " + name + " already exists");
			logger.warn("Aborting add operation for category " + name);
			return false;
		}
	}
	
	public boolean addBook(Book book, String category) {
		boolean result = true;
		
		if (book == null || category == null) {
			logger.warn("Unable to add book, given book or category name is null");
			result = false;
		} else if (!containsCategoryWithName(category)) {
			logger.warn("Library doesn't contain category with name " + category);
			logger.warn("Aborting add operation for book ISBN:" + book.getISBN());
			result = false;
		} else {
			for (Author author : book.getAuthors()) {
				dbHandler.saveAuthor(author);
			}
			
			getCategoryWithName(category).addBook(book);

			dbHandler.saveBook(book);
			dbHandler.saveCategory(getCategoryWithName(category));
		}

		return result;
	}
	
	
	/* Getters and Setters */
	
	public List<Category> getCategories() {
		return Categories;
	}
	public void setCategories(List<Category> categories) {
		Categories = categories;
	}
	
}
