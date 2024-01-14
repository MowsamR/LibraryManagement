package bcu.cmp5332.librarysystem.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

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

public class RenewBookWindow extends JFrame implements ActionListener{
	private MainWindow mw;
	private Library library; 
	
    private JButton renewButton = new JButton("Renew");
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
	
	JPanel patronPanel = new JPanel();
	JPanel bookPanel = new JPanel();
	JPanel bottomPanel = new JPanel();
	
	public RenewBookWindow(MainWindow mw, Library lib) {
		this.mw = mw;
		this.library = lib;
		initialize();
	}
	
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
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == patronComboBox) {
			int selectedPatronidx = patronComboBox.getSelectedIndex();
			selectedPatron = patronComboBox.getItemAt(selectedPatronidx);
			refreshBookComboBox();
			if(selectedBook == null && bookComboBox.getItemCount() == 0) {
                String message = "This patron does not have any books loaned to them at the moment.";
                JOptionPane.showMessageDialog(null, message, "Error: book not selected",
                        JOptionPane.WARNING_MESSAGE);
			}
		} else if(e.getSource() == bookComboBox) {
			int selectedBookidx = bookComboBox.getSelectedIndex();
			selectedBook = bookComboBox.getItemAt(selectedBookidx);
		}else if (e.getSource() == renewButton){			
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
				Command returnBook = new RenewBook(selectedBook.getId(), selectedPatron.getId());
				try {
					returnBook.execute(library, LocalDate.now());
		            String message = "Successlly renewed Book.";
		            JOptionPane.showMessageDialog(this, message, " Renew Book Success ",
		                    JOptionPane.INFORMATION_MESSAGE);
		            
		            mw.listPatrons();
				} catch (LibraryException ex) {
	                String message = ex.getMessage();
	                JOptionPane.showMessageDialog(null, message, "Error!",
	                        JOptionPane.WARNING_MESSAGE);
				}
			}
		}else if(e.getSource() == cancelButton) {
			this.dispose();
		}
		
	}
	private void refreshBookComboBox() {
        ArrayList<Book> borrowedBooks = new ArrayList<Book>();
        emptyBookComboBox();
        for(Book book: library.getBooks()) {
        	if(book.isOnLoan() && book.getLoan().getPatron() == selectedPatron) {
        		bookComboBox.addItem(book);
        	}
        }
        
        bookPanel.add(bookComboBox);
	}
	
	private void emptyBookComboBox() {
		while(bookComboBox.getItemCount() > 0) {
			bookComboBox.removeItemAt(0);
		}
	}
}
