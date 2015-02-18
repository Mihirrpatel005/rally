package com.samples.HTMLParser;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class App 
{
	public static Properties properties = new Properties();
	
    public static void main( String[] args )
    {
    	try {
    		FileInputStream is = new FileInputStream(new File(Constants.PROPERTIES_PATH));
			properties = new Properties();
			properties.load(is);
			is.close();
			
			String filePath = properties.getProperty("defaultpath", "");
			HTMLHandler.getInstance().containsKeyword(filePath, "Administrator");
	    	HTMLHandler.getInstance().checkExternaFilelLinkValidity(filePath, "");
			
		} catch (Exception e) {
			//
		}
    }
}
