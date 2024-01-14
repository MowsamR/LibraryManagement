package bcu.cmp5332.librarysystem.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
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
import bcu.cmp5332.librarysystem.commands.DeletePatron;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

/**
 * DeletePatronWindow allows user to delete patrons from the library via GUI. It
 * is executed when user clicks on 'delete' option under Patrons menu.
 * User select the patron for deletion on a dropdown list that contains all the
 * patrons in library.
 */
public class DeletePatronWindow extends JFrame implements ActionListener {

    private MainWindow mw;

    private JButton deleteButton = new JButton("Delete");
    private JButton cancelButton = new JButton("Cancel");
    private Library library;
    private JLabel selectedLabelOption;
    private Patron patronSelected;
    private int patronID;

    /**
     * Constructs DeletePatronWindow for referencing/connecting to the main window.
     * 
     * @param mw  MainWindow instance where AddPatronWindow is generated.
     * @param lib Library Instance containing the patron.
     */
    public DeletePatronWindow(MainWindow mw, Library lib) {
        this.mw = mw;
        this.library = lib;
        initialize();
    }

    /**
     * initialize() is responsible for GUI display properties and styling on. For
     * example, it sets window size, uses FlowLayout() for JLabel Text And JButton
     * display positions. <br>
     * Additionally, it contains methods for setting/configuring JComboBox dropdown
     * list and actionListeners of tracking the patron option chosen.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        	System.out.println(ex.getMessage());
        }

        setTitle("Delete Patron");

        setSize(600, 250);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        topPanel.add(new JLabel("Select Patron: "));

        List<Patron> patronList = library.getPatrons();
        // This makes the droplist into "Patron" class type
        DefaultComboBoxModel<Patron> patronModel = new DefaultComboBoxModel<>(patronList.toArray(new Patron[0]));

        // used to store the names of patrons in comboBox
        JComboBox<Patron> comboBox = new JComboBox<>(patronModel);

        // This action listener stores the Patron ID of the clicked Patron option so
        // that
        // it's accessible later on when you click delete and deletePatron() method is
        // called.
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                patronSelected = comboBox.getItemAt(comboBox.getSelectedIndex());
                patronID = patronSelected.getId();
                selectedLabelOption.setText("\nSelected patron for deletetion: " + patronSelected);
            }
        });
        topPanel.add(comboBox);
        selectedLabelOption = new JLabel("\nSelected Patron: ");
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
     * deletePatron() method is executed to delete patron from library if "Delete"
     * button is clicked.
     * <br>
     * "Cancel" button closes the pop up window.
     * 
     * @param ae ActionEvent instance (button click tracking for "Add" or "Cancel"
     *           Buttons).
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == deleteButton) {

            deletePatron();
        } else if (ae.getSource() == cancelButton) {
            this.setVisible(false);
        }

    }

    /**
     * Using the Patron ID, it calls DeletePatron Class from Command Interface to
     * delete the patron. If successfull, it shows a popup JOPtionPane message to
     * indicating successfull deletion.
     * <br>
     * Then, it will display the updated list of patrons.
     */
    private void deletePatron() {
        try {
            Command deletePatron = new DeletePatron(patronID);
            deletePatron.execute(mw.getLibrary(), LocalDate.now());
            // Display success message
            String message = "Successlly deleted Patron.";
            JOptionPane.showMessageDialog(this, message, " Delete Patron Success ",
                    JOptionPane.INFORMATION_MESSAGE);
            mw.listPatrons();
        } catch (LibraryException e) {
            JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

}
