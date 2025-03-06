/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024.DataContainers;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ngo2024.Main;
import ngo2024.util.Util;
import oru.inf.InfException;

/**
 * Class describing a city.
 *
 * @author leonb
 */
public class Stad extends DataContainerCommon {

    private static final String tablename = "stad";

    private String sid;
    private String namn;
    private String land;
    private Land landObj;

    /**
     * Creates object based on stad table given the key for a row
     *
     * @param id, key for the row.
     */
    public Stad(String id) {
        try {
            Util.MapRefrence(this, Main.infDB.fetchRow("SELECT * FROM stad WHERE sid = " + id));

            landObj = new Land(land);

        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ///////////// GETTERS AND SETERS ///////////////////////////////////////////////
    public String getSid() {
        return sid;
    }

    public String getNamn() {
        return namn;
    }

    public String getLand() {
        return land;
    }

    public Land getLandObj() {
        return landObj;
    }

    @Override
    public String getName() {
        return namn;
    }

    @Override
    public String getId() {
        return sid;
    }

    public static String getTablename() {
        return tablename;
    }

    public static String getIdName() {
        return "sid";
    }

    public static ArrayList<DataContainerCommon> getAll() {
        try {
            ArrayList<String> ids = Main.infDB.fetchColumn("SELECT " + getIdName() + " FROM " + tablename);

            ArrayList<DataContainerCommon> c = new ArrayList<DataContainerCommon>();
            for (String id : ids) {
                c.add(new Stad(id));
            }

            return c;

        } catch (InfException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList<DataContainerCommon>();
    }

}
