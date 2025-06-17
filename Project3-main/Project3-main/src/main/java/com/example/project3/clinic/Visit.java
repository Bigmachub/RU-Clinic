package com.example.project3.clinic;

/** Visit Class
 * @author Shreeyut
 * @author Andy
 */
public class Visit {
    private Appointment appointment;
    private Visit next;

    /**
     * Constructor for visit with appointment.
     * @param appointment
     */
    public Visit(Appointment appointment) {
        this.appointment = appointment;
        this.next = null; // Initially, the next visit is null
    }

    /**
     * Return the next visit in the Linked list.
     * @return The next visit.
     */

    public Visit getNext() {
        return next;
    }
    /**
     * Sets the next visit in the linked list.
     * @param next The visit to link as the next in the list.
     */
    public void setNext(Visit next) {
        this.next = next;
    }

    /**
     * Returns a String representation of the visit.
     * @return String representation.
     */
    @Override
    public String toString() {
        return appointment.toString();
    }
}

