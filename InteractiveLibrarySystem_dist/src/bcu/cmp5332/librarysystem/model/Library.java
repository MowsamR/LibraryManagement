package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.util.*;

public class Library {
    
    private final int loanPeriod = 7;
    private final Map<Integer, Patron> patrons = new TreeMap<>();
    private final Map<Integer, Book> books = new TreeMap<>();

    public int getLoanPeriod() {
        return loanPeriod;
    }

    public List<Book> getBooks() {
        List<Book> out = new ArrayList<>(books.values());
        return Collections.unmodifiableList(out);
    }
    
    public List<Patron> getPatrons() {
    List<Patron> patronList = new ArrayList<>(patrons.values());
    return Collections.unmodifiableList(patronList);
    }

    public Book getBookByID(int id) throws LibraryException {
        if (!books.containsKey(id)) {
            throw new LibraryException("There is no such book with that ID.");
        }else if(books.get(id).isRemoved()) {
        	throw new LibraryException("This book has been deleted and is not accessible.");
        }
        
        
        return books.get(id);
    }

    public Patron getPatronByID(int id) throws LibraryException {
        // TODO: implementation here
        if (!patrons.containsKey(id)) {
            throw new LibraryException("There is no patron with that ID.");
        }else if (patrons.get(id).isRemoved()) {
            throw new LibraryException("This patron has been deleted and is not accessible.");
        }
        
        return patrons.get(id);
    }

    public void addBook(Book book) {
        if (books.containsKey(book.getId())) {
            throw new IllegalArgumentException("Duplicate book ID.");
        }
        books.put(book.getId(), book);
    }
    
    public void removeBook(Book book) {
        if (books.containsKey(book.getId())) {
            books.remove(book.getId());
        }else {
        	throw new IllegalArgumentException("Book is not in the library.");
        }
    }
    public void addPatron(Patron patron) {
        // TODO: implementation here
        if (patrons.containsKey(patron.getId())) {
            throw new IllegalArgumentException("Duplicate patron ID.");
        }
        patrons.put(patron.getId(), patron);

    }
    
    public void removePatron(Patron patron) {
        if (patrons.containsKey(patron.getId())) {
        	patrons.remove(patron.getId());            
        }else {
        	throw new IllegalArgumentException("Patron is not in the library.");
        }
        
    }
    
}
 