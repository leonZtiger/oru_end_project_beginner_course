/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024.pages.SearchPages;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTabbedPane;
import ngo2024.Main;
import ngo2024.pages.SpecificsPage.SpecificEmployee;
import ngo2024.pages.SpecificsPage.SpecificEnvGoal;
import ngo2024.util.Util;
import oru.inf.InfException;

/**
 *
 * @author leonb
 */
public class SearchEnvGoal extends SearchBase {

    public SearchEnvGoal(JTabbedPane parent) {
        super(parent, "Hållbarhets mål utforskare");

        addSort("Prioritet fallande", "prioritet DESC");
        addSort("Prioritet stigande", "prioritet ASC");

    }

    @Override
    protected void onSearch(String searchTerm) {
        // Get search results
        try {
            Util.createSearchResults(resultPanel, null, "hallbarhetsmal", new String[]{"namn","malnummer"}, getSelectedFilters(), getSelectedSort(), searchTerm, "namn", "hid", SpecificEnvGoal.class);
        } catch (InfException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(ProjectSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected boolean hasThePrivlege() {
        return true;
    }

}
