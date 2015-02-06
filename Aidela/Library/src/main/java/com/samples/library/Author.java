package com.samples.library;

import org.apache.commons.lang3.StringEscapeUtils;

public class Author {
	public String Ident;
	public String First_Name;
	public String Last_Name;
	
	public Author(String first_name, String last_name) {
		setFirstName(first_name);
		setLastName(last_name);
		
		// TODO: generate unique idents, for now assume they are for given first-last name pair
		setIdent(new String (first_name.substring(0, 1) + last_name.substring(0, 1)).toUpperCase());
	}
	
	public String toXML() {
		// TODO: auto-generate
		return new String("<Author ident=\"" + this.Ident + "\">" +"\n" +
				            "\t" + "<First_Name>" + this.First_Name + "</First_Name>" + "\n" +
				            "\t" + "<Last_Name>" + this.Last_Name + "</Last_Name>" + "\n" +
				           "</Author>");
	}
	
	public String getIdent() {
		return Ident;
	}
	public void setIdent(String ident) {
		Ident = ident;
	}
	public String getFirstName() {
		return First_Name;
	}
	public void setFirstName(String first_name) {
		First_Name = StringEscapeUtils.escapeXml(first_name);
	}
	public String getLastName() {
		return Last_Name;
	}
	public void setLastName(String last_name) {
		Last_Name = StringEscapeUtils.escapeXml(last_name);
	}
}
