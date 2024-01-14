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

/**
 * DeleteBookWindow provides user to delete books from the library via GUI. It
 * is executed when user clicks on 'delete' option under Books menu.
 * User select the book for deletion on a dropdown list that contains all the
 * books in library.
 */
public class DeleteBookWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JButton deleteButton = new JButton("Delete");
    private JButton cancelButton = new JButton("Cancel");
    private Library library;
    private JLabel selectedLabelOption;
    private Book bookSelected;
    private int bookID;

    /**
     * Constructs DeleteBookWindow for referencing/connecting to the main window.
     * 
     * @param mw  MainWindow instance where AddBookWindow is generated.
     * @param lib Library Instance containing the book.
     */
    public DeleteBookWindow(MainWindow mw, Library lib) {
        this.mw = mw;
        this.library = lib;
        initialize();
    }

    /**
     * initialize() is responsible for GUI display properties and styling on. For
     * example, it sets window size, uses FlowLayout() for JLabel Text And JButton
     * display positions. <br>
     * Additionally, it contains methods for setting/configuring JComboBox dropdown
     * list and actionListeners of tracking the book option chosen.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Delete Book");
        // Styling and Layout
        setSize(600, 250);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.add(new JLabel("Select Book: "));

        List<Book> bookList = library.getBooks();
        // This makes the droplist into "Book" class type
        DefaultComboBoxModel<Book> bookModel = new DefaultComboBoxModel<>(bookList.toArray(new Book[0]));

        // used to store the names of books in comboBox
        JComboBox<Book> comboBox = new JComboBox<>(bookModel);

        // This action listener stores the Book ID of the clicked Book option so that
        // it's accessible later on when you click delete and deleteBook() method is
        // called.
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bookSelected = comboBox.getItemAt(comboBox.getSelectedIndex());
                // deleteBook() will use this ID as reference to delete book.
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

    /**
     * Handles ActionEvent instances for button clicks specifically "Delete" and
     * "Cancel" buttons. <br>
     * It uses getSource() method to track which button is clicked and runs the
     * following actions accordingly. <br>
     * deleteBook() method is executed to delete book if "Delete" button is clicked.
     * <br>
     * "Cancel" button closes the pop up window.
     * 
     * @param an ActionEvent instance (button click tracking for "Add" or "Cancel"
     *           Buttons).
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == deleteButton) {

            deleteBook();
        } else if (ae.getSource() == cancelButton) {
            this.setVisible(false);
        }

    }

    /**
     * Using the Book ID, it calls DeleteBook Class from Command Interface to delete
     * the book. If successful, it shows a popup JOPtionPane message to indicating
     * successful deletion.
     * <br>
     * Then, it will display the updated list of books.
     * 
     */
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

}
