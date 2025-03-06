/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ngo2024.DataContainers;

import java.util.ArrayList;

/**
 *
 * @author leonb
 */
public abstract class DataContainerCommon {

    /**
     *
     * @return all the tables rows parsed to objects
     */
    public static ArrayList<DataContainerCommon> getAll() {
        return new ArrayList<DataContainerCommon>();
    }

    /**
     * Abstract method used for getting the wanted name based on the
     * implementation.
     *
     * @return string that represents the name based on the implementation.
     */
    public abstract String getName();

    /**
     * Static method to Override to set the implementations tablename.
     *
     * @return the string representing the sql table name of the implementation.
     */
    public static String getTablename() {
        return "";
    }

    /**
     * Abstract method used for getting the implementations objects row id.
     *
     * @return the id of the object in the database.
     */
    public abstract String getId();

    /**
     * Static mathod used for getting the implementations sql key attribute
     * name.
     *
     * @return
     */
    public static String getIdName() {
        return "";
    }
;

}
