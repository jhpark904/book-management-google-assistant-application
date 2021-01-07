package com.book.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.management.domain.Author;

//Author and their id
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>{
	
	Author findByGivenNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(String givenName, String lastName);
}
