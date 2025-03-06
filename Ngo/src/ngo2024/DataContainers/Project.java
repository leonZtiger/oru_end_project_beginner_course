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
import oru.inf.InfException;

/**
 * Class describing a project.
 *
 * @author leonb
 */
public class Project {

    private static final String tablename = "projekt";
    private String projektnamn;
    private String pid;
    private String beskrivning;
    private String startdatum;
    private String slutdatum;
    private String kostnad;
    private String status;
    private String prioritet;
    private String projektchef;
    private String land;

    private ArrayList<Partner> partners;
    private ArrayList<HallbarhetsMal> hallbarhetsmal;
    private Land landObj;
    private User projektchefObj;

    /**
     * Creates object based on table projekt given the key for a row.
     *
     * @param id, key for the tow.
     */
    public Project(String id) {
        try {
            // Get all project info
            Util.MapRefrence(this, Main.infDB.fetchRow("SELECT * FROM projekt WHERE pid = " + id));

            hallbarhetsmal = new ArrayList<HallbarhetsMal>();
            Main.infDB.fetchColumn("SELECT hid FROM proj_hallbarhet WHERE pid = " + pid).forEach(e -> {
                hallbarhetsmal.add(new HallbarhetsMal(e));
            });

            partners = new ArrayList<Partner>();
            Main.infDB.fetchColumn("SELECT partner_pid FROM projekt_partner WHERE pid = " + pid).forEach(e -> {
                partners.add(new Partner(e));
            });

            projektchefObj = new User(projektchef);
            landObj = new Land(land);
            // Get info about project from other tables
        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ///////////// GETTERS AND SETERS ///////////////////////////////////////////////
    public ArrayList<User> getContributers() {
        ArrayList<User> contributers = new ArrayList<User>();

        try {
            Main.infDB.fetchColumn("SELECT aid FROM ans_proj WHERE pid = " + pid).forEach(e -> {
                System.out.println(e);

                if (e != null && !e.isEmpty()) {
                    contributers.add(new User(e));
                }
            });
        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }

        return contributers;
    }

    public ArrayList<String> getAvdelningIds() {
        ArrayList<String> avdids = new ArrayList<String>();
        try {
            avdids = Main.infDB.fetchColumn("SELECT avdelning FROM anstalld WHERE aid IN (SELECT aid FROM ans_proj WHERE pid =" + pid + ") ");
        } catch (InfException ex) {
        }

        return avdids;
    }

    public ArrayList<HallbarhetsMal> getHallbarhetsmal() {
        return hallbarhetsmal;
    }

    public Land getLandObj() {
        return landObj;
    }

    public String getProjektnamn() {
        return projektnamn;
    }

    public String getPid() {
        return pid;
    }

    public String getBeskrivning() {
        return beskrivning;
    }

    public String getStartDatum() {
        return startdatum;
    }

    public String getSlutDatum() {
        return slutdatum;
    }

    public String getKostnad() {
        return kostnad;
    }

    public String getStatus() {
        return status;
    }

    public String getPrioritet() {
        return prioritet;
    }

    public String getProjektchef() {
        return projektchef;
    }

    public String getLand() {
        return land;
    }

    public User getProjektchefObj() {
        return projektchefObj;
    }

    public void setProjektnamn(String projektnamn) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET projektnamn = '" + projektnamn + "' WHERE pid = " + this.pid);
            this.projektnamn = Main.infDB.fetchSingle("SELECT projektnamn FROM " + tablename + " WHERE pid = " + pid);
        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error updating 'projektnamn': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void setPid(String pid) throws InfException {
        Main.infDB.update("UPDATE " + tablename + " SET pid = '" + pid + "' WHERE id = '" + this.pid + "'");
        this.pid = pid;
    }

    public void setBeskrivning(String beskrivning) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET beskrivning = '" + beskrivning + "' WHERE pid = " + this.pid);
            this.beskrivning = Main.infDB.fetchSingle("SELECT beskrivning FROM " + tablename + " WHERE pid = " + pid);;
        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error updating 'beskrivning': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void addHallbarhetesMal(String hid) {
        try {
            Main.infDB.insert("INSERT INTO  proj_hallbarhet VALUES (" + pid + "," + hid + ")");
            hallbarhetsmal.clear();
            Main.infDB.fetchColumn("SELECT hid FROM proj_hallbarhet WHERE pid = " + pid).forEach(e -> {
                hallbarhetsmal.add(new HallbarhetsMal(e));
            });
        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error adding 'hid': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void removeHallbarhetesMal(String hid) {
        try {
            Main.infDB.delete("DELETE FROM proj_hallbarhet WHERE pid = " + pid + " AND hid = " + hid);
            hallbarhetsmal.clear();
            Main.infDB.fetchColumn("SELECT hid FROM proj_hallbarhet WHERE pid = " + pid).forEach(e -> {
                hallbarhetsmal.add(new HallbarhetsMal(e));
            });
        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error removing 'hid': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void addPartner(String pid) {
        try {
            Main.infDB.insert("INSERT INTO  projekt_partner VALUES (" + this.pid + "," + pid + ")");
            partners.clear();
            Main.infDB.fetchColumn("SELECT partner_pid FROM projekt_partner WHERE pid = " + this.pid).forEach(e -> {
                partners.add(new Partner(e));
            });
        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error adding 'partner': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void removePartner(String pid) {
        try {
            Main.infDB.delete("DELETE FROM projekt_partner WHERE pid = " + this.pid + " AND partner_pid = " + pid);
            partners.clear();
            Main.infDB.fetchColumn("SELECT partner_pid FROM projekt_partner WHERE pid = " + this.pid).forEach(e -> {
                partners.add(new Partner(e));
            });
        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error removing 'partner': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void addContributer(String aid) {
        try {
            Main.infDB.insert("INSERT INTO  ans_proj VALUES (" + this.pid + "," + aid + ")");

        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error adding 'deltagare': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void removeContributer(String aid) {
        try {
            Main.infDB.delete("DELETE FROM ans_proj WHERE pid = " + this.pid + " AND aid = " + aid);

        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error removing 'deltagare': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void setStartDatum(String startDatum) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET startdatum = '" + startDatum + "' WHERE pid = " + this.pid);
            this.startdatum = Main.infDB.fetchSingle("SELECT startdatum FROM " + tablename + " WHERE pid = " + pid);;
        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error updating 'startDatum': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void setSlutDatum(String slutDatum) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET slutdatum = '" + slutDatum + "' WHERE pid = " + this.pid);
            this.slutdatum = Main.infDB.fetchSingle("SELECT slutdatum FROM " + tablename + " WHERE pid = " + pid);;
        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error updating 'slutdatum': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void setKostnad(String kostnad) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET kostnad = '" + kostnad + "' WHERE pid = " + this.pid);
            this.kostnad = Main.infDB.fetchSingle("SELECT kostnad FROM " + tablename + " WHERE pid = " + pid);
        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error updating 'kostnad': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void setStatus(String status) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET status = '" + status + "' WHERE pid = " + this.pid);
            this.status = Main.infDB.fetchSingle("SELECT status FROM " + tablename + " WHERE pid = " + pid);

        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error updating 'status': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    public void setPrioritet(String prioritet) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET prioritet = '" + prioritet + "' WHERE pid = " + this.pid);
            this.prioritet = Main.infDB.fetchSingle("SELECT prioritet FROM " + tablename + " WHERE pid = " + pid);
        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error updating 'prioritet': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    public void setProjektchef(String projektchef) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET projektchef = " + projektchef + " WHERE pid = " + this.pid);

            this.projektchef = Main.infDB.fetchSingle("SELECT projektchef FROM " + tablename + " WHERE pid = " + pid);
            this.projektchefObj = new User(this.projektchef);

        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error updating 'projektchef': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public void setLand(String land) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET land = " + land + " WHERE pid = " + this.pid);

            this.land = Main.infDB.fetchSingle("SELECT land FROM " + tablename + " WHERE pid = " + pid);
            this.landObj = new Land(this.land);

        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error updating 'land': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    public ArrayList<Partner> getPartners() {
        return partners;
    }

    public ArrayList<HallbarhetsMal> getHallbarhetsMal() {
        return hallbarhetsmal;
    }

}
