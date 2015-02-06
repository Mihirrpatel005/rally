package com.samples.library;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class Main {
	final static Logger logger = Logger.getLogger(Main.class);
	
    public static void main(String[] args) {
        logger.info("Input xml file path to validate:");
        Scanner in = new Scanner(System.in);
        LibraryHandler.getInstance().validateXML(in.nextLine());
    	// C:\Users\akaramyan\Desktop\xml\library.xml
        in.close();
    	
    	Book book = new Book();
    	book.Title = "XHTML: moving toward";
    	book.Authors.add(new Author("Simon St.", "Laurent"));
    	book.Attributes = new BookAttributes("ISBN 0764547097", "M&T Books", "1st", "2000", 2);	
    	LibraryHandler.getInstance().insertBook(book);
    	
    	Book book2 = new Book();
    	book2.Title = "Physical Chemistry: with Applications to the Life Sciences";
    	book2.Authors.add(new Author("David", "Eisenberg"));
    	book2.Authors.add(new Author("Donald", "Crothers"));
    	book2.Attributes = new BookAttributes("ISBN-13: 978-0805324020", "The Benjamin/Cummings", "1979", 1);	
    	LibraryHandler.getInstance().insertBook(book2);
    }
}
