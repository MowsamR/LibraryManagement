package bcu.cmp5332.librarysystem.commands;


import java.io.IOException;
import java.time.LocalDate;

import bcu.cmp5332.librarysystem.data.LoanDataManager;
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
	public void execute(Library library, LocalDate currentDate) {

		
		try {
			Patron patron = library.getPatronByID(patronID);
			Book book = library.getBookByID(bookID);
	        if(!book.isOnLoan()) {
	        	patron.addBook(book);
	            book.setLoan(new Loan(patron, book, currentDate, currentDate.plusDays(7)));
	            LoanDataManager loanDataManager = new LoanDataManager();
	            try {
	            	loanDataManager.storeData(library);
	            	System.out.print(book.getTitle() + " has been successfully loaned to " + book.getLoan().getDueDate());
	            }catch(IOException e) {
	            	patron.removeBook(book);
	            	book.returnToLibrary();
	            	System.out.println("Unable to store the loan. Rolling back the changes.");
	            }
	        }else {
	        	throw new LibraryException(book.getTitle() + "is already on loan until " + book.getLoan().getDueDate());
	        }
		}catch(LibraryException e) {
			System.out.println(e.getMessage());
		}

	
	}

}
