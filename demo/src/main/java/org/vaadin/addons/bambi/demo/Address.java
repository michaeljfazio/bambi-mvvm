package org.vaadin.addons.bambi.demo;

import java.io.Serializable;

/**
 * Address domain object.
 *
 * @author Michael Fazio
 */
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String NAME_PROPERTY = "name";
	public static final String SURNAME_PROPERTY = "surname";
	public static final String LINE1_PROPERTY = "line1";
	public static final String LINE2_PROPERTY = "line2";
	public static final String STATE_PROPERTY = "state";
	public static final String AREACODE_PROPERTY = "areaCode";
	public static final String CITY_PROPERTY = "city";
	public static final String COUNTRY_PROPERTY = "country";
	
    private String name;
    private String surname;
    private String line1;
    private String line2;
    private String state;
    private String areaCode;
    private String city;
    private String country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
