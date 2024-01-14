package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** 
 * LibraryData class is responsible for loading data to and storing data from the library, using the text file storage. <br>
 * The class has a static list of objects that implement the DataManager interface. <br>
 * The list gets populated by a static block of code (runs only once when the object is loaded to memory) that
 * adds different implementations of the DataManager interface - one for each different entity of the library system
 * (e.g. BookDataManager is an implementation that loads and stores data for Book entities using the text file storage). <br>
 * The class has the static methods load() to load all data to the library and store(Library) to store all data from the library to the text file storage.
 */
public class LibraryData {
    
    private static final List<DataManager> dataManagers = new ArrayList<>();
    
    // Runs only once when the object gets loaded to memory.
    static {
        dataManagers.add(new BookDataManager());
        dataManagers.add(new PatronDataManager());
        dataManagers.add(new LoanDataManager());   
    }
    
    /** Reads the 
     * The method gets called whenever the program gets started.
     * @return The new Library object loaded with data for all entities. 
     * @throws LibraryException if an exception is thrown by the execution of any of the different loadData function implementations.
     * @throws IOException if an exception is thrown by the execution of any of the different loadData function implementations.
     * @throws LibraryException if the objects could not be added to the library.
     */
    public static Library load() throws LibraryException, IOException {

        Library library = new Library();
        for (DataManager dm : dataManagers) {
            dm.loadData(library);
        }
        return library;
    }

    /** Calls each dataManager's store method to store information in the text files. <br>
     * The method gets called when the 'exit' command is entered.
     * @param library - the library object which contains all the information, variables and objects related to books and patrons.
     * @throws IOException - if an exception is thrown by the execution of any of the different loadData function implementations.
     */
    public static void store(Library library) throws IOException {

        for (DataManager dm : dataManagers) {
            dm.storeData(library);
        }
    }
    
}
 