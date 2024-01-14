package bcu.cmp5332.librarysystem.commands;
import bcu.cmp5332.librarysystem.data.LoanDataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;

import java.io.IOException;
import java.time.LocalDate;

/** Renews a loan for a specific amount of more days.<br>
 * It is an implementation of the Command interface and its execute method renews the given patron from the system.<br>
 * It is created and executed by the CommandParser when the "renew" command is given by the user.<br>
 * The class has a constructor to intialise the fields needed to execute the command.
 */
public class RenewBook implements Command{
	private final int patronID;
	private final int bookID;

	/** Initialises and creates a RenewBook object using the necessary variables.
	 * @param patronID the patronID that was given to the system by the user.
	 * @param bookID the bookID that was given to the system by the user.
	 */
	public  RenewBook(int patronID, int bookID) {
		this.patronID = patronID;
		this.bookID = bookID;
	}

	/** Renews the book from the system.<br>
	 * After renewing the loans, it stores the changes in 'loans.txt'.
	 * @param library library object of the program.
	 * @param currentDate the current date.
	 * @throws LibraryException if the patronID and bookID cannot be found or the book is not on loan to the given patron.
	 */ 
	@Override
	public void execute(Library  library, LocalDate currentDate) throws LibraryException {


		// Finds the patron and book in the library based on the given patronID and bookID.
		// LibraryException will be thrown if the patron or book is not found or is inaccessible.
		Patron patron = library.getPatronByID(patronID);
		Book book = library.getBookByID(bookID);

		// Checks if the given book is actually borrowed by the given patron.
		if(book.getLoan().getPatron() == patron) {

			// Gets the loan object.
			Loan loan = book.getLoan();

			// Gets the old due date so that if the changes could not be saved, the changes can be undone.
			LocalDate oldDueDate = loan.getDueDate();

			// Renews the book for another loan period.
			patron.renewBook(book, library.getLoanPeriod());

			// Initialises a loanDataManager object in order to store the changes.
			LoanDataManager loanDataManager = new LoanDataManager();

			try {
				// Attempts to store the changes in 'loans.txt'.
				loanDataManager.storeData(library);
				System.out.println(book.getTitle() + " loan to " + patron.getName() + " has been successfully extended for 7 more days until " + book.getLoan().getDueDate() + ".");
			}catch(IOException e) {
				// If unable to store the changes, undo the changes.
				book.getLoan().setDueDate​(oldDueDate);
				System.out.println("Unable to extend the loan. Rolling back the changes.");
			}
		}else {
			throw new LibraryException(book.getTitle()+ " is not currently loaned to " + patron.getName() + ".");
		}


	}
}
