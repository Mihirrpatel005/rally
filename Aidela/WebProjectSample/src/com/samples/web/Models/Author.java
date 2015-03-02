package com.samples.web.Models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table (name = "Author")
public class Author implements Serializable {
	
	@Id
    @GeneratedValue
    @Column(name = "idAuthor")
    private int Id;
	
	@Column(name = "first_name")
	public String FirstName;
	
	@Column(name = "last_name")
	public String LastName;
	
	
	/* Constructors */
	
	public Author() {}
	
	public Author(String firstName, String lastName) {
		setFirstName(firstName);
		setLastName(lastName);
	}
	
	
	/* Public methods */
	
	public String getFullName() {
		return FirstName + " " + LastName;
	}
	
	
	/* Getters and setters */
	
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	
}
