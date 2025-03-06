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
 * Class describing a partnership
 *
 * @author leonb
 */
public class Partner extends DataContainerCommon {

    private static final String tablename = "partner";
    private String namn;
    private String pid;
    private String kontaktperson;
    private String kontaktepost;
    private String telefon;
    private String adress;
    private String branch;
    private String stad;

    private Stad stadObj;

    /**
     * Creates object based on table partner given the key for a row
     *
     * @param id, key of the row.
     */
    public Partner(String id) {
        try {
            Util.MapRefrence(this, Main.infDB.fetchRow("SELECT * FROM partner WHERE pid = " + id));

            stadObj = new Stad(stad);

        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    ///////////// GETTERS AND SETERS ///////////////////////////////////////////////

    public Stad getStadObj() {
        return stadObj;
    }

    public String getNamn() {
        return namn;
    }

    public String getPid() {
        return pid;
    }

    public String getKontaktperson() {
        return kontaktperson;
    }

    public String getKontaktepost() {
        return kontaktepost;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getAdress() {
        return adress;
    }

    public String getBranch() {
        return branch;
    }

    public String getStad() {
        return stad;
    }

    public void setNamn(String namn) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET namn = '" + namn + "' WHERE pid = '" + this.pid + "'");
            this.namn = Main.infDB.fetchSingle("SELECT namn FROM " + tablename + " WHERE " + getIdName() + " = " + this.pid);

        } catch (InfException ex) {
            Logger.getLogger(Partner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setPid(String pid) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET pid = '" + pid + "' WHERE pid = '" + this.pid + "'");
            this.pid = Main.infDB.fetchSingle("SELECT pid FROM " + tablename + " WHERE " + getIdName() + " = " + this.pid);
        } catch (InfException ex) {
            Logger.getLogger(Partner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setKontaktperson(String kontaktperson) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET kontaktperson = '" + kontaktperson + "' WHERE pid = '" + this.pid + "'");
            this.kontaktperson = Main.infDB.fetchSingle("SELECT kontaktperson FROM " + tablename + " WHERE " + getIdName() + " = " + this.pid);
        } catch (InfException ex) {
            Logger.getLogger(Partner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setKontaktepost(String kontaktepost) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET kontaktepost = '" + kontaktepost + "' WHERE pid = '" + this.pid + "'");
            this.kontaktepost = Main.infDB.fetchSingle("SELECT kontaktepost FROM " + tablename + " WHERE " + getIdName() + " = " + this.pid);
        } catch (InfException ex) {
            Logger.getLogger(Partner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setTelefon(String telefon) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET telefon = '" + telefon + "' WHERE pid = '" + this.pid + "'");
            this.telefon = Main.infDB.fetchSingle("SELECT telefon FROM " + tablename + " WHERE " + getIdName() + " = " + this.pid);
        } catch (InfException ex) {
            Logger.getLogger(Partner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAdress(String adress) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET adress = '" + adress + "' WHERE pid = '" + this.pid + "'");
            this.adress = Main.infDB.fetchSingle("SELECT adress FROM " + tablename + " WHERE " + getIdName() + " = " + this.pid);
        } catch (InfException ex) {
            Logger.getLogger(Partner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setBranch(String branch) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET branch = '" + branch + "' WHERE pid = '" + this.pid + "'");
            this.branch = Main.infDB.fetchSingle("SELECT branch FROM " + tablename + " WHERE " + getIdName() + " = " + this.pid);
        } catch (InfException ex) {
            Logger.getLogger(Partner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setStad(String stad) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET stad = '" + stad + "' WHERE pid = '" + this.pid + "'");
            this.stad = Main.infDB.fetchSingle("SELECT stad FROM " + tablename + " WHERE " + getIdName() + " = " + this.pid);
            this.stadObj = new Stad(stad);
        } catch (InfException ex) {
            Logger.getLogger(Partner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getName() {
        return namn;
    }

    @Override
    public String getId() {
        return pid;
    }

    public static String getIdName() {
        return "pid";
    }

    public static String getTablename() {
        return tablename;
    }

    public static ArrayList<DataContainerCommon> getAll() {
        try {
            ArrayList<String> ids = Main.infDB.fetchColumn("SELECT " + getIdName() + " FROM " + tablename);

            ArrayList<DataContainerCommon> c = new ArrayList<DataContainerCommon>();
            for (String id : ids) {
                c.add(new Partner(id));
            }

            return c;

        } catch (InfException ex) {
            Logger.getLogger(Partner.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList<DataContainerCommon>();
    }
}
