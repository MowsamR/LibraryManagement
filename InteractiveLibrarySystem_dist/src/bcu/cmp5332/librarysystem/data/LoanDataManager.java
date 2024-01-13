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
                    if(!book.isOnLoan()) {
                    	patron.addBook(book);
                        book.setLoan(new Loan(patron, book, startDate, dueDate));	
                    }else {
                    	throw new LibraryException(book.getTitle() + "is already on loan until " + book.getLoan().getDueDate());
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
            for (Book book : library.getBooks()) {
            	if (book.isOnLoan()) {
            		Loan loan = book.getLoan();
            		out.print(loan.getPatron().getId() + SEPARATOR);
                    out.print(loan.getBook().getId() + SEPARATOR);
                    out.print(loan.getStartDate() + SEPARATOR);
                    out.print(loan.getDueDate() + SEPARATOR);
                    out.println();
            	}
            }
        } catch (IOException ex){
        	throw new IOException("unable to write loans in loan.txt");
        }
    }
    
}
 