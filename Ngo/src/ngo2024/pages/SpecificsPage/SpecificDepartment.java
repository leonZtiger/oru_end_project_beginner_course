/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024.pages.SpecificsPage;

import ngo2024.pages.SearchPages.SearchEmployees;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import ngo2024.DataContainers.AccessLevel;
import ngo2024.DataContainers.Avdelning;
import ngo2024.DataContainers.Handlaggare;
import ngo2024.DataContainers.Land;
import ngo2024.DataContainers.Stad;
import ngo2024.DataContainers.User;
import ngo2024.Main;
import ngo2024.guiComponents.GuiUtil;
import ngo2024.pages.TabPageBase;

/**
 * A tabbed page showing information of the specified department.
 *
 * @author leonb
 */
public final class SpecificDepartment extends SpecificPageBase {
    
    private Avdelning avdelning;

    // Individual JTextFields for each attribute
    private JTextField namnField;
    private JTextField beskrivningField;
    private JTextField adressField;
    private JTextField epostField;
    private JTextField telefonField;

    //  private JTextField chefField;
    /**
     * Creates a tabbed page containg information about this department.
     *
     * @param title, title of the tab
     * @param depID, the key of the row in the avdelning table
     * @param parent, the JTabbedPane this will be added to.
     */
    public SpecificDepartment(String title, String depID, JTabbedPane parent) {
        super(parent, title);
        
        avdelning = new Avdelning(depID);
        
        addTab();
    }
    
    @Override
    protected void initiate() {
        
        setLayout(new BorderLayout());
        // Create  container with general info
        JPanel container = new JPanel(new GridBagLayout());
        
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add fields for each project attribute
        namnField = addField(container, gbc, "Namn");
        beskrivningField = addField(container, gbc, "Beskrivning");
        adressField = addField(container, gbc, "Startdatum");
        epostField = addField(container, gbc, "Slutdatum");
        telefonField = addField(container, gbc, "Kostnad");
        
        setAllFieldValues();
        enableFields(false);
        
        JLabel stadLbl = new JLabel(avdelning.getStadObj().getNamn());
        ActionListener onStadEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String id = GuiUtil.getSelectFromListPupop(Stad.getAll(), "Välj ny stad");
                if (id == null) {
                    return;
                }
                
                avdelning.setStad(id);
                stadLbl.setText(avdelning.getStadObj().getNamn());
            }
        };
        // Stad edit
        addEditableButton(container, gbc, "Stad:", stadLbl, onStadEdit);
        
        JLabel chefLbl = new JLabel(avdelning.getChefObj().getFullname());
        ActionListener onChefEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String id = GuiUtil.getSelectFromListPupop(Handlaggare.getAll(), "Välj en chef");
                if (id == null) {
                    return;
                }
                
                avdelning.setChef(id);
                chefLbl.setText(avdelning.getChefObj().getFullname());
            }
        };
        // Chef edit
        addEditableButton(container, gbc, "Chef:", chefLbl, onChefEdit);
        
        JButton btn = new JButton("Se anställda");
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchEmployees(parent, avdelning);
            }
        });
      
        // Add the info panel to the main panel
        add(container, BorderLayout.CENTER);
        JPanel adminPanel = createAdminOptions();
        adminPanel.add(btn);
        add(adminPanel, BorderLayout.SOUTH);
    }
    
    @Override
    protected boolean hasThePrivlege() {
        return true;
    }
    
    @Override
    protected void enableFields(boolean state) {
        namnField.setEnabled(state);
        beskrivningField.setEnabled(state);
        adressField.setEnabled(state);
        epostField.setEnabled(state);
        telefonField.setEnabled(state);
    }
    
    @Override
    protected void saveAllInputs() {
        avdelning.setNamn(namnField.getText());
        avdelning.setBeskrivning(beskrivningField.getText());
        avdelning.setAdress(adressField.getText());
        avdelning.setEpost(epostField.getText());
        avdelning.setTelefon(telefonField.getText());
    }
    
    @Override
    protected void setAllFieldValues() {
        namnField.setText(avdelning.getNamn());
        beskrivningField.setText(avdelning.getBeskrivning());
        adressField.setText(avdelning.getAdress());
        epostField.setText(avdelning.getEpost());
        telefonField.setText(avdelning.getTelefon());
    }
    
    @Override
    protected boolean allowedToEdit() {
        return true;
    }
    
}
