package com.book.management.util;

import java.util.*;

public class BookUtil {

	public static final String NOT_FOUND = "Could not find the book. Please try again.";
	public static final String NONE_FOUND = "Could not find any book. Please try again.";
	public static final String Error = "Something went wrong... Please try again.";

	private static final List<String> bookMessages = new ArrayList<String>();
	private static final List<String> bookSelectionMessages = new ArrayList<String>();
	private static final List<String> bookDetailMessages = new ArrayList<String>(); 
	
	static {
		bookMessages.add("Successfully found the books: ");
		bookMessages.add("Here are the books I found: ");
		bookMessages.add("Here are the results: ");
		
		bookSelectionMessages.add("Which book would you like to know more about?");
		bookSelectionMessages.add("Select a book you would like to learn more about");
		bookSelectionMessages.add("Choose a book for more detail");
		
		bookDetailMessages.add("Here are the details of the book: ");
		bookDetailMessages.add("Some details about the book: ");
		bookDetailMessages.add("This is what I know about the book: ");
	}
	
	public static String getBookMsg() {
		int i = (int) Math.random() * bookMessages.size();
		
		return bookMessages.get(i);
	}
	
	public static String getBookSelectionMsg() {
		int i = (int) Math.random() * bookSelectionMessages.size();
		
		return bookSelectionMessages.get(i);
	}
	
	public static String getBookDetailMsg() {
		int i = (int) Math.random() * bookDetailMessages.size();
		
		return bookDetailMessages.get(i);
	}
}
