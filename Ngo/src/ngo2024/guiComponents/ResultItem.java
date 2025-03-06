/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024.guiComponents;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import ngo2024.pages.LandingPage;

/**
 * Class describing a item from a search result.
 *
 * @author leonb
 */
public class ResultItem extends JPanel {

    private JLabel nameLbl;

    /**
     * Creates object with a title and on click opens a page object based on the
     * id.
     *
     * @param title, the title of the item.
     * @param id, the key that the specificPageClass object will use.
     * @param specificPageClass, a class that must have a the type of
     * constructor (String "title of the tab", String "key", JTabbedPane
     * "container to add to").
     */
    public ResultItem(String title, String id, Class specificPageClass) {
        super();

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        setPreferredSize(new Dimension(0, 30));
        setMaximumSize(new Dimension(500, 30));
        setMinimumSize(new Dimension(200, 30));
        
        nameLbl = new JLabel(title);
        add(nameLbl, BorderLayout.WEST);

        JButton open = new JButton("Öppna");

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    specificPageClass.getConstructor(String.class, String.class, JTabbedPane.class).newInstance(title, id, LandingPage.content);
                } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(ResultItem.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        add(open, BorderLayout.EAST);

    }
}
