package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;

import bcu.cmp5332.librarysystem.data.PatronDataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;


/** Deletes a patron from the library.<br>
 * It is an implementation of the Command interface and its execute method deletes the given patron from the system.<br>
 * It is created and executed by the CommandParser when the "deletepatron" command is given by the user.<br>
 * The class has a constructor to intialise the fields needed to execute the command.
 */
public class DeletePatron implements Command{

	private final int patronID;
	/** Initialises and creates a DeletePatron object using the necessary variables.
	 * @param patronID - the patronID that was given to the system by the user.
	 */
	public DeletePatron(int patronID) {
		this.patronID = patronID;
	}

	/** Deletes the patron from the system.<br>
	 * After deleting the patron, it stores the changes in 'patrons.txt'.
	 * @param library - library object of the program.
	 * @param currentDate - the current date.
	 * @throws LibraryException if a patronID is not valid or the patron has books that needs to be returned before deleting the patron.
	 */
	public void execute(Library library, LocalDate currentDate) throws LibraryException {


		// Finds the patron in the library based on the given patronID.
		// LibraryException will be thrown if the patron is not found or is inaccessible.
		Patron patron = library.getPatronByID(patronID); 
		if(patron.isBooksListEmpty()) {
			// After making sure the patron is not currently borrowing any books, delete the patron.
			patron.removePatron();
			try {
				// Initialises a patronDataManger object in order to store the changes.
				PatronDataManager patronDataManger = new PatronDataManager();

				// Attempts to store the changes in 'patrons.txt'.
				patronDataManger.storeData(library);

				System.out.println(patron.getDetailsShort() + " has been successfully deleted.");	
			}catch (IOException e) {
				// If unable to store the changes, undo the changes.
				patron.reAddPatron();
				System.out.println("Unable to delete the patron.");
			}				
		}else {
			throw new LibraryException("Selected patron cannot be deleted because they have a book loaned to them. Please first return the books and then try again.");
		}




	}
}
