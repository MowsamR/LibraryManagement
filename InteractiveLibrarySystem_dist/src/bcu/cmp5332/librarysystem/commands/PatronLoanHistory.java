package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;
import java.util.List;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;

/** Lists all the terminated loans of a given patron. It does not include the active loans. <br>
 * It is an implementation of the Command interface and its execute method list all the terminated loans of patron in the system. <br>
 * It is created and executed by the CommandParser when the "loanhistory" command is given by the user.
 */
public class PatronLoanHistory implements Command{
	private int patronID;
	/** Initialises PatronLoanHistory using the necessary variables.
	 * @param patronID - the patronID that was given to the system by the user.
	 */
	public PatronLoanHistory(int patronID) {
		this.patronID = patronID;
	}

	/** The method lists all the terminated loans of a patron by iterating over each book and checking if it is currently on loan.
	 * @param library library object of the program.
	 * @param currentDate the current date.
	 * @throws LibraryException  if a patronID cannot be found.
	 */
	public void execute(Library library, LocalDate currentDate) throws LibraryException {

		// Finds the patron in the library based on the given patronID.
		// LibraryException will be thrown if the patron is not found or is inaccessible.
		Patron patron = library.getPatronByID(patronID);

		// Gets the 'loanHistory' array list of the patron.
		List<Loan> loanHistory = patron.getLoanHistory();
		if(loanHistory.size() == 0) {
			// Just prints the line below if the patron does not have any loans in 'loanHistory'.
			System.out.println(patron.getDetailsShort() + " has not returned any books yet.");
		}else {
			// Prints the number of terminated loans by this patron.
			System.out.println( loanHistory.size() + " book(s) borrowed by " + patron.getDetailsShort() + ": ");
			for(Loan loan: loanHistory) {
				// Prints some details of the loan.
				System.out.println("\t" + loan.getBook().getDetailsShort() + " from " + loan.getStartDate() + " to " + loan.getReturnDate() + ".");
			}
		}

	}
}
