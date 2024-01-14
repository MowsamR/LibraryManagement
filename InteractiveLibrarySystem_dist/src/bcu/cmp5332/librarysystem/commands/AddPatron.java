package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.data.PatronDataManager;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

import java.io.IOException;
import java.time.LocalDate;

/** Adds a new patron to the library. <br>
 * It is an implementation of the Command interface and its execute method adds a new patron to the library.<br>
 * It is created and executed by the CommandParser when the "addPatron" command is given by the user.<br>
 * The class has a constructor to intialise the fields needed to execute the command.
 */
public class AddPatron implements Command {

    private final String name;
    private final String phone;
    private final String email;

    /**
     * Initialises an AddPatron object using the necessary fields.
     * @param name the name of the new patron.
     * @param phone the phone number of the new patron.
     * @param email the email address of the new patron.
     */
    public AddPatron(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    /** Creates and adds a new patron to the library.<br>
     * After adding the patron to the library, it also overwrites the 'patrons.txt' file with the new data.
	 * @param library library object of the program.
	 * @param currentDate the current date.
     */
    @Override
    public void execute(Library library, LocalDate currentDate) {
    	// Initialises the maxId variable. 
        int maxId = 0;
        
        // Finds the next smallest PatronID
        if (library.getPatrons().size() > 0) {
            int lastIndex = library.getPatrons().size() - 1;
            maxId = library.getPatrons().get(lastIndex).getId();
    	}
        
        // Adds the patron to the library.
        Patron newPatron = new Patron(++maxId, name, phone, email);
        library.addPatron(newPatron);
        
        // Initialises a new patronDataManger in order to rewrite 'patrons.txt' file.
        PatronDataManager patronDataManger = new PatronDataManager();
        try {
        	// Rewrites 'patrons.txt' file. 
			patronDataManger.storeData(library);
			
			System.out.println("Patron #" + newPatron.getId() + " added.");
		} catch (IOException e) {
			// Rolls back the changes if the data could not be successfully stored in 'patrons.txt' file.
			library.removePatron(newPatron);
			
			System.out.println("Unable to store the patron. Rolling back the changes.");
		}
        
    }
}
