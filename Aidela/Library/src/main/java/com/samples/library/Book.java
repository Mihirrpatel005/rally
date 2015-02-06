package com.samples.library;

import java.util.ArrayList;
import org.apache.commons.lang3.StringEscapeUtils;

public class Book {
	public String Title;
	public String Description;
	public BookAttributes Attributes;
	public ArrayList<Author> Authors;
	
	public Book() {
		Authors = new ArrayList<Author>();
	}
	
	public String toXML() {
		// TODO: auto-generate based on schema
		String result = new String("<Book " + Attributes.toXml() + ">" + "\n" +
								   "\t" + "<Title>" + Title + "</Title>" + "\n" +				
								   "\t" + "<Authors>" + "\n");
		for(Author author : Authors) {
			result += "\t\t" + "<Author authorIdent=\"" + author.getIdent() + "\"/>" + "\n";
		}
		result += "\t" + "</Authors>" + "\n";
		
		if (Description != null) {
			result += "\t" + "<Description>" + Description + "</Description>" + "\n";
		}
		result += "</Book>";

		return result;
	}
	
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = StringEscapeUtils.escapeXml(title);
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = StringEscapeUtils.escapeXml(description);
	}
	public BookAttributes getAttributes() {
		return Attributes;
	}
	public void setAttributes(BookAttributes attributes) {
		this.Attributes = attributes;
	}
	public ArrayList<Author> getAuthors() {
		return Authors;
	}
	public void setAuthors(ArrayList<Author> authors) {
		Authors = authors;
	}
}
