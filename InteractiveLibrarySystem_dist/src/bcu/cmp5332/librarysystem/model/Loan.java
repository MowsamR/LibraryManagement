package bcu.cmp5332.librarysystem.model;

import java.time.LocalDate;

/*Loan class models an object that holds information about a book that is on loan.
The class has as fields a Patron object which represents the patron that has borrowed the book,
a Book object that represents the book that is on loan and two LocalDate objects
that represent the date the loan started and its due date.

The class also offers the basic getter and setter methods to get and set the class's different properties. */

public class Loan {
    
    private Patron patron;
    private Book book;
    private LocalDate startDate;
    private LocalDate dueDate;
    private boolean isTerminated;
    private LocalDate returnDate;

    public Loan(Patron patron, Book book, LocalDate startDate, LocalDate dueDate) {
        // TODO: implementation here (Loan Constructor)
        this.patron = patron;
        this.book = book;
        this.startDate = startDate;
        this.dueDate = dueDate;
        
        isTerminated = false;
        returnDate = null;
    }
    
    // TODO: implementation of Getter and Setter methods
    public Patron getPatron() {
        return patron;
    }

    public void setPatron​(Patron patron) {
        this.patron = patron;
    }

    public Book getBook() {
        return book;
    }

    public void setBook​(Book book) {
        this.book = book;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate​(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public void setDueDate​(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    public boolean getTerminationStatus() {
    	return isTerminated;
    }
    
    public void setTerminationStatus(boolean isTerminated) {
    	this.isTerminated = isTerminated;
    }
    
    public LocalDate getReturnDate() {
    	return returnDate;
    }
    
    public void setReturnDate(LocalDate returnDate) {
    	this.returnDate = returnDate;
    }

    
    public void terminateLoan() {
    	isTerminated = true;
    	returnDate = LocalDate.now();
    }
    
    // This method is used to roll back changes in case the program is not able to store the changes in the backend storage.
    public void unTerminateLoan() {
    	isTerminated = false;
    	returnDate = null;
    }
    


}
 