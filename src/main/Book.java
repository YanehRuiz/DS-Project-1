package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Book {
	
		public double fee;
	    public int id;
	    public String title;
	    public String author;
	    public String genre;
	    private LocalDate lastCheckOut;
	    public LocalDate currentDate = LocalDate.of(2023, 9, 15);
	    public LocalDate latefeeDate = LocalDate.of(2023, 8, 16);
	    public boolean checkedOut;
	   

	    public Book(int id, String title, String author, String genre, LocalDate lastCheckOut, boolean checkedOut) {
	        this.id = id;
	        this.title = title;
	        this.author = author;
	        this.genre = genre;
	        this.lastCheckOut = lastCheckOut;
	        this.checkedOut = checkedOut;
	        
	        toString();
	        calculateFees();
	    
	    }

	public int getId() { 
		return this.id;
	}
	public void setId(int id) {
		this.id=id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title=title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author=author;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre=genre;
	}
	public LocalDate getLastCheckOut() {
		return lastCheckOut;
	}
	public void setLastCheckOut(LocalDate lastCheckOut) {
		this.lastCheckOut=lastCheckOut;
	}
	public boolean isCheckedOut() {
		return false;
	}
	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut=checkedOut;
	}
	
	@Override
	public String toString() {
		/*
		 * This is supposed to follow the format
		 * 
		 * {TITLE} By {AUTHOR}
		 * 
		 * Both the title and author are in uppercase.
		 */
		return (title+ " BY "+ author).toUpperCase();
	}
	public float calculateFees() {
		/*
		 * fee (if applicable) = base fee + 1.5 per additional day
		 */
	
				
			 long daysBetweenthem = ChronoUnit.DAYS.between(lastCheckOut,currentDate);
			
			 if(daysBetweenthem>31) {
				
				double  fee = (daysBetweenthem-31)*1.5;
				return (float) fee;
			 }
		
		
		return 0.0f;
	}

	
	
	
	}
