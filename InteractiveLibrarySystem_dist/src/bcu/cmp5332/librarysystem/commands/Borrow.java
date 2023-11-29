package bcu.cmp5332.librarysystem.commands;


import java.time.LocalDate;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;

public class Borrow implements Command{
	private int bookID;
	private int patronID;
	
	public Borrow(int bookID, int patronID) {
		this.bookID = bookID;
		this.patronID =  patronID;
	}
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Patron patron = library.getPatronByID(patronID);
		Book book = library.getBookByID(bookID);
		
        if(!book.isOnLoan()) {
        	patron.addBook(book);
            book.setLoan(new Loan(patron, book, currentDate, currentDate.plusDays(7)));	
        }else {
        	throw new LibraryException(book.getTitle() + "is already on loan until " + book.getLoan().getDueDate());
        }
	
	}

}
