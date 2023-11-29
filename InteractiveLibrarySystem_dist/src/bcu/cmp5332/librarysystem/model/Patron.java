package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Patron {
    
    private int id;
    private String name;
    private String phone;
    private final List<Book> books = new ArrayList<>();
    
    // TODO: implement constructor here
    public Patron(int id, String name, String phone){
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    } 

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    } 

    public void setName(String newName) {
        this.name = newName;
    }

    public String getPhone() {
        return phone;
    } 

    public void setPhone(String newPhone) {
        this.phone = newPhone;
    }
    public List getBooks(){
        //TODO: Get a list of books that the patron has borrowed.
    	return Collections.unmodifiableList(books);
    }
    
    public void borrowBook(Book book, LocalDate dueDate) throws LibraryException {
    	this.addBook(book);
    }

    public void renewBook(Book book, LocalDate dueDate) throws LibraryException {
        // TODO: implementation here
    }

    public void returnBook(Book book) throws LibraryException {
    	if (books.contains(book)) {
            books.remove(book);
            book.returnToLibrary();
    	}else {
    		throw new LibraryException(book.getTitle() + "has not been borrowed by " + this.name);
    	}
    }
    
    public void addBook(Book book) {
        books.add(book);
    }
    public void removeBook(Book book) throws LibraryException {
        if(books.contains(book)) {
        	books.remove(book);
        }else {
        	throw new LibraryException("This book has not been borrowed by this patron");
        }
   
    }
}
 