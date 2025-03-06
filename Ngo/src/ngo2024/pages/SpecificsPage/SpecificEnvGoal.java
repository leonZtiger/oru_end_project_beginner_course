/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024.pages.SpecificsPage;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import ngo2024.DataContainers.AccessLevel;
import ngo2024.DataContainers.HallbarhetsMal;
import ngo2024.Main;
import ngo2024.guiComponents.GuiUtil;
import ngo2024.pages.TabPageBase;
import ngo2024.util.Util;
import oru.inf.InfException;

/**
 * A tabbed page showing information of the specified department.
 *
 * @author leonb
 */
public final class SpecificEnvGoal extends TabPageBase {

    private HallbarhetsMal mal;

    /**
     * Creates a tabbed page containing information about this enviroment goal.
     *
     * @param title, title of the tab
     * @param id, the key of the row in the hallbarhetsmal table
     * @param parent, the JTabbedPane this will be added to.
     */
    public SpecificEnvGoal(String title, String id, JTabbedPane parent) {
        super(parent, title);

        mal = new HallbarhetsMal(id);

        addTab();
    }

    @Override
    protected void initiate() {

        // Set layout for the main panel
        setLayout(new BorderLayout());

        // Create a panel for envirioment goals information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add labels for each field with appropriate titles
        infoPanel.add(GuiUtil.createLabelPair("Hållbarhets namn", mal.getNamn()));
        infoPanel.add(GuiUtil.createLabelPair("Hållbarhets nummer", mal.getMalnummer()));
        infoPanel.add(GuiUtil.createLabelPair("Beskrivning", mal.getBeskrivning()));
        infoPanel.add(GuiUtil.createLabelPair("Prioritet", mal.getPrioritet()));

        // Add the info panel to the main panel
        add(infoPanel, BorderLayout.CENTER);
    }

    @Override
    protected boolean hasThePrivlege() {
        return Main.user.getBehorig() != AccessLevel.GUEST;
    }
}
