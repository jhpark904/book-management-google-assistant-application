package com.book.management.service;

import java.util.*;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.management.domain.Author;
import com.book.management.repository.AuthorRepository;
import com.book.management.util.AuthorUtil;
import com.book.management.util.IntentUtil;
import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;

@Service
@Transactional
public class AuthorService extends DialogflowApp{
	private Logger logger = LoggerFactory.getLogger(AuthorService.class);
	
	@Autowired
	private AuthorRepository ar;
	
	@ForIntent(IntentUtil.LIST_AUTHORS)
	public ActionResponse findAll(ActionRequest request) {
		logger.info("Executing " + IntentUtil.LIST_AUTHORS);
		
		// find authors list from the DB
		List<Author> authorsList = ar.findAll();
		
		// create response to send back to google assistant
		StringBuilder response = new StringBuilder(AuthorUtil.pickAuthorMessage());
		
		// use comma delimited authors
		StringJoiner sj = new StringJoiner(", ");
		authorsList.forEach(author -> sj.add(author.toString()));
		
		// add authors to response
		response.append(sj);
		response.append(". ");
		
		// add a follow up message for the user
		response.append(AuthorUtil.pickAuthorSelectionMessage());
		
		// build the ActionResponse and add the response
		ResponseBuilder rb = getResponseBuilder(request).add(response.toString());
		ActionResponse ar = rb.build();
		logger.info(ar.toJson());
		
		return ar;
		
	}
}
