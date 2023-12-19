package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;
import java.util.List;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

/*Shows a list of all patrons of the library to the user.

It is an implementation of the Command interface and its execute(Library, LocalDate)
method displays a list of the library patrons to the user. At the end of the list,
the total amount of patrons in the library is also displayed to the user.

It is created and executed by the CommandParser when the "listpatrons" command is given by the user. */

public class ListPatrons implements Command{
    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        List<Patron> patrons = library.getPatrons();
        for (Patron patron : patrons) {
            System.out.println("Patron #" + patron.getId() + " " + patron.getName() + " " + patron.getPhone() + " " + patron.getEmail());
        }
        System.out.println("Total: " + patrons.size() + " patron(s)");
    }
}
