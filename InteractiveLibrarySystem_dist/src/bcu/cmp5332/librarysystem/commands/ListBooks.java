package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.time.LocalDate;
import java.util.List;

/** Lists the books that are in the library.<br>
 * It is an implementation of the Command interface and its execute method list the books in the system.<br>
 * It is created and executed by the CommandParser when the "listbooks" command is given by the user.
 */
public class ListBooks implements Command {

    /** Shows only the visible books. If a book has been removed it will not be printed.<br>
     * The method also prints the total number of visible books in the system.
     * @param library - library object of the program.
	 * @param currentDate - the current date.
     */
    @Override
    public void execute(Library library, LocalDate currentDate) {
    	// Gets the books array list from the library.
        List<Book> books = library.getBooks();
        
        // Initialises visibleBooks to count only the books that are visible and are not hidden(removed).
        int visibleBooks = 0;
        for (Book book : books) {
        	if(!book.isRemoved()) {
        		// Book will not be printed if book.isisRemoved()==true which means the book is removed.
        		System.out.println(book.getDetailsShort());	
        		
        		// Increments the visibleBooks variable to print it later.
        		visibleBooks ++;
        	}
        }
        // Prints the number of visible books.
        System.out.println(visibleBooks + " book(s)");
    }
}
 