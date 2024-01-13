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

//REMEMBER TO ADD DOC!!

public class AddPatronWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField patronNameText = new JTextField();
    private JTextField patronPhoneNumText = new JTextField();
    private JTextField patronEmailText = new JTextField();
    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    public AddPatronWindow(MainWindow mw) {
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
        setTitle("Add a New Patron");

        setSize(300, 200);
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

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addPatron();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    private void addPatron() {
        try {
            String name = patronNameText.getText();
            String phoneNumer = patronPhoneNumText.getText();
            String email = patronEmailText.getText();
            // create and execute the AddPatron Command
            Command addPatron = new AddPatron(name, phoneNumer, email);
            addPatron.execute(mw.getLibrary(), LocalDate.now());
            // refresh the view with the list of patrons
            mw.listPatrons();
            // hide (close) the AddPatronWindow
            this.setVisible(false);
        } catch (LibraryException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}