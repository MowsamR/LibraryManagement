package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.util.*;

/** Library class models the whole library.<br>
It has a collection of all books and all members (patrons) and can also hold other global library properties such as the maximum loan period in days. <br>

When the application starts, the Main class of the app loads the stored data from the file storage using
the LibraryData class and populates the collections holding the patrons and books. <br>

The class offers methods to get all the books and patrons as a list and to get a specific book or patron using their id.
 */
public class Library {
    
    private final int loanPeriod = 7;
    private final Map<Integer, Patron> patrons = new TreeMap<>();
    private final Map<Integer, Book> books = new TreeMap<>();

    /** Get the predefined maximum period to loan a book in days
     * @return the loan period in days as integer
     */
    public int getLoanPeriod() {
        return loanPeriod;
    }

    /** Get a list of all books that exist in the library.
     * @return an unmodifiable List of all books in the library.
     */
    public List<Book> getBooks() {
        List<Book> out = new ArrayList<>(books.values());
        return Collections.unmodifiableList(out);
    }
    
    /** Get a list of all patrons (members) of the library.
     * @return an unmodifiable List of all patrons in the library.
     */
    public List<Patron> getPatrons() {
    List<Patron> patronList = new ArrayList<>(patrons.values());
    return Collections.unmodifiableList(patronList);
    }

    /** Get a book from the library based on its id. <br>
     *The function looks in the collection of all the library books and returns the book that its id matches the given one. <br>
     *If a book with that id does not exist, the function throws a LibraryException with a message saying that there is no book with that id.
     * @param id - the id of the book to be found.
     * @return the Book with id matching the one given as argument to the function.
     * @throws LibraryException if a book with that id does not exist in the library or the book is removed.
     */
    public Book getBookByID(int id) throws LibraryException {
        if (!books.containsKey(id)) {
            throw new LibraryException("There is no such book with ID #" + id + ".");
        }else if(books.get(id).isRemoved()) {
        	throw new LibraryException("This book has been deleted and is not accessible.");
        }
        
        return books.get(id);
    }

    /** Get a patron (member) of the library based on their id. <br>
     * The function looks in the collection of all the patrons and returns the patron that its id matches the given one. <br>
     * If a patron with that id does not exist, the function throws a LibraryException with a message indicating that there is no patron with that id.
     * @param id - the id of the patron to be found.
     * @return the Patron with id matching the one given as argument to the function.
     * @throws LibraryException if a patron with that id does not exist in the library or the patron is removed.
     */
    public Patron getPatronByID(int id) throws LibraryException {
        if (!patrons.containsKey(id)) {
            throw new LibraryException("There is no patron with ID #" + id + ".");
        }else if (patrons.get(id).isRemoved()) {
            throw new LibraryException("This patron has been deleted and is not accessible.");
        }
        
        return patrons.get(id);
    }

    /** Add a book to the library. <br>
     * The function adds a book to the collection of all books of the library.<br>
     * If a book with that id already exists in the library, the function throws an IllegalArgumentException with a message indicating that there is already a book with that id in the library.
     * @param book - the Book to be added to the library.
     */
    public void addBook(Book book) {
        if (books.containsKey(book.getId())) {
            throw new IllegalArgumentException("Duplicate book ID.");
        }
        books.put(book.getId(), book);
    }
    
    /** Remove a book from the books list of the library. <br>
     * The method is used to undo changes if a book's information could not be added the text storage files.
     * @param book - the book to be removed from the books list of the library.
     */
    public void removeBook(Book book) {
        if (books.containsKey(book.getId())) {
            books.remove(book.getId());
        }else {
        	throw new IllegalArgumentException("Book is not in the library.");
        }
    }
    /** Add a patron to the library. <br>
     * The function adds a patron to the collection of all patrons of the library. <br>
     * If a patron with that id already exists in the library, the function throws an IllegalArgumentException with a message indicating that there is already a patron with that id in the library.
     * @param patron - the Patron to be added to the library
     */
    public void addPatron(Patron patron) {
        if (patrons.containsKey(patron.getId())) {
            throw new IllegalArgumentException("Duplicate patron ID.");
        }
        patrons.put(patron.getId(), patron);

    }
    
    /** Remove a patron from the patrons list of the library. <br>
     * The method is used to undo changes if a patron's information could not be added the text storage files.
     * @param patron - the patron to be removed from the patrons list of the library.
     */
    public void removePatron(Patron patron) {
        if (patrons.containsKey(patron.getId())) {
        	patrons.remove(patron.getId());            
        }else {
        	throw new IllegalArgumentException("Patron is not in the library.");
        }
        
    }
    
}
 