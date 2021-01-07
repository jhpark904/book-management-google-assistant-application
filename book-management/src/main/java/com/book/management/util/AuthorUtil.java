package com.book.management.util;

import java.util.*;

public class AuthorUtil {

	public static final String NOT_FOUND = "Author could not be found. Please try again.";
	
	// list of authors intent requests
	public static final List<String> authorMessages = new ArrayList<>();
	
	//list of authors intent response
	public static final List<String> authorSelectionMessages = new ArrayList<>();
	
	// add messages to the author intent
	static {
		authorMessages.add("Here are all the authors: ");
		authorMessages.add("The authors that we have are: ");
		authorMessages.add("Here are the authors that I found: ");
		
		authorSelectionMessages.add("Which author would you like to choose?");
		authorSelectionMessages.add("Which author would you like to explore more?");
		authorSelectionMessages.add("Tell me the author that you want to learn more about");
	}
	
	//choose a message randomly from the lists
	public static String pickAuthorMessage() {
		int i = (int) (Math.random() * authorMessages.size());
		
		return authorMessages.get(i);
	}
	
	public static String pickAuthorSelectionMessage() {
		int i = (int) (Math.random() * authorSelectionMessages.size());
		
		return authorSelectionMessages.get(i);
	}
}
