package com.book.management.service;

import java.util.List;
import java.util.StringJoiner;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.management.domain.Author;
import com.book.management.domain.Book;
import com.book.management.repository.AuthorRepository;
import com.book.management.repository.BookRepository;
import com.book.management.util.AuthorUtil;
import com.book.management.util.BookUtil;
import com.book.management.util.IntentUtil;
import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;

@Service
@Transactional
public class BookService extends DialogflowApp {
	
	private Logger logger = LoggerFactory.getLogger(BookService.class);
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@ForIntent(IntentUtil.GET_BOOKDETAILS_BY_TITLE)
	public ActionResponse getBookDetialsByYear(ActionRequest request) {
		logger.info("Executing Intent " + IntentUtil.GET_BOOKDETAILS_BY_TITLE);
		
		return getBookDetails(request);
	}
	
	@ForIntent(IntentUtil.GET_BOOKDETAILS_BY_YEAR) 
	public ActionResponse getBookDetailsByYear(ActionRequest request) {
		logger.info("Executing Intent " + IntentUtil.GET_BOOKDETAILS_BY_YEAR);
		
		return getBookDetails(request);
	}
	
	@ForIntent(IntentUtil.LIST_BOOKS_BY_YEAR)
	public ActionResponse findAllByYear(ActionRequest request) {
		logger.info("Executing Intent " + IntentUtil.LIST_BOOKS_BY_YEAR);
		
		StringBuilder response = new StringBuilder();
		
		Integer publicationYear = null;
		try {
			// retrieve year from user request
			publicationYear = Integer.parseInt(request.getParameter("publicationYear").toString());
		 
		} catch(NumberFormatException e) {
			publicationYear = -1;
		}
		
		if (publicationYear == -1) {
			response.append(BookUtil.Error);
			
		} else {
			List<Book> books = bookRepository.findByPublicationYear(publicationYear);
			
			if (books != null && books.size() > 0) {
				// start building response
				response = new StringBuilder(BookUtil.getBookMsg());
				
				StringJoiner sj = new StringJoiner(", ");
				books.forEach(book -> sj.add(book.toString()));
				
				// add the books to the response
				response.append(sj +". ");
				
				// to ask the user for follow up
				response.append(BookUtil.getBookSelectionMsg());
				
			} else {
				// books not found
				response.append(BookUtil.NONE_FOUND);
			}
		}
		
		// create the response and send it back to the user
		ResponseBuilder rb = getResponseBuilder(request).add(response.toString());
		ActionResponse ar = rb.build();
		return ar;
	}
	
	@ForIntent(IntentUtil.GET_BOOKDETAILS_BY_AUTHOR)
	public ActionResponse getBookDetailsByAuthor(ActionRequest request) {
		logger.info("Executing intent " + IntentUtil.GET_BOOKDETAILS_BY_AUTHOR);
		
		return getBookDetails(request);
	}
	
	@ForIntent(IntentUtil.GET_BOOKDETAILS_BY_GENRE)
	public ActionResponse getBookDetailsByGenre(ActionRequest request) {
		logger.info("Executing Intent " + IntentUtil.GET_BOOKDETAILS_BY_GENRE);
		
		return getBookDetails(request);
	}
	
	@ForIntent(IntentUtil.LIST_BOOKS_BY_GENRE)
	public ActionResponse findAllByGenre(ActionRequest request) {
		logger.info("Execusting intent " + IntentUtil.LIST_BOOKS_BY_GENRE);
		
		StringBuilder response = new StringBuilder();
		
		// retrieve genre from the request
		String genre = request.getParameter("Genre").toString();
		
		// get the books of the genre
		List<Book> books = bookRepository.findByGenre(genre);
		
		if (books != null && books.size() > 0) {
			// start building response
			response = new StringBuilder(BookUtil.getBookMsg());
			
			StringJoiner sj = new StringJoiner(", ");
			books.forEach(book -> sj.add(book.toString()));
			
			// add the books to the response
			response.append(sj +". ");
			
			// to ask the user for follow up
			response.append(BookUtil.getBookSelectionMsg());
			
		} else {
			// books not found
			response.append(BookUtil.NONE_FOUND);
		}
		
		// create the response and send it back to the user
		ResponseBuilder rb = getResponseBuilder(request).add(response.toString());
		ActionResponse ar = rb.build();
		return ar;
	}
	
	@ForIntent(IntentUtil.LIST_BOOKS_BY_AUTHOR)
	public ActionResponse findAllByAuthor(ActionRequest request) {
		logger.info("Executing Intent " + IntentUtil.LIST_BOOKS_BY_AUTHOR);
		
		StringBuilder response = new StringBuilder();
		
		// retrieve first and last names from user request
		String givenName = request.getParameter("given-name").toString();
		String lastNameNoFormatting = request.getParameter("last-name").toString();
		String lastName = lastNameNoFormatting.substring(1, lastNameNoFormatting.length() - 1);
		
		// get author from the DB
		Author author = authorRepository.findByGivenNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(givenName, lastName);
		
		if (author != null) {
			// get list of books for the author
			List<Book> books = bookRepository.findByAuthor(author);
			
			// start building responses
			response.append(BookUtil.getBookMsg());
			
			StringJoiner sj = new StringJoiner(", ");
			books.forEach(book -> sj.add(book.toString()));
			
			response.append(sj);
			response.append(". ");
			
			// add a random message - ask user to select a book
			response.append(BookUtil.getBookSelectionMsg());
			
		} else {
			// author not found
			response.append(AuthorUtil.NOT_FOUND);
		}
		
		// create action response to be sent back
		ResponseBuilder rb = getResponseBuilder(request).add(response.toString());
		ActionResponse ar = rb.build();
		
		return ar;
	}
	
	public ActionResponse getBookDetails(ActionRequest request) {
		StringBuilder response = new StringBuilder();
		ResponseBuilder rb = null;
		
		// get book titles from the request
		String title = request.getParameter("title").toString();
		
		// find the book in the database with the same title
		Book book = bookRepository.findByTitle(title);
		
		if (book != null) {
			// start building the response - book details
			response.append(BookUtil.getBookDetailMsg());
			
			response.append(book.getTitle());
			response.append(" was published in ");
			response.append(book.getPublicationYear());
			response.append(". The genre of the book is ");
			response.append(book.getGenre());
			response.append(". Here is a summary of the book. ");
			response.append(book.getSummary());
			
			// create a responsebuilder
			rb = getResponseBuilder(request).add(response.toString()).endConversation();
		} else {
			response.append(BookUtil.NOT_FOUND);
			
			// do not end conversation yet
			rb = getResponseBuilder(request).add(response.toString());
		}
		
		//send back response to the user
		ActionResponse ar = rb.build();
		return ar;
	}
	
}
