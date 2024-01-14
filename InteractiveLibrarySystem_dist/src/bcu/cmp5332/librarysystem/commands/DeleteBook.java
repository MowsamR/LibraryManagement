package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;

import bcu.cmp5332.librarysystem.data.BookDataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;

/** Deletes a book from the library.<br>
 * It is an implementation of the Command interface and its execute method deletes the given book from the system.<br>
 * It is created and executed by the CommandParser when the "deletebook" command is given by the user.<br>
 * The class has a constructor to intialise the fields needed to execute the command.<br>
 */
public class DeleteBook implements Command{
	private final int bookID;

	/** Initialises DeleteBook using the necessary variables. 
	 * @param bookID the bookID that was given to the system by the user.
	 */
	public DeleteBook(int bookID) {
		this.bookID = bookID;
	}

	/** Deletes the book from the system.<br>
	 * After deleting the book, it stores the changes in 'books.txt'.
	 * @param library library object of the program.
	 * @param currentDate the current date.
	 * @throws LibraryException if a bookID is not valid or the book is on loan and needs to be returned before deleting the book.
	 */
	public void execute(Library library, LocalDate currentDate) throws LibraryException {


		// Finds the book in the library based on the given bookID.
		// LibraryException will be thrown if the book is not found or is inaccessible.
		Book book = library.getBookByID(bookID);
		if(!book.isOnLoan()) {
			// After making sure the book is not on loan, removes the book.
			book.removeBook();
			try {
				// Initialises a bookDataManager object in order to store the changes.
				BookDataManager bookDataManager = new BookDataManager();

				// Attempts to store the changes in 'books.txt'.
				bookDataManager.storeData(library);

				System.out.println("Book successfully deleted.");	
			}catch(IOException e) {
				// If unable to store the changes, undo the changes.
				book.reAddBook();
				System.out.println("Unable to delete the book.");
			}
		}else {
			// Throws a LibraryException if the book in on loan.
			throw new LibraryException("Selected book cannot be deleted because it is on loan. Please first return the book and then try again.");
		}

	}
}
