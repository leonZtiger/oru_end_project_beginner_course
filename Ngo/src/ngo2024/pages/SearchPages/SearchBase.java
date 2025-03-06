/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024.pages.SearchPages;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import ngo2024.pages.TabPageBase;
import ngo2024.pages.TabPageBase;

/**
 * A Base class for implementing search capabiliies with a custom implemented
 * search function.
 *
 * @author leonb
 */
public abstract class SearchBase extends TabPageBase {

    private JTextField searchTermInput;
    private JButton searchButton;
    private JComboBox<String> sortDropdown;
    protected JPanel filterPanel;
    private List<JCheckBox> filters;
    private JScrollPane resultContainer;
    protected JPanel resultPanel;

    private ArrayList<String[]> filters_arr;
    private ArrayList<String> sort_arr;

    /**
     * Creates a tabbed page with search field, filter menu and a sort menu and
     * a result item container.
     *
     * @param parent, the JTabbedPane this will be added to.
     * @param title, the title of the tab
     */
    public SearchBase(JTabbedPane parent, String title) {
        super(parent, title);

        addTab();
    }

    /**
     * Adds filters to the filter menu
     *
     * @param filterName, name of the filter displayed.
     * @param sqlAttrName, the name of the sql-attribute in the database.
     * @param valueToCompare, the value to compare to the attribute.
     */
    protected void addFilter(String filterName, String sqlAttrName, String valueToCompare) {

        // Add to filter query array
        filters_arr.add(new String[]{sqlAttrName, valueToCompare});

        // Add graphical filter button
        JCheckBox filterCheckBox = new JCheckBox(filterName);
        filters.add(filterCheckBox);
        filterPanel.add(filterCheckBox);
        filterPanel.revalidate();
        filterPanel.repaint();

        filterCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search(searchTermInput.getText());
            }
        });
    }

    protected void setForceSelectedFilter(int index) {
        filters.get(index).setSelected(true);
        filters.get(index).setEnabled(false);
    }

    protected void setForceUnSelectedFilter(int index) {
        filters.get(index).setSelected(false);
        filters.get(index).setEnabled(false);
    }

    /**
     * Adds sort option to the sort menu.
     *
     * @param sortName, the string to be displayed as name.
     */
    protected void addSort(String sortName, String sqlAttr) {

        sortDropdown.addItem(sortName);
        sort_arr.add(sqlAttr);
        filterPanel.revalidate();
        filterPanel.repaint();
    }

    /**
     * Gets the select sort option.
     *
     * @return the select sort option, null if nothing is selected.
     */
    protected String getSelectedSort() {
        // Make sure selected item index is inbound
        if (sort_arr.size() == 0 || sortDropdown.getSelectedIndex() >= sort_arr.size()) {
            return null;
        }

        return sort_arr.get(sortDropdown.getSelectedIndex());
    }

    /**
     * Gets all the selected filters.
     *
     * @return a 2d list, each element is of a 1d array thats 2 long, first
     * elemnt is sql-attribute second is the compare to value.
     */
    protected ArrayList<String[]> getSelectedFilters() {
        ArrayList<String[]> selectedFilters = new ArrayList<String[]>();

        int i = 0;
        for (JCheckBox filter : filters) {
            if (filter.isSelected()) {
                if (filters_arr.get(i).length == 2) {
                    selectedFilters.add(new String[]{filters_arr.get(i)[0], filters_arr.get(i)[1]});
                }
            }
            ++i;
        }

        return selectedFilters;
    }

    /**
     * Hook method for triggering the custom search implementation
     *
     * @param searchTerm
     */
    protected abstract void onSearch(String searchTerm);

    private void search(String searchTerm) {

        // Clear previus search results
        resultPanel.removeAll();

        onSearch(searchTerm);

        // Repaint container to update 
        resultPanel.repaint();
        resultPanel.revalidate();
    }

    @Override
    protected void initiate() {

        setLayout(new BorderLayout());  // Use BorderLayout for fixed top panel height
        filters = new ArrayList<>();

        filters_arr = new ArrayList<String[]>();
        sort_arr = new ArrayList<String>();

        // Top panel for search term, search button, filter, and sorting
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setPreferredSize(new Dimension(0, 80));
        searchTermInput = new JTextField(20);
        searchButton = new JButton("Sök");
        topPanel.add(new JLabel("Sök term: "));
        topPanel.add(searchTermInput);
        topPanel.add(searchButton);

        // Sort dropdown
        sortDropdown = new JComboBox<>(new String[]{});
        topPanel.add(new JLabel("Sortera efter: "));
        topPanel.add(sortDropdown);
        sortDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search(searchTermInput.getText());
            }
        });

        // Filter panel
        filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter"));
        topPanel.add(filterPanel);

        add(topPanel, BorderLayout.NORTH);

        // Result panel
        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

        // Scrollable container for the results
        resultContainer = new JScrollPane(resultPanel);
        resultContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        resultContainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(resultContainer, BorderLayout.CENTER);

        // Action listener for search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search(searchTermInput.getText());
            }
        });

        searchTermInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                search(searchTermInput.getText());
            }
        });
    }

}
