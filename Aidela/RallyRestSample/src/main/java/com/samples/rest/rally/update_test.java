package com.samples.rest.rally;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;

import com.google.gson.JsonArray;
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

public class update_test {

	public static void main(String[] args) throws URISyntaxException, IOException {
		String host = "https://rally1.rallydev.com";

		// String projectRef = "/project/2222";
		// String workspaceRef = "/workspace/11111";

		RallyRestApi restApi = new RallyRestApi(new URI("https://rally1.rallydev.com"),
				"_1MYIpayTcSUgRzjf3HrCR6BSTIp0C2lkIVNxg9KDg");

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

		try {
			// Add a Test Case Result
			System.out.println(testCaseRef);
			System.out.println("Creating Test Case Result...");
			JsonObject newTestCaseResult = new JsonObject();
			java.util.Date date= new java.util.Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
			String timestamp = sdf.format(date);
			newTestCaseResult.addProperty("Verdict", "Pass");
			newTestCaseResult.addProperty("Date", timestamp);
			newTestCaseResult.addProperty("Notes", "Some Scheduled Test2");
			newTestCaseResult.addProperty("Build", "Develop_"+timestamp+"_20");
			 //newTestCaseResult.addProperty("Tester", "Mihirkumar Patel");
			newTestCaseResult.addProperty("TestCase", testCaseRef);
			newTestCaseResult.addProperty("Workspace", "/workspace/46772661387");

			CreateRequest createRequest = new CreateRequest("testcaseresult", newTestCaseResult);
			CreateResponse createResponse = restApi.create(createRequest);
			if (createResponse.wasSuccessful()) {

				System.out.println(String.format("Created %s", createResponse.getObject().get("_ref").getAsString()));

				// Read Test Case
				String ref = Ref.getRelativeRef(createResponse.getObject().get("_ref").getAsString());
				System.out.println(String.format("\nReading Test Case Result %s...", ref));
				GetRequest getRequest = new GetRequest(ref);
				getRequest.setFetch(new Fetch("Date", "Verdict"));
				GetResponse getResponse = restApi.get(getRequest);
				JsonObject obj = getResponse.getObject();
				System.out.println(String.format("my Read Test Case Result. Date = %s  Verdict = %s",
						obj.get("Date").getAsString(), obj.get("Verdict").getAsString()));
			} else {
				String[] createErrors;
				createErrors = createResponse.getErrors();
				System.out.println("Error occurred creating Test Case Result: ");
				
			}

		} finally {
			// Release all resources
			restApi.close();
		}

	}

}