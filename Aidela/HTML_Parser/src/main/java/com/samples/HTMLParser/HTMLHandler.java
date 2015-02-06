package com.samples.HTMLParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class HTMLHandler {
	final static Logger logger = Logger.getLogger(HTMLHandler.class);
	
	class HTMLModel {
		public String Head;
		public String Body;
		public HTMLModel(String head, String body) { Head = head; Body = body; }
	}

    private static HTMLHandler instance = null;
	private static String[] fullHTMLRegexArray = null;
	private static String doctypeRegex = "<!DOCTYPE(\\w*|\"[^\"]*\"|\\s*)*>";
	private static String attributeRegex = "([\\w][:-]?)+=\"[^\"]*\"|\'[^\']*'";
	private static String anythingRegex = "((.|\\s)*?)*";
	
	// TODO: hardcoded indexes
	public static int PatternIndexHead = 2;
	public static int PatternIndexBody = 4;

	
	// Useful patterns
	// tagRegex - <\w*(\s*([:\w-])+=\"[^\"]*\"|\'[^\']*'>]))*\s*>anything</\w*>
	// hbContentRegex - (\s*(((?!<head|<body|</head>|</body>).)*)\s*)*  <- bad regex
	// anythingRegex - (\s*(.*)\s*)* <- bad regex
	
	private HTMLHandler() {
		// TODO: Current regex wont check if tags inside body are valid or if head contains only tags
		// TODO: Temp solution, will be doing fragmented matching to avoid hanging
		// Will match up until <head>, next starting from last char up until end of head, similar for <body>
		String HTMLRegexP1 = "", HTMLRegexP2 = "", HTMLRegexP3 = "", HTMLRegexP4 = "";
		String attributeListRegex = "(\\s*" + attributeRegex + ")*\\s*";
		
		HTMLRegexP1 = "(?i)^"; // case insensitive pattern
		HTMLRegexP1 += "\\s*(" + doctypeRegex + ")?";
		HTMLRegexP1 += "\\s*<html" + attributeListRegex +">\\s*";
		
		HTMLRegexP1 += "<head" + attributeListRegex + ">";
		HTMLRegexP2	+= "" + anythingRegex + "</head>\\s*";  // Important: hangs without last \s*
		
		HTMLRegexP3 += "<body" + attributeListRegex + ">";
		HTMLRegexP4 += "" + anythingRegex + "</body>\\s*";
		//HTMLRegexP4 += "</html>\\s*$";
		
		fullHTMLRegexArray = new String[] {HTMLRegexP1, HTMLRegexP2, HTMLRegexP3, HTMLRegexP4};
	}
	
    public static HTMLHandler getInstance() {
        if(instance == null) {
            instance = new HTMLHandler();
        }
        return instance;
    } 
	
    /**
     * Validates file at given path.
     * File is considered invalid if it is empty, protected or unreadable;
     * or if given path is null.
     * 
     * @param filePath - path to file
     * @return <code>true</code> if file is valid, <code>false</code> otherwise
     */
	public boolean checkFileIntegrity(String filePath) {	
		if (filePath == null) {
			return false;
		}
		
		boolean result = true;
		FileInputStream fis = null;

		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			
			if (file.length() == 0) { 
				result = false;
			}
		} catch (IOException | SecurityException e) {
			 logger.info(e.getMessage());
			result = false;
		} finally {
		    IOUtils.closeQuietly(fis);
		}		
		
		return result;
	}
	
	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public boolean checkHTMLFileValidity(String filePath) {
		if (!checkFileIntegrity(filePath)) {
			logger.error("File is empty, corrupted or protected");
			return false;
		}
		
		//TODO: check extension
		
		FileInputStream fis = null;
		String fileContent = null;
		
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
    		byte[] buffer = new byte[(int)file.length()]; 
    		fis.read(buffer);
    		
    		fileContent = new String(buffer);
    		
		} catch (IOException e) {
			logger.info(e.getMessage());
		} finally {
			IOUtils.closeQuietly(fis);
		}
		
		
		Matcher matcher;
		int index = 0;
		String stringPart = fileContent; 
		HTMLModel sampleHTML = new HTMLModel("", "");
		
		for (int i = 0; i < fullHTMLRegexArray.length; i++) {
			String regexPart = fullHTMLRegexArray[i];
			matcher = Pattern.compile(regexPart).matcher(stringPart);
			
			if (matcher.find()) {
				index = matcher.end();
				stringPart = stringPart.substring(index);
				
				if (i == PatternIndexHead) {
					sampleHTML.Head = matcher.group(1);
				} else if (i == PatternIndexBody) {
					sampleHTML.Body = matcher.group(1);
				}
				logger.warn(index); 
			} else {
				logger.warn("File content is not a valid HTML");
				return false;
			}
		}
		
		logger.warn("File content is a valid HTML");
		return true;
	}
	
}
