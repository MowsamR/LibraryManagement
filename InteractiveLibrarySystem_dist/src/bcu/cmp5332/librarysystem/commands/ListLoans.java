package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;

/** Lists all the loans currently in the system. It does not include the terminated loans.<br>
 * It is an implementation of the Command interface and its execute method list all the active loans in the system.<br>
 * It is created and executed by the CommandParser when the "listloans" command is given by the user.
 */
public class ListLoans implements Command{
	
	/** The method lists all the active loans by iterating over each book and checking if it is currently on loan. 
	 * @param library library object of the program.
	 * @param currentDate the current date.
	 */
	@Override
	public void execute(Library library, LocalDate currentDate) {
		for(Book book: library.getBooks()) {
			
			// Shows the book's borrower if on loan and shows 'is Available!' if book is not on loan currently.
			if(book.isOnLoan()) {
				System.out.println(book.getDetailsShort() + " has been loaned to " + book.getLoan().getPatron().getDetailsShort());
			}else {
				System.out.println(book.getDetailsShort() + " is Available!");
			}
		}
		
	}

}
