package com.samples.library;

import org.apache.commons.lang3.StringEscapeUtils;

public class BookAttributes {
	public String ISBN;
	public String publisher;
	public String edition;
	public String year;
	public int count;

	public BookAttributes(String ISBN, String publisher, String year, int count) {
		setISBN(ISBN);
		setPublisher(publisher);
		setYear(year);
		setCount(count);
	}
	
	public BookAttributes(String ISBN, String publisher, String edition, String year, int count) {
		this(ISBN, publisher, year, count);
		setEdition(edition);
	}

	public String toXml() {
		String result = "ISBN=\"" + this.ISBN + "\" ";
		result += "publisher=\"" + this.publisher + "\" ";
		result += edition != null ? "edition=\"" + this.edition + "\" " : "";
		result += "year=\"" + this.year + "\" ";
		result += "count=\"" + this.count + "\" ";
		return result;
	}
	
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = StringEscapeUtils.escapeXml(publisher);
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
