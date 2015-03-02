package com.samples.web.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table (name= "Book")
@SecondaryTable(name = "BookDescription", pkJoinColumns=@PrimaryKeyJoinColumn(name="book_idBook"))
public class Book implements Serializable {
	
	@Id
    @GeneratedValue
    @Column(name = "idBook")
    private int Id;
	
	@Column(name = "title")
	private String Title;
	
	@Column(name = "isbn")
	private String ISBN;
	
	@Column(name = "publisher")
	private String Publisher;
	
	@Column(name = "edition")
	private String Edition;
	
	@Column(name = "year")
	private String Year;
	
	@Column(name = "count")
	private int Count;
	
	@Column(table="BookDescription", name="description")
	private String Description;
	
	@ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="BooksByAuthors",
    				joinColumns={@JoinColumn(name="book_idBook", referencedColumnName="idBook")},
    				inverseJoinColumns={@JoinColumn(name="author_idAuthor", referencedColumnName="idAuthor")})
	private List<Author> Authors = new ArrayList<Author>();
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="category_idCategory")
	private Category Category;

	
	/* Constructors */

	public Book() {}
	
	public Book(String title, String isbn, String publisher, String year, int count) {
		super();
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
	
	
	/* Public methods */
	
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
	
	
	/* Getters and setters */
	
	public int getId() {
		return Id;
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
	public List<Author> getAuthors() {
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
	public Category getCategory() {
		return Category;
	}
	public void setCategory(Category category) {
		Category = category;
	}
	
}
