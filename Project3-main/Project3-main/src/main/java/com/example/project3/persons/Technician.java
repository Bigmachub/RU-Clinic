package com.example.project3.persons;

import com.example.project3.clinic.Profile;
import com.example.project3.util.Location;

/**
 * Technician subclass of Provider.
 * @auhtor Shreeyut
 * @author Andy
 */
public class Technician extends Provider {
    private int ratePerVisit;

    /**
     * Constructor for Technician.
     * @param profile Profile of technician.
     * @param location Location of technician.
     * @param ratePerVisit RatePerVisit of Technician.
     */
    public Technician(Profile profile, Location location, int ratePerVisit) {
        super(profile, location); // Call the Provider constructor
        this.ratePerVisit = ratePerVisit;
    }

    /**
     * Method to get the rate per visit.
     * @return int
     */
    @Override
    public int rate() {
        return ratePerVisit; // Return the technician's rate per visit
    }


    /**
     * Returns a string representation
     * @return String.
     */
    @Override
    public String toString() {
        return String.format("[%s, %s][rate: $%d.00]",
                profile.toString(),
                getLocation().getFullLocation(),
                ratePerVisit);
    }

    /**
     * Getter method that gives you the profile of the provider.
     * @return Profile
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Getter Method that returns location of technician.
     * @return Location
     */
    public Location getLocation() {
        return super.getLocation();
    }
}
