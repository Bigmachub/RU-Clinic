package com.example.project3.clinic;

import com.example.project3.persons.Patient;
import com.example.project3.persons.Technician;
import com.example.project3.util.Date;

/**
 * Imaging Class which is subclass of Appointment to deal with Imaging Appointments.
 * @author shreeyut
 * @author Andy
 */
public class Imaging extends Appointment {
    private Radiology room; // X-ray, Ultrasound, CAT Scan

    /**
     * Constructs an Imaging appointment with a specified date, timeslot, patient, technician, and imaging room type.
     *
     * @param date       the date of the imaging appointment
     * @param timeslot   the timeslot for the imaging appointment
     * @param patient    the patient for the imaging appointment
     * @param technician the technician performing the imaging
     * @param room       the type of imaging room for the appointment (e.g., X-ray, Ultrasound, CAT Scan)
     */
    public Imaging(Date date, Timeslot timeslot, Patient patient, Technician technician, Radiology room) {
        super(date, timeslot, patient, technician); // Call parent constructor
        this.room = room;
    }

    /**
     * Gets the radiology room type for this imaging appointment.
     * @return the radiology room type for this appointment
     */    public Radiology getRoom() {
            return room;
    }

    /**
     * Provides a string representation of the imaging appointment, including basic appointment details
     * and the specific type of imaging.
     *
     * @return a string representing the imaging appointment with details from the superclass and the imaging type
     */
    @Override
    public String toString() {
        return String.format("%s[%s]", super.toString().trim(), room);
    }
}
