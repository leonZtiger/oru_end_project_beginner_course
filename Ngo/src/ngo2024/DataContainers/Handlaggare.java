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
 * Class describing a manager.
 *
 * @author leonb
 */
public class Handlaggare extends User {

    private static final String tablename = "handlaggare";

    private String mentor;
    private String aid;
    private String ansvarighetsomrade;

    private ArrayList<Handlaggare> mentorskap;
    private Handlaggare mentorObj;

    /**
     * Creates object based on the table handlaggare given the row key
     *
     * @param id, key of row
     */
    public Handlaggare(String id) {
        super(id);
        try {
            Util.MapRefrence(this, Main.infDB.fetchRow("SELECT * FROM handlaggare WHERE aid = " + id));

            /*   if (mentor != null && !mentor.isEmpty()) {
                mentorObj = new Handlaggare(mentor);
            }*/
            mentorskap = new ArrayList<Handlaggare>();
            Main.infDB.fetchColumn("SELECT aid FROM handlaggare WHERE mentor = " + id).forEach(e -> {
                mentorskap.add(new Handlaggare(e));
            });

        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates a manager based and setting the department to the passed param.
     * useful for when creating a chef object, since it can create a infinant
     * loop if the standard constructur is called when creating a department
     * object.
     *
     * @param id of thid manager.
     * @param avdelning the department this manager works on.
     */
    public Handlaggare(String id, Avdelning avdelning) {
        super(id, avdelning);
        try {
            Util.MapRefrence(this, Main.infDB.fetchRow("SELECT * FROM handlaggare WHERE aid = " + id));

            mentorskap = new ArrayList<Handlaggare>();
            Main.infDB.fetchColumn("SELECT aid FROM handlaggare WHERE mentor = " + id).forEach(e -> {
                mentorskap.add(new Handlaggare(e));
            });

        } catch (InfException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    ///////////// GETTERS AND SETERS ///////////////////////////////////////////////

    public String getMentor() {
        return mentor;
    }

    @Override
    public String getAid() {
        return aid;
    }

    public String getAnsvarighetsomrade() {
        return ansvarighetsomrade;
    }

    public ArrayList<Handlaggare> getMentorskap() {
        return mentorskap;
    }

    public Handlaggare getMentorObj() {
        return mentorObj;
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
            Logger.getLogger(Handlaggare.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList<DataContainerCommon>();
    }
}
