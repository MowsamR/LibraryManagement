package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/** Patron class models a patron (member) of the library. <br>
 * The class has fields for different patron properties such as name and phone number and has an id field that represents the patron's unique id in the library.
 * In addition, the class has a list of the books that a patron has borrowed. <br>
 * The class also offers getter and setter methods to get and set the values of the different properties and methods that allow for borrowing, returning and renewing books.
 */
public class Patron {

    private int id;
    private String name;
    private String phone;
    private String email;
    private boolean isHidden;
    private final int BOOKLIMIT = 4;
    private final List<Book> books = new ArrayList<>();
    private final List<Loan> loanHistory = new ArrayList<>();

    
    /**Creates a new Patron instance.
     * <br>The constructor initialises a Patron object using the basic mandatory fields a patron should have to exist in the library
     * @param id - the patron's id.
     * @param name - the patron's name
     * @param phone - the patron's phone
     * @param email - the patron's email
     */
    public Patron(int id, String name, String phone, String email){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.isHidden = false;
    }
    
    

    /** Get the patron's id.
     * @return the patron's id as integer.
     */
    public int getId() {
        return id;
    }

    /** Set the patron's id.
     * @param id - the integer value that will be set as patron's id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Get the patron's name.
     * @return the patron's name as String.
     */
    public String getName() {
        return name;
    }

    /** Set the patron's name.
     * @param newName - the String value that will be set as patron's name
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /** Get the patron's phone.
     * @return the patron's phone as String.
     */
    public String getPhone() {
        return phone;
    }
    
    /** Set the patron's phone.
     * @param newPhone - the String value that will be set as patron's phone.
     */
    public void setPhone(String newPhone) {
        this.phone = newPhone;
    }
    
    /** Get the patron's email.
     * @return the patron's email as String.
     */
    public String getEmail() {
        return email;
    }
    
    /** Set the patron's email.
     * @param newEmail - the String value that will be set as patron's email.
     */
    public void setEmail(String newEmail) {
    	this.email = newEmail;
    }
    
    /** Get short description for the patron.
     * @return the patron's short description as a string.
     */
    public String getDetailsShort() {
        return "Patron #" + id + " - " + name;
    }
    
    /** Get a long and detailed description for the patron.
     * The method prints the patron's id, name, publisher, phone number, email address and a list of all the books currently borrowed by the user.
     * @return the patron's long description as a string.
     */
    public String getDetailsLong() {
        String result = "Patron #" + id + " - " + name +
                "\n  Phone number: " + phone +
                "\n  Email: " + email;

        if (!books.isEmpty()) {
            result += "\n  Books:";
        }

        for (Book book : books) {
            result += "\n    " + book.getDetailsShort();
        }
        return result;
    }
    
    /** Get a list of books that the patron has borrowed.
     * @return an unmodifiable list of books that the patron has borrowed.
     */
    public List<Book> getBooks(){
    	return Collections.unmodifiableList(books);
    }
    
    /** Checks whether the book list is empty, which means whether the patron is borrowing anything currently.
     * @return true if the book list is empty, otherwise false.
     */
    public boolean isBooksListEmpty() {
        return books.isEmpty();
    }
    
    /** Get the number of the books that is currently loaned to the patron. 
     * @return the number of books currently loaned the the patron as an integer.
     */
    public int getNumberOfBorrowedBooks() {
        return books.size();
    }
    
    /** Borrow a book from the library. <br>
     * The method creates a new Loan object that associates a book with the patron and updates the book with this loan information.
     * The function also adds the book to the patron's list of borrowed books. <br>
     * If the book is on loan by another patron, an exception is thrown with a message saying that the book is currently on loan.
     * @param book - the book that will be borrowed by the patron.
     * @param dueDate - the LocalDate value that will be set as the loan's due date.
     * @throws LibraryException if the book is currently on loan to another patron.
     */
    public void borrowBook(Book book, LocalDate dueDate) throws LibraryException {
        this.addBook(book);
    }

    /** Renew the loan's due date for a book that is already on loan.<br>
     * The method changes (renews) the due date of a book that is currently on loan by the patron.<br>
     * If the book to be renewed is not on loan by the patron, an exception is thrown with a message saying that the book is not on loan to this patron.
     * @param book - the book to be renewed.
     * @param numberOfDays - number of days that the loan will be extended for.
     */
    
    public void renewBook(Book book, int numberOfDays) {
    	Loan loan = book.getLoan();
    	LocalDate currentDueDate = loan.getDueDate();
    	loan.setDueDate​(currentDueDate.plusDays(numberOfDays));
    }

    /** Renews the book from the patron's list of borrowed books. <br>
     * The method also terminates the loan by setting its return date and termination status. <br>
     * The method adds the returned book
     * @param book - the book to be returned to the library.
     * @throws LibraryException if the book is not on loan to this patron.
     */
    public void returnBook(Book book) throws LibraryException {
        if (books.contains(book)) {
            books.remove(book);
        	book.getLoan().setReturnDate(LocalDate.now());
        	book.getLoan().setTerminationStatus(true);
        	loanHistory.add(book.getLoan());
            book.returnToLibrary();
        } else {
            throw new LibraryException(book.getTitle() + "has not been borrowed by " + this.getDetailsShort());
        }
    }
    
    /** Add a book to the patron's list of borrowed books.
     * @param book - the book to be added to the patron's list of borrowed books
     * @throws LibraryException if the patron has exceeded the book limit.
     */
    public void addBook(Book book) throws LibraryException {
    	if(books.size() > BOOKLIMIT) {
    		throw new LibraryException(this.getDetailsShort() + " has currently borrowed " + BOOKLIMIT + " books, which is the maximum number of books a patron can borrow.");
    	}else {
    		 books.add(book);
    	}
       
    }

    /** The method removes a loan from the loanHistory array. <br>
     * The method is used when loan changes could not be stored in the text storage file and changes need to be undone.
     * @param loan - the loan object to be removed.
     * @throws LibraryException
     */
    public void removeFromLoanHistory(Loan loan) throws LibraryException {
        if (loanHistory.contains(loan)) {
            loanHistory.remove(loan);
        } else {
            throw new LibraryException("Loan not in loan history.");
        }
    }
    
    /** Deletes the patron by setting isHidden to true.
     */
    public void removePatron() {
    	this.isHidden = true;
    }
    
    /** Removes the book from the patron's books list. <br>
     * This method is used when the borrow class is not able to store the data in the text storage files, and the changes need to be undone.
     * @param book - the book to be removed.
     * @throws LibraryException if the book is not loaned by the patron.
     */
    public void removeBook(Book book) throws LibraryException {
    	if(book.getLoan().getPatron() == this) {
    		books.remove(book);
    	}else {
    		throw new LibraryException(book.getTitle() + "has not been borrowed by " + this.getDetailsShort());
    	}
    }
    /**  The method makes the patron visible again. <br>
     * The method is used when loan changes could not be stored in the text storage file and changes need to be undone.
     */
    public void reAddPatron() {
        this.isHidden = false;
    }
    
    /** Adds a loan object the loanHistory array.
     * @param loan - the loan object to be added to the loanHistory array.
     */
    public void addLoanToHistory(Loan loan) {
        loanHistory.add(loan);
    }
    
    /** Get the value of isHidden.
     * @return true if the patron is supposed to be hidden and false otherwise.
     */
    public boolean isRemoved() {
        return this.isHidden;
    }
    
    /** Get unmodifiable list of the loanHistory array
     * @return returns an unmodifiable list of the loanHistory array.
     */
    public List<Loan> getLoanHistory(){
    	return Collections.unmodifiableList(loanHistory);
    }

    @Override
    public String toString() {
        return getDetailsShort();
    }
}
