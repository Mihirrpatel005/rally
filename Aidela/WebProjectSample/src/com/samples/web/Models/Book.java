package com.samples.web.Models;

import java.util.ArrayList;

public class Book {
	public String Title;
	public String Description;
	public ArrayList<Author> Authors;
	public String ISBN;
	public String Publisher;
	public String Edition;
	public String Year;
	public int Count;
	
	public Book(String title, String isbn, String publisher, String year, int count) {
		setTitle(title);
		setISBN(isbn);
		setPublisher(publisher);
		setYear(year);
		setCount(count);
	}
	
	public Book(String title, String isbn, String publisher, String year, int count, String edition) {
		this(title, isbn, publisher, year, count);
		setEdition(edition);
	}
	
	public String getStringWithAuthors() {
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < Authors.size(); i++) {
			String name = Authors.get(i).getFullName();
			if (i == 0) {
				builder.append(name);
			} else if (i + 1 == Authors.size()) {
				builder.append(" and ");
				builder.append(name);
			} else {
				builder.append(", ");
				builder.append(name);
			}
		}

		return builder.toString();
	}
	
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public ArrayList<Author> getAuthors() {
		return Authors;
	}
	public void setAuthors(ArrayList<Author> authors) {
		Authors = authors;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getPublisher() {
		return Publisher;
	}
	public void setPublisher(String publisher) {
		Publisher = publisher;
	}
	public String getEdition() {
		return Edition;
	}
	public void setEdition(String edition) {
		Edition = edition;
	}
	public String getYear() {
		return Year;
	}
	public void setYear(String year) {
		Year = year;
	}
	public int getCount() {
		return Count;
	}
	public void setCount(int count) {
		Count = count;
	}
	
}
