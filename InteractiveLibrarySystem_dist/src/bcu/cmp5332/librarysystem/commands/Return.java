package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;

public class Return implements Command {
	private int bookID;
	private int patronID;

	public Return(int bookID, int patronID) {
		this.bookID = bookID;
		this.patronID = patronID;
	}

	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		Patron patron = library.getPatronByID(patronID);
		Book book = library.getBookByID(bookID);

		// Check if book exists in the system
		if (!library.getBooks().contains(book)) {
			throw new LibraryException("Book: " + book.getTitle() + "is not recognised in our system.");
		}

		// Check if patron exists in the system
		if (!library.getPatrons().contains(patron)) {
			throw new LibraryException("Patron: " + patron.getName() + "is not recognised in our system.");
		}

		// if currentDate (date of returning book) is before due date, return the book
		if (!currentDate.isAfter(book.getDueDate())) {
			try {
				patron.removeBook(book);
				book.returnToLibrary();
				System.out.println("Successfully returned book.");
			} catch (LibraryException e) {
				System.out.println("invalid loan");
			}
		} else {
			// calculate the how many days the books is overdue
			long numberDaysOverdue = ChronoUnit.DAYS.between(book.getDueDate(), currentDate);

			//overdue days are negative, so use Math.abs() method to convert to positive integer
			System.out.println("Book: " + book.getTitle() + "is " + Math.abs(numberDaysOverdue) + "days overdue! ");
		}

	}
}