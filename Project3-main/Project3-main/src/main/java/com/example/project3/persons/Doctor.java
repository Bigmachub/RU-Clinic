package com.example.project3.persons;

import com.example.project3.clinic.Profile;
import com.example.project3.clinic.Specialty;
import com.example.project3.util.Location;

/**
 * Doctor Class which is a subclass of Provider
 * @author Shreeyut
 * @author Andy
 */
public class Doctor extends Provider {
    private Specialty specialty; // Encapsulates the rate per visit based on specialty
    private String npi; // National Provider Identification unique to the doctor

    /**
     * Constructor for a new Doctor.
     *
     * @param profile The profile associated with the doctor.
     * @param location The location where the doctor practices.
     * @param specialty The specialty of the doctor.
     * @param npi The National Provider Identification number.
     */
    public Doctor(Profile profile, Location location, Specialty specialty, String npi) {
        super(profile, location);
        this.specialty = specialty;
        this.npi = npi;
    }

    /**
     * Getter for the doctor's specialty.
     *
     * @return The specialty of the doctor.
     */
    public Specialty getSpecialty() {
        return specialty;
    }

    /**
     * Getter for the doctor's NPI (National Provider Identification).
     *
     * @return The NPI of the doctor.
     */
    public String getNpi() {
        return npi;
    }

    /**
     * Implements the rate() method to return the charging rate per visit based on the doctor's specialty.
     *07:211:130
     * @return The rate per visit.
     */
    @Override
    public int rate() {
        return specialty.getCharge();
    }

    /**
     * String representation of the doctor, including the profile, location, specialty, and NPI.
     *
     * @return A string representing the doctor.
     */
    @Override
    public String toString() {
        return String.format("[%s, %s][%s, #%s]",
                profile.toString(),
                getLocation().getFullLocation(),
                specialty.toString(),
                npi);
    }

    /**
     * Checks equality based on the profile, location, specialty, and NPI of the doctor.
     *
     * @param obj The object to compare with.
     * @return True if the doctors are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Doctor doctor = (Doctor) obj;
        return profile.equals(doctor.profile) &&
                getLocation().equals(doctor.getLocation()) &&
                specialty.equals(doctor.specialty) &&
                npi.equals(doctor.npi);
    }
}