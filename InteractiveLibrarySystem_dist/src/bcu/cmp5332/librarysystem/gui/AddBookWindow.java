package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.commands.AddBook;
import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * AddBookWindow class extends JFrame that's responsible for creating a pop up
 * window when the user wants to add new books.<br>
 * This class is created and executed when "Add" option is clicked under Books
 * Menu in MainWindow class.<br>
 * When executed, it displays a pop up window that provide TextFields to write
 * the book's details: "Title, Author, Publishing Date and Publisher".<br>
 */
public class AddBookWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField titleText = new JTextField();
    private JTextField authText = new JTextField();
    private JTextField pubDateText = new JTextField();
    private JTextField pubNameText = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    /**
     * Constructs AddBookWindow for referencing/connecting to the main window.
     * 
     * @param mw The MainWindow instance where AddBookWindow is generated.
     */
    public AddBookWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * initialize() is responsible for GUI display properties and styling on. For
     * example, it sets window size, uses GridLayout() for JLabel Text And JButton
     * display positions.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Add a New Book");

        setSize(300, 200);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(5, 2));
        topPanel.add(new JLabel("Title : "));
        topPanel.add(titleText);
        topPanel.add(new JLabel("Author : "));
        topPanel.add(authText);
        topPanel.add(new JLabel("Publishing Date : "));
        topPanel.add(pubDateText);
        topPanel.add(new JLabel("Publisher Name : "));
        topPanel.add(pubNameText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(addBtn);
        bottomPanel.add(cancelBtn);

        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);

    }

    /**
     * This method is responsible for handling ActionEvent instances for button
     * clicks specifically "Add" and "Cancel" buttons. It uses getSource() method to
     * track which button is clicked and runs the following actions accordingly.
     * <br>
     * addBook() method is executed to add book if "Add" button is
     * clicked.
     * <br>
     * "Cancel" button closes the pop up window.
     *
     * @param ae ActionEvent instance (button click).
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addBook();

        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
            this.dispose();
        }

    }

    /**
     * If "ae.getSource() == addBtn" (if user clicks on add button in the window),
     * it runs this addBook() method.<br>
     * It checks if all the Textfields aren't empty and calls AddBook Class from
     * Command interface to add new book.<br>
     * If the fields are empty, JOptionPane WARNING_MESSAGE will pop up. Or else, it
     * will display success message if the book is added without issues.
     * <br>
     * Finally, it will display updated book list.
     */
    private void addBook() {
        try {
            String title = titleText.getText();
            String author = authText.getText();
            String publicationYear = pubDateText.getText();
            String publisherName = pubDateText.getText();

            // These conditionals ensure that all textfields are entered before adding.
            if (!title.isEmpty() && !author.isEmpty() && !publicationYear.isEmpty() && !publisherName.isEmpty()) {
                // create and execute the AddBook Command
                Command addBook = new AddBook(title, author, publicationYear, publisherName);
                addBook.execute(mw.getLibrary(), LocalDate.now());

                // Display success message
                String message = "Successlly added new Book to Library";
                JOptionPane.showMessageDialog(this, message, " Added Book Success ",
                        JOptionPane.INFORMATION_MESSAGE);
                mw.displayBooks();
            }
            // Display Error message if not all fields are entered.
            else {
                String message = "Please enter all the Fields!";
                JOptionPane.showMessageDialog(null, message, "Error: Enter All TextFields",
                        JOptionPane.WARNING_MESSAGE);
            }

        } catch (LibraryException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
