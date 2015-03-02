package com.samples.web.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Hibernate;


@SuppressWarnings("serial")
@Entity
@Table (name = "BookCategory")
public class Category implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "idCategory")
	public int id;
	
	@Column(name = "name")
	public String Name;
	
	@Column(name = "book_count")
	public int BookCount;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="Category") 
	@MapKey
	public Map<String, Book> BooksDictionary;
	
	/* Constructors */
	
	public Category() {
		super();
		setBooksDictionary(new HashMap<String, Book>());
	}
	
	public Category(String name) {
		super();
		setName(name);
		setBookCount(0);
		setBooksDictionary(new HashMap<String, Book>());
	}

	
	/* Public methods */
	
	public boolean addBook(Book book) {
		if (!BooksDictionary.containsKey(book.getISBN())) {
			BooksDictionary.put(book.getISBN(), book);
			BookCount++;
			book.setCategory(this);
			return true;
		}
		return false;
	}
	
	public Book getBookWithISBN(String isbn) {
		if (BooksDictionary.containsKey(isbn)) {
			return BooksDictionary.get(isbn);
		}
		return null;
	}
	
	public boolean containsBookWithISBN(String isbn) {
		if (BooksDictionary.containsKey(isbn)) {
			return true;
		}
		return false;
	}
	
	public boolean isLoaded() {
		return Hibernate.isInitialized(BooksDictionary);
	}
	
	public void populateWithBookList(List<Book> bookList) {
		Map<String, Book> bookMap = new HashMap<String, Book>();
		
		for (Book book : bookList) {
	    	bookMap.put(book.getISBN(), book);
	    }
		
		setBooksDictionary(bookMap);
	}
	
	
	/* Getters and setters */
	
	public int getId() {
		return id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Map<String, Book> getBooksDictionary() {
		return BooksDictionary;
	}
	public void setBooksDictionary(Map<String, Book> booksDictionary) {
		BooksDictionary = booksDictionary;
	}
	public int getBookCount() {
		return BookCount;
	}
	public void setBookCount(int count) {
		BookCount = count;
	}

}
