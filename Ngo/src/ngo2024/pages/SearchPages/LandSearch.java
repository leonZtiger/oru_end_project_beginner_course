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
import ngo2024.pages.SpecificsPage.SpecificLand;
import ngo2024.util.Util;
import oru.inf.InfException;

/**
 * A tabbed page with search functionality. Can search on all the Countries in
 * the database.
 *
 * @author leonb
 */
public class LandSearch extends SearchBase {

    /**
     * Initiates the tabbed page.
     *
     * @param parent, the JTabbedPabe to add this to.
     */
    public LandSearch(JTabbedPane parent) {
        super(parent, "Land utforskare");
        
    }

    @Override
    protected void onSearch(String searchTerm) {
       
        // Get search results
        try {
            Util.createSearchResults(resultPanel,null, "land", new String[]{"namn"}, getSelectedFilters(),getSelectedSort(), searchTerm, "namn", "lid", SpecificLand.class);
        } catch (InfException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(ProjectSearch.class.getName()).log(Level.SEVERE, null, ex);
        }

      
    }

     @Override
    protected boolean hasThePrivlege() {
        return Main.user.getBehorig() != AccessLevel.GUEST;
    }

}
