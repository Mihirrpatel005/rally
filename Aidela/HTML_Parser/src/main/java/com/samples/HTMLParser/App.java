package com.samples.HTMLParser;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String filePath = "C:\\Users\\akaramyan\\Desktop\\overview.html";
        HTMLHandler.getInstance().checkHTMLFileValidity(filePath);
    }
}
