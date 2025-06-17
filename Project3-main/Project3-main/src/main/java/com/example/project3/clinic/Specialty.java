package com.example.project3.clinic;

/** Specialty Enum Class
 * @author Shreeyut
 * @author Andy
 */
public enum Specialty {
    FAMILY(250),
    PEDIATRICIAN(300),
    ALLERGIST(350);

    private final int charge;

    /**
     * Constructor for Specialty that uses Charge.
     * @param charge Charge
     */
    Specialty(int charge) {
        this.charge = charge;
    }

    /**
     * Gets the charge associated with Specialty
     * @return Charge Value
     */
    public int getCharge() {
        return charge;
    }

}
