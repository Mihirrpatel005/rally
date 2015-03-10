package com.samples.library;

import java.util.ArrayList;

import org.apache.commons.lang3.StringEscapeUtils;

public class Book {
	private String _title;
	private String _description;
	private BookAttributes _attributes;
	private ArrayList<Author> _authorList;
	
	public Book() {
		setTitle("");
		setDescription("");
		setAttributes(new BookAttributes());
		setAuthorList(new ArrayList<Author>());
	}
	
	// TODO: auto-generate based on schema
	public String toXML() {
		String result = new String("<Book " + _attributes.toXml() + ">" + "\n" +
								   "\t" + "<Title>" + _title + "</Title>" + "\n" +				
								   "\t" + "<Authors>" + "\n");
		for(Author author : _authorList) {
			result += "\t\t" + "<Author authorIdent=\"" + author.getIdent() + "\"/>" + "\n";
		}
		result += "\t" + "</Authors>" + "\n";
		
		if (_description.length() != 0) {
			result += "\t" + "<Description>" + _description + "</Description>" + "\n";
		}
		result += "</Book>";

		return result;
	}
	
	public String getTitle() {
		return _title;
	}
	public void setTitle(String title) {
		if (title == null) throw new IllegalArgumentException();
		_title = StringEscapeUtils.escapeXml(title);
	}
	public String getDescription() {
		return _description;
	}
	public void setDescription(String description) {
		if (description == null) throw new IllegalArgumentException();
		_description = StringEscapeUtils.escapeXml(description);
	}
	public BookAttributes getAttributes() {
		return _attributes;
	}
	public void setAttributes(BookAttributes attributes) {
		if (attributes == null) throw new IllegalArgumentException();
		this._attributes = attributes;
	}
	public ArrayList<Author> getAuthorList() {
		return _authorList;
	}
	public void setAuthorList(ArrayList<Author> authors) {
		if (authors == null) throw new IllegalArgumentException();
		_authorList = authors;
	}
}
