package com.example.project3.persons;

import com.example.project3.clinic.Profile;

/** inheritance.Person Class
 * Superclass for Patient and Provider classes.
 * @author Shreeyut
 * @author Andy
 */
public class Person implements Comparable<Person> {
    protected Profile profile; // Profile instance variable (protected as per project guidelines)

    /**
     * Constructor to initialize the Person with a Profile.
     * @param profile The profile of the person.
     */
    public Person(Profile profile) {
        this.profile = profile;
    }

    /**
     * Getter method to retrieve the profile of the person.
     * @return Profile instance.
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * equals method to check if two persons are equal based on their profile.
     * @param obj The object to compare with.
     * @return true if profiles are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Person other = (Person) obj;
        return this.profile.equals(other.profile);
    }

    /**
     * compareTo method to compare two persons based on their profile.
     * @param other The other Person object to compare with.
     * @return -1, 0, 1 depending on the comparison result.
     */
    @Override
    public int compareTo(Person other) {
        return this.profile.compareTo(other.profile); // Assuming Profile class has a compareTo method
    }

    /**
     * toString method to provide a string representation of the Person.
     * @return String representation of the profile.
     */
    @Override
    public String toString() {
        return profile.toString(); // Assuming Profile class has a toString method
    }
}