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
import ngo2024.Main;
import ngo2024.pages.SpecificsPage.SpecificDepartment;
import ngo2024.util.Util;
import oru.inf.InfException;

/**
 * A tabbed page with search functionality. Can search on all the departments in
 * the database.
 *
 * @author leonb
 */
public class DepartmentSearch extends SearchBase {

    /**
     * Initiates the page.
     *
     * @param parent, JTabbedPane object to add this to.
     */
    public DepartmentSearch(JTabbedPane parent) {
        super(parent, "Avdelnings utforskare");
        
        // Configure filters
        addFilter("Mina avdelning", "avdid", Main.user.getAvdelning());
        
    }

    @Override
    protected void onSearch(String searchTerm) {

        // Get search results
        try {
            Util.createSearchResults(resultPanel,null, "avdelning", new String[]{"namn"}, getSelectedFilters(), getSelectedSort(), searchTerm, "namn", "avdid", SpecificDepartment.class);
        } catch (InfException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(ProjectSearch.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

 
    @Override
    protected boolean hasThePrivlege() {
        return true;
    }
}
