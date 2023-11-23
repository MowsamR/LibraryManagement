package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patron {
    
    private int id;
    private String name;
    private String phone;
    private final List<Book> books = new ArrayList<>();
    
    // TODO: implement constructor here
    
    public void borrowBook(Book book, LocalDate dueDate) throws LibraryException {
        // TODO: implementation here
    }

    public void renewBook(Book book, LocalDate dueDate) throws LibraryException {
        // TODO: implementation here
    }

    public void returnBook(Book book) throws LibraryException {
        // TODO: implementation here
    }
    
    public void addBook(Book book) {
        // TODO: implementation here
    }
}
 