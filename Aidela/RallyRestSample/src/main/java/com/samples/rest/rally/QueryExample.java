package com.samples.rest.rally;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.QueryRequest;
import com.rallydev.rest.response.QueryResponse;
import com.rallydev.rest.util.Fetch;
import com.rallydev.rest.util.QueryFilter;

public class QueryExample {

    public static void main(String[] args) throws URISyntaxException, IOException {

        //Create and configure a new instance of RallyRestApi
        RallyRestApi restApi = new RallyRestApi(new URI("https://rally1.rallydev.com"),"_1MYIpayTcSUgRzjf3HrCR6BSTIp0C2lkIVNxg9KDg");
        restApi.setApplicationName("QueryExample");

        try {

            System.out.println("Querying for top 5 highest priority unfixed defects...");

            QueryRequest defects = new QueryRequest("defect");

            defects.setFetch(new Fetch("FormattedID", "Name", "State", "Priority"));
            defects.setQueryFilter(new QueryFilter("State", "<", "Fixed"));
            defects.setOrder("Priority ASC,FormattedID ASC");

            //Return up to 5, 1 per page
            defects.setPageSize(1);
            defects.setLimit(5);

            QueryResponse queryResponse = restApi.query(defects);
            if (queryResponse.wasSuccessful()) {
                System.out.println(String.format("\nTotal results: %d", queryResponse.getTotalResultCount()));
                System.out.println("Top 5:");
                for (JsonElement result : queryResponse.getResults()) {
                    JsonObject defect = result.getAsJsonObject();
                    System.out.println(String.format("\t%s - %s: Priority=%s, State=%s",
                            defect.get("FormattedID").getAsString(),
                            defect.get("Name").getAsString(),
                            defect.get("Priority").getAsString(),
                            defect.get("State").getAsString()));
                }
            } else {
                System.err.println("The following errors occurred: ");
                for (String err : queryResponse.getErrors()) {
                    System.err.println("\t" + err);
                }
            }

        } finally {
            //Release resources
            restApi.close();
        }
    }
}
