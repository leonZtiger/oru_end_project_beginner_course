/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024.pages;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import ngo2024.DataContainers.AccessLevel;
import ngo2024.DataContainers.User;
import ngo2024.Main;

/**
 * A JDialog with login and logout funcktionality
 *
 * @author leonb
 */
public class CredentialsPrompt extends JDialog {

    private JTextField emailField;
    private JTextField passwordField;
    private JLabel errorMessage;

    /**
     * Creates a window dialog object with the passed JFrame as parent window.
     *
     * @param parent, parent window.
     */
    public CredentialsPrompt(JFrame parent) {
        super(parent, "Login", true);

        createLabeledInputs();

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    /**
     * Adds all the graphical inputs to this window.
     */
    private void createLabeledInputs() {

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = User.getUser(emailField.getText(), passwordField.getText());

                // If user is succesfully loged in
                if (user != null && user.getBehorig() != AccessLevel.GUEST) {
                    Main.user = user;
                    dispose();
                    // Else show error.
                } else {
                    errorMessage.setVisible(true);
                }
            }
        });

        JButton logoutButton = new JButton("Logga ut");

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.user = new User();
                dispose();
            }
        });

        errorMessage = new JLabel("Fel inlogg");
        errorMessage.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        errorMessage.setForeground(Color.red);
        errorMessage.setVisible(false);
        loginPanel.add(errorMessage);

        loginPanel.add(emailLabel);
        loginPanel.add(emailField);
        loginPanel.add(Box.createVerticalStrut(10)); // Add spacing
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(Box.createVerticalStrut(10)); // Add spacing

        JPanel p = new JPanel();

        p.add(loginButton);
        p.add(logoutButton);
        loginPanel.add(p);

        this.add(loginPanel);

    }
}
