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

public class LoanDataManager implements DataManager {
    
    public final String RESOURCE = "./resources/data/loans.txt";

    @Override
    public void loadData(Library library) throws IOException, LibraryException {
        // TODO: implementation here
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
            int line_idx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                try {
                	Patron patron = library.getPatronByID(Integer.parseInt(properties[0]));
                	Book book = library.getBookByID(Integer.parseInt(properties[1]));
                	LocalDate startDate = LocalDate.parse(properties[2]);
                	LocalDate dueDate = LocalDate.parse(properties[3]);
                	
                	Boolean isTerminated;
                	if(properties[4].equals("1")) {
                		isTerminated = true;
                	}else {
                		isTerminated = false;
                	}
                	
                	LocalDate returnDate;
                	if(properties[5].equals("")) {
                		returnDate = null;
                	}else {
                		returnDate = LocalDate.parse(properties[5]);
                	}
                	
                	
                	if(isTerminated) {
                		Loan loan = new Loan(patron, book, startDate, dueDate);
                		loan.setReturnDate(returnDate);
                		loan.setTerminationStatus(true);
                		patron.addLoanToHistory(loan);
                	}else {
                		
                		if(!book.isOnLoan()) {
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
                line_idx++;
            }
        }
    }

    @Override
    public void storeData(Library library) throws IOException {
        // TODO: implementation here
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
        	
        	//first we save the loans that are not terminated. These are the loans that are not saved in any patron's loan history
            for (Book book : library.getBooks()) {
            	if (book.isOnLoan()) {
            		Loan loan = book.getLoan();
            		out.print(loan.getPatron().getId() + SEPARATOR);
                    out.print(loan.getBook().getId() + SEPARATOR);
                    out.print(loan.getStartDate() + SEPARATOR);
                    out.print(loan.getDueDate() + SEPARATOR);
                 
                    // because termination status == false, then the loan is NOT terminated. In the storage, 'false' values are stored as '0'.
                    out.print("0" + SEPARATOR);
                    out.print("" + SEPARATOR);
                    
                    out.println();
            	}
            }
            
            for (Patron patron : library.getPatrons()) {
            	for(Loan loan : patron.getLoanHistory()) {
            		out.print(loan.getPatron().getId() + SEPARATOR);
                    out.print(loan.getBook().getId() + SEPARATOR);
                    out.print(loan.getStartDate() + SEPARATOR);
                    out.print(loan.getDueDate() + SEPARATOR);
                    
                    // because termination status == true, then the loan is terminated. In the storage, 'true' values are stored as '1'.
                    out.print("1" + SEPARATOR);
                    out.print(loan.getReturnDate() + SEPARATOR);
                    
                    out.println();
            	}
            }
            
        } catch (IOException ex){
        	throw new IOException("unable to write loans in loan.txt");
        }
    }
    
}
 