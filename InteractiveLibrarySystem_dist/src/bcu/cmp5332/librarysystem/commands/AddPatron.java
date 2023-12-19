package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

import java.time.LocalDate;

public class AddPatron implements Command {

    private final String name;
    private final String phone;
    private final String email;

    public AddPatron(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        // TODO: implementation here
        int maxId = 0;
    	if (library.getPatrons().size() > 0) {
    		int lastIndex = library.getPatrons().size() - 1;
            maxId = library.getPatrons().get(lastIndex).getId();
    	}
        Patron newPatron = new Patron(++maxId, name, phone, email);
        library.addPatron(newPatron);
        System.out.println("Patron #" + newPatron.getId() + " added.");
    }
}

 