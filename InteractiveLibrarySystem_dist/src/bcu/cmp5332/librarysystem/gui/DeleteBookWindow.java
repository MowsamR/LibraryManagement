package bcu.cmp5332.librarysystem.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.commands.DeleteBook;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;

public class DeleteBookWindow extends JFrame implements ActionListener {

    private MainWindow mw;

    private JComboBox<String> comboBox;
    private JButton deleteButton = new JButton("Delete");
    private JButton cancelButton = new JButton("Cancel");
    private Library library;
    private JLabel selectedLabelOption;
    private Book bookSelected;
    private int bookID;

    public DeleteBookWindow(MainWindow mw, Library lib) {
        this.mw = mw;
        this.library = lib;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Delete Book");

        setSize(600, 250);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        topPanel.add(new JLabel("Select Book: "));

        // This is a dropdown option that shows all existing books
        List<Book> bookList = library.getBooks();
        // used to store the names of books in comboBox
        DefaultComboBoxModel<Book> bookModel = new DefaultComboBoxModel<>(bookList.toArray(new Book[0]));
        JComboBox<Book> comboBox = new JComboBox<>(bookModel);

        // This action listener stores the clicked option into variable so that it's
        // accessible later on when you click delete
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bookSelected = comboBox.getItemAt(comboBox.getSelectedIndex());
                bookID = bookSelected.getId();
                selectedLabelOption.setText("\nSelected book for deletetion: " + bookSelected);
            }
        });
        topPanel.add(comboBox);
        selectedLabelOption = new JLabel("\nSelected Book: ");
        topPanel.add(selectedLabelOption);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(deleteButton);
        bottomPanel.add(cancelButton);

        deleteButton.addActionListener(this);
        cancelButton.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == deleteButton) {

            deleteBook();
        } else if (ae.getSource() == cancelButton) {
            this.setVisible(false);
        }

    }

    private void deleteBook() {
        try {
            Command deleteBook = new DeleteBook(bookID);
            deleteBook.execute(mw.getLibrary(), LocalDate.now());
            // Display success message
            String message = "Successlly deleted Book.";
            JOptionPane.showMessageDialog(this, message, " Delete Book Success ",
                    JOptionPane.INFORMATION_MESSAGE);
            mw.displayBooks();
        } catch (LibraryException e) {
            JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public List<String> bookDropdownOption() {
        List<Book> bookList = library.getBooks();
        // used to store the names of books in comboBox
        List<String> bookNames = new ArrayList<>();
        for (Book book : bookList) {
            bookNames.add(book.getDetailsShort());
        }
        return bookNames;
    }

}
