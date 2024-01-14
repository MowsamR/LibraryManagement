package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;

/**
 * Book class models a book in the library.
 * The book has fields for different book properties such as title and author and also has an id field that represents the book's unique id in the library system.
 * In addition, the class has a loan field of type Loan that holds information about a book that is on loan.
 * The class also offers getter and setter methods to get and set the values of the different properties and methods that change the status of the book when it gets borrowed or returned to the library.
 */
public class Book {

    private int id;
    private String title;
    private String author;
    private String publicationYear;
    private String publisher;
    private boolean isHidden;

    private Loan loan;

    /**
     * The constructor initialises a Book object using the basic mandatory fields a book should have to exist in the library.
     * Additional fields can be set after initialisation using the class's setter methods 
     * @param id - the book's id.
     * @param title - the book's title.
     * @param author - the book' author.
     * @param publicationYear - the book's publication year. 
     * @param publisher - the book's publisher.
     */
    public Book(int id, String title, String author, String publicationYear, String publisher) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
        this.isHidden = false;
    }

    /** Get the book's id.
     * @return the id of the Book as an integer.
     */
    public int getId() {
        return id;
    }

    /** Set the book's id
     * @param id - the integer value that will be set as book id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Get the book's title.
     * @return the title of the Book as a string.
     */
    public String getTitle() {
        return title;
    }

    /** Set the book's title
     * @param title - the string value that will be set as book's title.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /** Get the book's author.
     * @return the author of the Book as a string.
     */
    public String getAuthor() {
        return author;
    }
    
    /** Set the book's author
     * @param author - the string value that will be set as book's author.
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    
    /** Get the book's publisher.
     * @return the publisher of the Book as a string.
     */
    public String getPublisher() {
        return publisher;
    }

    /** Set the book's publisher
     * @param publisher - the string value that will be set as book's publisher.
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
    /** Get the book's publisher.
     * @return the publisher of the Book as a string.
     */
    public String getPublicationYear() {
        return publicationYear;
    }

    /** Set the book's publication year
     * @param publicationYear - the string value that will be set as book's publication year.
     */
    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }
    
    /** Get the book's Loan object that holds information about a book that is on loan.
     * @return the value of the book's loan field
     */
    public Loan getLoan() {
        return loan;
    }

    /** Get the book's Loan object that holds information about a book that is on loan
     * @param loan - the Loan object to be set on the book.
     */
    public void setLoan(Loan loan) {
        this.loan = loan;
    }
	
    /** Get the due date of a book that is on loan.
     * @return the due date of a book if the book is on loan.
     */
    public LocalDate getDueDate() {
        return loan.getDueDate();
    }
    
    /** Set the due date of a book. <br>
     * The function sets (changes) the due date of a book that is currently on loan.
     * If the book is not on loan, an exception should be thrown, notifying the user that the due date can't be set because the book is not on loan.
     * @param dueDate - the LocalDate value that will be set as the book's due date
     * @throws LibraryException if the book is not on loan.
     */
    public void setDueDate(LocalDate dueDate) throws LibraryException {
        if(loan != null) {
        	loan.setDueDate​(dueDate);	
        }else {
        	throw new LibraryException(this.getDetailsShort() + " is not on loan.");
        }
    }
    
    /** Get short description for the book.
     * @return the book's short description as a string.
     */
    public String getDetailsShort() {
        return "Book #" + id + " - " + title;
    }
    
    /** Get a long and detailed description for the book.
     * The method prints the book id, author, publisher, publication year, and a list of all the books currently borrowed by the user.
     * @return the book's long description as a string.
     */
    public String getDetailsLong() {

        String result = "Book #" + id + " - " + title +
                "\n  Author: " + author +
                "\n  Publisher: " + publisher +
                "\n  Publication year: " + publicationYear;

        if (loan != null) {
            result += "\n  On loan to patron #" + loan.getPatron().getId() + " - " + loan.getPatron().getName() + ".";
        } else {
            result += "\n  Not currently on loan.";
        }

        return result;
    }
    
    /** Get the book's status.
     * The function returns "On loan" if the book is on loan and "Available" if the book is available.
     * @return the book status as String.
     */
    public String getStatus() {
        if (loan != null) {
            return "On loan";
        } else {
            return "Available";
        }
    }
    
    /** Check if the book is on loan.
     * The function checks if the book has a Loan associated with it and returns the results.
     * @return true if the book is on loan and false if the book is available
     */
    public boolean isOnLoan() {
        return (loan != null);
    }
    
    /** Return the book to the library
     * This function is used to unset any loan information that exists for the book.
     */
    public void returnToLibrary() {
        loan = null;
    }
    
    /** Remove the book from the library.
     * This function is used to change the isHidden value to true, when deleting the book from the library.
     */
    public void removeBook() {
    	this.isHidden = true;
    }
    
    /** Undo the 'remove from library' process.
     * This function is used when the book could not be successfully removed from the library. As a result, the changes need to be undone.
     */
    public void reAddBook() {
        isHidden = false;
    }
    
    
    /** Checks the hidden status of the book.
     * @return returns true if the book is supposed to be hidden and is not accessible
     */
    public boolean isRemoved() {
        return this.isHidden;
    }

    @Override
    public String toString() {
        return getDetailsShort();
    }
}
