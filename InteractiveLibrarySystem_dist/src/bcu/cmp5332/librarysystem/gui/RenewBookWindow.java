package bcu.cmp5332.librarysystem.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.commands.RenewBook;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

/**
 * RenewBookWindow class extends JFrame that's responsible for creating a pop up
 * window when the user wants renew a loan.<br>
 * This class is created and executed when "Renew" option is clicked under Books
 * Menu in MainWindow class.<br>
 * When executed, it displays a pop up window that provides two combo boxes to select
 * the patron and the book.<br>
 */
public class RenewBookWindow extends JFrame implements ActionListener{
	// Initialiasing all the variables that need to be used across the window. 
	
	private MainWindow mw;
	private Library library; 
	
    private JButton renewButton = new JButton("Renew");
    private JButton cancelButton = new JButton("Cancel");
    

	JComboBox<Patron> patronComboBox = new JComboBox<>();
	private Patron selectedPatron = null;
	
	JComboBox<Book> bookComboBox = new JComboBox<>();
	private Book selectedBook = null;

	
	JPanel patronPanel = new JPanel();
	JPanel bookPanel = new JPanel();
	JPanel bottomPanel = new JPanel();
	
	/** Constructs RenewBookWindow for referencing/connecting to the main window.
	 * @param mw The MainWindow instance where AddBookWindow is generated.
	 * @param lib the library of the system that includes data of the patrons and books.
	 */
	public RenewBookWindow(MainWindow mw, Library lib) {
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
        setTitle("Renew Books");
        setSize(600, 250);
        
        
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
        
        
        bookPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        bookPanel.add(new JLabel("Patron's books: "));
        
        bookComboBox.addActionListener(this);
        bookPanel.add(bookComboBox);
        
        
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        cancelButton.addActionListener(this);
        renewButton.addActionListener(this);
        bottomPanel.add(cancelButton);
        bottomPanel.add(renewButton);
        
        this.getContentPane().add(patronPanel, BorderLayout.NORTH);
        this.getContentPane().add(bookPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);
        
        setVisible(true);
	}
    /**
     * This method is responsible for handling ActionEvent instances for buttons "Renew" and "Cancel".
     * The method Also handles actions for combo boxes in the window.
     * It uses getSource() method to
     * track which button is clicked and runs the following actions accordingly.
     * <br>
     * returnBook.execute() method is executed to renew a loan, if "Renew" button is
     * clicked.
     * <br>
     * "Cancel" button closes the pop up window.
     *
     * @param e an ActionEvent instance (button click).
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == patronComboBox) {
			// Sets 'selectedPatron' if the value of 'patronComboBox' has been changed.
			int selectedPatronidx = patronComboBox.getSelectedIndex();
			selectedPatron = patronComboBox.getItemAt(selectedPatronidx);
			
			// Refreshes bookComboBox with the selected patron's currently loaned books.
			refreshBookComboBox();
			
			// Shows a warning message if the patron does not have borrowed any books.
			if(selectedBook == null && bookComboBox.getItemCount() == 0) {
                String message = "This patron does not have any books loaned to them at the moment.";
                JOptionPane.showMessageDialog(null, message, "Error: book not selected",
                        JOptionPane.WARNING_MESSAGE);
			}
		} else if(e.getSource() == bookComboBox) {
			// Sets 'selectedBook' if the value of 'bookComboBox' has been changed.
			int selectedBookidx = bookComboBox.getSelectedIndex();
			selectedBook = bookComboBox.getItemAt(selectedBookidx);
			
		}else if (e.getSource() == renewButton) {
			// First, it makes sure whether a patron and a book has been selected in the combo boxes or not.
			if(selectedPatron == null) {
                String message = "Please select a patron!";
                JOptionPane.showMessageDialog(null, message, "Error: patron not selected",
                        JOptionPane.WARNING_MESSAGE);
                
			}else if(selectedBook == null && bookComboBox.getItemCount() == 0) {
                String message = "This patron does not have any books loaned to them at the moment.";
                JOptionPane.showMessageDialog(null, message, "Error: book not selected",
                        JOptionPane.WARNING_MESSAGE);
                
			} else if(selectedBook == null && bookComboBox.getItemCount() > 0) {
				String message = "Please select a book!";
                JOptionPane.showMessageDialog(null, message, "Error: book not selected",
                        JOptionPane.WARNING_MESSAGE);
                
			} else {
				
				// If the patron and book have correctly selected, initialises a returnBook Command object.
				Command returnBook = new RenewBook(selectedBook.getId(), selectedPatron.getId());
				try {
					// Executing the returnBook command.
					returnBook.execute(library, LocalDate.now());
					
					// Showing a success message
		            String message = "Successlly renewed Book.";
		            JOptionPane.showMessageDialog(this, message, " Renew Book Success ",
		                    JOptionPane.INFORMATION_MESSAGE);
		            
		            mw.listPatrons();
				} catch (LibraryException ex) {
					// Catches and shows any LibraryException.
	                String message = ex.getMessage();
	                JOptionPane.showMessageDialog(null, message, "Error!",
	                        JOptionPane.WARNING_MESSAGE);
				}
			}
		}else if(e.getSource() == cancelButton) {
			// Disposes (destroys) the window if 'Cancel' button is clicked.
			this.dispose();
		}
		
	}
	/** refreshBookComboBox changes the items of the bookComboBox
	 * 	to the borrowed books of the currently selected patron. <br>
	 *  The method calls the 'emptyBookComboBox' in order to first empty the bookComboBox.
	 *  Then it adds the borrowed books of the currently selected patron on by one.
	 */
	private void refreshBookComboBox() {
        emptyBookComboBox();
        for(Book book: library.getBooks()) {
        	if(book.isOnLoan() && book.getLoan().getPatron() == selectedPatron) {
        		bookComboBox.addItem(book);
        	}
        }
        
        bookPanel.add(bookComboBox);
	}
	
	/**
	 * The method empties the bookComboBox by removing its items using a while loop until all the items are removed.
	 */
	private void emptyBookComboBox() {
		while(bookComboBox.getItemCount() > 0) {
			bookComboBox.removeItemAt(0);
		}
	}
}
