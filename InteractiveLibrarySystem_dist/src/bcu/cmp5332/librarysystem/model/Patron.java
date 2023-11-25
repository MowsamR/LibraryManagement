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

    public void setId(String newPhone) {
        this.phone = newPhone;
    }
    
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
 