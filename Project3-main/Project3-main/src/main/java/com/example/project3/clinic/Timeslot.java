package com.example.project3.clinic;
/**
 * Timeslot class that gives us timeslot associated with the appointment.
 * @author Shreeyut
 * @author Andy
 */
public class Timeslot implements Comparable<Timeslot> {
    private int hour;
    private int minute;

    /**
     * Constructor for timeslot.
     * @param hour hour passed in.
     * @param minute minute passed in.
     */
    public Timeslot(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Factory method to create a Timeslot instance based on a slot number using custom mapping.
     * Each slot number corresponds to a specific time of day, with 30-minute intervals.
     *
     * @param slotNumber the number of the slot, which represents a specific time
     * @return a Timeslot object corresponding to the given slot number
     * @throws IllegalArgumentException if the slot number is outside the valid range (1-12)
     */
    public static Timeslot fromSlotNumber(int slotNumber) {
        switch (slotNumber) {
            case 1: return new Timeslot(9, 0);    // 9:00 AM
            case 2: return new Timeslot(9, 30);   // 9:30 AM
            case 3: return new Timeslot(10, 0);   // 10:00 AM
            case 4: return new Timeslot(10, 30);  // 10:30 AM
            case 5: return new Timeslot(11, 0);   // 11:00 AM
            case 6: return new Timeslot(11, 30);  // 11:30 AM
            // Skipping lunch break from 12:00 PM - 1:30 PM
            case 7: return new Timeslot(2, 0);    // 2:00 PM
            case 8: return new Timeslot(2, 30);   // 2:30 PM
            case 9: return new Timeslot(3, 0);    // 3:00 PM
            case 10: return new Timeslot(3, 30);  // 3:30 PM
            case 11: return new Timeslot(4, 0);   // 4:00 PM
            case 12: return new Timeslot(4, 30);  // 4:30 PM
            default:
                throw new IllegalArgumentException(slotNumber + " is not a valid time slot.");
        }
    }

    /**
     * Retrieves the slot number for this Timeslot based on a custom mapping of time intervals.
     *
     * @return the slot number that corresponds to this Timeslot's hour and minute
     * @throws IllegalArgumentException if the time does not match a valid slot
     */
    public int getSlotNumber() {
        if (hour == 9 && minute == 0) return 1;
        if (hour == 9 && minute == 30) return 2;
        if (hour == 10 && minute == 0) return 3;
        if (hour == 10 && minute == 30) return 4;
        if (hour == 11 && minute == 0) return 5;
        if (hour == 11 && minute == 30) return 6;
        if (hour == 2 && minute == 0) return 7;
        if (hour == 2 && minute == 30) return 8;
        if (hour == 3 && minute == 0) return 9;
        if (hour == 3 && minute == 30) return 10;
        if (hour == 4 && minute == 0) return 11;
        if (hour == 4 && minute == 30) return 12;
        throw new IllegalArgumentException("Time does not match a valid slot.");
    }

    /**
     * Returns a string representation of this Timeslot in a 12-hour format with AM/PM.
     * @return a formatted string representing the time (e.g., "9:00 AM" or "4:30 PM")
     */    @Override
    public String toString() {
        int hour12 = (hour == 0 || hour == 12) ? 12 : (hour > 12 ? hour - 12 : hour);
        String period = (hour < 12) ? "AM" : "PM";
        if (hour >= 9 && hour < 12) {
            period = "AM";
        }
        else if (hour <= 8) {
            period = "PM";
        }
        return String.format("%d:%02d %s", hour12, minute, period);
    }

    /**
     * Compares this Timeslot to another object for equality, based on the hour and minute fields.
     *
     * @param obj the object to compare with this Timeslot
     * @return true if the specified object is a Timeslot with the same hour and minute, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Timeslot)) return false;
        Timeslot other = (Timeslot) obj;
        return this.hour == other.hour && this.minute == other.minute;
    }

    /**
     * Compares this Timeslot to another Timeslot for order, sorting by hour first, then by minute.
     *
     * @param other the other Timeslot to compare with this Timeslot
     * @return a negative integer, zero, or a positive integer if this Timeslot is less than, equal to,
     *         or greater than the specified Timeslot
     */
    @Override
    public int compareTo(Timeslot other) {
        int thisHour24 = (this.hour < 9) ? this.hour + 12 : this.hour;  // Convert PM to 24-hour format
        int otherHour24 = (other.hour < 9) ? other.hour + 12 : other.hour;  // Convert PM to 24-hour format

        if (thisHour24 != otherHour24) {
            return Integer.compare(thisHour24, otherHour24);
        }
        return Integer.compare(this.minute, other.minute);
    }

}
