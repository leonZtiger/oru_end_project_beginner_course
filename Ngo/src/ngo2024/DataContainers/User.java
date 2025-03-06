package ngo2024.DataContainers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ngo2024.Main;
import ngo2024.util.Util;
import ngo2024.util.validationClass;
import oru.inf.InfException;

/**
 * Class describing a user.
 *
 * @author helinakravi
 */
public class User extends DataContainerCommon {

    private static final String tablename = "anstalld";

    private String aid;
    private String fornamn;
    private String efternamn;
    private String telefon;
    private String anstallningsdatum;
    private String losenord;
    private String avdelning;
    private String epost;
    private String adress;

    private Avdelning avdelningObj;

    private AccessLevel behorig;

    /**
     * Creates object with empty values and a access level of GUEST.
     */
    public User() {
        aid = "";
        fornamn = "";
        efternamn = "";
        telefon = "";
        anstallningsdatum = "";
        losenord = "";
        avdelning = "";
        epost = "";
        adress = "";
        behorig = AccessLevel.GUEST;
    }

    /**
     * Creates object based on table anstalld given a key for the row.
     *
     * @param id, key for the row.
     */
    public User(String id) {
        try {
            Util.MapRefrence(this, Main.infDB.fetchRow("SELECT * FROM anstalld WHERE aid = " + id));

            avdelningObj = new Avdelning(avdelning);

        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates object based on table anstalld given the key for the row. also
     * sets avdelning to the passed param avdelningObj. Use this constructor for
     * when creating a chef object. Otherwise a infinant loop will occur.
     *
     * @param id, key for the row
     * @param avdelningObj, the Avdelning this user is working at.
     */
    public User(String id, Avdelning avdelningObj) {
        try {
            Util.MapRefrence(this, Main.infDB.fetchRow("SELECT * FROM anstalld WHERE aid = " + id));

            this.avdelningObj = avdelningObj;

        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates a User object based on the passed credentails.
     *
     * @param email
     * @param password
     * @return a user that matches these credentials in the db or null if no
     * found.
     */
    public static User getUser(String email, String password) {

        User user = new User();

        try {
            ngo2024.util.Util.MapRefrence(user, Main.infDB.fetchRow("SELECT * FROM anstalld WHERE epost = '" + email + "' AND losenord = '" + password + "'"));

            user.findAccessLevel();
            return user;

        } catch (InfException | NullPointerException ex) {
            return null;
        }
    }

    /**
     * Create a new user in the database. and prompts the generate password to
     * the user.
     *
     * @param fornamn
     * @param efternamn
     * @param telefon
     * @param anstallningsdatum
     * @param epost
     * @param adress
     * @param avdelning
     * @param isAdmin
     * @return returns the id of the newly created user.
     */
    public static int createUser(String fornamn, String efternamn, String telefon, String anstallningsdatum, String epost, String adress, String avdelning, boolean isAdmin) {

        String losenord = Util.getRandomPassword(12);

        try {
            // Varför finns det ingen auto increment eller liknande for id?!!!
            // Måste göra detta fula
            int aid = Integer.parseInt(Main.infDB.fetchColumn("SELECT aid FROM anstalld WHERE aid IS NOT NULL ORDER BY aid DESC LIMIT 1").get(0)) + 1;

            Main.infDB.insert("INSERT INTO anstalld (aid, fornamn, efternamn, telefon, anstallningsdatum, losenord, epost, adress, avdelning) "
                    + "VALUES (" + aid + ",'" + fornamn + "', '" + efternamn + "', '" + telefon + "', '" + anstallningsdatum + "', '"
                    + losenord + "', '" + epost + "', '" + adress + "', '" + avdelning + "') ");

            if (isAdmin) {
                Main.infDB.insert("INSERT INTO admin (aid,behorighetsniva) VALUES (" + aid + "," + "0)");
            } else {
                Main.infDB.insert("INSERT INTO handlaggare (aid,ansvarighetsomrade) VALUES (" + aid + ",'Inga just nu')");
            }

            // If suceesfull prompt the password
            JOptionPane.showMessageDialog(null, "Användare skappades. Lössenored för " + fornamn + " " + efternamn + " är: " + losenord);

            return aid;
        } catch (InfException ex) {
            ex.printError();
            return -1;
        }

    }

    /**
     * Dletes a user from the database-
     *
     * @param id of user to delete.
     */
    public static void remove(String id) {
        try {
            // Since the database doesnt not utilies any cascade delete or other refrence deletes
            // we need to manualy clear them.
            Main.infDB.update("UPDATE projekt SET projektchef = NULL WHERE projektchef = " + id);
            Main.infDB.update("UPDATE avdelning SET chef = NULL WHERE chef = " + id);
            Main.infDB.delete("DELETE FROM ans_proj WHERE aid = " + id);
            Main.infDB.update("UPDATE handlaggare SET mentor = NULL WHERE mentor = " + id);
            Main.infDB.delete("DELETE FROM handlaggare WHERE aid = " + id);
            Main.infDB.delete("DELETE FROM admin WHERE aid = " + id);

            Main.infDB.delete("DELETE FROM anstalld WHERE aid = " + id);
        } catch (InfException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sets this objects accesslevel to the apropiet AccessLevel enum.
     */
    public void findAccessLevel() {

        // Check if admin
        try {
            String res = Main.infDB.fetchSingle("SELECT behorighetsniva FROM admin  WHERE aid = '" + aid + "'");
            if (res != null) {
                if (res.equals("1")) {
                    behorig = AccessLevel.ADMIN1;
                    return;
                } else if (res.equals("2")) {
                    behorig = AccessLevel.ADMIN2;
                    return;
                }
            }

        } catch (InfException ex) {
        }

        // Else check if chef of department
        try {
            String res = Main.infDB.fetchSingle("SELECT chef FROM avdelning  WHERE chef = '" + aid + "'");
            if (res != null) {
                if (res.equals(aid)) {
                    behorig = AccessLevel.CHEF;
                    return;
                }
            }

        } catch (InfException ex) {
        }

        // Else check if project Chef
        try {
            String res = Main.infDB.fetchSingle("SELECT projektchef FROM projekt  WHERE projektchef = '" + aid + "'");
            if (res != null) {
                if (res.equals(aid)) {
                    behorig = AccessLevel.PROJECT_CHEF;
                    return;
                }
            }

        } catch (InfException ex) {
        }

        // Else Check if manager
        try {
            String res = Main.infDB.fetchSingle("SELECT aid FROM handlaggare  WHERE aid = '" + aid + "'");
            if (res != null) {
                if (res.equals(aid)) {
                    behorig = AccessLevel.MANAGER;
                    return;
                }
            }

        } catch (InfException ex) {

        }

        // If no accesslevel found resort to guest
        behorig = AccessLevel.GUEST;

    }

    ///////////// GETTERS AND SETERS ///////////////////////////////////////////////
    public AccessLevel getBehorig() {
        return behorig;
    }

    public String getAdress() {
        return adress;
    }

    public String getEpost() {
        return epost;
    }

    public String getAid() {
        return aid;
    }

    public String getFornamn() {
        return fornamn;
    }

    public String getEfternamn() {
        return efternamn;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getAnstallningsdatum() {
        return anstallningsdatum;
    }

    public String getLosenord() {
        return losenord;
    }

    public String getAvdelning() {
        return avdelning;
    }

    public String getAvdelningStr() {
        if (avdelning != null) {
            try {
                // Get name for department
                return Main.infDB.fetchSingle("SELECT namn FROM avdelning WHERE avdid = " + this.avdelning);
            } catch (InfException ex) {
                return "N/A";
            }
        }

        return "N/A";
    }

    public void setAid(String aid) throws InfException {
        Main.infDB.update("UPDATE " + tablename + " SET aid = '" + aid + "' WHERE aid = '" + this.aid + "'");
        this.aid = aid;
    }

    public void setFornamn(String fornamn) {
        try {
            if (!validationClass.isLetters(fornamn)) {
                throw new InfException("Fornamn kan endast ges av bokstäver!");
            }

            Main.infDB.update("UPDATE " + tablename + " SET fornamn = '" + fornamn + "' WHERE aid = '" + this.aid + "'");
            this.fornamn = fornamn;

        } catch (InfException ex) {
            JOptionPane.showMessageDialog(null, "Error updating 'fornamn': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setEfternamn(String efternamn) {
        try {
            if (!validationClass.isLetters(efternamn)) {
                throw new InfException("Efternamn kan endast ges av bokstäver!");
            }
            Main.infDB.update("UPDATE " + tablename + " SET efternamn = '" + efternamn + "' WHERE aid = '" + this.aid + "'");
            this.efternamn = efternamn;
        } catch (InfException ex) {
            JOptionPane.showMessageDialog(null, "Error updating 'efternamn': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setTelefon(String telefon) {
        try {

            Main.infDB.update("UPDATE " + tablename + " SET telefon = '" + telefon + "' WHERE aid = '" + this.aid + "'");
            this.telefon = telefon;
        } catch (InfException ex) {
            JOptionPane.showMessageDialog(null, "Error updating 'telefon': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAnstallningsdatum(String anstallningsdatum) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET anstallningsdatum = '" + anstallningsdatum + "' WHERE aid = '" + this.aid + "'");
            this.anstallningsdatum = anstallningsdatum;
        } catch (InfException ex) {
            JOptionPane.showMessageDialog(null, "Error updating 'anstallningsdatum'. Vänligen ange detta format yyyy-mm-dd: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setLosenord(String losenord) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET losenord = '" + losenord + "' WHERE aid = '" + this.aid + "'");
            this.losenord = losenord;
        } catch (InfException ex) {
            JOptionPane.showMessageDialog(null, "Error updating 'losenord': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAvdelning(String avdelning) {
        try {
            if (!validationClass.isNumbers(avdelning)) {
                throw new InfException("Avdelning kan endast ges av nummer!");
            }
            Main.infDB.update("UPDATE " + tablename + " SET avdelning = '" + avdelning + "' WHERE aid = '" + this.aid + "'");
            this.avdelning = avdelning;
        } catch (InfException ex) {
            JOptionPane.showMessageDialog(null, "Error updating 'avdelning': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setEpost(String epost) {
        try {

            Main.infDB.update("UPDATE " + tablename + " SET epost = '" + epost + "' WHERE aid = '" + this.aid + "'");
            this.epost = epost;
        } catch (InfException ex) {
            JOptionPane.showMessageDialog(null, "Error updating 'epost': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAdress(String adress) {
        try {
            Main.infDB.update("UPDATE " + tablename + " SET adress = '" + adress + "' WHERE aid = '" + this.aid + "'");
            this.adress = adress;
        } catch (InfException ex) {
            JOptionPane.showMessageDialog(null, "Error updating 'adress': " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);

            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Avdelning getAvdelningObj() {
        return avdelningObj;
    }

    public String getFullname() {
        return fornamn + " " + efternamn;
    }

    @Override
    public String getName() {
        return getFullname();
    }

    public static String getTablename() {
        return tablename;
    }

    @Override
    public String getId() {
        return aid;
    }

    public static String getIdName() {
        return "aid";
    }

    public static ArrayList<DataContainerCommon> getAll() {
        try {
            ArrayList<String> ids = Main.infDB.fetchColumn("SELECT " + getIdName() + " FROM " + tablename);

            ArrayList<DataContainerCommon> c = new ArrayList<DataContainerCommon>();
            for (String id : ids) {
                c.add(new User(id));
            }

            return c;

        } catch (InfException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList<DataContainerCommon>();
    }

}
