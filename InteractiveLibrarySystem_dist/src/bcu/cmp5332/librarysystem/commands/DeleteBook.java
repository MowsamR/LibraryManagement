package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;

import bcu.cmp5332.librarysystem.data.BookDataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;

public class DeleteBook implements Command{
	private final int bookID;
	
	public DeleteBook(int bookID) {
		this.bookID = bookID;
	}
	
	public void execute(Library library, LocalDate currentDate) {
		
		try {
			Book book = library.getBookByID(bookID);
			if(!book.isOnLoan()) {
				book.removeBook();
				try {
					BookDataManager bookDataManager = new BookDataManager();
					bookDataManager.storeData(library);
					System.out.println("Book successfully deleted.");	
				}catch(IOException e) {
					book.reAddBook();
					System.out.println("Unable to delete the book.");
				}
			}else {
				throw new LibraryException("Selected book cannot be deleted because it is on loan. Please first return the book and then try again.");
			}
		}catch(LibraryException e) {
			System.out.println(e.getMessage());
		}
	}
}
