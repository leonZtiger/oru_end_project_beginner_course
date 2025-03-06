/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024.DataContainers;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ngo2024.Main;
import ngo2024.util.Util;
import ngo2024.util.validationClass;
import oru.inf.InfException;

/**
 * Class describing a country
 *
 * @author leonb
 */
public class Land extends DataContainerCommon {

    private static final String tablename = "land";
    private String lid;
    private String namn;
    private String sprak;
    private String valuta;
    private String tidszon;
    private String politisk_struktur;
    private String ekonomi;

    /**
     * Creates object based on table land given the key for a row.
     *
     * @param id, key for the row
     */
    public Land(String id) {
        try {
            Util.MapRefrence(this, Main.infDB.fetchRow("SELECT * FROM land WHERE lid = " + id));
        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ///////////// GETTERS AND SETERS ///////////////////////////////////////////////

    public String getLid() {
        return lid;
    }

    public String getNamn() {
        return namn;
    }

    public String getSprak() {
        return sprak;
    }

    public String getValuta() {
        return valuta;
    }

    public String getTidszon() {
        return tidszon;
    }

    public String getPolitisk_struktur() {
        return politisk_struktur;
    }

    public String getEkonomi() {
        return ekonomi;
    }

    public void setLid(String lid) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET lid = '" + lid + "' WHERE lid = '" + this.lid + "'");
            this.lid = lid;
        } catch (InfException ex) {
            Logger.getLogger(Land.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error updating 'lid': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void setNamn(String namn) {
        try {
            if (!validationClass.isLetters(namn)) {
                throw new InfException("Namn kan endast ges av bokstäver!");
            }
            Main.infDB.update("UPDATE " + tablename + " SET namn = '" + namn + "' WHERE lid = '" + this.lid + "'");
            this.namn = Main.infDB.fetchSingle("SELECT namn FROM " + tablename + " WHERE lid = " + this.lid);
        } catch (InfException ex) {
            Logger.getLogger(Land.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error updating 'namn': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void setSprak(String sprak) {
        try {
            if (!validationClass.isLetters(sprak)) {
                throw new InfException("Språk kan endast ges av bokstäver!");
            }
            Main.infDB.update("UPDATE " + tablename + " SET sprak = '" + sprak + "' WHERE lid = '" + this.lid + "'");
            this.sprak = Main.infDB.fetchSingle("SELECT sprak FROM " + tablename + " WHERE lid = " + this.lid);
        } catch (InfException ex) {
            Logger.getLogger(Land.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error updating 'sprak': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    public void setValuta(String valuta) {
        try {
            if (!validationClass.isNumbers(valuta)) {
                throw new InfException("Valuta kan endast vara ett nummer!");
            }

            Main.infDB.update("UPDATE " + tablename + " SET valuta = '" + valuta + "' WHERE lid = '" + this.lid + "'");
            this.valuta = Main.infDB.fetchSingle("SELECT valuta FROM " + tablename + " WHERE lid = " + this.lid);
        } catch (InfException ex) {
            Logger.getLogger(Land.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error updating 'valuta': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void setTidszon(String tidszon) {
        try {
            if (!validationClass.isLetters(tidszon)) {
                throw new InfException("Tidszon kan endast vara ett nummer!");
            }
            Main.infDB.update("UPDATE " + tablename + " SET tidszon = '" + tidszon + "' WHERE lid = '" + this.lid + "'");
            this.tidszon = Main.infDB.fetchSingle("SELECT tidszon FROM " + tablename + " WHERE lid = " + this.lid);
        } catch (InfException ex) {
            Logger.getLogger(Land.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error updating 'tidszon': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void setPolitiskStruktur(String politisk_struktur) {
        try {

            Main.infDB.update("UPDATE " + tablename + " SET politisk_struktur = '" + politisk_struktur + "' WHERE lid = '" + this.lid + "'");
            this.politisk_struktur = Main.infDB.fetchSingle("SELECT politisk_struktur FROM " + tablename + " WHERE lid = " + this.lid);
        } catch (InfException ex) {
            Logger.getLogger(Land.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error updating 'politisk_struktur': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setEkonomi(String ekonomi) {
        try {
            if (!validationClass.isNumbers(ekonomi)) {
                throw new InfException("ekonomi kan endast vara ett nummer!");
            }
            Main.infDB.update("UPDATE " + tablename + " SET ekonomi = '" + ekonomi + "' WHERE lid = '" + this.lid + "'");
            this.ekonomi = Main.infDB.fetchSingle("SELECT ekonomi FROM " + tablename + " WHERE lid = " + this.lid);
        } catch (InfException ex) {
            Logger.getLogger(Land.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error updating 'ekonomi': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    @Override
    public String getName() {
        return namn;
    }

    public static String getTablename() {
        return tablename;
    }

    @Override
    public String getId() {
        return lid;
    }

    public static String getIdName() {
        return "lid";
    }

    public static ArrayList<DataContainerCommon> getAll() {

        try {
            ArrayList<String> ids = Main.infDB.fetchColumn("SELECT " + getIdName() + " FROM " + tablename);

            ArrayList<DataContainerCommon> c = new ArrayList<DataContainerCommon>();
            for (String id : ids) {
                c.add(new Land(id));
            }

            return c;

        } catch (InfException ex) {
            Logger.getLogger(Land.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList<DataContainerCommon>();
    }

}
