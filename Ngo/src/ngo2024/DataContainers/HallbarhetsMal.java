/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024.DataContainers;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static ngo2024.DataContainers.User.getIdName;
import ngo2024.Main;
import ngo2024.util.Util;
import oru.inf.InfException;

/**
 * Class describing a enviroment goal.
 *
 * @author leonb
 */
public class HallbarhetsMal extends DataContainerCommon {

    private static final String tablename = "hallbarhetsmal";
    private String hid;
    private String namn;
    private String malnummer;
    private String beskrivning;
    private String prioritet;

    /**
     * Creates object based on data from hallbarhetsmal table given the id of
     * the row.
     *
     * @param id, id of the row to be based on.
     */
    public HallbarhetsMal(String id) {
        try {
            Util.MapRefrence(this, Main.infDB.fetchRow("SELECT * FROM hallbarhetsmal WHERE hid = " + id));
        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    ///////////// GETTERS AND SETERS ///////////////////////////////////////////////

    public String getHid() {
        return hid;
    }

    public String getNamn() {
        return namn;
    }

    public String getMalnummer() {
        return malnummer;
    }

    public String getBeskrivning() {
        return beskrivning;
    }

    public String getPrioritet() {
        return prioritet;
    }

    public void setHid(String hid) throws InfException {
        Main.infDB.update("UPDATE " + tablename + " SET hid = '" + hid + "' WHERE id = '" + this.hid + "'");
        this.hid = hid;
    }

    public void setNamn(String namn) throws InfException {
        Main.infDB.update("UPDATE " + tablename + " SET namn = '" + namn + "' WHERE id = '" + this.hid + "'");
        this.namn = namn;
    }

    public void setMalnummer(String malnummer) throws InfException {
        Main.infDB.update("UPDATE " + tablename + " SET malnummer = '" + malnummer + "' WHERE id = '" + this.hid + "'");
        this.malnummer = malnummer;
    }

    public void setBeskrivning(String beskrivning) throws InfException {
        Main.infDB.update("UPDATE " + tablename + " SET beskrivning = '" + beskrivning + "' WHERE id = '" + this.hid + "'");
        this.beskrivning = beskrivning;
    }

    public void setPrioritet(String prioritet) throws InfException {
        Main.infDB.update("UPDATE " + tablename + " SET prioritet = '" + prioritet + "' WHERE id = '" + this.hid + "'");
        this.prioritet = prioritet;
    }

    @Override
    public String getName() {
        return namn;
    }

    @Override
    public String getId() {
        return hid;
    }

    public static String getIdName() {
        return "hid";
    }

    public static String getTablename() {
        return tablename;
    }

    public static ArrayList<DataContainerCommon> getAll() {
        try {
            ArrayList<String> ids = Main.infDB.fetchColumn("SELECT " + getIdName() + " FROM " + tablename);

            ArrayList<DataContainerCommon> c = new ArrayList<DataContainerCommon>();
            for (String id : ids) {
                c.add(new HallbarhetsMal(id));
            }

            return c;

        } catch (InfException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList<DataContainerCommon>();
    }
}
