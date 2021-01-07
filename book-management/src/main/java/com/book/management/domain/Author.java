package com.book.management.domain;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
//set up unique constraint for the authors' names
@Table(name = "author", uniqueConstraints = { @UniqueConstraint(columnNames = {"givenName", "lastName"}) })
public class Author {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=100)
	@NotNull
	@Size(min = 1, max = 100)
	private String givenName;
	
	@Column(length=100)
	@NotNull
	@Size(min=1, max=100)
	private String lastName;
	
	//to map to books
	@OneToMany(mappedBy="author", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Book> books = new ArrayList<>();
	
	public Author() {
		
	}
	
	public Author(String givenName, String lastName) {
		this.givenName = givenName;
		this.lastName = lastName;
	}
	
	public void addBook(Book book) {
		books.add(book);
		book.setAuthor(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((givenName == null) ? 0 : givenName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Author other = (Author) obj;
		if (givenName == null) {
			if (other.givenName != null)
				return false;
		} else if (!givenName.equals(other.givenName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return givenName + " " + lastName;
	}

	public String getgivenName() {
		return givenName;
	}

	public void setgivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	
	
}
