package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Library;

import java.time.LocalDate;

/** It shows the help message.
 * 	It is an implementation of the Command interface.
 *  It is created and executed by the CommandParser when the "help" command is given by the user.
 */
public class Help implements Command {

    @Override
    public void execute(Library library, LocalDate currentDate) {
        System.out.println(Command.HELP_MESSAGE);
    }
}
 