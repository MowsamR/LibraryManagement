package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/** This class is used to load and store data using the text file storage. <br>
 * It is an implementation of the DataManager interface. <br>
 * It has a RESOURCE field that holds the path to the text file (books.txt) that will be used as storage for the books data. <br>
 * The loadData(Library) method loads data from 'books.txt' file to the library. <br>
 * The storeData(Library) method stores the book data that exist in the library to 'books.txt' file.
 */
public class BookDataManager implements DataManager {
    
    // private final String RESOURCE = "InteractiveLibrarySystem_dist/resources/data/books.txt";
	private final String RESOURCE = "./resources/data/books.txt";
    
    /** Loads data from 'books.txt'. <br>
     * The method opens the file that holds the books data using the RESOURCES variable which stores the file path. <br>
     * The method creates a Scanner object to read the opened file. Then it reads each line of the file. <br>
     * Each line is read as a string. By using the 'string.split()' method
     * and splitting the method using the predefined SEPERATOR variable, the properties array is created. <br>  
     * The properties array contains the 6 variables required for creating a Book object. Finally the Book object is created and added to the library.
     * @throws IOException if it was not able to read the data in the file. 
     * @throws LibraryException if the book could not be added to the library.
     */
    @Override
    public void loadData(Library library) throws IOException, LibraryException {
    	
    	// Initialises a Scanner object to read from 'patrons.txt'.
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
        	// line_idx shows the line number of the line that the scanner is reading data from.
            int line_idx = 1;
            
            // Checks if a next line exists.
            while (sc.hasNextLine()) {
            	// Reads the next line.
                String line = sc.nextLine();
                
                // Splits the line into an array using the SEPARATOR.
                String[] properties = line.split(SEPARATOR, -1);
                try {
                	// Tries to parse the properties accordingly.
                    int id = Integer.parseInt(properties[0]);
                    String title = properties[1];
                    String author = properties[2];
                    String publicationYear = properties[3];
                    String publisher = properties[4];
                    String removed = properties[5];
                    Book book = new Book(id, title, author, publicationYear, publisher);
                    
                    // If the book is supposed to be 'removed', it hides the book.
                    if(removed.equals("1")) {
                    	// removed == "1" means that the book is supposed to be removed/hidden.
                    	book.removeBook();
                    }
                    
                    // Adds the newly created book object to the library.
                    library.addBook(book);
                } catch (NumberFormatException ex) {
                    throw new LibraryException("Unable to parse book id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex);
                }
                // Goes to the next line to load the next book's details. Stops if the line is empty.
                line_idx++;
            }
        }
    }
    
    /** Writes data to 'books.txt'. <br>
     * The method creates a new FileWriter object using the RESOURCE field
     * and then creates a PrintWriter that will be used to write characters to the FileWriter. <br>
     * The PrintWriter is initialised using the try-with-resources statement to take care of properly closing the resources.
     * Each book is written variable by variable, with the SEPARATOR after each of the book's properties.
     * @throws IOException if it was not able to store the data in the file.
     */
    @Override
    public void storeData(Library library) throws IOException {
    	
    	// Initialises PrintWriter in order to write in 'book.txt'.
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Book book : library.getBooks()) {
            	// Writing each of the book's variables in the line
                out.print(book.getId() + SEPARATOR);
                out.print(book.getTitle() + SEPARATOR);
                out.print(book.getAuthor() + SEPARATOR);
                out.print(book.getPublicationYear() + SEPARATOR);
                out.print(book.getPublisher() + SEPARATOR);
                
                // Writes "1" if the book is removed and "0" otherwise.
                if(book.isRemoved()) {
                	// 1 = true and means that the book is removed/hidden
                	out.print("1" + SEPARATOR);	
                }else {
                	// 0 = false and means that the book is visible
                	out.print("0" + SEPARATOR);
                }
                
                // Goes to the next line to write the next book's details.
                out.println();
            }
        }
    }
}
 