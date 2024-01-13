package bcu.cmp5332.librarysystem.commands;
import bcu.cmp5332.librarysystem.data.LoanDataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;

import java.io.IOException;
import java.time.LocalDate;

public class RenewBook implements Command{
	private final int patronID;
	private final int bookID;
	public  RenewBook(int patronID, int bookID) {
		this.patronID = patronID;
		this.bookID = bookID;
	}
	
	@Override
	public void execute(Library  library, LocalDate currentDate) throws LibraryException {
		
		Patron patron = library.getPatronByID(patronID);
		Book book = library.getBookByID(bookID);
		
		if(book.getLoan().getPatron() == patron) {
			Loan loan = book.getLoan();
			LocalDate currentDueDate = loan.getDueDate();
			LoanDataManager loanDataManager = new LoanDataManager();
			
            try {
            	patron.renewBook(book);
            	loanDataManager.storeData(library);
            	System.out.println(book.getTitle() + " loan to " + patron.getName() + " has been successfully extended for 7 more days until " + book.getLoan().getDueDate() + ".");
            }catch(IOException e) {
            	book.getLoan().setDueDate​(currentDueDate);
            	System.out.println("Unable to extend the loan. Rolling back the changes.");
            }
		}else {
			throw new LibraryException(book.getTitle()+ " is not currently loaned to " + patron.getName() + ".");
		}
		
	}
}
