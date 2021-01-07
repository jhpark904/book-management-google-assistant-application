package com.book.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.management.domain.Author;
import com.book.management.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	Book findByTitle(String title);
	
	List<Book> findByGenre(String genre);
	
	List<Book> findByAuthor(Author author);
	
	List<Book> findByPublicationYear(Integer publicationYear);
}
