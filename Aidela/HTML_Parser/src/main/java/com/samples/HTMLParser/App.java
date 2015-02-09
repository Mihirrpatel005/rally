package com.samples.HTMLParser;

public class App 
{
    public static void main( String[] args )
    {
    	String filePath = "C:\\Users\\akaramyan\\Desktop\\Problem_Alerts\\overview.html";
    	HTMLHandler.getInstance().containsKeyword(filePath, "Administrator");
    	HTMLHandler.getInstance().checkExternaFilelLinkValidity(filePath, "");
    }
}
