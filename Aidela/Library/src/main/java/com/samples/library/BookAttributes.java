package com.samples.library;

import org.apache.commons.lang3.StringEscapeUtils;

public class BookAttributes {
	private String _isbn;
	private String _publisher;
	private String _edition;
	private String _year;
	private int _count;

	public BookAttributes() {
		setISBN("");
		setPublisher("");
		setEdition("");
		setYear("");
		setCount(0);
	}
	
	public BookAttributes(String isbn, String publisher, String year, int count) {
		setISBN(isbn);
		setPublisher(publisher);
		setEdition("");
		setYear(year);
		setCount(count);
	}
	
	public BookAttributes(String isbn, String publisher, String edition, String year, int count) {
		this(isbn, publisher, year, count);
		setEdition(edition);
	}

	// TODO: auto-generate
	public String toXml() {
		String result = "ISBN=\"" + this._isbn + "\" ";
		result += "publisher=\"" + this._publisher + "\" ";
		result += _edition != "" ? "edition=\"" + this._edition + "\" " : "";
		result += "year=\"" + this._year + "\" ";
		result += "count=\"" + this._count + "\" ";
		return result;
	}
	
	public String getISBN() {
		return _isbn;
	}
	public void setISBN(String isbn) {
		if (isbn == null) throw new IllegalArgumentException();
		_isbn = isbn;
	}
	public String getPublisher() {
		return _publisher;
	}
	public void setPublisher(String publisher) {
		if (publisher == null) throw new IllegalArgumentException();
		this._publisher = StringEscapeUtils.escapeXml(publisher);
	}
	public String getEdition() {
		return _edition;
	}
	public void setEdition(String edition) {
		if (edition == null) throw new IllegalArgumentException();
		this._edition = edition;
	}
	public String getYear() {
		return _year;
	}
	public void setYear(String year) {
		if (year == null) throw new IllegalArgumentException();
		this._year = year;
	}
	public int getCount() {
		return _count;
	}
	public void setCount(int count) {
		if (count < 0) throw new IllegalArgumentException();
		this._count = count;
	}
}
