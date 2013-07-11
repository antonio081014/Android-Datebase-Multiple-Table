/**
 * 
 */
package com.antonio081014.multidatabasetable;

/**
 * @author "Dev Perfecular Inc."
 * @time: Sep 28, 2012, 3:35:51 PM
 */
public class ProfilePhoto {

    private int id;
    private int belongTo;
    private String name;

    public ProfilePhoto(int id, int belongTo, String name) {
	this.id = id;
	this.belongTo = belongTo;
	this.name = name;
    }

    public ProfilePhoto(int belongTo, String name) {
	this.belongTo = belongTo;
	this.name = name;
    }

    /**
     * @return the _id
     */
    public int get_id() {
	return id;
    }

    /**
     * @param _id
     *            the _id to set
     */
    public void set_id(int _id) {
	this.id = _id;
    }

    /**
     * @return the _belongTo
     */
    public int get_belongTo() {
	return belongTo;
    }

    /**
     * @param _belongTo
     *            the _belongTo to set
     */
    public void set_belongTo(int _belongTo) {
	this.belongTo = _belongTo;
    }

    /**
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    @Override
    public String toString() {
	return this.getName() + " belong to: "
		+ Integer.toString(this.get_belongTo()) + "\n";
    }
}
