package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;
import java.util.List;

import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

/** Shows a list of all patrons of the library to the user. <br>

It is an implementation of the Command interface and its execute(Library, LocalDate)
method displays a list of the library patrons to the user. At the end of the list,
the total amount of patrons in the library is also displayed to the user. <br>

It is created and executed by the CommandParser when the "listpatrons" command is given by the user.
*/

public class ListPatrons implements Command{
	
    /** Shows only the visible patrons. If a patron has been removed it will not be printed.
     * The method also prints the total number of visible patrons in the system.
     * @param library - library object of the program.
	 * @param currentDate - the current date.
     */
    @Override
    public void execute(Library library, LocalDate currentDate) {
    	// Gets the patrons array list from the library.
    	List<Patron> patrons = library.getPatrons();
    	
    	// Initialises visiblePatrons to count only the patrons that are visible and are not hidden(removed).
        int visiblePatrons = 0;
        for (Patron patron : patrons) {
        	if(!patron.isRemoved()) {
        		// Patron will not be printed if patron.isisRemoved()==true which means the patron is removed.
        		System.out.println(patron.getDetailsLong());
        		
        		System.out.println();
        		// Increments the visiblePatrons variable to print it later.
        		visiblePatrons ++;
        	}
        }
        // Prints the number of visible patrons.
        System.out.println("Total: " + visiblePatrons + " patron(s)");
    }
}
