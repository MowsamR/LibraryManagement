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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class AddBookWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField titleText = new JTextField();
    private JTextField authText = new JTextField();
    private JTextField pubDateText = new JTextField();
    private JTextField pubNameText = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");
    private JButton closeBtn = new JButton("Close");

    public AddBookWindow(MainWindow mw) {
        this.mw = mw;
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

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addBook();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }

    }

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
