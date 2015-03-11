package com.samples.rest.rally;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.google.gson.JsonObject;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.CreateRequest;
import com.rallydev.rest.request.DeleteRequest;
import com.rallydev.rest.response.CreateResponse;
import com.rallydev.rest.response.DeleteResponse;


public class App {
    public static void main(String[] args)  {
    	
    	RallyRestApi restApi = null;
    	
    	try {
    		restApi = new RallyRestApi(new URI("https://rally.eng.vmware.com"), "akaramyan@vmware.com", "4133521VMw@#");
    		
    		JsonObject newDefect = new JsonObject();
    		newDefect.addProperty("Name", "Test defect");
    		CreateRequest createRequest = new CreateRequest("defect", newDefect);
    		CreateResponse createResponse = restApi.create(createRequest);
    		System.out.printf("create: %s \n", createResponse.wasSuccessful() ? "successfull" : "fail");
    		
//    		// TODO: get ref for created object programmatically before delete
//    		DeleteRequest deleteRequest = new DeleteRequest("/defect/7355969128");
//    		DeleteResponse deleteResponse = restApi.delete(deleteRequest);
//    		System.out.printf("delete: %s \n", deleteResponse.wasSuccessful() ? "successfull" : "fail");

    	} catch (IOException | URISyntaxException ex) {
    		
    	} finally {
			try {
				restApi.close();
			} catch (IOException e) {}
    	}
    	
    }
}
