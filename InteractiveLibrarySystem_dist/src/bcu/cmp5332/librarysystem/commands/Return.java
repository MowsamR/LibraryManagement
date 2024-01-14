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

/** Returns a loan. If the loan has been overdue, it also shows the number of days overdue.<br>
 * It is an implementation of the Command interface and its execute method renews the given patron from the system.<br>
 * It is created and executed by the CommandParser when the "return" command is given by the user.<br>
 * The class has a constructor to intialise the fields needed to execute the command.
 */
public class Return implements Command {
	private int bookID;
	private int patronID;

	/** Initialises and creates a Return object using the necessary variables.
	 * @param patronID - the patronID that was given to the system by the user.
	 * @param bookID - the bookID that was given to the system by the user.
	 */
	public Return(int bookID, int patronID) {
		this.bookID = bookID;
		this.patronID = patronID;
	}

	/**Returns a book to the library.<br>
	 * After renewing the loans, it stores the changes in 'loans.txt'.
	 * @param library - library object of the program.
	 * @param currentDate - the current date.
	 * @throws LibraryException if the patronID and bookID cannot be found or the book is not on loan to the given patron.
	 */
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {

		// Finds the patron and book in the library based on the given patronID and bookID.
		// LibraryException will be thrown if the patron or book is not found or is inaccessible.
		Patron patron = library.getPatronByID(patronID);
		Book book = library.getBookByID(bookID);

		// Gets the loan object.
		Loan loan = book.getLoan();

		// If currentDate (date of returning book) is before due date, returns the book
		if (currentDate.isAfter(book.getDueDate())) {

			// Calculates the how many days the books is overdue
			long numberDaysOverdue = ChronoUnit.DAYS.between(book.getDueDate(), currentDate);

			// Overdue days are negative, so use Math.abs() method to convert to positive integer
			System.out.println(book.getDetailsShort() + " is " + Math.abs(numberDaysOverdue) + " days overdue! ");

		}			

		// Unlinks the patron and the book.
		patron.returnBook(book);

		// Initialises a loanDataManager object in order to store the changes.
		LoanDataManager loanDataManager = new LoanDataManager();
		try {
			// Attempts to store the changes in 'loans.txt'.
			loanDataManager.storeData(library);
			System.out.println("Successfully returned " + book.getDetailsShort() + ".");
		}catch(IOException e) {
			// If unable to store the changes, undo the changes.
			patron.addBook(book);
			patron.removeFromLoanHistory(loan);
			book.setLoan(loan);
			loan.unTerminateLoan();
			System.out.println("Unable to store the changes. Rolling back the changes.");
		}


	}
}