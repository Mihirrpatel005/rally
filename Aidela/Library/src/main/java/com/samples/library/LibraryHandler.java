package com.samples.library;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import com.ximpleware.XMLModifier;

public class LibraryHandler {
	
    private static LibraryHandler _instance = new LibraryHandler();
    private static String _xsdFilePath = "./src/main/resources/xsd-library-xml.xsd";
    private static String _libFilePath = "./src/main/resources/library.xml";
    private SchemaFactory _schemaFactory;
    private LoggerHelper _loggerHelper;

    // TODO: Allow user to setup/change xsdPath and libPath
    
    public enum ObjectTypes {
        Book, Author
    }
    
    protected LibraryHandler() {
    	_schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    	
    	_loggerHelper = LoggerHelper.getInstance();
    	_loggerHelper.registerLogger();
    }

    
    /* Public Methods */
    
    public static LibraryHandler getInstance() {
        return _instance;
    }
    
    /**
     * Public:
     * Validates XML file at given path with <i>"xsd-library-xml.xsd"</i> schema
     * 
     * @param xmlFilePath
     * @return <code>true</code> when validation passes, <code>false</code> otherwise
     */
    public boolean validateXmlFile(String xmlFilePath) {
    	assert _xsdFilePath != null && !_xsdFilePath.isEmpty();
    	assert _loggerHelper != null;
    	
    	if (xmlFilePath == null || xmlFilePath.isEmpty()) {
    		throw new IllegalArgumentException();
    	}
    	
    	boolean result = validateXmlFileWithSchema(xmlFilePath, _xsdFilePath);
    	_loggerHelper.logInfo(result ? xmlFilePath + " validation passed" : xmlFilePath + " validation failed");
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
    	assert _loggerHelper != null;
    	
    	if (book == null) {
    		throw new IllegalArgumentException();
    	}
    	
    	boolean result = insertBook(book, false);
    	_loggerHelper.logInfo(result ? "Book was succesfully inserted" : "Book insertion failed");
    	return result;
    }

    
    /* Private Methods */
    
    /**
     * Private:
     * Validates XML string against given schema
     * Note: XML string structure should fully match schema structure
     * to pass validation, e.g. contain root node
     * @param xmlString   : XML string for validation
     * @param xsdFilePath : path to XML schema file
     * 
     * @return <code>true</code> when validation passes, <code>false</code> otherwise
     */
    private boolean validateXmlStringWithSchema(String xmlString, String xsdFilePath) {
    	assert xmlString != null && !xmlString.isEmpty();
    	assert xsdFilePath != null && !xsdFilePath.isEmpty();
    	assert _schemaFactory != null;
    	assert _loggerHelper != null;
    	
    	try {
            Schema schema = _schemaFactory.newSchema(new File(xsdFilePath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xmlString)));
        } catch (IOException | SAXException e) {
        	_loggerHelper.logError(e.getMessage());
            return false;
        }
        return true;
	}
    
    /**
     * Private:
     * Validates XML file against given schema
     * @param xmlFilePath  : path to XML file
     * @param xsdFilePath  : path to XML schema file
     * 
     * @return <code>true</code> when validation passes, <code>false</code> otherwise
     */
    private boolean validateXmlFileWithSchema(String xmlFilePath, String xsdFilePath) {   
    	assert xmlFilePath != null && !xmlFilePath.isEmpty();
    	assert xsdFilePath != null && !xsdFilePath.isEmpty();
    	assert _schemaFactory != null;
    	assert _loggerHelper != null;
    	
        try {
            Schema schema = _schemaFactory.newSchema(new File(xsdFilePath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlFilePath)));
        } catch (IOException | SAXException e) {
        	_loggerHelper.logError(e.getMessage());
            return false;
        }
        return true;
    }
    
    /**
     * Private:
     * Creates <i>Library</i> XML node string containing given book and it's authors
     * matching <i>"xsd-library-xml.xsd"</i> schema structure
     *
     * @param book  : book for wrapping
     * @return <i>Library</i> XML node string
     */
    private String libraryNodeStringFromBook(Book book) {
    	assert book != null;
    	
    	String xml = "<Library> \n" + book.toXML();
    	for(Author author : book.getAuthorList()) {
    		xml += "\t" + author.toXML();
    	}
    	xml += "</Library>";

    	return xml;
    }
    
    /**
     * Private:
     * Adds given number of tabs after each new line in XML string
     * 
     * @param xmlString  : string containing XML node
     * @param tabCount   : number of tabs to be added
     * @return indented string
     */
    private String identXmlStringWithTabs(String xmlString, int tabCount) {
    	assert xmlString != null && !xmlString.isEmpty();
    	assert tabCount > 0;

    	String tabs = "";
    	while (tabCount-- > 0)
    		tabs += "\t";
    	
    	return tabs + xmlString.replace("\n", "\n" + tabs);
    }
    
    /**
     * Private:
     * Inserts given book object as XML record into <i>"library.xml"</i> 
     *
     * @param book              : book object for inserting into XML library
     * @param replaceExisting   : indicates whether to replace existing record if there's a match
     * @return <code>true</code> when insertion passes, <code>false</code> otherwise
     */
    private boolean insertBook(Book book, boolean replaceExisting) {
    	assert book != null;
    	assert _xsdFilePath != null && !_xsdFilePath.isEmpty();
    	assert _libFilePath != null && !_libFilePath.isEmpty();
    	assert _schemaFactory != null;
    	assert _loggerHelper != null;
    	
    	String xmlToValidate = libraryNodeStringFromBook(book);
    	assert xmlToValidate != null && !xmlToValidate.isEmpty();
    	
    	boolean result = validateXmlStringWithSchema(xmlToValidate, _xsdFilePath);
    	_loggerHelper.logInfo(result ? "Insertion validation passed" : "Insertion validation failed");
    	
    	if(result) {
    		try {
    			File f = new File(_libFilePath);
        		FileInputStream fis = new FileInputStream(f);
        		byte[] ba = new byte[(int)f.length()]; 
        		fis.read(ba); 	
        		fis.close();
        		
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
            				_loggerHelper.logWarning("Book with given ISBN \"" + isbn + "\" arelady exists");
            				
            				// TODO: Get rid of empty tabs after replacing
            				if (replaceExisting) {
            					_loggerHelper.logWarning("Book with ISBN \"" + isbn + "\" will be replaced");
            					xm.remove();
            				} else {
            					return false;
            				}
            			}
            		} while (vn.toElement(VTDNav.NEXT_SIBLING, "Book"));
            	}
        		
        		vn.toElement(VTDNav.ROOT);
        		// TODO: Replace hard-coded nesting level number
        		xm.insertAfterHead(identXmlStringWithTabs("\n" + book.toXML(), 1)); 
        		
        		// Insert book authors if they don't exists
        		for (Author author : book.getAuthorList()) {
        			boolean exists = false;
        			String authorId = author.getIdent();
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
                		xm.insertAfterElement(identXmlStringWithTabs("\n" + author.toXML(), 1)); 
        			}
        		}
        		
        		xm.output(new FileOutputStream(_libFilePath));
        		 
        		return true;
        		
        	} catch (Exception e) { 
        		_loggerHelper.logError(e.getMessage());
                return false;
        	}
    	} else {
    		return false;
    	}
    }
}
