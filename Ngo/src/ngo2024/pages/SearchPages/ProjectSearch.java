/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024.pages.SearchPages;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerDateModel;
import ngo2024.Main;
import ngo2024.pages.SearchPages.SearchBase;
import ngo2024.pages.SpecificsPage.SpecificProject;
import ngo2024.util.Util;
import oru.inf.InfException;

/**
 * A tabbed page with search functionality. Can search on all the projects in
 * the database.
 *
 * @author leonb
 */
public class ProjectSearch extends SearchBase {

    private JSpinner startDateSpinner;
    private JSpinner endDateSpinner;

    /**
     * Initiates the tabbed page.
     *
     * @param parent, the JTabbedPane this will be added to
     */
    public ProjectSearch(JTabbedPane parent) {
        super(parent, "Projekt utforskare");

        // Add date picker for start and end dates
        startDateSpinner = new JSpinner(new SpinnerDateModel());
        startDateSpinner.setEditor(new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd"));

        // Set oldest date
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.JANUARY, 1);
        startDateSpinner.setValue(calendar.getTime());

        filterPanel.add(new JLabel("Från"));
        filterPanel.add(startDateSpinner);

        filterPanel.add(new JLabel("Till"));
        endDateSpinner = new JSpinner(new SpinnerDateModel());
        endDateSpinner.setEditor(new JSpinner.DateEditor(endDateSpinner, "yyyy-MM-dd"));

        filterPanel.add(endDateSpinner);

        // Configure filters
        addFilter("Mina", "pid", "( SELECT pid FROM ans_proj WHERE aid = '" + Main.user.getAid() + "' )");
        addFilter("Ansvarig för", "projektchef", Main.user.getAid());
        addFilter("Min avdelning", "pid", "( SELECT pid FROM ans_proj WHERE aid IN (SELECT aid FROM anstalld WHERE avdelning = '" + Main.user.getAvdelning() + "') GROUP BY pid) ");
        addFilter("Pågående", "status", "'Pågående'");
        addFilter("Avslutat", "status", "'Avslutat'");
        addFilter("Planerat", "status", "'Planerat'");

        addSort("Prioritet fallande", "prioritet DESC");
        addSort("Prioritet stigande", "prioritet ASC");
        addSort("Datum fallande", "startdatum DESC");
        addSort("Datum stigande", "startdatum ASC");
        addSort("Kostnad fallande", "kostnad DESC");
        addSort("Kostnad stigande", "kostnad ASC");

    }

    @Override
    protected boolean hasThePrivlege() {
        return true;
    }

    @Override
    protected void onSearch(String searchTerm) {

        ArrayList<String[]> filters = getSelectedFilters();

        // Adds date spectrum to the search
        String dateQuery = " startdatum BETWEEN '" + getSqlDate(startDateSpinner) + "' AND '" + getSqlDate(endDateSpinner) + "' ";
        // Bad way of adding date search but it works
        filters.add(new String[]{dateQuery, ""});
        // Get search results
        try {
            Util.createSearchResults(resultPanel, null, "projekt", new String[]{"projektnamn"}, filters, getSelectedSort(), searchTerm, "projektnamn", "pid", SpecificProject.class);
        } catch (InfException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(ProjectSearch.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private String getSqlDate(JSpinner dateSpinner) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            String dateString = ((JSpinner.DateEditor) dateSpinner.getEditor()).getFormat().format(dateSpinner.getValue());
            Date utilDate = format.parse(dateString);

            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            return sqlDate.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "0000-00-00";
    }
}
