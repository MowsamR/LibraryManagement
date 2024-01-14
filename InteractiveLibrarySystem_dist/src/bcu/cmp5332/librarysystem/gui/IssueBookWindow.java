package bcu.cmp5332.librarysystem.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.commands.Borrow;
import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.main.LibraryException;

/**
 * IssueBookWindow class extends JFrame that's responsible for creating a pop up
 * window when the user wants issue a book to a patron.<br>
 * This class is created and executed when "Issue" option is clicked under Books
 * Menu in MainWindow class.<br>
 * When executed, it displays a pop up window that provides two combo boxes to select
 * the patron and the book.<br>
 */
public class IssueBookWindow extends JFrame implements ActionListener{

	private MainWindow mw;
	private Library library; 
	
    private JButton issueButton = new JButton("Issue");
    private JButton cancelButton = new JButton("Cancel");
    
	private Patron patronSelected;
	private int patronID;
	JComboBox<Patron> patronComboBox = new JComboBox<>();
	private Patron selectedPatron = null;
	private JLabel selectedPatronLabel;
	
	JComboBox<Book> bookComboBox = new JComboBox<>();
	private Book selectedBook = null;
	private JLabel selectedBookLabel;
	private int bookID;
	
	/** Constructs IssueBookWindow for referencing/connecting to the main window.
	 * @param mw The MainWindow instance where AddBookWindow is generated.
	 * @param lib the library of the system that includes data of the patrons and books.
	 */
	public IssueBookWindow(MainWindow mw, Library lib) {
		this.mw = mw;
		this.library = lib;
		initialize();
	}
    /**
     * initialize() is responsible for GUI display properties and styling. For
     * example, it sets window size, uses FlowLayout() for JLabel, ComboBox, and JButton
     * display positions.
     */
	private void initialize() {
		
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        	System.out.println(ex.getMessage());
        }
        
        setTitle("Borrow Books");
        setSize(600, 250);
        
        JPanel patronPanel = new JPanel();
        patronPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        patronPanel.add(new JLabel("Select Patron: "));
        
        // Adding the visible patrons to the patron combo box.
        for(Patron patron: library.getPatrons()) {
        	if(!patron.isRemoved()) {
        		patronComboBox.addItem(patron);
        	}
        }
        
        patronComboBox.addActionListener(this);
        patronPanel.add(patronComboBox);
        
        JPanel bookPanel = new JPanel();
        bookPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        bookPanel.add(new JLabel("Select Book: "));
        // Adding the visible and available books to the books combo box.
        for(Book book: library.getBooks()) {
        	if(!book.isRemoved() && !book.isOnLoan()) {
        		bookComboBox.addItem(book);
        	}
        }
        
        bookComboBox.addActionListener(this);
        bookPanel.add(bookComboBox);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        cancelButton.addActionListener(this);
        issueButton.addActionListener(this);
        bottomPanel.add(cancelButton);
        bottomPanel.add(issueButton);
        
        this.getContentPane().add(patronPanel, BorderLayout.NORTH);
        this.getContentPane().add(bookPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);
        
        setVisible(true);
	}
	

    /**
     * This method is responsible for handling ActionEvent instances for buttons "Issue" and "Cancel".
     * The method Also handles actions for combo boxes in the window.
     * It uses getSource() method to
     * track which button is clicked and runs the following actions accordingly.
     * <br>
     * borrow.execute() method is executed to issue a loan, if "Issue" button is
     * clicked.
     * <br>
     * "Cancel" button closes the pop up window.
     *
     * @param an ActionEvent instance (button click).
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == patronComboBox) {
			// Sets 'selectedPatron' if the value of 'patronComboBox' has been changed.
			int selectedPatronidx = patronComboBox.getSelectedIndex();
			selectedPatron = patronComboBox.getItemAt(selectedPatronidx);
			
		}
		if(e.getSource() == bookComboBox) {
			// Sets 'selectedBook' if the value of 'bookComboBox' has been changed.
			int selectedBookidx = bookComboBox.getSelectedIndex();
			selectedBook = bookComboBox.getItemAt(selectedBookidx);
		}
		if(e.getSource() == issueButton) {
			// First, it makes sure whether a patron and a book has been selected in the combo boxes or not.
			if(selectedPatron == null) {
                String message = "Please select a patron!";
                JOptionPane.showMessageDialog(null, message, "Error: patron not selected",
                        JOptionPane.WARNING_MESSAGE);
			}else if(selectedBook == null) {
                String message = "Please select a book!";
                JOptionPane.showMessageDialog(null, message, "Error: book not selected",
                        JOptionPane.WARNING_MESSAGE);
			}else {
				
				// If the patron and book have correctly selected, initialises a borrow Command object.
				Command borrow = new Borrow(selectedBook.getId(), selectedPatron.getId());
				try {
					borrow.execute(library, LocalDate.now());
					
					// Showing a success message
		            String message = "Successlly issued Book.";
		            JOptionPane.showMessageDialog(this, message, " Issue Book Success ",
		                    JOptionPane.INFORMATION_MESSAGE);
		            
		            // Updates bookComboBox
					bookComboBox.removeItem(selectedBook);
					
					// Redirects the main window to the displaybooks() page.
					mw.displayBooks();
					
				}catch(LibraryException ex) {
					// Catches and shows any LibraryException.
	                String message = ex.getMessage();
	                JOptionPane.showMessageDialog(null, message, "Error!",
	                        JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		if(e.getSource() == cancelButton) {
			// Disposes (destroys) the window if 'Cancel' button is clicked.
			this.dispose();
		}
	}
}
