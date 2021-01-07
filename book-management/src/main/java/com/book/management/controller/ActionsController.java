package com.book.management.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.*;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.book.management.service.ActionService;
import com.book.management.service.AuthorService;
import com.book.management.service.BookService;
import com.book.management.util.IntentUtil;

@RestController
@RequestMapping("/api/actions/")
public class ActionsController {
	
	private Logger logger = LoggerFactory.getLogger(ActionsController.class);
	
	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private ActionService actionService;
	
	@Autowired
	private BookService bookService;
	
	@PostMapping
	public ResponseEntity<?> executePostAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		logger.info("----- executing Post Action -----");
		
		//get json body from HttpServletRequest
		String body = request.getReader().lines().collect(Collectors.joining());
		
		logger.info(new JSONObject(body).toString(4));
		
		// get intent name based on the user request
		try {
			String intentName = actionService.getIntentName(body);
			
			if (intentName.equals(IntentUtil.LIST_AUTHORS)) {
				// invoke AuthorService -> list authors
				String authorJsonResponse = authorService.handleRequest(body, getHeadersMap(request)).get();
				return new ResponseEntity<String>(authorJsonResponse, HttpStatus.OK);
				
			} else if (intentName.equals(IntentUtil.LIST_BOOKS_BY_AUTHOR) || 
					intentName.equals(IntentUtil.GET_BOOKDETAILS_BY_AUTHOR) ||
					intentName.equals(IntentUtil.LIST_BOOKS_BY_GENRE) ||
					intentName.equals(IntentUtil.GET_BOOKDETAILS_BY_GENRE) ||
					intentName.equals(IntentUtil.LIST_BOOKS_BY_YEAR) ||
					intentName.equals(IntentUtil.GET_BOOKDETAILS_BY_YEAR) ||
					intentName.equals(IntentUtil.GET_BOOKDETAILS_BY_TITLE)) {
				
				// invoke bookService -> list_books_by_author intent
				String bookJsonResponse = bookService.handleRequest(body, getHeadersMap(request)).get();
				return new ResponseEntity<String>(bookJsonResponse, HttpStatus.OK);
				
			} else if (intentName.equals(IntentUtil.LIST_ACTIONS)) {
				String actionJsonResponse = actionService.handleRequest(body, getHeadersMap(request)).get();
				return new ResponseEntity<String>(actionJsonResponse, HttpStatus.OK);
				
			} else if (intentName.equals(IntentUtil.FIND_BOOK_BY_TITLE)) {
				String actionJsonResponse  = actionService.handleRequest(body, getHeadersMap(request)).get();
				return new ResponseEntity<String>(actionJsonResponse, HttpStatus.OK);
				
			} else {
				return new ResponseEntity<String>("Response could not be processed", HttpStatus.OK);
			}
			
		} catch (Exception e) {
			logger.error("Error " + e.getMessage());
			return new ResponseEntity<String>("Could not process the request", HttpStatus.OK);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> executeGetAction() {
		return new ResponseEntity<String>("ActionsController accepts only Post requests from Google Assistant", 
				HttpStatus.OK);
	}
	
	// construct headers' map sent to our intents
	private Map<String, String> getHeadersMap(HttpServletRequest request) {
		Map<String, String> headerMap = new HashMap<>();
		
		Enumeration<?> headerNamesEnumeration = request.getHeaderNames();
		while(headerNamesEnumeration.hasMoreElements()) {
			String key = (String) headerNamesEnumeration.nextElement();
			String value = request.getHeader(key);
			
			headerMap.put(key, value);
		}
		
		return headerMap;
	}

}
