package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

/** Shows the information of the patron that has been entered by the user. <br>
 * It is an implementation of the Command interface and its execute method shows a book. <br>
 * It is created and executed by the CommandParser when the "showbook" command is given by the user.
 */
public class ShowPatron implements Command{

	private final int patronID;
	/** Initialises and creates a ShowPatron object using the necessary variables.
	 * @param patronID - the patronID that was given to the system by the user.
	 */
	public ShowPatron(int patronID) {
		this.patronID = patronID;
	}

	/** Shows the patron details including their name, phone number, email address, and the books they are currently borrowing.
	 * @param library - library object of the program.
	 * @param currentDate - the current date.
	 * @throws LibraryException if the patronID cannot be found.
	 */
	@Override
	public void execute(Library  library, LocalDate currentDate) throws LibraryException {

		// Finds the patron in the library based on the given patronID.
		// LibraryException will be thrown if the patron or book is not found or is inaccessible.
		Patron patron = library.getPatronByID(patronID);
		System.out.println(patron.getDetailsLong());

	}
}
