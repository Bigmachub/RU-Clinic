package com.example.project3.persons;

import com.example.project3.clinic.Profile;
import com.example.project3.clinic.Specialty;
import com.example.project3.util.Location;


/** inheritance.Provider Class
 * Abstract class that represents a service provider in the clinic.
 * Extends the inheritance.Person class.
 * @author Shreeyut
 * @author Andy
 */
public abstract class Provider extends Person {
    private Location location;    // Practice location of the provider

    /**
     * Constructor to initialize the provider with a profile, location, and specialty.
     * @param profile The profile of the provider.
     * @param location The location of the provider's practice.
     */
    public Provider(Profile profile, Location location) {
        super(profile); // Call the constructor of the Person class
        this.location = location;
    }

    /**
     * Getter method to retrieve the provider's location.
     * @return location The location of the provider.
     */
    public Location getLocation() {
        return location;
    }
    /**
     * Abstract method that must be implemented by subclasses to return the provider’s charging rate per visit.
     * @return int The rate charged by the provider per visit.
     */
    public abstract int rate();

    /**
     * Get the provider's name, which can be used for identification.
     * @return String The name of the provider.
     */
    public String getName() {
        return profile.getFname() + " " + profile.getLname(); // Assuming Profile has first and last names
    }

    /**
     * toString method to provide a string representation of the provider.
     * @return String representation of the provider, including profile, location, and specialty details.
     */
    @Override
    public String toString() {
        return String.format("%s, %s, %s", getName(), location.getCity());
    }

    /**
     * equals method to check if two providers are equal based on their profile, location, and specialty.
     * @param obj The object to compare with.
     * @return true if the providers' profiles, locations, and specialties are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Provider)) return false;
        if (!super.equals(obj)) return false;

        Provider other = (Provider) obj;
        return this.location.equals(other.location);
    }
}