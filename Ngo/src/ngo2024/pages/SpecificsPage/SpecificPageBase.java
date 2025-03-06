/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024.pages.SpecificsPage;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import ngo2024.DataContainers.AccessLevel;
import ngo2024.Main;
import ngo2024.pages.TabPageBase;

/**
 *
 * @author leonb
 */
public abstract class SpecificPageBase extends TabPageBase {

    private JButton cancelButton;
    private JButton saveButton;
    private JButton editButton;

    private ArrayList<JButton> button_foradmin;

    /**
     * Creates a page based in the passed tab container.
     *
     * @param parent tabbedPane to add this to.
     * @param title the title of the tab.
     */
    public SpecificPageBase(JTabbedPane parent, String title) {
        super(parent, title);

        button_foradmin = new ArrayList<JButton>();
    }

    /**
     * Creates a panel containing buttons that give editable actions. Edit,
     * cancel, save.
     *
     * @return A Jpanel with the buttons, edit, cancel, save.
     */
    protected JPanel createAdminOptions() {
        editButton = new JButton("Redigera");
        editButton.setFocusPainted(false);
        editButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        cancelButton = new JButton("Avbryt");

        cancelButton.setFocusPainted(false);
        cancelButton.setVisible(false);
        cancelButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        saveButton = new JButton("Spara");
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAllInputs();
                setEditMode(false);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEditMode(false);
                setAllFieldValues();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setEditMode(true);

            }
        });

        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonContainer.add(cancelButton);
        buttonContainer.add(editButton);
        buttonContainer.add(saveButton);

        setEditMode(false);

        return buttonContainer;

    }

    /**
     * Creates a JTextField and adds it to the passed panel. The textfield is
     * aligned based on the gridbagconatraints x +1. ALso adds a label to the
     * left to the textfiel with the passed string.
     *
     * @param panel the panel to add this to.
     * @param gbc the gridbagconstraint of the panel.
     * @param label the string of the label to the left of the field.
     * @return the created textfield.
     */
    protected JTextField addField(JPanel panel, GridBagConstraints gbc, String label) {
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        JTextField textField = new JTextField();
        panel.add(textField, gbc);

        return textField;
    }

    /**
     * Creates a button with a label to the left and another label in the
     * middle, then thebutton to the right. The button is added to the passed
     * panel aligned with the gridbagconstraints x +1.
     *
     * @param panel the panel this buttons is added to.
     * @param gbc the gridbagconstraints the buttons is aligned with.
     * @param label left most label, often used to describe the input type.
     * @param value middle label, can be used to describe the value of the
     * picked option.
     * @param actionListener the actionlistener thats triggered when the button
     * is pressed.
     */
    protected void addEditableButton(JPanel panel, GridBagConstraints gbc, String label, JLabel value, ActionListener actionListener) {
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(value, gbc);

        gbc.gridx = 2;
        JButton button = new JButton("Ändra");
        button.setEnabled(false);
        button.addActionListener(actionListener);

        button_foradmin.add(button);
        panel.add(button, gbc);
    }

    /**
     * Enables the edit buttons and the fields to the passed state.
     *
     * @param state the state to set the fields and edit buttons. True =
     * editable, False = not editable.
     */
    protected void setEditMode(boolean state) {

        if (!allowedToEdit()) {
            state = false;
        }

        if (state) {
            enableFields(true);

            button_foradmin.forEach(f -> {
                f.setEnabled(true);
            });

            editButton.setVisible(false);
            saveButton.setVisible(true);
            cancelButton.setVisible(true);

        } else {

            enableFields(false);

            button_foradmin.forEach(f -> {
                f.setEnabled(false);
            });

            editButton.setVisible(true);
            saveButton.setVisible(false);
            cancelButton.setVisible(false);
        }

    }

    /**
     * Abstract method to override and add all the fields you want to be state
     * changable.
     *
     * @param state the state to set all the fields, true = editable, false =
     * not editable.
     */
    protected abstract void enableFields(boolean state);

    /**
     * Abstract method which is called on save button press. Override this and
     * add all the data you want to save on save,
     */
    protected abstract void saveAllInputs();

    /**
     * Abstract method which is called when to set all fields to their apropiet
     * values. Overrride this for adding data you want to be set in the fields.
     */
    protected abstract void setAllFieldValues();

    /**
     * Abstract method which is called when checking if the edit mode can be enabled.
     * @return True if the edit mode can be enabled, false otherwise.
     */
    protected abstract boolean allowedToEdit();
}
