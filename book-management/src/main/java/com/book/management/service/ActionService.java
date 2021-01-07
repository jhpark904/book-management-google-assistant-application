package com.book.management.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.book.management.util.IntentUtil;
import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;

@Service
public class ActionService extends DialogflowApp {
	
	private Logger logger = LoggerFactory.getLogger(ActionService.class);
	
	@ForIntent(IntentUtil.FIND_BOOK_BY_TITLE)
	public ActionResponse findBookByTitle(ActionRequest request) {
		logger.info("Executing Intent " + IntentUtil.FIND_BOOK_BY_TITLE);
		
		StringBuilder response = new StringBuilder();
		response.append("Tell me the title that you would like to search for.");
		
		ResponseBuilder rb = getResponseBuilder(request).add(response.toString());
		ActionResponse ar = rb.build();
		
		return ar;
	}
	
	@ForIntent(IntentUtil.LIST_ACTIONS)
	public ActionResponse listActions(ActionRequest request) {
		logger.info("Executing Intent " + IntentUtil.LIST_ACTIONS);
		
		StringBuilder response = new StringBuilder();
		response.append("You can search books by author, genre, publication year, or title. ");
		response.append("What would you like to do?");
		
		// build the response and send it back
		ResponseBuilder rb = getResponseBuilder(request).add(response.toString());
		ActionResponse ar = rb.build();
		
		return ar;
	}
	
	public String getIntentName(String body) {
		// convert body to Json object
		JSONObject bodyJson = new JSONObject(body);
		
		// get query result
		JSONObject queryResultObject = bodyJson.getJSONObject("queryResult");
		
		// get intent
		JSONObject intentObject = queryResultObject.getJSONObject("intent");
		
		// get name of intent
		return intentObject.getString("displayName").toString();
	}
}
