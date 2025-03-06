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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import ngo2024.DataContainers.AccessLevel;
import ngo2024.DataContainers.Avdelning;
import ngo2024.DataContainers.Land;
import ngo2024.DataContainers.User;
import ngo2024.Main;
import ngo2024.guiComponents.GuiUtil;
import ngo2024.pages.TabPageBase;

/**
 *
 * @author leonb
 */
public class AddEmployee extends SpecificPageBase {

    private JTextField firstNamnField;
    private JTextField lastNamnField;
    private JTextField adressField;
    private JTextField epostField;
    private JTextField telefonField;
    private JTextField anstalldSedanField;

    private String avdelningId = "";

    private User person;

    /**
     * Creates a employee configure page, where you can add an employee.
     * @param parent, the tabbedPane container to add this page to.
     */
    public AddEmployee(JTabbedPane parent) {
        super(parent, "Lägg till anställd");

        // Create empty temporary user
        person = new User();

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
        JLabel depLbl = new JLabel("Välj");
        ActionListener onDepEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = GuiUtil.getSelectFromListPupop(Avdelning.getAll(), "Välj ny avdelning");
                if (id == null) {
                    return;
                }

                depLbl.setText(new Avdelning(id).getNamn());
                avdelningId = id;
            }
        };

        // Department edit
        addEditableButton(container, gbc, "Avdelning:", depLbl, onDepEdit);

        JCheckBox adminBox = new JCheckBox("Är admin");

        gbc.gridy++;
        gbc.gridx = 0;
        container.add(adminBox, gbc);

        createAdminOptions();
        setEditMode(true);

        // Add the info panel to the main panel
        add(container, BorderLayout.CENTER);

        JButton btn = new JButton("Lägg till");
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Final refrence for the actionlistener to use
        final Component c = this;

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int choice = JOptionPane.showConfirmDialog(null, "Är du säker på att lägga till en anställd?", "Godkänn", JOptionPane.YES_NO_OPTION);
                
                if (choice == JOptionPane.YES_OPTION) {
                    // Create user
                    int id = User.createUser(
                            firstNamnField.getText(),
                            lastNamnField.getText(),
                            telefonField.getText(),
                            anstalldSedanField.getText(),
                            epostField.getText(),
                            adressField.getText(),
                            avdelningId,
                            adminBox.isSelected()
                    );
                    // if not succesfull, show that to the user
                    if (id == -1) {
                        // If creation failed, keep window and prompt message.
                        JOptionPane.showMessageDialog(null, "Ett fel inträffade vid inmatning av data. vänligen försök igen.");
                    } 
                    // Otherwise succesesfull
                    else {
                        // Delete tab
                        parent.remove(c);

                        // Open employe page of the newly created user
                        new SpecificEmployee(new User(String.valueOf(id)).getFullname(), String.valueOf(id), parent);
                    }
                }
            }
        });

        add(btn, BorderLayout.SOUTH);

    }

    @Override
    protected boolean hasThePrivlege() {
        return Main.user.getBehorig() != AccessLevel.GUEST;
    }

    @Override
    protected void enableFields(boolean state) {

    }

    @Override
    protected void saveAllInputs() {

    }

    @Override
    protected void setAllFieldValues() {

    }

    @Override
    protected boolean allowedToEdit() {
        return true;
    }

}
