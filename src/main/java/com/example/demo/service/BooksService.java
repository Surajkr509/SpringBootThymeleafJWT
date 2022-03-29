package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.demo.model.Books;
import com.example.demo.repository.BooksRepository;

@Service

public class BooksService {

	@Autowired
	BooksRepository booksRepository;

	public String save(Books books) {
		booksRepository.save(books);
		return null;
	}

	public List<Books> list() {
		return booksRepository.findAll();
	}

	public Books get(Long id) {
		Optional<Books> bookData = booksRepository.findById(id);
		return bookData.isPresent() ? bookData.get() : null;
	}

	public Books update(Books books) {

		if (books.getId() == null)
			return null;
		Optional<Books> bookLib = booksRepository.findById(books.getId());
		if (bookLib.isPresent()) {

			Books book = bookLib.get();
			book.setBookName(books.getBookName());
			book.setAuthor(books.getAuthor());
			book.setYearOfPublished(books.getYearOfPublished());
			return booksRepository.save(book);

		} else
			return null;
	}

	public String delete(Long id) {
		if (booksRepository.findById(id).isPresent()) {
			booksRepository.deleteById(id);
		}
		return null;
	}

	public Page<Books> filter(Integer pageNo,Integer pageSize,String sortBy,String sortDirection){	
		if(pageNo==null || pageNo<0)
			pageNo=0;
		
		if(pageSize==null || pageSize<0)
			pageSize=10;
		
		if(sortBy==null || sortBy.trim().equals(""))
			sortBy="id";
		
		Sort sort = Sort.by(Direction.ASC, sortBy);
		
		if(sortDirection==null || sortDirection.trim().equals("") || sortDirection.trim().equals("desc")) 
			sort = Sort.by(Direction.DESC, sortBy);
		
		Pageable pageable=PageRequest.of(pageNo, pageSize, sort);
		
		return booksRepository.findAll(pageable);
	}

}
