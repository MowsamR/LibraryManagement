package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.commands.AddPatron;
import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;

import java.awt.BorderLayout;
import java.awt.GridLayout;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class DisplayBorrowerDetailsWindow extends JFrame {
    private MainWindow mw;
    private Book book;

    public DisplayBorrowerDetailsWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    public Book setDisplayBook(Book book) {
        return this.book = book;
    }

    // get the (Patron) borrower of the current book
    private Patron getBorrower(Book book) {
        Loan loan = book.getLoan();
        return loan.getPatron();
    }

    private void displayDetails() {
        setTitle("Display Borrower Details");

        setSize(500, 500);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        Patron borrower = getBorrower(book);
        panel.add((new JLabel("ID : ")));
        panel.add(new JLabel(String.valueOf(borrower.getId())));
        panel.add((new JLabel("Name : ")));
        panel.add(new JLabel(borrower.getName()));
        panel.add((new JLabel("Phone Number : ")));
        panel.add(new JLabel(borrower.getPhone()));
        panel.add((new JLabel("Email : ")));
        panel.add(new JLabel(borrower.getEmail()));

        this.getContentPane().add(panel, BorderLayout.CENTER);
        setLocationRelativeTo(mw);
        setVisible(true);
    }

    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.out.println("Error Found");

        }
        displayDetails();
    }


}
