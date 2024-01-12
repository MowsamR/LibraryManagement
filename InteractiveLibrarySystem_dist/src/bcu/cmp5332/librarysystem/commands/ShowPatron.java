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
			System.out.println(patron.getDetailsLong());
			
		} catch (LibraryException e) {
			System.out.println(e.getMessage());
		}
		
	}
}
