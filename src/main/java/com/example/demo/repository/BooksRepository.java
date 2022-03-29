package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Books;

public interface BooksRepository extends JpaRepository<Books, Long> {
	
//	public Page<Books> findByBookNameAndAuthorAndYearOfPublished(String bookName,String author,String yearOfPublished,Pageable pageable );
	public Page<Books> findAll(Pageable pageable );
}
