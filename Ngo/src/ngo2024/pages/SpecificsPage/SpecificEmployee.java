/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024.pages.SpecificsPage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import ngo2024.DataContainers.AccessLevel;
import ngo2024.DataContainers.Avdelning;
import ngo2024.DataContainers.Handlaggare;
import ngo2024.DataContainers.User;
import ngo2024.Main;
import ngo2024.guiComponents.GuiUtil;
import ngo2024.pages.SearchPages.SearchEmployees;
import ngo2024.pages.TabPageBase;

/**
 *
 * @author leonb
 */
public final class SpecificEmployee extends SpecificPageBase {

    private User person;

    private JTextField firstNamnField;
    private JTextField lastNamnField;
    private JTextField adressField;
    private JTextField epostField;
    private JTextField telefonField;
    private JTextField anstalldSedanField;

    public SpecificEmployee(String title, String aid, JTabbedPane parent) {
        super(parent, title);

        person = new User(aid);

        addTab();
    }

    @Override
    protected void initiate() {

        // Set layout for the main panel
        setLayout(new BorderLayout());

        // Create a panel for employee information
        JPanel container = new JPanel(new GridBagLayout());

        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add labels for each field with appropriate attributes
        firstNamnField = addField(container, gbc, "Förnamn");
        lastNamnField = addField(container, gbc, "Efternamn");
        adressField = addField(container, gbc, "Adress");
        epostField = addField(container, gbc, "Epost");
        telefonField = addField(container, gbc, "Telefon");
        anstalldSedanField = addField(container, gbc, "Anställd sedan");

        // Button for opening department selector
        JLabel depLbl = new JLabel(person.getAvdelningStr());
        ActionListener onDepEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = GuiUtil.getSelectFromListPupop(Avdelning.getAll(), "Välj ny avdelning");
                if (id == null) {
                    return;
                }

                person.setAvdelning(id);
                depLbl.setText(person.getAvdelningStr());
            }
        };

        // Department edit
        addEditableButton(container, gbc, "Avdelning:", depLbl, onDepEdit);

        setAllFieldValues();
        enableFields(false);

        // Add the info panel to the main panel
        add(container, BorderLayout.CENTER);

        JPanel adminPanel = createAdminOptions();

        JButton btn = new JButton("Tabort ");
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        final Component thisRef = this;
        
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int choice = JOptionPane.showConfirmDialog(null, "Är du säker på att tabort en anställd?", "Godkänn", JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    User.remove(person.getAid());
                    
                    parent.remove(thisRef);
                }
            }
        });

        adminPanel.add(btn);

        add(adminPanel, BorderLayout.SOUTH);

    }

    @Override
    protected boolean hasThePrivlege() {
        return Main.user.getBehorig() != AccessLevel.GUEST;
    }

    @Override
    protected void enableFields(boolean state) {
        lastNamnField.setEnabled(state);
        firstNamnField.setEnabled(state);
        adressField.setEnabled(state);
        epostField.setEnabled(state);
        telefonField.setEnabled(state);
        anstalldSedanField.setEnabled(state);
    }

    @Override
    protected void saveAllInputs() {
        person.setFornamn(firstNamnField.getText());
        person.setEfternamn(lastNamnField.getText());
        person.setAdress(adressField.getText());
        person.setEpost(epostField.getText());
        person.setTelefon(telefonField.getText());
        person.setAnstallningsdatum(anstalldSedanField.getText());
    }

    @Override
    protected void setAllFieldValues() {
        firstNamnField.setText(person.getFornamn());
        lastNamnField.setText(person.getEfternamn());
        adressField.setText(person.getAdress());
        epostField.setText(person.getEpost());
        telefonField.setText(person.getTelefon());
        anstalldSedanField.setText(person.getAnstallningsdatum());
    }

    @Override
    protected boolean allowedToEdit() {
        return true;
    }
}
