package com.example.project3.util;

/** Location Enum Class
 * @author Shreeyut
 * @author Andy
 */

public enum Location {
    BRIDGEWATER("Somerset", "08807"),
    EDISON("Middlesex", "08817"),
    PISCATAWAY("Middlesex", "08854"),
    PRINCETON("Mercer", "08542"),
    MORRISTOWN("Morris", "07960"),
    CLARK("Union", "07066");

    private final String county;
    private final String zip;

    /**
     * Constructor for Location Enum with Country and ZIP
     * @param county County
     * @param zip ZIP
     */
    Location(String county, String zip) {
        this.county = county;
        this.zip = zip;
    }

    /**
     * Returns the City Name
     * @return  City Name
     */
    public String getCity(){
        return this.name();
    }

    /**
     *  Returns the County Name
     * @return County Name
     */
    public String getCounty() {
        return county;
    }

    /**
     *  Returns the Zip Code
     * @return Zip Code
     */
    public String getZip() {
        return zip;
    }

    /**
     * Returns the Full location.
     * @return String.
     */
    public String getFullLocation() {
        return String.format("%s, %s %s", getCity(), getCounty(), getZip());
    }


}
