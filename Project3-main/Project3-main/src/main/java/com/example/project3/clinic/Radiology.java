package com.example.project3.clinic;

/** Radiology Enum to represent the types of radiology imaging services.
 * @author Shreeyut
 * @author Andy
 */
public enum Radiology {
    CATSCAN("CATSCAN"),
    ULTRASOUND("ULTRASOUND"),
    XRAY("XRAY");

    private final String serviceName;

    /**
     * Constructor to associate a display name with each radiology service.
     * @param serviceName The name of the radiology service.
     */
    Radiology(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Get the display name of the radiology service.
     * @return The name of the radiology service.
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * toString method to return the radiology service name.
     * @return String representation of the radiology service.
     */
    @Override
    public String toString() {
        return serviceName;
    }
}