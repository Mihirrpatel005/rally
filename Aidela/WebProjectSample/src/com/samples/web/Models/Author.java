package com.samples.web.Models;

public class Author {
	public String FirstName;
	public String LastName;
	
	public Author(String firstName, String lastName) {
		setFirstName(firstName);
		setLastName(lastName);
	}
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
