package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

public class ShowPatron implements Command{
	
	private final int patronID;
	public ShowPatron(int patronID) {
		this.patronID = patronID;
	}
	
	public void execute(Library  library, LocalDate currentDate) {
		try {
			Patron patron = library.getPatronByID(patronID);
			System.out.println("  Name: " + patron.getName());
			System.out.println("  Phone number: " + patron.getPhone());
			System.out.println("  Email: " + patron.getEmail());
			System.out.println(patron.getBooks());
			
		} catch (LibraryException e) {
			System.out.println("No patron exists in the system with id " + patronID + ".");
		}
		
	}
}
