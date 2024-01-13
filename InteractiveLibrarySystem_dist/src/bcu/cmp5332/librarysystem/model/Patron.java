package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Patron {

    private int id;
    private String name;
    private String phone;
    private String email;
    private boolean isHidden;
    private final int BOOKLIMIT = 4;
    private final List<Book> books = new ArrayList<>();
    private final List<Loan> loanHistory = new ArrayList<>();

    // TODO: implement constructor here
    public Patron(int id, String name, String phone, String email){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.isHidden = false; 
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getPhone() {
        return phone;
    } 
    
    public void setEmail(String newEmail) {
    	this.email = newEmail;
    }
    public String getEmail() {
    	return email;
    }
    public void setPhone(String newPhone) {
        this.phone = newPhone;
    }
    
    public String getDetailsShort() {
        return "Patron #" + id + " - " + name;
    }
    
    public String getDetailsLong() {
    	String result = "Patron #" + id + " - " + name + 
        		"\n  Phone number: " + phone +
        		"\n  Email: " + email;
    	
    	if(!books.isEmpty()) {
    		result += "\n  Books:";
    	}
    	
    	for(Book book: books) {
    		result += "\n    " + book.getDetailsShort();
    	}
        return result;
    }
    
    public List<Book> getBooks(){
        //TODO: Get a list of books that the patron has borrowed.
    	return Collections.unmodifiableList(books);
    }
    public boolean isBooksListEmpty() {
    	return books.isEmpty();
    }
    public int getNumberOfBorrowedBooks() {
    	return books.size();
    }
    
    public void borrowBook(Book book, LocalDate dueDate) throws LibraryException {
        this.addBook(book);
    }

    public void renewBook(Book book) throws LibraryException {
        // TODO: implementation here
    	Loan loan = book.getLoan();
    	LocalDate currentDueDate = loan.getDueDate();
    	loan.setDueDate​(currentDueDate.plusDays(7));
    }

    public void returnBook(Book book) throws LibraryException {
        if (books.contains(book)) {
            books.remove(book);
            book.returnToLibrary();
        } else {
            throw new LibraryException(book.getTitle() + "has not been borrowed by " + this.name);
        }
    }
    
    public void addBook(Book book) throws LibraryException {
    	if(books.size() >= BOOKLIMIT) {
    		throw new LibraryException(this.getDetailsShort() + " has currently borrowed " + BOOKLIMIT + " books, which is the maximum number of books a patron can borrow.");
    	}else {
    		 books.add(book);
    	}
       
    }
    
    public void removeBook(Book book) throws LibraryException {

        if(books.contains(book)) {
        	books.remove(book);
        	book.getLoan().setReturnDate(LocalDate.now());
        	book.getLoan().setTerminationStatus(true);
        	loanHistory.add(book.getLoan());
        	
        }else {
        	throw new LibraryException("This book has not been borrowed by this patron");
        }

    }
    public void removeFromLoanHistory(Loan loan) throws LibraryException {
    	if(loanHistory.contains(loan)) {
    		loanHistory.remove(loan);
    	}else {
    		throw new LibraryException("Loan not in loan history.");
    	}
    }
    public void removePatron() {
    	isHidden = true;
    }
    public void reAddPatron() {
    	isHidden = false;
    }
    public void addLoanToHistory(Loan loan) {
    	loanHistory.add(loan);
    }
    public boolean isRemoved() {
    	return isHidden;
    }
    public List<Loan> getLoanHistory(){
        //TODO: Get a list of books that the patron has borrowed.
    	return Collections.unmodifiableList(loanHistory);
    }
}
