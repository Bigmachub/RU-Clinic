package com.example.project3.clinic;
import com.example.project3.util.Date;

/** Profile Class
 * @author Shreeyut
 * @author Andy
 */
public class Profile implements Comparable<Profile> {
    private String fname; // First name of the patient
    private String lname; // Last name of the patient
    private Date dob;     // clinic.Date of birth of the patient

    /**
     * Constructor that initializes the given variable.
     * @param fname First Name of the patient.
     * @param lname Last Name of the patient.
     * @param dob clinic.Date of Birth of the patient.
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    // Method to return the full name in "FirstName LastName" format
    public String getFullName() {
        return fname + " " + lname;
    }

    /**
     * Getter method to obtain the First Name.
     * @return String
     */
    public String getFname() {
        return fname;
    }

    /**
     * Getter method to obtain the Last Name.
     * @return String
     */
    public String getLname() {
        return lname;
    }

    /**
     * Getter method to obtain the clinic.Patient DOB.
     * @return String
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Equals method to check if the two given profile match in therms of their content.
     * @param obj Passed in profile object
     * @return Boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Profile other = (Profile) obj;
        return this.fname.equalsIgnoreCase(other.fname) &&
                this.lname.equalsIgnoreCase(other.lname) &&
                this.dob.equals(other.dob);
    }

    /**
     * Method to return the given profile in String format.
     * @return String
     */
    @Override
    public String toString() {
        return String.format("%s %s %s", fname, lname, dob.toString());
    }

    /**
     * Method to compare the relative order of the two given profile.
     * @param other the object to be compared.
     * @return -1, 0, 1
     */
    @Override
    public int compareTo(Profile other) {
        int lastNameComparison = this.lname.compareToIgnoreCase(other.lname);
        if (lastNameComparison != 0) {
            return lastNameComparison;
        }

        int firstNameComparison = this.fname.compareToIgnoreCase(other.fname);
        if (firstNameComparison != 0) {
            return firstNameComparison;
        }
        return this.dob.compareTo(other.dob);
    }
}
