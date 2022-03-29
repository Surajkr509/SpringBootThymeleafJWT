package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Books;
import com.example.demo.service.BooksService;

@Controller
@RequestMapping("/books")
public class BooksController {
	@Autowired
	BooksService booksService;

	Logger logger;

	@PostConstruct
	public void init() {
		logger = LoggerFactory.getLogger(this.getClass());
		logger.info("BooksController Initialised");
	}

	@PostMapping
	public ModelAndView addBooks(@ModelAttribute Books books) {
		logger.info("ADD REQ");
		booksService.save(books);
		return this.getBooks(null, null, null, null);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addBook(Books books) {
		logger.info("ADD REQ");
		return ResponseEntity.ok(booksService.save(books));
		
	}

	@GetMapping("/new")
	public String goToAddBook() {
		return "/add-book";

	}

	@GetMapping("/edit/{id}")
	public ModelAndView editBook(@PathVariable Long id) {

		ModelAndView modelAndView = new ModelAndView();
		Books book = booksService.get(id);
		if (book == null)
			return this.getBooks(null, null, null, null);

		modelAndView.setViewName("/edit-book");
		modelAndView.addObject("bookinfo", book);

		return modelAndView;

	}

	@GetMapping("/view/{id}")
	public ModelAndView viewBook(@PathVariable Long id) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/view-book");
		Books book = booksService.get(id);
		if (book == null)
			return this.getBooks(null, null, null, null);

		modelAndView.addObject("bookdetails", book);

		return modelAndView;

	}


	@GetMapping("/get/{id}")
	public ResponseEntity<?> getBook(@PathVariable Long id) {
		return ResponseEntity.ok(booksService.get(id));

	}
	
	@PostMapping("/update/{id}")
	public ResponseEntity<?> update(Books books){
		return ResponseEntity.ok(booksService.update(books));

	}

	@GetMapping
	public ModelAndView getBooks(@RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String sortBy,
			@RequestParam(required = false) String sortDirection) {
		
		if(pageNo == null)
			pageNo = 0;
		
		if(pageSize == null)
			pageSize=10;
				
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("/books");

		Page<Books> bookSearchResult = booksService.filter(pageNo, pageSize, sortBy, sortDirection);
		List<Books> bookdata = bookSearchResult.getContent();
		modelAndView.addObject("booklist", bookdata);
		modelAndView.addObject("pageNumber", bookSearchResult.getNumber());
		modelAndView.addObject("sortBy", sortBy);
		modelAndView.addObject("sortDirection", sortDirection);
		modelAndView.addObject("pageSize", pageSize);
		List<Integer> pages = new ArrayList<>();
		List<Integer> pageSizes = new ArrayList<>();
		pageSizes.add(5);
		pageSizes.add(10);
		pageSizes.add(15);
		pageSizes.add(20);
		pageSizes.add(25);
		pageSizes.add(30);
		for(int i=0;i<bookSearchResult.getTotalPages(); i++) {
			pages.add(i);
		}
		modelAndView.addObject("pages", pages);
		modelAndView.addObject("pageSizes", pageSizes);
		modelAndView.addObject("totalPages", bookSearchResult.getTotalPages());
		modelAndView.addObject("totalElements", bookSearchResult.getTotalElements());
		return modelAndView;

	}
	@GetMapping("/bookList")
	public ResponseEntity<?> getBook(@RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String sortBy,
			@RequestParam(required = false) String sortDirection) {
		
		if(pageNo == null)
			pageNo = 0;
		
		if(pageSize == null)
			pageSize=10;
		Page<Books> bookSearchResult = booksService.filter(pageNo, pageSize, sortBy, sortDirection);
		List<Books> bookdata = bookSearchResult.getContent();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("booklist", bookdata);
		data.put("pageNumber", bookSearchResult.get());
		data.put("sortBy", sortBy);
		data.put("sortDirection", sortDirection);
		data.put("pageSize", pageSize);
		List<Integer> pages = new ArrayList<>();
		List<Integer> pageSizes = new ArrayList<>();
		pageSizes.add(5);
		pageSizes.add(10);
		pageSizes.add(15);
		pageSizes.add(20);
		pageSizes.add(25);
		pageSizes.add(30);
		for(int i=0;i<bookSearchResult.getTotalPages(); i++) {
			pages.add(i);
		}
		data.put("pages", pages);
		data.put("pageSizes", pageSizes);
		data.put("totalPages", bookSearchResult.getTotalPages());
		data.put("totalElements", bookSearchResult.getTotalElements());
		return ResponseEntity.ok(data);

	}


	@PostMapping("/update")
	public ModelAndView updateBooks(@ModelAttribute Books books) {
		logger.info("IN REQ");
		booksService.update(books);
		return this.getBooks(null, null, null, null);
	}

	@GetMapping("/{id}")
	public ModelAndView delete(@PathVariable Long id) {
		booksService.delete(id);
		return this.getBooks(null, null, null, null);
	}
}
