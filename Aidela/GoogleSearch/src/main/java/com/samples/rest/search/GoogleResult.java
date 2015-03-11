package com.samples.rest.search;

import java.util.List;

public class GoogleResult {
	private ResponseData responseData;
	 
	public ResponseData getResponseData() {
		return responseData;
	}
	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}

	static class ResponseData {
        private List<Result> results;

		public List<Result> getResults() {
			return results;
		}
		public void setResults(List<Result> results) {
			this.results = results;
		}
    }
	 
    static class Result {
        private String url;
		private String title;
		
        public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
    }
}
