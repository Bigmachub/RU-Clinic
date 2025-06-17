package com.example.project3.util;

/** Date Class
 * @author Shreeyut
 * @author Andy
 */
import java.util.Calendar;

public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    /**
     * Constructor to initialize the given variables.
     * @param year Year of the clinic.Date
     * @param month Month of the clinic.Date
     * @param day Day of the date
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Method to check if the provided date is a Valid date.
     * @return BooleanuaSp
     */
    public boolean isValid() {
        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);
        try {
            calendar.set(year, month - 1, day);
            calendar.getTime();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Method that compares to two given dates
     * @param other the object to be compared.
     * @return -1. 0, 1
     */
    @Override
    public int compareTo(Date other) {
        if (this.year != other.year) {
            return this.year - other.year;
        }
        if (this.month != other.month) {
            return this.month - other.month;
        }
        return this.day - other.day;
    }

    /**
     * Method that checks if the two given dates are equal
     * @param obj
     * @return Boolean
     */
    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if(!(obj instanceof Date d)){
            return false;
        }
        return this.day == d.day && this.month == d.month && this.year == d.year;
    }

    /**
     * Method to return the date in String format.
     * @return String
     */
    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    /**
     * Method to check if the given date is more than six months ahead.
     * @return Boolean
     */
    public boolean isMoreThanSixMonthsAhead() {
        Calendar now = Calendar.getInstance();
        Calendar future = Calendar.getInstance();
        future.set(year, month - 1, day);

        now.add(Calendar.MONTH, 6);
        return future.after(now);
    }

    /**
     * Method to check if the given date is in the Past.
     * @return Boolean
     */
    public boolean isInPast() {
        Calendar now = Calendar.getInstance();
        Calendar date = Calendar.getInstance();
        date.set(year, month - 1, day);

        return date.before(now);
    }

    /**
     * Method to check if the given date is a Future clinic.Date.
     * @return Boolean
     */
    public boolean isFutureDate() {
        Calendar today = Calendar.getInstance();

        Calendar dateToCheck = Calendar.getInstance();
        dateToCheck.set(Calendar.YEAR, this.year);
        dateToCheck.set(Calendar.MONTH, this.month - 1);
        dateToCheck.set(Calendar.DAY_OF_MONTH, this.day);

        return dateToCheck.after(today);
    }

    /**
     * Method to check if the given date falls on a weekend.
     * @return Boolean
     */
    public boolean isWeekend() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
    }
}
