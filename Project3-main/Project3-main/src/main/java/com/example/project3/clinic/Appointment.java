package com.example.project3.clinic;

import com.example.project3.persons.Person;
import com.example.project3.persons.Doctor;
import com.example.project3.persons.Technician;
import com.example.project3.util.Date;

/** Appointment Class
 * @author Shreeyut
 * @author Andy
 */
public class Appointment implements Comparable<Appointment> {
    protected Date date;
    protected Timeslot timeslot;
    protected Person patient;
    protected Person provider;

    /**
     * Constructor that initializes the given variables.
     * @param date Date of the Appointment
     * @param timeslot Timeslot of the main.Appointment
     * @param patient inheritance.Person details, First and Last Name (can be a Patient).
     * @param provider inheritance.Person details of the provider (can be a Doctor or Technician).
     */
    public Appointment(Date date, Timeslot timeslot, Person patient, Person provider) {
        this.date = date;
        this.timeslot = timeslot;
        this.patient = patient;
        this.provider = provider;
    }

    /**
     * Getter method to obtain the main.Date of the appointment.
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Getter method to obtain the main.Timeslot of the appointment.
     * @return timeslot.
     */
    public Timeslot getTimeslot() {
        return timeslot;
    }

    /**
     * Getter method to obtain inheritance.Person details of the patient.
     * @return patient
     */
    public Person getPatient() {
        return patient;
    }

    /**
     * Sets the patients
     * @param patient passed in patient details.
     */
    public void setPatient(Person patient) {
        this.patient = patient;
    }


    /**
     * Getter method to return the inheritance.Person details of the provider.
     * @return provider
     */
    public Person getProvider() {
        return provider;
    }

    /**
     * equals method that checks if two appointments are equal.
     * @param obj Passed in appointment as object.
     * @return A boolean value after the comparison happens.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Appointment other = (Appointment) obj;
        Profile thisProfile = this.patient.getProfile();
        Profile otherProfile = other.patient.getProfile();
        return this.date.equals(other.date) &&
                this.timeslot.equals(other.timeslot) &&
                thisProfile.getFname().equalsIgnoreCase(otherProfile.getFname()) &&
                thisProfile.getLname().equalsIgnoreCase(otherProfile.getLname()) &&
                thisProfile.getDob().equals(otherProfile.getDob());
    }

    /**
     * Prints out the given appointment in String format.
     * @return String
     */
    @Override
    public String toString() {
        String providerDetails;
        if (provider instanceof Technician) {
            Technician tech = (Technician) provider;
            providerDetails = String.format("[%s %s %s, %s][rate: $%d.00]",
                    tech.getProfile().getFname(),
                    tech.getProfile().getLname(),
                    tech.getProfile().getDob(),
                    tech.getLocation().getFullLocation(),
                    tech.rate());
        } else {
            Doctor doc = (Doctor) provider;
            providerDetails = String.format("[%s %s %s, %s][%s, #%s]",
                    doc.getProfile().getFname(),
                    doc.getProfile().getLname(),
                    doc.getProfile().getDob(),
                    doc.getLocation().getFullLocation(),
                    doc.getSpecialty(),
                    doc.getNpi());
        }
        return String.format("%s %s %s %s\n",
                date.toString(),
                timeslot.toString(),
                patient.toString(),
                providerDetails);
    }


    /**
     * Method that compares two appointments based on date, timeslot, provider, and patient.
     * @param other the object to be compared.
     * @return -1, 0 , 1
     */
    @Override
    public int compareTo(Appointment other) {
        int dateComparison = date.compareTo(other.date);
        if (dateComparison != 0) {
            return dateComparison;
        }

        int timeslotComparison = timeslot.compareTo(other.timeslot);
        if (timeslotComparison != 0) {
            return timeslotComparison;
        }

        // Using dynamic polymorphism for provider comparison
        int providerComparison = provider.compareTo(other.provider);
        if (providerComparison != 0) {
            return providerComparison;
        }

        // Using dynamic polymorphism for patient comparison
        return patient.compareTo(other.patient);
    }
}