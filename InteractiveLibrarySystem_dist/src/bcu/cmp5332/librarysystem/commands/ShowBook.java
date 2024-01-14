package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;

/** Shows the information of the book that has been entered by the user. <br>
 * It is an implementation of the Command interface and its execute method shows a book. <br>
 * It is created and executed by the CommandParser when the "showbook" command is given by the user.
 */
public class ShowBook implements Command{
	
	private final int bookID;
	
	/** Initialises and creates a ShowBook object using the necessary variables.
	 * @param bookID - the bookID that was given to the system by the user.
	 */
	public ShowBook(int bookID) {
		this.bookID = bookID;
	}
	
	/** Shows the book details including its title, author, publisher and publication year.
	 * @param library - library object of the program.
	 * @param currentDate - the current date.
	 * @throws LibraryException if the bookID cannot be found.
	 */
	@Override
	public void execute(Library  library, LocalDate currentDate) throws LibraryException {
		
			// Finds the book in the library based on the given bookID.
			// LibraryException will be thrown if the patron or book is not found or is inaccessible.
			Book book = library.getBookByID(bookID);
			System.out.println(book.getDetailsLong());
			
		
		
	}
	
}
