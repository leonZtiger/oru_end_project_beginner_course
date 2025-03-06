/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024.guiComponents;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import ngo2024.DataContainers.DataContainerCommon;
import ngo2024.Main;

/**
 * Util class for redundant graphical components.
 *
 * @author leonb
 */
public class GuiUtil {

    /**
     * Create a JPanel containing a pair of labels horrizontally aligned.
     *
     * @param p1, string value of the left label.
     * @param p2, String value of the right label.
     * @return JPanel with pair of JLabels with text from the string param.
     */
    public static JPanel createLabelPair(String p1, String p2) {
        JPanel con = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(p1 + ":");
        label.setFont(new Font("Arial", Font.BOLD, 12));
        con.add(label);
        con.add(new JLabel(p2 != null ? p2 : "N/A"));

        return con;
    }

    /**
     * Create a JPanel containing a JLabel to the left and a JTextField to the
     * right, Horizontaly aligned.
     *
     * @param p1, String value of the JLabel
     * @param p2, String value of the JTextField
     * @return JPanel with a JLabel and a JTextField pair.
     */
    public static JPanel createLabelTextfield(String p1, String p2) {
        JPanel con = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(p1 + ":");
        label.setFont(new Font("Arial", Font.BOLD, 12));
        con.add(label);
        con.add(new JTextField(p2 != null ? p2 : "N/A"));

        return con;
    }

    /**
     * Creates a list with items. Each item has a label based on the string
     * passed in the map. Each item has a asigned JButton with a actionlistener
     * from the passed map
     *
     * @param items, items to be listed.
     * @return a JPanel with items y-axis aligned.
     */
    public static JPanel createClickableItemList(HashMap<String, ActionListener> items) {
        // Create a JPanel to hold the items
        JPanel con = new JPanel();
        con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS)); // Vertical list

        int itemHeight = 30; // Fixed height for each item
        for (String title : items.keySet()) {
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setPreferredSize(new Dimension(0, itemHeight)); // Fixed height
            itemPanel.setMaximumSize(new Dimension(500, itemHeight));
            itemPanel.setMinimumSize(new Dimension(200, itemHeight));
            itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            JLabel label = new JLabel(title);
            JButton button = new JButton("Välj");

            // Add action listener to the button
            ActionListener action = items.get(title);
            if (action != null) {
                button.addActionListener(action);
            }

            itemPanel.add(label, BorderLayout.CENTER);
            itemPanel.add(button, BorderLayout.EAST);
            con.add(itemPanel);
        }

        // Wrap the panel in a JScrollPane
       
        return con;
    }

    /**
     * Creates a popup with a list clickable items based on DataContainerCommon
     * list.
     *
     * @param labels, the visible items.
     * @param title, the title of the window.
     * @return the id selected from the items, or if no value is selected null
     * also if window is closed null.
     */
    public static String getSelectFromListPupop(ArrayList<DataContainerCommon> labels, String title) {
        // Create a dialog window
        JDialog d = new JDialog();
        d.setTitle(title);
        d.setModal(true); // Make the dialog block input to other windows

        // Panel to hold the list and buttons
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());

        // Create a JList for clickable items
        DefaultListModel m = new DefaultListModel();
        JList<String> itemList = new JList<>(m);

        labels.forEach(e -> {
            m.addElement(e.getName());
        });

        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(itemList);

        // Add scrollPane to the panel
        p.add(scrollPane, BorderLayout.CENTER);

        // Selected item holder
        // Must be an array since its final because its accessed in a actionlistener
        final DataContainerCommon[] selectedItem = {null};

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Cancel button
        JButton cancel = new JButton("Avbryt");
        cancel.addActionListener(e -> d.dispose());

        // OK button
        JButton ok = new JButton("Välj");
        ok.addActionListener(e -> {
            int selectedIndex = itemList.getSelectedIndex();
            if (selectedIndex != -1) {
                selectedItem[0] = labels.get(selectedIndex);
            }
            d.dispose();
        });

        buttonPanel.add(cancel);
        buttonPanel.add(ok);

        // Add button panel to the main panel
        p.add(buttonPanel, BorderLayout.SOUTH);

        // Add panel to dialog
        d.add(p);
        d.pack();
        d.setLocationRelativeTo(null); // Center the dialog
        d.setVisible(true);
        
        if(selectedItem[0] == null)
            return null;
        
        return selectedItem[0].getId();
    }

    /**
     * Creates a popup with a list clickable items based on passed string values
     *
     * @param labels, the visible title of the item.
     * @param title, the title of the window.
     * @return the string select from the items, or if no value is selected null
     * also if window is closed null.
     */
    public static String getSelectFromStringListPupop(String[] labels, String title) {
        // Create a dialog window
        JDialog d = new JDialog();
        d.setTitle(title);
        d.setModal(true); // Make the dialog block input to other windows

        // Panel to hold the list and buttons
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());

        // Create a JList for the clickable items
        JList<String> itemList = new JList<>(labels);

        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(itemList);

        // Add scrollPane to the panel
        p.add(scrollPane, BorderLayout.CENTER);

        // Selected item holder
        // Must be an array since its final because its accessed in a actionlistener
        final String[] selectedItem = {null};

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Cancel button
        JButton cancel = new JButton("Avbryt");
        cancel.addActionListener(e -> d.dispose());

        // OK button
        JButton ok = new JButton("Välj");
        ok.addActionListener(e -> {
            int selectedIndex = itemList.getSelectedIndex();
            if (selectedIndex != -1) {
                selectedItem[0] = labels[selectedIndex];
            }
            d.dispose();
        });

        buttonPanel.add(cancel);
        buttonPanel.add(ok);

        // Add button panel to the main panel
        p.add(buttonPanel, BorderLayout.SOUTH);

        // Add panel to dialog
        d.add(p);
        d.pack();
        d.setLocationRelativeTo(null);
        d.setVisible(true);

        return selectedItem[0];
    }
}
