package com.samples.library;

import java.util.Scanner;

import org.apache.log4j.Logger;

public class Main {
	final static Logger logger = Logger.getLogger(Main.class);
	
    public static void main(String[] args) {
        logger.info("Input xml file path to validate:");
        Scanner in = new Scanner(System.in);
        LibraryHandler.getInstance().validateXmlFile(in.nextLine());
        in.close();
    	
    	Book book = new Book();
    	book.setTitle("XHTML: moving toward");
    	book.getAuthorList().add(new Author("Simon St.", "Laurent"));
    	book.setAttributes(new BookAttributes("ISBN 0764547097", "M&T Books", "1st", "2000", 2));	
    	LibraryHandler.getInstance().insertBook(book);
    	
    	Book book2 = new Book();
    	book2.setTitle("Physical Chemistry: with Applications to the Life Sciences");
    	book2.getAuthorList().add(new Author("David", "Eisenberg"));
    	book2.getAuthorList().add(new Author("Donald", "Crothers"));
    	book2.setAttributes(new BookAttributes("ISBN-13: 978-0805324020", "The Benjamin/Cummings", "1979", 1));	
    	LibraryHandler.getInstance().insertBook(book2);
    	
    	//C:\Users\akaramyan\Desktop\git\shared_repo\Aidela\Library\src\main\resources\library.xml
    }
}
