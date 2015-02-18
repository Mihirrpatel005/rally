package com.samples.web.Models;

import java.util.ArrayList;

import com.samples.web.DBHandler;

public class Library {
	private static Library instance = null;
	
	public ArrayList<Category> Categories;

	public static Library getInstance() {
        if(instance == null) {
            instance = new Library();
        }
        return instance;
    } 
    
	protected Library() {
		Categories = new ArrayList<Category>();
		
		// Sample initialization
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
		addBook(book2, "Fiction");
//		addBook(book2, "Biography");
		
		Book book3 = new Book("The Reaper: Autobiography of One of the Deadliest Special Ops Snipers",
								"9781250045447", "St. Martin's Press", "2015", 1, "1st");
		book3.setAuthors(new ArrayList<Author>() {{
		    add(new Author("Nicholas", "Irving"));
		}});
		addBook(book3, "Biography");
	}
	
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
				return category;
			}
		}
		return null;
	}
	
	public Book getBookWithISBN(String isbn) {
		for (Category category : Categories) {
			if (category.containsBookWithISBN(isbn)) {
				return category.getBookWithISBN(isbn);
			}
		}
		return null;
	}

	public boolean addCategory(String name) {
		Category category = new Category(name);
		if(!Categories.contains(category)) {
			Categories.add(category);
			DBHandler.getInstance().saveCategory(category);
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<Category> getCategories() {
		return Categories;
	}
	
	public boolean addBook(Book book, String category) {
		boolean result = true;
		
		if (book == null || category == null || !containsCategoryWithName(category)) {
			result = false;
		} else {
			for (Author author : book.getAuthors()) {
//				DBHandler.getInstance().saveAuthor(author);
			}
			getCategoryWithName(category).addBook(book);
			DBHandler.getInstance().saveBook(book);
			DBHandler.getInstance().updateCategory(getCategoryWithName(category));
		}

		return result;
	}
	
}
