package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;

public class Book {
    
    private int id;
    private String title;
    private String author;
    private String publicationYear;
    private String publisher;
    private boolean isHidden;
    
    private Loan loan;

    public Book(int id, String title, String author, String publicationYear, String publisher) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
        this.isHidden = false;
    }

    public int getId() {
        return id;
    } 

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }
	
    public String getDetailsShort() {
        return "Book #" + id + " - " + title;
    }

    public String getDetailsLong() {
		
        String result = "Book #" + id + " - " + title +
        		"\n  Author: " + author + 
        		"\n  Publisher: " + publisher +
        		"\n  Publication year: " + publicationYear;
        
        if(loan != null) {
        	result += "\n  On loan to patron #" + loan.getPatron().getId() + " - " + loan.getPatron().getName() + ".";
        }else {
        	result += "\n  Not currently on loan.";
        }
        
        return result;
    }
    
    public boolean isOnLoan() {
        return (loan != null);
    }
    
    public String getStatus() {
         if(loan != null) {
        	 return "On loan";
         }else {
        	 return "Available";
         }
    }

    public LocalDate getDueDate() {
        return loan.getDueDate();
    }
    
    public void setDueDate(LocalDate dueDate) throws LibraryException {
        loan.setDueDate​(dueDate);
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public void returnToLibrary() {
        loan = null;
    }
    
    public void removeBook() {
    	isHidden = true;
    }
    public void reAddBook() {
    	isHidden = false;    	
    }
    public boolean isRemoved() {
    	return isHidden;
    }
}
