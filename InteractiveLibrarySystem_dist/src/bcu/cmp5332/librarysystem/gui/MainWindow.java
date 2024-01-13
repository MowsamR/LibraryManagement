package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

public class MainWindow extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu booksMenu;
    private JMenu membersMenu;
    private JMenu patronsMenu;

    private JMenuItem adminExit;

    private JMenuItem booksView;
    private JMenuItem booksAdd;
    private JMenuItem booksDel;
    private JMenuItem booksIssue;
    private JMenuItem booksReturn;

    private JMenuItem memView;
    private JMenuItem memAdd;
    private JMenuItem memDel;

    private JMenuItem patronList;
    private JButton borrowerDetailsButton;
    private JPanel buttonPanel;

    private Library library;

    public MainWindow(Library library) {

        initialize();
        this.library = library;
    }

    public Library getLibrary() {
        return library;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Library Management System");

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // adding adminMenu menu and menu items
        adminMenu = new JMenu("Admin");
        menuBar.add(adminMenu);

        adminExit = new JMenuItem("Exit");
        adminMenu.add(adminExit);
        adminExit.addActionListener(this);

        // adding booksMenu menu and menu items
        booksMenu = new JMenu("Books");
        menuBar.add(booksMenu);

        booksView = new JMenuItem("View");
        booksAdd = new JMenuItem("Add");
        booksDel = new JMenuItem("Delete");
        booksIssue = new JMenuItem("Issue");
        booksReturn = new JMenuItem("Return");
        booksMenu.add(booksView);
        booksMenu.add(booksAdd);
        booksMenu.add(booksDel);
        booksMenu.add(booksIssue);
        booksMenu.add(booksReturn);
        for (int i = 0; i < booksMenu.getItemCount(); i++) {
            booksMenu.getItem(i).addActionListener(this);
        }

        // adding membersMenu menu and menu items
        membersMenu = new JMenu("Members");
        menuBar.add(membersMenu);

        memView = new JMenuItem("View");
        memAdd = new JMenuItem("Add");
        memDel = new JMenuItem("Delete");

        membersMenu.add(memView);
        membersMenu.add(memAdd);
        membersMenu.add(memDel);

        memView.addActionListener(this);
        memAdd.addActionListener(this);
        memDel.addActionListener(this);

        // adding patronMenu menu and menu items
        patronsMenu = new JMenu("Patrons");
        menuBar.add(patronsMenu);

        patronList = new JMenuItem("List");

        setSize(800, 500);

        setVisible(true);
        setAutoRequestFocus(true);
        toFront();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        /*
         * Uncomment the following line to not terminate the console app when the window
         * is closed
         */
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    /* Uncomment the following code to run the GUI version directly from the IDE */
    // public static void main(String[] args) throws IOException, LibraryException {
    // Library library = LibraryData.load();
    // new MainWindow(library);
    // }

    @Override
    public void actionPerformed(ActionEvent ae) {
        // Exit GUI
        if (ae.getSource() == adminExit) {
            System.exit(0);
        }
        // Display existing books in a table view
        else if (ae.getSource() == booksView) {
            displayBooks();
        }
        // Displays a popup menu to add book
        else if (ae.getSource() == booksAdd) {
            new AddBookWindow(this);

        } else if (ae.getSource() == booksDel) {

        } else if (ae.getSource() == booksIssue) {

        } else if (ae.getSource() == booksReturn) {

        }
        // Display a list of existing patrons with no. of borrowed books
        else if (ae.getSource() == memView) {
            listPatrons();
        }
        // Displays a popup menu to add patron
        else if (ae.getSource() == memAdd) {
            new AddPatronWindow(this);
        } else if (ae.getSource() == memDel) {

        }
    }

    public void displayBooks() {
        List<Book> booksList = library.getBooks();
        // headers for the table
        String[] tableColumns = new String[] { "Book ID", "Title", "Author", "Pub Date", "Status", "Borrower Details" };

        Object[][] data = new Object[booksList.size()][6]; // Change the size to 5

        for (int i = 0; i < booksList.size(); i++) {
            Book book = booksList.get(i);
            data[i][0] = book.getId();
            data[i][1] = book.getTitle();
            data[i][2] = book.getAuthor();
            data[i][3] = book.getPublicationYear();
            data[i][4] = book.getStatus();

            if (book.isOnLoan()) {
                data[i][5] = "View Borrower Details";

            } else {
                data[i][5] = "N/A";
            }
        }

        JTable table = new JTable(data, tableColumns);
        // In order to create pop up window when table cell is clicked, MouseAdapter
        // class is used rather than MouseListener because you can
        // override the methods that you need, in this case: only mouseClicked()
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                // get the point where mouse clicked
                int selectedRow = table.rowAtPoint(me.getPoint());
                int selectedColumn = table.columnAtPoint(me.getPoint());

                // if user clicks on any of the "Borrower Details" column:
                if (tableColumns[selectedColumn].equals("Borrower Details")) {
                    try {
                        // get the book ID where mouse was clicked
                        Integer clickedBookID = (Integer) data[selectedRow][0];
                        Book bookClicked = library.getBookByID(clickedBookID);

                        // if book on loan, show the patron details
                        if (bookClicked.isOnLoan()) {
                            Patron borrowerPatron = bookClicked.getLoan().getPatron();
                            String newLine = "\n";
                            String patronDetails = "Patron ID: " + borrowerPatron.getId() + newLine + "Name: "
                                    + borrowerPatron.getName() + newLine
                                    + "Email: " + borrowerPatron.getEmail() + newLine + "Phone Number: "
                                    + borrowerPatron.getPhone() + newLine;

                            JOptionPane.showMessageDialog(null, patronDetails, "View Borrower Details",
                                    JOptionPane.INFORMATION_MESSAGE);
                            // else show warning: book is not on loan to view patron details
                        } else {
                            String message = "Book is not on Loan to view borrower details";
                            JOptionPane.showMessageDialog(null, message, "View Borrower Details",
                                    JOptionPane.WARNING_MESSAGE);

                        }

                    } catch (LibraryException e) {
                        e.printStackTrace();
                    }

                }

            }

        });

        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }

    // REMEMBER TO ADD DOC!!
    public void listPatrons() {
        List<Patron> patronList = library.getPatrons();
        // headers for the table
        String[] columns = new String[] { "ID", "Name", "Phone Number", "Email", "Number of Borrowed Books" };

        Object[][] data = new Object[patronList.size()][6];

        for (int i = 0; i < patronList.size(); i++) {
            Patron patron = patronList.get(i);
            data[i][0] = patron.getId();
            data[i][1] = patron.getName();
            data[i][2] = patron.getPhone();
            data[i][3] = patron.getEmail();
            data[i][4] = patron.getBooks().size();

        }
        JTable table = new JTable(data, columns);
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }

}
