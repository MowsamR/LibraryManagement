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
import bcu.cmp5332.librarysystem.commands.DeletePatron;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

public class DeletePatronWindow extends JFrame implements ActionListener {

    private MainWindow mw;

    private JComboBox<String> comboBox;
    private JButton deleteButton = new JButton("Delete");
    private JButton cancelButton = new JButton("Cancel");
    private Library library;
    private JLabel selectedLabelOption;
    private Patron patronSelected;
    private int patronID;

    public DeletePatronWindow(MainWindow mw, Library lib) {
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
        	System.out.println(ex.getMessage());
        }

        setTitle("Delete Patron");

        setSize(600, 250);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        topPanel.add(new JLabel("Select Patron: "));

        // This is a dropdown option that shows all existing patrons
        List<Patron> patronList = library.getPatrons();
        // used to store the names of patrons in comboBox
        DefaultComboBoxModel<Patron> patronModel = new DefaultComboBoxModel<>(patronList.toArray(new Patron[0]));
        JComboBox<Patron> comboBox = new JComboBox<>(patronModel);

        // This action listener stores the clicked option into variable so that it's
        // accessible later on when you click delete
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

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == deleteButton) {

            deletePatron();
        } else if (ae.getSource() == cancelButton) {
            this.setVisible(false);
        }

    }

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

    public List<String> patronDropdownOption() {
        List<Patron> patronList = library.getPatrons();
        // used to store the names of patron in comboBox
        List<String> patronNames = new ArrayList<>();
        for (Patron patron : patronList) {
            patronNames.add(patron.getDetailsShort());
        }
        return patronNames;
    }

}
