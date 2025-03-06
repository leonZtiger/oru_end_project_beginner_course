/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngo2024;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;
import javax.swing.JOptionPane;
import ngo2024.DataContainers.User;
import ngo2024.pages.LandingPage;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 * Entry class for application. Initiates window and handles preserved states
 * before and after exits.
 *
 * @author jpn
 */
public class Main {

    // Stores database connection
    public static InfDB infDB;
    // Current User of this application
    public static User user;

    /**
     * Entry point of application
     *
     * @param args none
     */
    public static void main(String[] args) {

        try {
            // Create connection with database
            infDB = new InfDB("NGO_2024", "3306", "dbAdmin2024", "dbAdmin2024PW");

            // Init look and feel
            FlatDarkLaf.setup();

            // Load logging info if stored
            Preferences pref = Preferences.userRoot();
            String email = pref.get("epost", "");
            String pass = pref.get("pass", "");

            user = User.getUser(email, pass);

            // If logging failed from saved values, create guest user
            if (user == null) {
                user = new User();
            }

            // Open main page
            LandingPage p = new LandingPage();

            // Save credentials from last logged in on window exit
            p.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    pref.put("epost", user.getEpost());
                    pref.put("pass", user.getLosenord());
                }
            });

        } catch (InfException ettUndantag) {
            // Prompt user that no connection could be done
            JOptionPane.showMessageDialog(null, "Kunde ej ansluta till databasen. Kolla din internet uppkoppling :)");
        }
    }

}
