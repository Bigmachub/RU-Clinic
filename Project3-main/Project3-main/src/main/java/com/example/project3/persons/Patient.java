package com.example.project3.persons;

import com.example.project3.clinic.Profile;
import com.example.project3.clinic.Visit;

/** inheritance.Patient Class
 * Represents a Patient, which extends the inheritance.Person class.
 * @author Shreeyut
 * @author Andy
 */
public class Patient extends Person {
    private Visit visit; // Instance variable to keep track of a linked list of visits

    /**
     * Constructor to initialize a Patient with a Profile and an initial Visit.
     * @param profile The profile of the patient.
     * @param visit The initial visit associated with the patient.
     */
    public Patient(Profile profile, Visit visit) {
        super(profile); // Call the constructor of the Person class
        this.visit = visit;
    }

    /**
     * Copy Constructor.
     * @param profile Passed in profile.
     */
    public Patient(Profile profile){
        super(profile);
    }

    /**
     * Getter method to obtain the visit associated with the patient.
     * @return visit The visit of the patient.
     */
    public Visit getVisit() {
        return visit;
    }

    /**
     * Setter method to update the patient's visit.
     * @param visit The new visit to set.
     */
    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    /**
     * toString method to provide a string representation of the Patient.
     * @return String representation of the patient, including profile and visit details.
     */
    @Override
    public String toString() {
        return String.format("%s", super.toString());
    }

    /**
     * equals method to check if two patients are equal based on their profile and visits.
     * @param obj The object to compare with.
     * @return true if the patients' profiles and visits are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Patient)) return false;
        if (!super.equals(obj)) return false;

        Patient other = (Patient) obj;
        return this.visit.equals(other.visit);
    }
}