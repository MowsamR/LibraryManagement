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

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.commands.Return;

public class ReturnBookWindow extends JFrame implements ActionListener{
	private MainWindow mw;
	private Library library; 
	
    private JButton returnButton = new JButton("Return");
    private JButton cancelButton = new JButton("Cancel");
    

	JComboBox<Patron> patronComboBox = new JComboBox<>();
	private Patron selectedPatron = null;
	
	JComboBox<Book> bookComboBox = new JComboBox<>();
	private Book selectedBook = null;

	
	JPanel patronPanel = new JPanel();
	JPanel bookPanel = new JPanel();
	JPanel bottomPanel = new JPanel();
	
	public ReturnBookWindow(MainWindow mw, Library lib) {
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
        setTitle("Return Books");
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
        returnButton.addActionListener(this);
        bottomPanel.add(cancelButton);
        bottomPanel.add(returnButton);
        
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
		}else if (e.getSource() == returnButton){			
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
				Command returnBook = new Return(selectedBook.getId(), selectedPatron.getId());
				try {
					returnBook.execute(library, LocalDate.now());
		            String message = "Successlly returned Book.";
		            JOptionPane.showMessageDialog(this, message, " Return Book Success ",
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
