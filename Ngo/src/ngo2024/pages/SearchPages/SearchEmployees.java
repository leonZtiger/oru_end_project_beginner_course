/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024.pages.SearchPages;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTabbedPane;
import ngo2024.DataContainers.AccessLevel;
import ngo2024.DataContainers.Avdelning;
import ngo2024.Main;
import ngo2024.pages.SearchPages.SearchBase;
import ngo2024.pages.SpecificsPage.SpecificEmployee;
import ngo2024.util.Util;
import oru.inf.InfException;

/**
 * A tabbed page with search functionality. Can search on all the employees in
 * the database.
 *
 * @author leonb
 */
public class SearchEmployees extends SearchBase {

    /**
     * Initiates a tabbed page with search functionality.
     *
     * @param parent, the JTabbedPane this will be added to.
     * @param avdelning, if this page is derived from a department and search
     * should be based on that
     */
    public SearchEmployees(JTabbedPane parent, Avdelning avdelning) {
        super(parent, avdelning != null ? avdelning.getNamn() + " anställda"
                : "Anställda utforskare");

        if (avdelning != null) {
            addFilter(avdelning.getNamn().substring(0, 10) + "...", "avdelning", avdelning.getAvdid());
            setForceSelectedFilter(0);
        } else if (Main.user.getBehorig() != AccessLevel.GUEST) {
            addFilter("Min avdelning", "avdelning", Main.user.getAvdelning());
            //setForceSelectedFilter(filters.get(0));
        }

    }

    @Override
    protected void onSearch(String searchTerm) {

        // Get search results
        try {

            Util.createSearchResults(resultPanel, "*, concat(fornamn,' ',efternamn) as namn", "anstalld", new String[]{"efternamn", "fornamn", "telefon", "epost"}, getSelectedFilters(), getSelectedSort(), searchTerm, "namn", "aid", SpecificEmployee.class);
        } catch (InfException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(ProjectSearch.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected boolean hasThePrivlege() {
        return true;
    }

}
