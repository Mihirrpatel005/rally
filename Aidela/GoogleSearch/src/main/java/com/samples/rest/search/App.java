package com.samples.rest.search;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import com.google.gson.Gson;


public class App {
    public static void main( String[] args ) {
    	String addressPart1 = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0";
		String charset = "UTF-8";
		
		Scanner in = new Scanner(System.in);
		System.out.print("Please enter text to query: ");
		String query = in.nextLine();
		in.close();
		
		for (int i = 0; i < 25; i = i + 4) {
			String addressPart2= "&start=" + i + "&q=";
			
			try {
				URL url = new URL(addressPart1 + addressPart2 + URLEncoder.encode(query, charset));
				Reader reader = new InputStreamReader(url.openStream(), charset);
				GoogleResult result = new Gson().fromJson(reader, GoogleResult.class);
				
				int total = result.getResponseData().getResults().size();
				for (int j = 0; j < total; j++){
					System.out.println("Title: " + result.getResponseData().getResults().get(j).getTitle());
					System.out.println("URL: " + result.getResponseData().getResults().get(j).getUrl() + "\n");
				}
				
			} catch (Exception  ex) {
				//
			}
		}
		
		
		
		
    }
}
