package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;
import java.time.LocalDate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/** This class is used to load and store data using the text file storage.<br>
 * It is an implementation of the DataManager interface.<br>
 * It has a RESOURCE field that holds the path to the text file (loans.txt) that will be used as storage for loans data.<br>
 * The loadData(Library) method loads data from 'loans.txt' file to the library.<br>
 * The storeData(Library) method stores the book data that exist in the library to 'loans.txt' file.
 */
public class LoanDataManager implements DataManager {
    
    // public final String RESOURCE = "InteractiveLibrarySystem_dist/resources/data/loans.txt";
	public final String RESOURCE = "InteractiveLibrarySystem_dist/resources/data/loans.txt";

    /** Loads data from 'loans.txt'.
     * The method opens the file that holds the books data using the RESOURCES variable which stores the file path.<br>
     * The method creates a Scanner object to read the opened file. Then it reads each line of the file.<br>
     * Each line is read as a string. By using the 'string.split()' method and splitting the method using the predefined SEPERATOR variable,
     * the properties array is created.<br>  
     * The properties array contains the 6 variables required for creating a Loan object. Finally the Loan object is created and added to the library.
     * @throws IOException if it was not able to read the data in the file.
     * @throws LibraryException if the loan could not be added to the library. 
     */
    @Override
    public void loadData(Library library) throws IOException, LibraryException {
    	// Initialises a Scanner object to read from 'patrons.txt'.
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
        	// line_idx shows the line number of the line that the scanner is reading data from.
            int line_idx = 1;
            
            // Checks if a next line exists.
            while (sc.hasNextLine()) {
            	// Reads the next line.
                String line = sc.nextLine();
                
                // Splits the line into an array using the SEPARATOR.
                String[] properties = line.split(SEPARATOR, -1);
                try {
                	// Tries to parse the properties accordingly.
                	Patron patron = library.getPatronByID(Integer.parseInt(properties[0]));
                	Book book = library.getBookByID(Integer.parseInt(properties[1]));
                	LocalDate startDate = LocalDate.parse(properties[2]);
                	LocalDate dueDate = LocalDate.parse(properties[3]);
                	
                	Boolean isTerminated;
                	// properties[4] is the termination status. "1" means true and "0" means false.
                	if(properties[4].equals("1")) {
                		// "1" means loans has been terminated.
                		isTerminated = true;
                	}else {
                		// "0" means loans has not been terminated.
                		isTerminated = false;
                	}
                	
                	LocalDate returnDate;
                	// properties[5] is the return date of the loan.
                	// If this field is empty, then the loan has not been returned yet. 
                	if(properties[5].equals("")) {
                		returnDate = null;
                	}else {
                		returnDate = LocalDate.parse(properties[5]);
                	}
                	
                	// Checks if the loan has been terminated.
                	if(isTerminated) {
                		
                		// If the loan has been terminated, then a Loan object is created.
                		Loan loan = new Loan(patron, book, startDate, dueDate);
                		
                		// Then the newly created loan's return date and termination status are manually configured.
                		loan.setReturnDate(returnDate);
                		loan.setTerminationStatus(true);
                		patron.addLoanToHistory(loan);
                	}else {
                		
                		// Checks to make sure if the book is already on loan or not.
                		if(!book.isOnLoan()) {
                			// Links the patron and book together using the Loan.
                        	patron.addBook(book);
                            book.setLoan(new Loan(patron, book, startDate, dueDate));	
                        }else {
                        	throw new LibraryException(book.getTitle() + "is already on loan until " + book.getLoan().getDueDate());
                        }
                	}
                    
                } catch (NumberFormatException ex) {
                    throw new LibraryException("Unable to parse patron or book id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex);
                }
             // Goes to the next line to load the next book's details. Stops if the line is empty.
                line_idx++;
            }
        }
    }

    /** Writes data to 'loans.txt'.<br>
     * The method creates a new FileWriter object using the RESOURCE field
     * and then creates a PrintWriter that will be used to write characters to the FileWriter.<br>
     * The PrintWriter is initialised using the try-with-resources statement to take care of properly closing the resources.<br>
     * Each Loan is written variable by variable, with the SEPARATOR after each of the book's properties.<br>
     * The active loans are stored first and then by iterating on the patrons list, each patron's loan history is written in the file.
     * @throws IOException if it was not able to store the data in the file.
     */
    @Override
    public void storeData(Library library) throws IOException {
    	// Initialises PrintWriter in order to write in 'book.txt'.
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
        	
        	// First we save the loans that are not terminated.
        	// These loans that are not saved in any patron's loan history and are retrieved from the Book objects.
            for (Book book : library.getBooks()) {
            	if (book.isOnLoan()) {
            		// Writing each of the loan's variables in the line
            		Loan loan = book.getLoan();
            		out.print(loan.getPatron().getId() + SEPARATOR);
                    out.print(loan.getBook().getId() + SEPARATOR);
                    out.print(loan.getStartDate() + SEPARATOR);
                    out.print(loan.getDueDate() + SEPARATOR);
                    
                    // because the loan is being retrieved from a Book object, termination status is false and the loan is NOT terminated.
                    // In the storage, 'false' values are stored as '0'.
                    out.print("0" + SEPARATOR);
                    out.print("" + SEPARATOR);
                    
                    out.println();
            	}
            }
            
            // Writing the loans that have been terminated by going through each patron's history.
            for (Patron patron : library.getPatrons()) {
            	for(Loan loan : patron.getLoanHistory()) {
            		// Writing each of the loan's variables in the line
            		out.print(loan.getPatron().getId() + SEPARATOR);
                    out.print(loan.getBook().getId() + SEPARATOR);
                    out.print(loan.getStartDate() + SEPARATOR);
                    out.print(loan.getDueDate() + SEPARATOR);
                    
                    // because the loan is being retrieved from a Patron object, termination status is true and the loan is terminated.
                    // In the storage, 'true' values are stored as '1'.
                    out.print("1" + SEPARATOR);
                    out.print(loan.getReturnDate() + SEPARATOR);
                    
                    // Goes to the next line to write the next loan's details.
                    out.println();
            	}
            }
            
        } catch (IOException ex){
        	// Catches and shows the message of any LibraryException.
        	throw new IOException("unable to write loans in loan.txt");
        }
    }
    
}
 