package bcu.cmp5332.librarysystem.commands;


import java.io.IOException;
import java.time.LocalDate;

import bcu.cmp5332.librarysystem.data.LoanDataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;


/** Adds a new patron to the library.<br>
 * It is an implementation of the Command interface and its execute method creates a loan object and attaches it to the given patron and book.<br>
 * It is created and executed by the CommandParser when the "borrow" command is given by the user.<br>
 * The class has a constructor to intialise the fields needed to execute the command.<br>
 */
public class Borrow implements Command{

	private int bookID;
	private int patronID;

	/** Initialises a Borrow object using the necessary fields.
	 * @param bookID - the bookID that was given to the system by the user. This will be used to find its book object in the system.
	 * @param patronID - the patronID that was given to the system by the user. This will be used to find its patron object in the system.
	 */
	public Borrow(int bookID, int patronID) {
		this.bookID = bookID;
		this.patronID =  patronID;
	}
	/** Creates and adds a new loan to the library. <br>
	 * It adds the loan to the 'books' array list of the patron and sets the 'loan' parameter of the book object to the created loan object.<br>
	 * After adding the loan to the library, it also overwrites the 'loan.txt' file with the new data.
	 * @param library - library object of the program.
	 * @param currentDate - the current date.
	 * @throws LibraryException if a patronID  or bookID is invalid, or a book is already on loan.
	 */
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {

		// Finds the patron and book in the library based on the given patronID and bookID.
		// LibraryException will be thrown if a patron or a book is not found or is inaccessible.
		Patron patron = library.getPatronByID(patronID);
		Book book = library.getBookByID(bookID);

		if(!book.isOnLoan()) {

			// Adds the book to the list of borrowed book of the patron.
			patron.addBook(book);

			// Creates a new loan object and setting it as the new loan of the book.
			book.setLoan(new Loan(patron, book, currentDate, currentDate.plusDays(library.getLoanPeriod())));

			// Initialises a loanDataManager object in order to store the new changes made to the system.
			LoanDataManager loanDataManager = new LoanDataManager();
			try {
				// Attempts to store the data of the new loan by rewriting 'loan.txt'.
				loanDataManager.storeData(library);
				System.out.println(book.getDetailsShort() + " has been successfully loaned to " + patron.getDetailsShort() + " until " + book.getLoan().getDueDate());
			}catch(IOException e) {
				// If unable to store the changes to 'loan.txt', undo the changes.
				patron.removeBook(book);
				book.returnToLibrary();

				System.out.println("Unable to store the loan. Rolling back the changes.");
			}
		}else {
			// If the requested book is already on loan, throw a LibraryException.
			throw new LibraryException(book.getTitle() + "is already on loan until " + book.getLoan().getDueDate());
		}

	}

}
