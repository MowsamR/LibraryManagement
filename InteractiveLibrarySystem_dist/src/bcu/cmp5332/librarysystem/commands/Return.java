package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import bcu.cmp5332.librarysystem.data.LoanDataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;

public class Return implements Command {
	private int bookID;
	private int patronID;

	public Return(int bookID, int patronID) {
		this.bookID = bookID;
		this.patronID = patronID;
	}

	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		
		try {
			Patron patron = library.getPatronByID(patronID);
			Book book = library.getBookByID(bookID);
			Loan loan = book.getLoan();

			// if currentDate (date of returning book) is before due date, return the book
			if (currentDate.isAfter(book.getDueDate())) {
				
				// calculate the how many days the books is overdue
				long numberDaysOverdue = ChronoUnit.DAYS.between(book.getDueDate(), currentDate);

				//overdue days are negative, so use Math.abs() method to convert to positive integer
				System.out.println(book.getDetailsShort() + " is " + Math.abs(numberDaysOverdue) + " days overdue! ");
				
			}			
			
			patron.removeBook(book);
			book.returnToLibrary();
			
            LoanDataManager loanDataManager = new LoanDataManager();
            try {
            	loanDataManager.storeData(library);
            	System.out.println("Successfully returned " + book.getDetailsShort() + ".");
            }catch(IOException e) {
            	patron.addBook(book);
            	patron.removeFromLoanHistory(loan);
	            book.setLoan(loan);
	            loan.unTerminateLoan();
            	System.out.println("Unable to store the changes. Rolling back the changes.");
            }

		}catch (LibraryException e) {
			System.out.println(e.getMessage());
		}


	}
}