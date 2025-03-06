/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024.pages.SpecificsPage;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import ngo2024.DataContainers.AccessLevel;
import ngo2024.DataContainers.Handlaggare;
import ngo2024.DataContainers.Land;
import ngo2024.DataContainers.Partner;
import ngo2024.DataContainers.Stad;
import ngo2024.Main;
import ngo2024.guiComponents.GuiUtil;
import ngo2024.pages.TabPageBase;
import ngo2024.util.Util;
import oru.inf.InfException;

/**
 * A tabbed page showing information of the specified partner.
 *
 * @author leonb
 */
public final class SpecificPartner extends SpecificPageBase {

    private Partner partner;

    private JTextField kontaktnamnField;
    private JTextField namnField;
    private JTextField epostField;
    private JTextField telefonField;
    private JTextField adressField;
    private JTextField branchField;

    /**
     * Creates a partner page containing all relevent information.
     *
     * @param name, title of the tab.
     * @param pid, partner row key
     * @param parent, the JTabbedPane this will be added to.
     */
    public SpecificPartner(String name, String pid, JTabbedPane parent) {
        super(parent, name);

        partner = new Partner(pid);

        addTab();

    }

    @Override
    protected void initiate() {

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Main container for content
        JPanel container = new JPanel(new GridBagLayout());

        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add fields for each partner attribute
        namnField = addField(container, gbc, "Namn");
        branchField = addField(container, gbc, "Branch");
        kontaktnamnField = addField(container, gbc, "Kontaktnamn");
        epostField = addField(container, gbc, "Epost");
        telefonField = addField(container, gbc, "Telefon");
        adressField = addField(container, gbc, "Adress");

        setAllFieldValues();
        enableFields(false);

        JLabel landLbl = new JLabel(partner.getStadObj().getNamn());
        ActionListener onStadEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = GuiUtil.getSelectFromListPupop(Stad.getAll(), "Välj ny stad");
                if (id == null) {
                    return;
                }

                partner.setStad(id);
                landLbl.setText(partner.getStadObj().getNamn());
            }
        };

        // Stad edit
        addEditableButton(container, gbc, "Stad:", landLbl, onStadEdit);

        add(container, BorderLayout.CENTER);

        add(createAdminOptions(), BorderLayout.SOUTH);

    }

    @Override
    protected boolean hasThePrivlege() {
        return Main.user.getBehorig() != AccessLevel.GUEST;
    }

    @Override
    protected void enableFields(boolean state) {
        namnField.setEnabled(state);
        branchField.setEnabled(state);
        kontaktnamnField.setEnabled(state);
        epostField.setEnabled(state);
        telefonField.setEnabled(state);
        adressField.setEnabled(state);

    }

    @Override
    protected void saveAllInputs() {
        partner.setNamn(namnField.getText());
        partner.setBranch(branchField.getText());
        partner.setKontaktperson(kontaktnamnField.getText());
        partner.setKontaktepost(epostField.getText());
        partner.setTelefon(telefonField.getText());
        partner.setAdress(adressField.getText());
    }

    @Override
    protected void setAllFieldValues() {
        namnField.setText(partner.getName());
        branchField.setText(partner.getBranch());
        kontaktnamnField.setText(partner.getKontaktperson());
        epostField.setText(partner.getKontaktepost());
        telefonField.setText(partner.getTelefon());
        adressField.setText(partner.getAdress());
    }

    @Override
    protected boolean allowedToEdit() {
        return true;
    }
}
