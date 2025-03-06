package ngo2024.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JPanel;
import ngo2024.Main;
import ngo2024.guiComponents.ResultItem;
import oru.inf.InfException;

/**
 * General Util class
 *
 * @author leonb
 */
public class Util {

    /**
     * Auto populates the passed JPanel to the search result with ResultItem:s.
     *
     * @param panel the container for the results.
     * @param selections the sql attributes to be selected, can be null for all.
     * @param table the table to perform this search on.
     * @param attributes the attributes to compare to.
     * @param filters sql strings with sql comparisons, note this is added to a
     * 'WHERE' clause and the data must be {"attribute","value"}.
     * @param sortBy string containing the atribute to sort be followed by a sql
     * "desc" or "asc". if null no sorting is perfromed.
     * @param searchTerm string representing the search query, this string is
     * compared to all the passed atributes in the search.
     * @param nameOfAttrForTitle the sql name for the attribute to use for the
     * title of the ResultItem
     * @param nameOfAttrForId the sql name for the attribue to use for the id
     * when a ResultItem is clicked, usually the primary key.
     * @param specificPageClass the class to be constructed when the ResultItem
     * is clicked.
     * @throws InfException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static void createSearchResults(JPanel panel, String selections, String table, String attributes[], ArrayList<String[]> filters, String sortBy, String searchTerm, String nameOfAttrForTitle, String nameOfAttrForId, Class specificPageClass) throws InfException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        // Start building the base SQL query
        StringBuilder query = new StringBuilder("SELECT ");
        query.append(selections == null ? "*" : selections).append(" FROM ").append(table).append(" WHERE (");

        // Loop through the attributes array to build the WHERE clause
        for (int i = 0; i < attributes.length; i++) {
            query.append(attributes[i]).append(" LIKE CONCAT('%','").append(searchTerm).append("', '%')");
            if (i < attributes.length - 1) {
                query.append(" OR ");
            }
        }
        // And of or group
        query.append(") ");

        // Build the filter query
        for (String[] f : filters) {
            if (f.length != 2) {
                continue;
            }
            query.append(" AND ").append(f[0]);
            // Check if filter is by a multiple or a single value
            if (f[1].startsWith("(")) {
                //Filter with multiple values
                query.append(" IN ").append(f[1]);
            } // Chefk so theres no between statment
            else if (!f[0].contains("BETWEEN")) {
                // Filter with single value
                query.append(" = ").append(f[1]);
            }
        }

        // Add sort by if specified
        if (sortBy != null) {
            query.append(" ORDER  BY ").append(sortBy);
        }

        query.append(";");

        System.out.println(query.toString());

        ArrayList<HashMap<String, String>> res = Main.infDB.fetchRows(query.toString());

        for (HashMap<String, String> i : res) {

            panel.add((JComponent) new ResultItem(i.get(nameOfAttrForTitle), i.get(nameOfAttrForId), specificPageClass));
        }

    }

    /**
     * Maps passed objects fields to the hashmaps values. If keys dont match
     * fields in the class they will be ignored.
     *
     * @param ref object get its fields initiated.
     * @param response hashmap of key,value pairs that will map their value to
     * the object.
     */
    public static void MapRefrence(Object ref, HashMap<String, String> response) {
        // Maps columns with the matching field by name
        for (Field f : ref.getClass().getDeclaredFields()) {

            try {
                f.setAccessible(true);

                // If the field name doesnt exist in the class, continue;
                if (response.get(f.getName()) == null) {
                    continue;
                }

                f.set(ref, response.get(f.getName()));
                f.setAccessible(false);

            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * returns a string with sudo-random charactes.
     * @param length the number of characters
     * @return a string with sudo-random charactes.
     */
    public static String getRandomPassword(int length) {

        char[] str = new char[length];
        Random rand = new Random();

        for (int i = 0; i < str.length; i++) {
            // Get random number thats casted to a charater, clamped betweeen '@'->'z'
            str[i] = (char) Math.min(Math.max(rand.nextInt(64, 122), 64), 122);
        }

        return str.toString();
    }
}
