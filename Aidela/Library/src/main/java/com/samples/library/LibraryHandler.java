package com.samples.library;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import com.ximpleware.XMLModifier;

public class LibraryHandler {
	final static Logger logger = Logger.getLogger(Main.class);
	
    private static LibraryHandler instance = null;
    private static String _xsdPath = "./src/main/resources/xsd-library-xml.xsd";
    private static String _libPath = "./src/main/resources/library.xml";
    private SchemaFactory _schemaFactory;

    // TODO: Allow user to setup/change xsdPath and libPath
    
    public enum ObjectTypes {
        Book, Author
    }
    
    protected LibraryHandler() {
    	_schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    }

    public static LibraryHandler getInstance() {
        if(instance == null) {
            instance = new LibraryHandler();
        }
        return instance;
    } 
    
    /**
     * Public:
     * Validates XML file at given path against <i>"xsd-library-xml.xsd"</i> schema
     * 
     * @param xmlPath  : path to XML file
     * @return <code>true</code> when validation passes, <code>false</code> otherwise
     */
    public boolean validateXML(String xmlPath) {
    	boolean result = validateWithXMLSchema(_xsdPath, xmlPath);
    	logger.info(result ? xmlPath + " validation passed" : xmlPath + " validation failed");
    	return result;
    }
    
    /**
     * Public:
     * Inserts given book object as XML record into <i>"library.xml"</i>
     * 
     * @param book  : book object to insert into XML library
     * @return <code>true</code> when insertion passes, <code>false</code> otherwise
     */
    public boolean insertBook(Book book) {
    	boolean result = insertBook(book, false);
    	logger.info(result ? "Book was succesfully inserted" : "Book insertion failed");
    	return result;
    }
    
    /**
     * Protected:
     * Validates XML string against given schema
     * Note: XML string structure should fully match schema structure
     * to pass validation, e.g. contain root node
     * 
     * @param xsdPath    : path to XML schema file
     * @param xmlString  : XML string for validation
     * @return <code>true</code> when validation passes, <code>false</code> otherwise
     */
    protected boolean validateStringWithXMLSchema(String xsdPath, String xmlString) {
    	try {
            Schema schema = _schemaFactory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xmlString)));
        } catch (IOException | SAXException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
	}
    
    /**
     * Protected:
     * Validates XML file against given schema
     * 
     * @param xsdPath  : path to XML schema file
     * @param xmlPath  : path to XML file
     * @return <code>true</code> when validation passes, <code>false</code> otherwise
     */
    protected boolean validateWithXMLSchema(String xsdPath, String xmlPath) {   
        try {
            Schema schema = _schemaFactory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }
    
    /**
     * Private:
     * Converts book object into hardcoded XML string 
     * matching <i>"xsd-library-xml.xsd"</i> schema structure
     *
     * @param book  : book object for converting
     * @return library XML string containing given book and it's authors
     */
    private String convertBookToFullXMLString(Book book) {
    	String xml = "<Library> \n" + book.toXML();
    	for(Author author : book.Authors) {
    		xml += "\t" + author.toXML();
    	}
    	xml += "</Library>";

    	return xml;
    }
    
    /**
     * Private:
     * Normalizes XML string indentation for given level 
     * 
     * @param xmlString  : input string containing XML node
     * @param level      : nested level for given XML
     * @return normalized string
     */
    private String normalizeXMLTabs(String xmlString, int level) {
    	if (level <= 0)
    		return xmlString;
    	String tabs = "";
    	while (level-- > 0)
    		tabs += "\t";
    	return tabs + xmlString.replace("\n", "\n" + tabs);
    }
    
    /**
     * Public:
     * Inserts given book object as XML record into <i>"library.xml"</i> 
     *
     * @param book              : book object for inserting into XML library
     * @param replaceExisting   : indicates whether to replace existing record if there's a match
     * @return <code>true</code> when insertion passes, <code>false</code> otherwise
     */
    protected boolean insertBook(Book book, boolean replaceExisting) {
    	String xmlToValidate = convertBookToFullXMLString(book);
    	boolean result = validateStringWithXMLSchema(_xsdPath, xmlToValidate);
    	logger.info(result ? "Insertion validation passed" : "Insertion validation failed");
    	
    	if(result) {
    		try {
    			File f = new File(_libPath);
        		FileInputStream fis = new FileInputStream(f);
        		byte[] ba = new byte[(int)f.length()]; 
        		fis.read(ba); 	
        		
        		VTDGen vg = new VTDGen(); 
        		vg.setDoc(ba); 
        		vg.parse(false); 
        		VTDNav vn = vg.getNav(); 
        		
        		XMLModifier xm = new XMLModifier();
        		xm.bind(vn);
        		
        		// Check if book already exists
        		vn.toElement(VTDNav.ROOT);
        		String isbn = book.getAttributes().getISBN();
        		if (vn.toElement(VTDNav.FIRST_CHILD, "Book")) {
            		do {
            			int i = vn.getAttrVal("ISBN");
            			if (i!=-1 && vn.toString(i).equals(isbn)) {
            				logger.warn("Book with given ISBN \"" + isbn + "\" arelady exists");
            				
            				// TODO: get rid of empty tabs after replacing
            				if (replaceExisting) {
            					logger.warn("Book with ISBN \"" + isbn + "\" will be replaced");
            					xm.remove();
            				} else {
            					return false;
            				}
            			}
            		} while (vn.toElement(VTDNav.NEXT_SIBLING, "Book"));
            	}
        		
        		vn.toElement(VTDNav.ROOT);
        		// TODO: replace hardcoded nesting level number
        		xm.insertAfterHead(normalizeXMLTabs("\n" + book.toXML(), 1)); 
        		
        		// Insert book authors if they don't exists
        		for (Author author : book.Authors) {
        			boolean exists = false;
        			String authorId = author.Ident;
        			vn.toElement(VTDNav.ROOT);
        			if (vn.toElement(VTDNav.FIRST_CHILD, "Author")) {
                		do {
                			int i = vn.getAttrVal("ident");
                			if (i!=-1 && vn.toString(i).equals(authorId)) {
                				// If author with given ident already exists, skip
                				// Note: We are assuming idents are unique for given first-last-name pair
                				exists = true;
                				break;
                			}
                		} while (vn.toElement(VTDNav.NEXT_SIBLING, "Author"));
                	}
        			
        			if (!exists) {
        				vn.toElement(VTDNav.ROOT);
                		vn.toElement(VTDNav.LAST_CHILD, "Book");
                		xm.insertAfterElement(normalizeXMLTabs("\n" + author.toXML(), 1)); 
        			}
        		}
        		
        		xm.output(new FileOutputStream(_libPath));
        		 
        		return true;
        		
        	} catch (Exception e) { 
        		logger.error(e.getMessage());
                return false;
        	}
    	} else {
    		return false;
    	}
    }
}
