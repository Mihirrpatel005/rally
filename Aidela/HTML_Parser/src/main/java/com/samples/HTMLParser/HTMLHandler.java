package com.samples.HTMLParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.Subject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class HTMLHandler {
	final static Logger logger = Logger.getLogger(HTMLHandler.class);

    private static HTMLHandler instance = null;
	private static String fullHTMLRegexP1 = null;
	private static String fullHTMLRegexP2 = null;
	
	private static String doctypeRegex = "<!DOCTYPE(?:\\w*|\"[^\"]*\"|\\s*)*>";
	private static String attributeRegex = "(?:[\\w][:-]?)+=\"[^\"]*\"|\'[^\']*'";
	private static String anythingRegex = "((?:(?:.|\\s)*?)*)";
	
	public static String testRegex = null;

	public static int HeadContentGroup = 1;
	
	// Useful patterns
	// tagRegex - <\w*(\s*([:\w-])+=\"[^\"]*\"|\'[^\']*'>]))*\s*>anything</\w*>
	// hbContentRegex - (\s*(((?!<head|<body|</head>|</body>).)*)\s*)*  <- bad regex
	// anythingRegex - (\s|.))* <- bad anything regex
	
	
	private HTMLHandler() {
		// TODO: Current regex wont check if tags inside body are valid or if head contains only tags
		// TODO: Temp solution, will be doing fragmented matching to avoid hanging
		// Will match up until <head>, next starting from last char up until end of head, similar for <body>
		String attributeListRegex = "(?:\\s*" + attributeRegex + ")*\\s*";
		
		fullHTMLRegexP1 = "(?i)^";
		fullHTMLRegexP1 += "\\s*(?:" + doctypeRegex + ")?";
		fullHTMLRegexP1 += "\\s*<html" + attributeListRegex +">\\s*";		
		fullHTMLRegexP1 += "<head" + attributeListRegex + ">" + anythingRegex + "</head>\\s*";
		fullHTMLRegexP1 += "<body" + attributeListRegex + ">";
		
		// This part will be done without regex to avoid crash 
		//fullHTMLRegex += "" + anythingRegex + "</body>\\s*"; 
		
		fullHTMLRegexP2 = "^\\s*</html>\\s*$";
		
		testRegex = "<td [ \\t]*class=\"test\"[ \\t]*>\\s*<a [ \\t]*href=\"([\\w\\. ]*)\"[ \\t]*>"
				  + anythingRegex + "</a>\\s*</td>";
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
			fis = new FileInputStream(file);getClass();
			
			if (((Long)file.length()).compareTo((long)0) == 0) { 
				logger.error("File at path " + filePath + " is empty");
				result = false;
			}
		} catch (IOException | SecurityException e) {
			 logger.error(e.getMessage());
			result = false;
		} finally {
		    IOUtils.closeQuietly(fis);
		}		
		
		return result;
	}
	
	/**
	 * Checks if file has HTML file extension
	 * 
	 * @param filePath - path to file
	 * @return  <code>true</code> if file has HTML file extension, <code>false</code> otherwise
	 */
	public boolean isHTMLFile(String filePath) {
		String extension = FilenameUtils.getExtension(filePath);
		
		if(extension.equalsIgnoreCase("html") || extension.equalsIgnoreCase("htm"))
			return true;
		
		return false;
	}

	/**
	 * Parses given HTML file content and extracts HTML head and body content.
	 * 
	 * @param filePath - path to HTML file
	 * @return HTMLModel with file head and body content; or
	 * null if file is corrupted or content doesn't match HTML structure 
	 * 
	 */
	public HTMLModel parseHTMLFile(String filePath) {
		if (!checkFileIntegrity(filePath) || !isHTMLFile(filePath)) {
			logger.error("File is empty, corrupted or protected");
			return null;
		}

		HTMLModel resultHTML = null;
		
		FileInputStream fis = null;
		String fileContent = null;
		
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
    		byte[] buffer = new byte[(int)file.length()]; 
    		fis.read(buffer);
    		
    		fileContent = new String(buffer);
    		
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			IOUtils.closeQuietly(fis);
		}
		
		
		Matcher matcher = Pattern.compile(fullHTMLRegexP1).matcher(fileContent);	
		if (matcher.find()) {
			// Workaround for hanging regex
			String lastPart = fileContent.substring(matcher.end());
			int bodyEndtagIndex = lastPart.indexOf("</body>");
			
			resultHTML = new HTMLModel("", "");
			resultHTML.Head = matcher.group(HeadContentGroup).trim();
			resultHTML.Body = lastPart.substring(0, bodyEndtagIndex).trim();
			
			matcher = Pattern.compile(fullHTMLRegexP2)
					.matcher(lastPart.substring(bodyEndtagIndex + ((String)"</body>").length()));
			if(!matcher.find()) {
				resultHTML = null;
			}
		} 
		
		if (resultHTML == null) logger.info("File content is not a valid HTML");
		else logger.info("File content is a valid HTML");

		return resultHTML;
	}
	
	/**
	 * Checks if files referenced in given file are valid (exist, non-empty, non-corrupted)
	 * 
	 * @param filePath - path to HTML file
	 * @param optionalRegex - optional regex to match external link filename with first capture group
	 *  , to use default testRegex pass ""
	 * @return <code>true</code> if all files are valid, <code>false</code> otherwise
	 */
	public boolean checkExternaFilelLinkValidity(String filePath, String regex) {
		HTMLModel html = parseHTMLFile(filePath);
		if (html == null) {
			return false;
		}
		
		boolean result = true;
		try {
			if (regex == "") {
				regex = testRegex;
				logger.info("Will use built in regex for searching");
			}
			
			String parentDirPath = FilenameUtils.getFullPath(filePath);
			ArrayList<String> paths = new ArrayList<String>();
			Matcher matcher = Pattern.compile(regex).matcher(html.Body);	
			while(matcher.find()) {
				paths.add(parentDirPath + matcher.group(1));
			}
			
			for (String path : paths) {
				if (!checkFileIntegrity(path)) {
					result = false;
				}
			}
			
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	
		if (result)logger.info("File and all linked files passed validation");
		else logger.info("File and/or linked files didn't pass validation");
		
		return result;
	}
	
	public boolean containsKeyword(String filePath, String keyword) {
		HTMLModel html = parseHTMLFile(filePath);
		if (html == null) {
			return false;
		}
		
		boolean result = false;
		try {
			String keywordRegex = "<([^<>])*>[^<>]?" + keyword + "[^<>]*</?\\w";
			Matcher matcher = Pattern.compile(keywordRegex).matcher(html.Body);
			if (matcher.find()) {
				result = true;
			}
			
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	
		if (result)logger.info("File contains given keyword");
		else logger.info("File doesn't contain given keyword");
		
		return result;
	}
	
}
