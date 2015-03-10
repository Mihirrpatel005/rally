package com.samples.library;

import org.apache.commons.lang3.StringEscapeUtils;

public class Author {
	private String _ident;
	private String _firstName;
	private String _lastName;
	
	public Author(String firstName, String lastName) {
		setFirstName(firstName);
		setLastName(lastName);
		
		// TODO: generate unique idents, for now assume they are for a given first-last name pair
		setIdent(new String (firstName.substring(0, 1) + lastName.substring(0, 1)).toUpperCase());
	}
	
	public String toXML() {
		// TODO: auto-generate
		return new String("<Author ident=\"" + this._ident + "\">" +"\n" +
				            "\t" + "<First_Name>" + this._firstName + "</First_Name>" + "\n" +
				            "\t" + "<Last_Name>" + this._lastName + "</Last_Name>" + "\n" +
				           "</Author>");
	}
	
	public String getIdent() {
		return _ident;
	}
	public void setIdent(String ident) {
		if (ident == null) throw new IllegalArgumentException();
		_ident = ident;
	}
	public String getFirstName() {
		return _firstName;
	}
	public void setFirstName(String first_name) {
		if (first_name == null) throw new IllegalArgumentException();
		_firstName = StringEscapeUtils.escapeXml(first_name);
	}
	public String getLastName() {
		return _lastName;
	}
	public void setLastName(String last_name) {
		if (last_name == null) throw new IllegalArgumentException();
		_lastName = StringEscapeUtils.escapeXml(last_name);
	}
}
