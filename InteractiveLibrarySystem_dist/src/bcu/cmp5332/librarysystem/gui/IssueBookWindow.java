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
	
	public IssueBookWindow(MainWindow mw, Library lib) {
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
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == patronComboBox) {
			int selectedPatronidx = patronComboBox.getSelectedIndex();
			selectedPatron = patronComboBox.getItemAt(selectedPatronidx);
			
		}
		if(e.getSource() == bookComboBox) {
			int selectedBookidx = bookComboBox.getSelectedIndex();
			selectedBook = bookComboBox.getItemAt(selectedBookidx);
		}
		if(e.getSource() == issueButton) {
			if(selectedPatron == null) {
                String message = "Please select a patron!";
                JOptionPane.showMessageDialog(null, message, "Error: patron not selected",
                        JOptionPane.WARNING_MESSAGE);
			}else if(selectedBook == null) {
                String message = "Please select a book!";
                JOptionPane.showMessageDialog(null, message, "Error: book not selected",
                        JOptionPane.WARNING_MESSAGE);
			}else {
				Command borrow = new Borrow(selectedBook.getId(), selectedPatron.getId());
				try {
					borrow.execute(library, LocalDate.now());
		            String message = "Successlly issued Book.";
		            JOptionPane.showMessageDialog(this, message, " Issue Book Success ",
		                    JOptionPane.INFORMATION_MESSAGE);
		            
					bookComboBox.removeItem(selectedBook);
					mw.displayBooks();
					
				}catch(LibraryException ex) {
	                String message = ex.getMessage();
	                JOptionPane.showMessageDialog(null, message, "Error!",
	                        JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		if(e.getSource() == cancelButton) {
			this.dispose();
		}
	}
}
