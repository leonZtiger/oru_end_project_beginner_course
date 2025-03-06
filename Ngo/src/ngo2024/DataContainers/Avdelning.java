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
 * Class describing the model of a department.
 *
 * @author leonb
 */
public class Avdelning extends DataContainerCommon {

    private static final String tablename = "avdelning";

    private String avdid;
    private String namn;
    private String beskrivning;
    private String adress;
    private String epost;
    private String telefon;
    private String stad;
    private String chef;

    private User chefObj;
    private Stad stadObj;
    private ArrayList<HallbarhetsMal> mal;

    /**
     * Initiates object with data from database Main.infDB connection. If
     * Main.infDB is null or a connection fault acuress no data will be fetched.
     *
     * @param id , id of the department.
     */
    public Avdelning(String id) {
        try {
            Util.MapRefrence(this, Main.infDB.fetchRow("SELECT * FROM avdelning WHERE avdid = " + id));

            chefObj = new User(chef, this);

            stadObj = new Stad(stad);

            mal = new ArrayList<HallbarhetsMal>();

            Main.infDB.fetchColumn("SELECT hid FROM avd_hallbarhet WHERE avdid = " + id).forEach(e -> {
                mal.add(new HallbarhetsMal(e));
            });

        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ///////////// GETTERS AND SETERS ///////////////////////////////////////////////
    
    public void setAvdid(String avdid) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET epost = '" + epost + "' WHERE avdid = '" + this.avdid + "'");
            this.avdid = Main.infDB.fetchSingle("SELECT avdid FROM " + tablename + " WHERE avdid = " + this.avdid);
        } catch (InfException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setNamn(String namn) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET namn = '" + namn + "' WHERE avdid = '" + this.avdid + "'");
            this.namn = Main.infDB.fetchSingle("SELECT namn FROM " + tablename + " WHERE avdid = " + this.avdid);
        } catch (InfException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setBeskrivning(String beskrivning) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET beskrivning = '" + beskrivning + "' WHERE avdid = '" + this.avdid + "'");
            this.beskrivning = Main.infDB.fetchSingle("SELECT beskrivning FROM " + tablename + " WHERE avdid = " + this.avdid);
        } catch (InfException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAdress(String adress) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET adress = '" + adress + "' WHERE avdid = '" + this.avdid + "'");
            this.adress = Main.infDB.fetchSingle("SELECT adress FROM " + tablename + " WHERE avdid = " + this.avdid);
        } catch (InfException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setEpost(String epost) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET epost = '" + epost + "' WHERE avdid = '" + this.avdid + "'");
            this.epost = Main.infDB.fetchSingle("SELECT epost FROM " + tablename + " WHERE avdid = " + this.avdid);
        } catch (InfException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setTelefon(String telefon) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET telefon = '" + telefon + "' WHERE avdid = '" + this.avdid + "'");
            this.telefon = Main.infDB.fetchSingle("SELECT telefon FROM " + tablename + " WHERE avdid = " + this.avdid);
        } catch (InfException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setStad(String stad) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET stad = '" + stad + "' WHERE avdid = '" + this.avdid + "'");
            this.stad = Main.infDB.fetchSingle("SELECT stad FROM " + tablename + " WHERE avdid = " + this.avdid);
            this.stadObj = new Stad(stad);
        } catch (InfException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setChef(String chef) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET chef = '" + chef + "' WHERE avdid = '" + this.avdid + "'");
            this.chef = Main.infDB.fetchSingle("SELECT chef FROM " + tablename + " WHERE avdid = " + this.avdid);
            this.chefObj = new User(this.chef, this);
        } catch (InfException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getAvdid() {
        return avdid;
    }

    public String getNamn() {
        return namn;
    }

    public String getBeskrivning() {
        return beskrivning;
    }

    public String getAdress() {
        return adress;
    }

    public String getEpost() {
        return epost;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getStad() {
        return stad;
    }

    public String getChef() {
        return chef;
    }

    public User getChefObj() {
        return chefObj;
    }

    public Stad getStadObj() {
        return stadObj;
    }

    @Override
    public String getName() {
        return namn;
    }

    @Override
    public String getId() {
        return avdid;
    }

    public static String getTablename() {
        return tablename;
    }

    public static String getIdName() {
        return "avdid";
    }

    public static ArrayList<DataContainerCommon> getAll() {
        try {
            ArrayList<String> ids = Main.infDB.fetchColumn("SELECT " + getIdName() + " FROM " + tablename);

            ArrayList<DataContainerCommon> c = new ArrayList<DataContainerCommon>();
            for (String id : ids) {
                c.add(new Avdelning(id));
            }

            return c;

        } catch (InfException ex) {
            Logger.getLogger(Avdelning.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList<DataContainerCommon>();
    }
}
