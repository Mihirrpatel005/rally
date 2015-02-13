package com.samples.web.Models;

import java.util.HashMap;
import java.util.Map;

public class Category {
	public String Name;
	public int ItemsCount;
	public Map<String, Book> BooksDictionary;
	
	public Category(String name) {
		setName(name);
		BooksDictionary = new HashMap<String, Book>();
		ItemsCount = 0;
	}

	public boolean addBook(Book book) {
		if (BooksDictionary.containsKey(book.getISBN())) {
			BooksDictionary.put(book.getISBN(), book);
			ItemsCount++;
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
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getItemsCount() {
		return ItemsCount;
	}
	public Map<String, Book> getBooksDictionary() {
		return BooksDictionary;
	}
	

}
