package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Books {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String bookName;
	private String author;
	private String yearOfPublished;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getYearOfPublished() {
		return yearOfPublished;
	}
	public void setYearOfPublished(String yearOfPublished) {
		this.yearOfPublished = yearOfPublished;
	}
	
}
