package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;

import bcu.cmp5332.librarysystem.data.PatronDataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

public class DeletePatron implements Command{
	
	private final int patronID;
	public DeletePatron(int patronID) {
		this.patronID = patronID;
	}
	
	public void execute(Library library, LocalDate currentDate) {
		
		try {
			Patron patron = library.getPatronByID(patronID); 
			if(patron.isBooksListEmpty()) {
				patron.removePatron();
				try {
					PatronDataManager patronDataManger = new PatronDataManager();
					patronDataManger.storeData(library);
	            	System.out.println("Patron successfully deleted.");	
				}catch (IOException e) {
					patron.reAddPatron();
					System.out.println("Unable to delete the patron.");
				}				
			}else {
				throw new LibraryException("Selected patron cannot be deleted because they have a book loaned to them. Please first return the books and then try again.");
			}
			
		} catch (LibraryException e) {
			System.out.println(e.getMessage());	
		}
		
		
	}
}
