package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.commands.AddPatron;
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
 * AddPatronWindow class extends JFrame that's responsible for creating a pop up
 * window when the user wants to add new patrons.<br>
 * It's created and used when "Add" option is clicked under Patrons
 * Menu in MainWindow class.<br>
 * It displays a form to type in Patron details: (Name, Phone Number, Email).
 * 
 */
public class AddPatronWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField patronNameText = new JTextField();
    private JTextField patronPhoneNumText = new JTextField();
    private JTextField patronEmailText = new JTextField();
    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    /**
     * Constructs AddPatronWindow for referencing/connecting to the main window.
     * 
     * @param mw The MainWindow instance where AddPatronWindow is generated.
     */
    public AddPatronWindow(MainWindow mw) {
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
        setTitle("Add a New Patron");

        setSize(500, 200);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(5, 2));
        topPanel.add(new JLabel("Name : "));
        topPanel.add(patronNameText);
        topPanel.add(new JLabel("Phone Number : "));
        topPanel.add(patronPhoneNumText);
        topPanel.add(new JLabel("Email : "));
        topPanel.add(patronEmailText);

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
     * Handles ActionEvent instances for button clicks specifically "Add" and
     * "Cancel" buttons. <br>
     * It uses getSource() method to track which button is clicked and runs the
     * following actions accordingly.
     * <br>
     * addPatron() method is executed to add patron if "Add" button is
     * clicked.
     * <br>
     * "Cancel" button closes the pop up window.
     *
     * @param an ActionEvent instance (button click tracking for "Add" or "Cancel"
     *           Buttons).
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addPatron();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    /**
     * Executed when "ae.getSource() == addBtn" (if user clicks on 'add button' in
     * the window). <br>
     * It checks if all the Textfields aren't empty and calls AddPatron Class from
     * Command interface to add new patron into the library.<br>
     * If the fields are empty, JOptionPane WARNING_MESSAGE will pop up. Or else, it
     * will display success message if the patron is added without issues.
     * <br>
     * Finally, it will display updated patron list.
     */
    private void addPatron() {
        try {
            String name = patronNameText.getText();
            String phoneNumber = patronPhoneNumText.getText();
            String email = patronEmailText.getText();

            // These conditionals ensure that all textfields are entered before adding.
            if (!name.isEmpty() && !phoneNumber.isEmpty() && !email.isEmpty()) {
                // create and execute the AddPatron Command
                Command addPatron = new AddPatron(name, phoneNumber, email);
                addPatron.execute(mw.getLibrary(), LocalDate.now());

                // Display success message
                String message = "Successlly added new Patron to Library";
                JOptionPane.showMessageDialog(this, message, " Added Patron Success ",
                        JOptionPane.INFORMATION_MESSAGE);
                mw.listPatrons();
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