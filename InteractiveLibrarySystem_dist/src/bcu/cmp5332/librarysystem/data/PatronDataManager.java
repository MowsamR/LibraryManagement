package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/** This class is used to load and store data using the text file storage.<br>
 * It is an implementation of the DataManager interface.<br>
 * It has a RESOURCE field that holds the path to the text file (patrons.txt) that will be used as storage for the books data.<br>
 * The loadData(Library) method loads data from 'patrons.txt' file to the library.<br>
 * The storeData(Library) method stores the book data that exist in the library to 'patrons.txt' file.
 */
public class PatronDataManager implements DataManager {

    private final String RESOURCE = "InteractiveLibrarySystem_dist/resources/data/patrons.txt";
    
    /** Loads data from 'patrons.txt'.<br>
     * The method opens the file that holds the books data using the RESOURCES variable which stores the file path.<br>
     * The method creates a Scanner object to read the opened file. Then it reads each line of the file.<br>
     * Each line is read as a string. By using the 'string.split()' method
     * and splitting the method using the predefined SEPERATOR variable, the properties array is created.<br>  
     * The properties array contains the 5 variables required for creating a Patron object. Finally the Patron object is created and added to the library. 
     * @throws IOException if it was not able to read the data in the file.
     * @throws LibraryException if the patron could not be added to the library.
     */
    @Override
    public void loadData(Library library) throws IOException, LibraryException {
        // Initialises a Scanner object to read from 'patrons.txt'.
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
        	// line_idx shows the line number of the line that the scanner is reading data from.
            int line_idx = 1;
            
            // Checks if a next line exists.
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                try {
                	// Tries to parse the properties accordingly.
                    int id = Integer.parseInt(properties[0]);
                    String name = properties[1];
                    String phone = properties[2];
                    String email = properties[3];
                    String removed = properties[4];
                    Patron patron = new Patron(id, name, phone, email);
                    
                    // If the patron is supposed to be 'removed', it hides the patron.
                    if(removed == "1") {
                    	// removed == "1" means that the patron is supposed to be removed/hidden.
                    	patron.removePatron();
                    }
                    
                    // Adds the newly created patron object to the library.
                    library.addPatron(patron);
                } catch (NumberFormatException ex) {
                    throw new LibraryException("Unable to parse patron id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex);
                }
                // Goes to the next line to load the next book's details. Stops if the line is empty.
                line_idx++;
            }
        }
    }

    /** Writes data to 'patrons.txt'.<br>
     * The method creates a new FileWriter object using the RESOURCE field
     * and then creates a PrintWriter that will be used to write characters to the FileWriter. <br>
     * The PrintWriter is initialised using the try-with-resources statement to take care of properly closing the resources. <br>
     * Each patron is written variable by variable, with the SEPARATOR after each of the book's properties.
     * @throws IOException if it was not able to store the data in the file.
     */
    @Override
    public void storeData(Library library) throws IOException {
    	// Initialises PrintWriter in order to write in 'patrons.txt'.
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Patron patron : library.getPatrons()) {
            	// Writing each of the patron's variables in the line
                out.print(patron.getId() + SEPARATOR);
                out.print(patron.getName() + SEPARATOR);
                out.print(patron.getPhone() + SEPARATOR);
                out.print(patron.getEmail() + SEPARATOR);
                
             // Writes "1" if the patron is removed and "0" otherwise.
                if(patron.isRemoved()) {
                	// "1" means true and that the book is removed/hidden.
                	out.print("1" + SEPARATOR);	
                }else {
                	// "0" means false and that the book is visible.
                	out.print("0" + SEPARATOR);
                }
                
                // Goes to the next line to write the next patron's details.
                out.println();
            }
        }
    }
}
 