package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;
import java.util.List;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;

public class PatronLoanHistory implements Command{
	private int patronID;
	public PatronLoanHistory(int patronID) {
		this.patronID = patronID;
	}
	public void execute(Library library, LocalDate currentDate) {
		
		try {
			Patron patron = library.getPatronByID(patronID);
			List<Loan> loanHistory = patron.getLoanHistory();
			if(loanHistory.size() == 0) {
				System.out.println(patron.getDetailsShort() + " has not returned any books yet.");
			}else {
				System.out.println( loanHistory.size() + " book(s) borrowed by " + patron.getDetailsShort() + ": ");
				for(Loan loan: loanHistory) {
					System.out.println("\t" + loan.getBook().getDetailsShort() + " from " + loan.getStartDate() + " to " + loan.getReturnDate() + ".");
				}
				if(loanHistory.size() == 0) {
					System.out.println(patron.getDetailsShort() + " has not returned any books yet.");
				}
			}
			
		}catch(LibraryException e) {
			System.out.println(e.getMessage());
		}
	}
}
