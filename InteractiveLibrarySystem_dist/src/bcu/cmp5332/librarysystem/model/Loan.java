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

    /** Create a new Loan instance. <br>
     * The constructor initialises a new Book instance using all the mandatory fields of the class.
     * @param patron the patron that borrowed the book.
     * @param book the book that is on loan.
     * @param startDate the start date of the loan as LocalDate.
     * @param dueDate the due date of the loan as LocalDate.
     */
    public Loan(Patron patron, Book book, LocalDate startDate, LocalDate dueDate) {
        this.patron = patron;
        this.book = book;
        this.startDate = startDate;
        this.dueDate = dueDate;
        
        isTerminated = false;
        returnDate = null;
    }
    
    
    /** Get the loan's patron object.
     * @return the loan's patron.
     */
    public Patron getPatron() {
        return patron;
    }

    /** Set the loan's patron object.
     * @param patron the Patron object that will be set as the loan's patron
     */
    public void setPatron​(Patron patron) {
        this.patron = patron;
    }

    /** Get the loan's book object.
     * @return the loan's book.
     */
    public Book getBook() {
        return book;
    }

    /** Set the loan's book object.
     * @param book the Book object that will be set as the loan's book.
     */
    public void setBook​(Book book) {
        this.book = book;
    }

    /** Get the loan's start date.
     * @return the loan's start date as LocalDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /** Set the loan's start date.
     * @param startDate the LocalDate value that will be set as the loan's start date
     */
    public void setStartDate​(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    /** Get the loan's due date.
     * @return the loan's due date as LocalDate
     */
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    /** Set the loan's due date.
     * @param dueDate the LocalDate value that will be set as the loan's due date
     */
    public void setDueDate​(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    /** Get the loan's termination status.
     * @return returns true if the loan has been terminated and otherwise, returns false.
     */
    public boolean getTerminationStatus() {
    	return isTerminated;
    }
    
    /** Set the loan's termination status.
     * @param isTerminated true to terminate the loan and false to undo the changes.
     */
    public void setTerminationStatus(boolean isTerminated) {
    	this.isTerminated = isTerminated;
    }
    
    /** Get the loan's return date.
     * @return the loan's return date as LocalDate.
     */
    public LocalDate getReturnDate() {
    	return returnDate;
    }
    
    /** Set the loan's return date.
     * @param returnDate the LocalDate value that will be set as the loan's return date.
     */
    public void setReturnDate(LocalDate returnDate) {
    	this.returnDate = returnDate;
    }

    
    /** Terminate the loan. <br>
     * The method changes isTerminated to true and sets to return date to the current date.
     */
    public void terminateLoan() {
    	isTerminated = true;
    	returnDate = LocalDate.now();
    }
    
    /** Undo loan termination. <br>
     * The method changes isTerminated to false and sets to return date to a null object.  <br>
     * The method is used to roll back changes in case the program is not able to store the changes in the text storage files. 
     */
    public void unTerminateLoan() {
    	isTerminated = false;
    	returnDate = null;
    }

}
 