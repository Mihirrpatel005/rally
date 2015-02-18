package com.samples.web.Models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.Table;

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
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@MapKey
    @JoinTable(name="BooksInCategory",
    	       joinColumns={@JoinColumn(name="category_idCategory", referencedColumnName="idCategory")},
    	       inverseJoinColumns={@JoinColumn(name="book_idBook", referencedColumnName="idBook")})
	public Map<String, Book> BooksDictionary;

	/* Constructors */
	public Category() {}
	
	public Category(String name) {
		super();
		setName(name);
		BooksDictionary = new HashMap<String, Book>();
	}

	/* Public methods */
	public boolean addBook(Book book) {
		if (!BooksDictionary.containsKey(book.getISBN())) {
			BooksDictionary.put(book.getISBN(), book);
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
	
	/* Getters and setters */
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getItemsCount() {
		return BooksDictionary.size();
	}
	public Map<String, Book> getBooksDictionary() {
		return BooksDictionary;

	}
	

}
