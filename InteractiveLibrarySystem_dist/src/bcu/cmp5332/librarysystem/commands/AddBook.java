package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.data.BookDataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;

/** Adds a new book to the library. <br>
 * It is an implementation of the Command interface and its execute method adds a new book to the library. <br>
 * It is created and executed by the CommandParser when the "addbook" command is given by the user. <br>
 * The class has a constructor to intialise the fields needed to execute the command. <br>
 */
public class AddBook implements  Command {

    private final String title;
    private final String author;
    private final String publicationYear;
    private final String publisher;

    /**
     * Initialises an AddBook object using the necessary fields. 
     * @param title the title of the new book.
     * @param author the author of the new book.
     * @param publicationYear the publication year of the new book.
     * @param publisher the publisher of the new book.
     */
    public AddBook(String title, String author, String publicationYear, String publisher) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
    }
    
    /** Creates and adds a new book to the library. <br>
     * After adding the book to the library, it also overwrites the 'books.txt' file with the new data.
     * @param library library object of the program.
	 * @param currentDate the current date.
     * @throws LibraryException if any fields are empty.
     */
    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException{
    	// Makes sure all the fields have been entered.
    	if(title.isBlank()) {
    		throw new LibraryException("The title field can not be empty.");
    	} else if(author.isBlank()) {
    		throw new LibraryException("The author field can not be empty.");
    	} else if(publicationYear.isBlank()) {
    		throw new LibraryException("The publication year field can not be empty.");
    	} else if(publisher.isBlank()) {
    		throw new LibraryException("The publisher field can not be empty.");
    	}
    	
    	for(Book book : library.getBooks()) {
    		if(book.getTitle().equals(title)) {
    			throw new LibraryException("This book title already exists in the system.");
    		}
    	}
    	
        // Initialises the maxId variable. 
    	int maxId = 0;
    	
    	// Finds the next smallest bookID by getting the last index.
    	if (library.getBooks().size() > 0) {
    		int lastIndex = library.getBooks().size() - 1;
            maxId = library.getBooks().get(lastIndex).getId();
    	}
    	
    	// Adds the book to the library.
        Book book = new Book(++maxId, title, author, publicationYear, publisher);
        library.addBook(book);
        
        // Initialises a new bookDataManager in order to rewrite 'books.txt' file.
        BookDataManager bookDataManager = new BookDataManager();
        
        try {
        	// Rewrites 'book.txt' file. 
			bookDataManager.storeData(library);
			System.out.println("Book #" + book.getId() + " added.");
		} catch (IOException e) {
			// Rolls back the changes if the data could not be successfully stored in 'books.txt' file.
			library.removeBook(book);
			System.out.println("Unable to store the book. Rolling back the changes.");
		}
        
    }
}
 