package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;

public class ListLoans implements Command{
	
	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
		for(Book book: library.getBooks()) {
			
			if(book.isOnLoan()) {
				System.out.println("#" + book.getId() + "-" + book.getTitle() + " has been loaned to #" + book.getLoan().getPatron().getId() + "-" + book.getLoan().getPatron().getName() + " until " + book.getLoan().getDueDate());
			}else {
				System.out.println("#" + book.getId() + "-" +book.getTitle() + " is Available!");
			}
		}
		
	}

}
