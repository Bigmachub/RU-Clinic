package com.example.project3.util;

import com.example.project3.clinic.*;
import com.example.project3.persons.*;

/**
 * The Sort class provides utility methods for sorting lists of appointments, providers, and patients.
 * It includes different sorting algorithms based on specific criteria like last name, date of birth, and location.
 * @author Shreeyut
 * @author Andy
 */
public class Sort {

    /**
     * Sorts the given list of appointments based on the provided key.
     *
     * @param list the list of appointments to sort
     * @param key  the sorting key:
     *             'P' - sorts by patient,
     *             'L' - sorts by location,
     *             'A' - sorts by appointment date and time
     */
    public static void appointment(List<Appointment> list, char key) {
        switch (key) {
            case 'P':
                sortByPatient(list);
                break;
            case 'L':
                sortByLocation(list);
                break;
            case 'A':
                sortByAppointment(list);
                break;

            default:
                throw new IllegalArgumentException("Invalid sort key: " + key);
        }
    }

    /**
     * Sorts the list of providers by their profile (first and last name).
     *
     * @param list the list of providers to sort
     */
    public static void provider(List<Provider> list) {
        boolean swapped;
        int n = list.size();
        do {
            swapped = false;
            for (int i = 0; i < n - 1; i++) {
                Provider current = list.get(i);
                Provider next = list.get(i + 1);
                int comparisonResult = current.getProfile().compareTo(next.getProfile());
                if (comparisonResult > 0) {
                    list.set(i, next);
                    list.set(i + 1, current);
                    swapped = true;
                }
            }
            n--;
        } while (swapped);
    }

    /**
     * Sorts an array of patients by last name, first name, and date of birth using the bubble sort algorithm.
     *
     * @param patientsArray        the array of patients to sort
     * @param patientTotalCharges  the array of total charges corresponding to each patient
     * @param count                the number of patients in the array
     */
    public static void sortPatientsByLastNameAndDOB(Patient[] patientsArray, int[] patientTotalCharges, int count) {
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                Patient patient1 = patientsArray[j];
                Patient patient2 = patientsArray[j + 1];
                int comparisonResult = patient1.getProfile().getLname().compareToIgnoreCase(patient2.getProfile().getLname());
                if (comparisonResult == 0) {
                    comparisonResult = patient1.getProfile().getFname().compareToIgnoreCase(patient2.getProfile().getFname());
                }
                if (comparisonResult == 0) {
                    comparisonResult = patient1.getProfile().getDob().compareTo(patient2.getProfile().getDob());
                }
                if (comparisonResult > 0) {
                    Patient tempPatient = patientsArray[j];
                    patientsArray[j] = patientsArray[j + 1];
                    patientsArray[j + 1] = tempPatient;
                    int tempCharge = patientTotalCharges[j];
                    patientTotalCharges[j] = patientTotalCharges[j + 1];
                    patientTotalCharges[j + 1] = tempCharge;
                }
            }
        }
    }

    /**
     * Sorts an array of providers by last name and date of birth using the bubble sort algorithm.
     *
     * @param providersArray       the array of providers to sort
     * @param providerTotalCharges the array of total charges corresponding to each provider
     * @param providerCount        the number of providers in the array
     */
    public static void sortProvidersByLastNameAndDOB(Provider[] providersArray, int[] providerTotalCharges, int providerCount) {
        for (int i = 0; i < providerCount - 1; i++) {
            for (int j = 0; j < providerCount - i - 1; j++) {
                String lastName1 = providersArray[j].getProfile().getLname();
                String lastName2 = providersArray[j + 1].getProfile().getLname();
                int comparisonResult = lastName1.compareToIgnoreCase(lastName2);
                if (comparisonResult == 0) {
                    Date dob1 = providersArray[j].getProfile().getDob();
                    Date dob2 = providersArray[j + 1].getProfile().getDob();
                    comparisonResult = dob1.compareTo(dob2);
                }
                if (comparisonResult > 0) {
                    Provider tempProvider = providersArray[j];
                    providersArray[j] = providersArray[j + 1];
                    providersArray[j + 1] = tempProvider;
                    int tempCharge = providerTotalCharges[j];
                    providerTotalCharges[j] = providerTotalCharges[j + 1];
                    providerTotalCharges[j + 1] = tempCharge;
                }
            }
        }
    }

    /**
     * Sorts a list of appointments by patient, followed by date and time.
     *
     * @param appointments the list of appointments to sort
     */
    private static void sortByPatient(List<Appointment> appointments) {
        for (int i = 0; i < appointments.size() - 1; i++) {
            for (int j = 0; j < appointments.size() - i - 1; j++) {
                Person patient1 = appointments.get(j).getPatient();
                Person patient2 = appointments.get(j + 1).getPatient();
                int comparisonResult = patient1.compareTo(patient2);
                if (comparisonResult == 0) {
                    comparisonResult = appointments.get(j).getDate().compareTo(appointments.get(j + 1).getDate());
                    if (comparisonResult == 0) {
                        comparisonResult = appointments.get(j).getTimeslot().compareTo(appointments.get(j + 1).getTimeslot());
                    }
                }
                if (comparisonResult > 0) {
                    Appointment temp = appointments.get(j);
                    appointments.set(j, appointments.get(j + 1));
                    appointments.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * Sorts a list of appointments by provider location (county), followed by date and time.
     *
     * @param appointments the list of appointments to sort
     */
    private static void sortByLocation(List<Appointment> appointments) {
        for (int i = 0; i < appointments.size() - 1; i++) {
            for (int j = 0; j < appointments.size() - i - 1; j++) {
                Provider provider1 = (Provider) appointments.get(j).getProvider();
                Provider provider2 = (Provider) appointments.get(j + 1).getProvider();
                String county1 = provider1.getLocation().getCounty();
                String county2 = provider2.getLocation().getCounty();

                int comparisonResult = county1.compareToIgnoreCase(county2);
                if (comparisonResult == 0) {
                    comparisonResult = appointments.get(j).getDate().compareTo(appointments.get(j + 1).getDate());

                    if (comparisonResult == 0) {
                        comparisonResult = appointments.get(j).getTimeslot().compareTo(appointments.get(j + 1).getTimeslot());
                    }
                }
                if (comparisonResult > 0) {
                    Appointment temp = appointments.get(j);
                    appointments.set(j, appointments.get(j + 1));
                    appointments.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * Sorts a list of appointments by date, followed by time and provider's last name.
     *
     * @param appointments the list of appointments to sort
     */
    private static void sortByAppointment(List<Appointment> appointments) {
        for (int i = 0; i < appointments.size() - 1; i++) {
            for (int j = 0; j < appointments.size() - i - 1; j++) {
                Appointment app1 = appointments.get(j);
                Appointment app2 = appointments.get(j + 1);
                int comparisonResult = app1.getDate().compareTo(app2.getDate());
                if (comparisonResult == 0) {
                    comparisonResult = app1.getTimeslot().compareTo(app2.getTimeslot());
                    if (comparisonResult == 0) {
                        Provider provider1 = (Provider) app1.getProvider();
                        Provider provider2 = (Provider) app2.getProvider();
                        String providerFirstName1 = provider1.getProfile().getLname();
                        String providerFirstName2 = provider2.getProfile().getLname();
                        comparisonResult = providerFirstName1.compareToIgnoreCase(providerFirstName2);
                    }
                }
                if (comparisonResult > 0) {
                    appointments.set(j, app2);
                    appointments.set(j + 1, app1);
                }
            }
        }
    }

    /**
     * Sorts a list of office appointments by county, date, and time. Only appointments with doctors are included.
     *
     * @param appointments the list of office appointments to sort
     */
    public static void sortOfficeAppointments(List<Appointment> appointments) {
        for (int i = 0; i < appointments.size() - 1; i++) {
            for (int j = 0; j < appointments.size() - i - 1; j++) {
                Appointment app1 = appointments.get(j);
                Appointment app2 = appointments.get(j + 1);
                Person person1 = app1.getProvider();
                Person person2 = app2.getProvider();

                if (!(person1 instanceof Doctor) || !(person2 instanceof Doctor)) {
                    continue;
                }
                Doctor doctor1 = (Doctor) person1;
                Doctor doctor2 = (Doctor) person2;
                int comparisonResult = doctor1.getLocation().getCounty()
                        .compareToIgnoreCase(doctor2.getLocation().getCounty());
                if (comparisonResult == 0) {
                    comparisonResult = app1.getDate().compareTo(app2.getDate());
                    if (comparisonResult == 0) {
                        comparisonResult = app1.getTimeslot().compareTo(app2.getTimeslot());
                    }
                }
                if (comparisonResult > 0) {
                    appointments.set(j, app2);
                    appointments.set(j + 1, app1);
                }
            }
        }
    }

    /**
     * Sorts a list of imaging appointments by county, date, time, and technician's first name.
     *
     * @param appointments the list of imaging appointments to sort
     */
    public static void sortImagingAppointments(List<Appointment> appointments) {
        for (int i = 0; i < appointments.size() - 1; i++) {
            for (int j = 0; j < appointments.size() - i - 1; j++) {
                Appointment app1 = appointments.get(j);
                Appointment app2 = appointments.get(j + 1);
                Provider provider1 = (Provider) app1.getProvider();
                Provider provider2 = (Provider) app2.getProvider();
                int comparisonResult = provider1.getLocation().getCounty()
                        .compareToIgnoreCase(provider2.getLocation().getCounty());
                if (comparisonResult == 0) {
                    comparisonResult = app1.getDate().compareTo(app2.getDate());
                    if (comparisonResult == 0) {
                        comparisonResult = app1.getTimeslot().compareTo(app2.getTimeslot());
                        if (comparisonResult == 0) {
                            comparisonResult = ((Technician) provider1).getProfile().getFname()
                                    .compareToIgnoreCase(((Technician) provider2).getProfile().getFname());
                        }
                    }
                }
                if (comparisonResult > 0) {
                    appointments.set(j, app2);
                    appointments.set(j + 1, app1);
                }
            }
        }
    }
}