package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

public class ShowBook implements Command{
	
	private final int bookID;
	
	public ShowBook(int bookID) {
		this.bookID = bookID;
	}
	
	public void execute(Library  library, LocalDate currentDate) {
		try {
			Book book = library.getBookByID(bookID);
			System.out.println(book.getDetailsLong());
			
		} catch (LibraryException e) {
			System.out.println("No book exists in the system with id " + bookID + ".");
		}
		
	}
	
}
