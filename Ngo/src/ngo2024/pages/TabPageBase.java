/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024.pages;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Base class for implementing a tabbed page thats auto handled. This class
 * handles both removing duplicate tabs and also closing tabs and adding.
 *
 * @author leonb
 */
public abstract class TabPageBase extends JPanel {

    protected JLabel tabTitle;
    protected JTabbedPane parent;
    private String title;

    /**
     * Creates a tabbed page that is added to the parent JTabbedPane. If a tab
     * with same title exist it will be removed.
     *
     * @param parent, JTabbedPane, the parent this page will be added to.
     * @param title, the title of the page.
     */
    public TabPageBase(JTabbedPane parent, String title) {

        this.title = title;
        this.parent = parent;

    }

    protected void addTab() {

        if (!hasThePrivlege()) {
            JOptionPane.showMessageDialog(null, "Du har ej behörighet att se denna informaiton.");
            return;
        }

        if (doesAlreadyExist(parent, title)) {
            return;
        }
        // Create and add button for closing this tab
        parent.addTab(title, this);

        // Create a custom tab header with a close button
        int index = parent.indexOfComponent(this);
        JPanel tabHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        tabHeader.setOpaque(false);

        tabTitle = new JLabel(title);
        JButton closeButton = new JButton("X");
        closeButton.setPreferredSize(new Dimension(16, 16));

        closeButton.addActionListener(e -> parent.remove(this));

        tabHeader.add(tabTitle);
        tabHeader.add(closeButton);
        parent.setTabComponentAt(index, tabHeader);

        parent.setSelectedIndex(index);

        initiate();
    }

    /**
     * Initiates the graphical components
     */
    protected abstract void initiate();

    /**
     * Checks if the graphical components is allowed to be seen by the user
     *
     * @return true if user is allowed to see page, false otherwise
     */
    protected abstract boolean hasThePrivlege();

    /**
     * Checks if a tab with the same title as this object exist in the parent
     * JTabbedPane.
     *
     * @param parent, JTabbedPane container.
     * @param title, title to compare to.
     * @return true if a tab with same title exist. false if no duplicate title
     * exist.
     */
    protected boolean doesAlreadyExist(JTabbedPane parent, String title) {
        for (int i = 0; i < parent.getTabCount(); i++) {
            if (parent.getTitleAt(i).equalsIgnoreCase(title)) {
                parent.setSelectedIndex(i);
                return true;
            }
        }
        return false;
    }
}
