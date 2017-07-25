package com.samples.rest.rally;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.CreateRequest;
import com.rallydev.rest.request.GetRequest;
import com.rallydev.rest.request.QueryRequest;
import com.rallydev.rest.request.UpdateRequest;
import com.rallydev.rest.response.CreateResponse;
import com.rallydev.rest.response.GetResponse;
import com.rallydev.rest.response.QueryResponse;
import com.rallydev.rest.response.UpdateResponse;
import com.rallydev.rest.util.Fetch;
import com.rallydev.rest.util.QueryFilter;
import com.rallydev.rest.util.Ref;

public class App {

	public static void main(String[] args) throws URISyntaxException, IOException {

		RallyRestApi restApi = null;

		try {

			restApi = new RallyRestApi(new URI("https://rally1.rallydev.com"),
					"api_key");
			restApi.setApplicationName("TestcaseExample");
			System.out.println("Querying for get specific testplan...");

			QueryRequest testCaseRequest = new QueryRequest("TestCase");

			testCaseRequest
					.setFetch(new Fetch("FormattedID", "Name", "WorkProduct", "LastVerdict", "LastRun", "LastBuild"));
			testCaseRequest.setQueryFilter(new QueryFilter("FormattedID", "=", "TC84470"));

			QueryResponse testCaseQueryResponse = restApi.query(testCaseRequest);
			if (testCaseQueryResponse.getTotalResultCount() == 0) {
				System.out.println("Cannot find test case : " + "TC84470");
				return;
			}

			JsonObject testCaseJsonObject = testCaseQueryResponse.getResults().get(0).getAsJsonObject();
			System.out.println(testCaseJsonObject);
			String testCaseRef = testCaseJsonObject.get("_ref").getAsString();
			System.out.println(testCaseRef);
			System.out.println("ID: " + testCaseJsonObject.get("FormattedID").getAsString() + "   Name: "
					+ testCaseJsonObject.get("Name").getAsString() + "   \nLastVerdict: "
					+ testCaseJsonObject.get("LastVerdict").getAsString() + "   LastRun: "
					+ testCaseJsonObject.get("LastRun").getAsString() + "   LastBuild: "
					+ testCaseJsonObject.get("LastBuild").getAsString()

			);
			System.out.println("done");

			/*JsonObject updatedTestCase = new JsonObject();
			updatedTestCase.addProperty("LastBuild", "Fail");
			updatedTestCase.addProperty("Description", "Rally Rest APi Test");
			UpdateRequest updateRequest = new UpdateRequest("/testcase/73659799828", updatedTestCase);
			UpdateResponse updateResponse = restApi.update(updateRequest);
			JsonObject obj = updateResponse.getObject().getAsJsonObject();
			System.out.println(obj);
			System.out.println(obj.get("LastVerdict").getAsString());*/
			
			
			JsonObject newTestCaseResult = new JsonObject();
			//newTestCaseResult.addProperty("Workspace", testCaseJsonObject.get("Workspace").getAsJsonObject().get("_ref").getAsString());
            newTestCaseResult.addProperty("LastVerdict", "Fail");
            newTestCaseResult.addProperty("Date", "2014-03-07T18:00:00.000Z");
            newTestCaseResult.addProperty("Notes", "Some Scheduled Test");
            newTestCaseResult.addProperty("Build", "2.0");
           // newTestCaseResult.addProperty("Tester", userRef);
            newTestCaseResult.addProperty("TestCase", "/testcase/73659799828");

            
            UpdateRequest createRequest = new UpdateRequest(testCaseRef, newTestCaseResult);
            UpdateResponse createResponse = restApi.update(createRequest); 
            
            if (createResponse.wasSuccessful()) {

                System.out.println(String.format("Created %s", createResponse.getObject().get("_ref").getAsString()));          

                //Read Test Case
                String ref = Ref.getRelativeRef(createResponse.getObject().get("_ref").getAsString());
                System.out.println(String.format("\nReading Test Case Result %s...", ref));
                GetRequest getRequest = new GetRequest("https://rally1.rallydev.com/slm/webservice/v2.0/testcase/73659799828");
                getRequest.setFetch(new Fetch("LastVerdict"));
                GetResponse getResponse = restApi.get(getRequest);
                JsonObject obj = getResponse.getObject();
                System.out.println(String.format(obj.get("LastVerdict").getAsString()));                 
            }

		} catch (Exception e) {
			System.out.print(":"+e);
		} finally {
			// Release resources
			restApi.close();
		}
	}

}