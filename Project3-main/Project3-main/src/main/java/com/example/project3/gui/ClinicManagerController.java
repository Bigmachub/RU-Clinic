package com.example.project3.gui;

import com.example.project3.util.*;
import com.example.project3.clinic.*;
import com.example.project3.persons.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.beans.property.SimpleStringProperty;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.time.LocalDate;

public class ClinicManagerController {
        List<Provider> providerList = new List<>();  // List to store Doctor and Technician objects
        List<Technician> rotationList = new List<>();  // List to store Technician objects
        List<Appointment> appointments = new List<>(); // List to store Appointments

        @FXML
        private TabPane mainTabPane;
        @FXML
        private Tab scheduleCancelTab;
        @FXML
        private TextField firstNameField, lastNameField;
        @FXML
        private DatePicker dobPicker, appointmentDatePicker;
        @FXML
        private ComboBox<String> providerComboBox, timeslotComboBox, roomComboBox;
        @FXML
        private RadioButton officeRadioButton, imagingRadioButton;
        @FXML
        private Button  loadProviderButton;
        @FXML
        private ToggleGroup appointmentTypeGroup;
        @FXML
        private Tab rescheduleTab;
        @FXML
        private DatePicker rescheduleAppointmentDatePicker, rescheduleDobPicker;
        @FXML
        private TextField rescheduleFirstNameField, rescheduleLastNameField;
        @FXML
        private ComboBox<String> oldTimeslotComboBox, newTimeslotComboBox;
        @FXML
        private TableView<Location> clinicLocationsTable;
        @FXML
        private TableColumn<Location, String> countyColumn, cityColumn, zipColumn;
        @FXML
        private TextArea logTextArea;

        /**
         * Method that runs at the start the initial setup for the software.
         */
        @FXML
        private void initialize() {
                firstNameField.setTextFormatter(createTextOnlyFormatter());
                lastNameField.setTextFormatter(createTextOnlyFormatter());
                rescheduleFirstNameField.setTextFormatter(createTextOnlyFormatter());
                rescheduleLastNameField.setTextFormatter(createTextOnlyFormatter());
                cityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCity()));
                countyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCounty()));
                zipColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getZip()));
                clinicLocationsTable.getItems().setAll(Location.values());
                appointmentTypeGroup = new ToggleGroup();
                officeRadioButton.setToggleGroup(appointmentTypeGroup);
                imagingRadioButton.setToggleGroup(appointmentTypeGroup);
                initializeTimeslotComboBox(timeslotComboBox);
                initializeTimeslotComboBox(oldTimeslotComboBox);
                initializeTimeslotComboBox(newTimeslotComboBox);
                initializeRoomComboBox();
                roomComboBox.disableProperty().bind(imagingRadioButton.selectedProperty().not());
               providerComboBox.disableProperty().bind(officeRadioButton.selectedProperty().not());
        }

        /**
         * Method that performs the logic to obtain the information from the data fields and runs the specific scheduling method.
         */
        @FXML
        protected void onScheduleButtonClick() {
                try {
                        if (providerList.isEmpty()) {
                                logTextArea.appendText("Error: Please load providers before scheduling an appointment.\n");
                                return; }
                        String firstName = firstNameField.getText().trim();
                        String lastName = lastNameField.getText().trim();
                        LocalDate dobLocalDate = dobPicker.getValue();
                        LocalDate appointmentLocalDate = appointmentDatePicker.getValue();
                        if (dobLocalDate == null || appointmentLocalDate == null) {
                                logTextArea.appendText("Please fill out all date fields to schedule an appointment.\n");
                                return; }
                        String dobString = dobLocalDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                        String appointmentDateString = appointmentLocalDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                        Date dob = parseDate(dobString, false); // Validate date of birth
                        Date appointmentDate = parseDate(appointmentDateString, true); // Validate appointment date
                        if (dob == null || appointmentDate == null) {
                                return; }
                        Timeslot timeslot = Timeslot.fromSlotNumber(timeslotComboBox.getSelectionModel().getSelectedIndex() + 1);
                        String roomName = roomComboBox.getSelectionModel().getSelectedItem();
                        Radiology room = roomName != null ? Radiology.valueOf(roomName.toUpperCase()) : null;
                        if (officeRadioButton.isSelected()) {
                                Provider selectedProvider = parseProvider(providerComboBox.getSelectionModel().getSelectedItem());
                                if (firstName.isEmpty() || lastName.isEmpty() || timeslot == null || selectedProvider == null) {
                                        logTextArea.appendText("Please fill out all fields to schedule an appointment.\n");
                                        return;
                                }
                                Profile profile = new Profile(firstName, lastName, dob);
                                Patient patient = new Patient(profile);
                                scheduleOfficeAppointment(appointmentDate, timeslot, patient, selectedProvider);

                        } else if (imagingRadioButton.isSelected()) {
                                if (room == null) {
                                        logTextArea.appendText("Please select a valid room for the imaging appointment.\n");
                                        return;
                                }
                                Profile profile = new Profile(firstName, lastName, dob);
                                Patient patient = new Patient(profile);
                                scheduleImagingAppointment(appointmentDate, timeslot, patient, room);
                        } else {
                                logTextArea.appendText("Please select an appointment type (Office or Imaging).\n");
                        }
                } catch (Exception e) {
                        logTextArea.appendText("Error scheduling appointment: " + e.getMessage() + "\n");
                }
        }

        /**
         * Implements the logic to cancel a given appointment.
         */
        @FXML
        protected void onCancelButtonClick() {
                try {
                        String firstName = firstNameField.getText().trim();
                        String lastName = lastNameField.getText().trim();
                        LocalDate dobLocalDate = dobPicker.getValue();
                        LocalDate appointmentLocalDate = appointmentDatePicker.getValue();
                        int timeslotIndex = timeslotComboBox.getSelectionModel().getSelectedIndex();
                        if (firstName.isEmpty() || lastName.isEmpty() || dobLocalDate == null || appointmentLocalDate == null || timeslotIndex == -1) {
                                logTextArea.appendText("Please fill out all fields to cancel an appointment.\n");
                                return; }
                        DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                        String dobString = dobLocalDate.format(formattedDate);
                        String appointmentDateString = appointmentLocalDate.format(formattedDate);
                        Date dob = parseDate(dobString, false); // for date of birth
                        Date appointmentDate = parseDate(appointmentDateString, true); // for appointment date
                        if (dob == null || appointmentDate == null) {
                                return; }
                        Timeslot timeslot = Timeslot.fromSlotNumber(timeslotIndex + 1);
                        Profile patientProfile = new Profile(firstName, lastName, dob);

                        Appointment appointmentToCancel = null;
                        for (Appointment appointment : appointments) {
                                if (appointment.getDate().equals(appointmentDate) &&
                                        appointment.getTimeslot().equals(timeslot) &&
                                        appointment.getPatient().getProfile().equals(patientProfile)) {
                                        appointmentToCancel = appointment;
                                        break; } }
                        if (appointmentToCancel != null && appointments.contains(appointmentToCancel)) {
                                appointments.remove(appointmentToCancel);
                                logTextArea.appendText(String.format("Appointment on %s at %s for %s %s (%s) has been canceled.\n",
                                        appointmentDate, timeslot, firstName, lastName, dob));
                        } else {
                                logTextArea.appendText(String.format("No appointment found on %s at %s for %s %s (%s).\n",
                                        appointmentDate, timeslot, firstName, lastName, dob));
                        }
                } catch (Exception e) {
                        logTextArea.appendText("Error canceling appointment: " + e.getMessage() + "\n");
                }
        }

        /**
         * Implements the logic to reschedule a given appointment.
         */
        @FXML
        protected void onRescheduleButtonClick() {
                try {
                        String firstName = rescheduleFirstNameField.getText().trim();
                        String lastName = rescheduleLastNameField.getText().trim();
                        LocalDate dobLocalDate = rescheduleDobPicker.getValue();
                        LocalDate appointmentLocalDate = rescheduleAppointmentDatePicker.getValue();
                        int oldTimeslotIndex = oldTimeslotComboBox.getSelectionModel().getSelectedIndex();
                        int newTimeslotIndex = newTimeslotComboBox.getSelectionModel().getSelectedIndex();
                        if (firstName.isEmpty() || lastName.isEmpty() || dobLocalDate == null || appointmentLocalDate == null ||
                                oldTimeslotIndex == -1 || newTimeslotIndex == -1) {
                                logTextArea.appendText("Please fill out all fields to reschedule an appointment.\n");
                                return; }
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                        String dobString = dobLocalDate.format(formatter);
                        String appointmentDateString = appointmentLocalDate.format(formatter);
                        Date dob = parseDate(dobString, false);
                        Date appointmentDate = parseDate(appointmentDateString, true);
                        if (dob == null || appointmentDate == null) {
                                return; }
                        Timeslot oldTimeslot = Timeslot.fromSlotNumber(oldTimeslotIndex + 1);
                        Timeslot newTimeslot = Timeslot.fromSlotNumber(newTimeslotIndex + 1);
                        Profile patientProfile = new Profile(firstName, lastName, dob);

                        Appointment existingAppointment = findAppointmentWithoutProvider(appointmentDate, oldTimeslot, patientProfile);
                        if (existingAppointment == null) {
                                logTextArea.appendText(String.format("No appointment found on %s at %s for %s %s (%s).\n",
                                        appointmentDate, oldTimeslot, firstName, lastName, dob));
                                return; }
                        if (newTimeslot == null || oldTimeslot.equals(newTimeslot)) {
                                logTextArea.appendText(String.format("Appointment is already scheduled at %s on %s.\n", newTimeslot, appointmentDate));
                                return; }
                        for (Appointment appt : appointments) {
                                if (appt.getDate().equals(appointmentDate) && appt.getTimeslot().equals(newTimeslot) &&
                                        appt.getPatient().getProfile().equals(patientProfile)) {
                                        logTextArea.appendText(String.format("%s %s has an existing appointment at %s on %s.\n",
                                                patientProfile.getFullName(), dob, newTimeslot, appointmentDate));
                                        return; } }
                        Provider provider = (Provider) existingAppointment.getProvider();
                        if (isTimeslotBooked(appointmentDate, newTimeslot, provider)) {
                                logTextArea.appendText(String.format("Provider %s has an existing appointment at %s on %s.\n",
                                        provider.getName(), newTimeslot, appointmentDate));
                                return;
                        }
                        appointments.remove(existingAppointment);
                        appointments.add(new Appointment(appointmentDate, newTimeslot, existingAppointment.getPatient(), provider));
                        logTextArea.appendText(String.format("Rescheduled to %s %s %s %s %s\n",
                                appointmentDate, newTimeslot, patientProfile.getFullName(), dob, provider));
                } catch (Exception e) {
                        logTextArea.appendText("Error during rescheduling: " + e.getMessage() + "\n");
                }
        }

        /**
         * Implement the logic to load the given providers to the software.
         */
        @FXML
        protected void onLoadProviderButtonClick() {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select Provider File");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
                File file = fileChooser.showOpenDialog(null);
                if (file != null) {
                        try (Scanner fileScanner = new Scanner(file)) {
                                while (fileScanner.hasNextLine()) {
                                        String line = fileScanner.nextLine();
                                        StringTokenizer st = new StringTokenizer(line);
                                        String type = st.nextToken();
                                        String firstName = st.nextToken();
                                        String lastName = st.nextToken();
                                        String dob = st.nextToken();
                                        String locationStr = st.nextToken();
                                        Date dateOfBirth = parseDate(dob, false);
                                        Location loc = Location.valueOf(locationStr.toUpperCase());
                                        Profile profile = new Profile(firstName, lastName, dateOfBirth);

                                        if (type.equals("D")) {
                                                String specialtyStr = st.nextToken().toUpperCase();
                                                Specialty specialty = Specialty.valueOf(specialtyStr);
                                                String npi = st.nextToken();
                                                Doctor doctor = new Doctor(profile, loc, specialty, npi);
                                                providerList.add(doctor);
                                                providerComboBox.getItems().add(firstName + " " + lastName + " - " + npi);
                                        } else if (type.equals("T")) {
                                                int rate = Integer.parseInt(st.nextToken());
                                                Technician technician = new Technician(profile, loc, rate);
                                                providerList.add(technician);
                                                rotationList.add(technician);
                                                //providerComboBox .getItems().add(firstName + " " + lastName + " - " + rate);
                                        }
                                }
                                loadProviderButton.setDisable(true);
                                displayProviders();
                                displayRotationList();

                        } catch (FileNotFoundException e) {
                                logTextArea.appendText("Error: providers.txt file not found.\n");
                        } catch (IllegalArgumentException e) {
                                logTextArea.appendText("Error: Invalid data format in provider file - " + e.getMessage() + "\n");
                        }
                } else {
                        logTextArea.appendText("Provider file selection canceled.\n");
                }
        }

        /**
         * Method to clear the text fields and selected options.
         */
        @FXML
        protected void onScheduleClearButtonClick() {
                firstNameField.clear();
                lastNameField.clear();
                dobPicker.setValue(null);
                appointmentDatePicker.setValue(null);
                providerComboBox.getSelectionModel().clearSelection();
                timeslotComboBox.getSelectionModel().clearSelection();
                roomComboBox.getSelectionModel().clearSelection();
                appointmentTypeGroup.selectToggle(null);
        }

        /**
         * Method to clear the text fields and selected options.
         */
        @FXML
        protected void onRescheduleClearButtonClick() {
                rescheduleFirstNameField.clear();
                rescheduleLastNameField.clear();
                rescheduleDobPicker.setValue(null);
                rescheduleAppointmentDatePicker.setValue(null);
                oldTimeslotComboBox.getSelectionModel().clearSelection();
                newTimeslotComboBox.getSelectionModel().clearSelection();
        }

        /**
         * Method that calls the sorting algorithm to list the appointments by DATE/TIME/PROVIDER
         */
        @FXML
        protected void onListByDateTimeProviderMenuItemClick() {
                if (appointments.isEmpty()) {
                        logTextArea.appendText("Schedule calendar is empty.\n");
                        return; }

                logTextArea.appendText("\nListing appointments ordered by Date/Time/Provider...\n");
                Sort.appointment(appointments, 'A'); // Sort by appointment date and time (key 'A')
                displayAppointments();
        }

        /**
         * Method that calls the sorting algorithm to list the appointments by COUNTY/DATE/TIME
         */
        @FXML
        protected void onListByCountyDateTimeMenuItemClick() {
                if (appointments.isEmpty()) {
                        logTextArea.appendText("Schedule calendar is empty.\n");
                        return; }

                logTextArea.appendText("\nListing appointments ordered by County/Date/Time...\n");
                Sort.appointment(appointments, 'L'); // Sort by location (county), date, and time (key 'L')
                displayAppointments();
        }

        /**
         * Method that calls the sorting algorithm to list the appointments by PATIENT/DATE/TIME
         */
        @FXML
        protected void onListByPatientDateTimeMenuItemClick() {
                if (appointments.isEmpty()) {
                        logTextArea.appendText("Schedule calendar is empty.\n");
                        return; }

                logTextArea.appendText("\nListing appointments ordered by Patient/Date/Time...\n");
                Sort.appointment(appointments, 'P'); // Sort by patient (key 'P')
                displayAppointments();
        }

        /**
         * Method to list all the office appointments in a sorted manner.
         */
        @FXML
        protected void onOfficeAppointmentsMenuItemClick() {
                if (appointments.isEmpty()) {
                        logTextArea.appendText("Schedule calendar is empty.\n");
                        return;
                }
                List<Appointment> doctorAppointments = new List<>();
                for (Appointment appt : appointments) {
                        Person person = appt.getProvider();
                        if (person instanceof Doctor) {
                                doctorAppointments.add(appt);
                        }
                }
                if (doctorAppointments.isEmpty()) {
                        logTextArea.appendText("Schedule calendar is empty.\n");
                        return;
                }
                Sort.sortOfficeAppointments(doctorAppointments);
                logTextArea.appendText("\n** List of office appointments ordered by county/date/time.\n");
                for (Appointment appt : doctorAppointments) {
                        logTextArea.appendText(appt.toString());
                }
                logTextArea.appendText("** end of list **\n");
        }

        /**
         * Method to list all the imaging appointments in a sorted manner.
         */
        @FXML
        protected void onImagingAppointmentsMenuItemClick() {
                if (appointments.isEmpty()) {
                        logTextArea.appendText("Schedule calendar is empty.\n");
                        return;
                }
                List<Appointment> imagingAppointments = new List<>();
                for (Appointment appt : appointments) {
                        if (appt instanceof Imaging) {
                                imagingAppointments.add(appt);
                        }
                }
                if (imagingAppointments.isEmpty()) {
                        logTextArea.appendText("Schedule calendar is empty.\n");
                        return;
                }
                Sort.sortImagingAppointments(imagingAppointments);
                logTextArea.appendText("\n** List of radiology appointments ordered by county/date/time.\n");
                for (Appointment appt : imagingAppointments) {
                        logTextArea.appendText(appt.toString());
                }
                logTextArea.appendText("\n** end of list **\n");
        }

        /**
         * Displays the credit for each provider.
         */
        @FXML
        protected void onProviderCreditMenuItemClick() {
                if (appointments.isEmpty()) {
                        logTextArea.appendText("Schedule calendar is empty.\n");
                        return; }
                Provider[] providersArray = new Provider[appointments.size()]; // Adjust the size if necessary
                int[] providerTotalCharges = new int[appointments.size()]; // Corresponding charges for providers
                int providerCount = 0;
                for (Appointment appointment : appointments) {
                        Person providerPerson = appointment.getProvider();
                        if (providerPerson instanceof Provider) {
                                Provider provider = (Provider) providerPerson;
                                int index = -1;
                                for (int i = 0; i < providerCount; i++) {
                                        if (providersArray[i].equals(provider)) {
                                                index = i; // Found existing provider
                                                break; } }
                                if (index == -1) {
                                        providersArray[providerCount] = provider;
                                        index = providerCount;
                                        providerCount++; }
                                if (provider instanceof Doctor) {
                                        Specialty specialty = ((Doctor) provider).getSpecialty();
                                        providerTotalCharges[index] += specialty.getCharge();
                                } else if (provider instanceof Technician && appointment instanceof Imaging) {
                                        int technicianRate = ((Technician) provider).rate();
                                        providerTotalCharges[index] += technicianRate; } } }
                Sort.sortProvidersByLastNameAndDOB(providersArray, providerTotalCharges, providerCount);
                logTextArea.appendText("\n** Credit amount ordered by provider. **");
                for (int i = 0; i < providerCount; i++) {
                        logTextArea.appendText(String.format("\n(%d) %s %s [credit amount: $%,.2f]",
                                (i + 1),
                                providersArray[i].getProfile().getFullName().toUpperCase(),
                                providersArray[i].getProfile().getDob(),
                                (double) providerTotalCharges[i]));
                }
                logTextArea.appendText("\n** end of list **\n");
        }

        /**
         * Displays the Patient bills depending on their visit.
         */
        @FXML
        protected void onPatientBillsMenuItemClick() {
                if (appointments.isEmpty()) {
                        logTextArea.appendText("Schedule calendar is empty.\n");
                        return; }

                logTextArea.appendText("** Billing statement ordered by patient.**\n");

                int appointmentCount = appointments.size();
                Patient[] uniquePatients = new Patient[appointmentCount];
                int[] totalCharges = new int[appointmentCount];
                int patientCount = accumulatePatientCharges(appointments, uniquePatients, totalCharges);
                Sort.sortPatientsByLastNameAndDOB(uniquePatients, totalCharges, patientCount);

                for (int i = 0; i < patientCount; i++) {
                        Patient patient = uniquePatients[i];
                        int totalDue = totalCharges[i];
                        logTextArea.appendText(String.format("(%d) %s [due: $%,.2f]\n", i + 1, patient.getProfile().toString(), (double) totalDue));
                }
                logTextArea.appendText("** end of list **\n");
        }

        /**
         * Schedules the appointment depending on what type of appointment it is.
         */
        @FXML
        protected void onScheduleCancelMenuItemClick() {
              mainTabPane.getSelectionModel().select(scheduleCancelTab);
        }

        /**
         * Reschedules the appointment to a different timeslot.
         */
        @FXML
        protected void onRescheduleMenuItemClick() {
                mainTabPane.getSelectionModel().select(rescheduleTab);
        }


        /**
         * Method that allows only text inputs in the available text fields.
         * @return change
         */
        private TextFormatter<String> createTextOnlyFormatter() {
                return new TextFormatter<>(change -> {
                        String newText = change.getControlNewText();
                        if (newText.matches("[a-zA-Z]*")) {
                                return change;
                        }
                        return null;
                });
        }

        /**
         * Method that loads the Timeslot Combo-box with all the timeslots.
         * @param comboBox Combo-box to populate.
         */
        private void initializeTimeslotComboBox(ComboBox<String> comboBox) {
                for (int slotNumber = 1; slotNumber <= 12; slotNumber++) {
                        Timeslot timeslot = Timeslot.fromSlotNumber(slotNumber);
                        comboBox.getItems().add(timeslot.toString());
                }
        }

        /**
         * Populates the roomComboBox with values from the Radiology enum.
         */
        private void initializeRoomComboBox() {
                for (Radiology room : Radiology.values()) {
                        roomComboBox.getItems().add(room.getServiceName());
                }
        }

        /**
         * Method that schedules office appointment.
         * @param appointmentDate Date of the Appointment
         * @param timeslot Timeslot of the appointment
         * @param patient Patient associated with the appointment
         * @param provider Provider for the appointment
         */
        private void scheduleOfficeAppointment(Date appointmentDate, Timeslot timeslot, Patient patient, Provider provider) {
                try {
                        if (isDuplicateAppointment(patient.getProfile(), appointmentDate, timeslot)) {
                                return;
                        }
                        if (isTimeslotBooked(appointmentDate, timeslot, provider)) {
                                logTextArea.appendText(String.format("%s is not available at slot %d%n", provider, timeslot.getSlotNumber()));
                                return;
                        }
                        Appointment newAppointment = new Appointment(appointmentDate, timeslot, patient, provider);
                        appointments.add(newAppointment);
                        addVisitToPatient(patient, newAppointment);

                        logTextArea.appendText(String.format("\nOffice appointment scheduled successfully:\nDate: %s\nTime: %s\nPatient: %s\nProvider: %s\n\n",
                                appointmentDate, timeslot, patient.getProfile().getFullName() + " (" + patient.getProfile().getDob() + ")",
                                provider.getProfile().getFullName() + " - " + provider.getLocation().getFullLocation()));

                } catch (Exception e) {
                        logTextArea.appendText("Error scheduling office appointment: " + e.getMessage() + "\n");
                }
        }

        /**
         * Method to schedule an imaging appointment.
         * @param appointmentDate Date of the imaging appointment.
         * @param timeslot timeslot of the appointment.
         * @param patient patient details.
         * @param room type of service for the appointment.
         */
        private int technicianIndex = 0;
        private void scheduleImagingAppointment(Date appointmentDate, Timeslot timeslot, Patient patient, Radiology room) {
                try {
                        if (isDuplicateAppointment(patient.getProfile(), appointmentDate, timeslot)) {
                                return;
                        }
                        Technician technician = null;
                        int startIdx = technicianIndex;
                        do {
                                Technician currentTech = rotationList.get(technicianIndex);
                                if (!isTimeslotBooked(appointmentDate, timeslot, currentTech) &&
                                        isRoomAvailableAtLocation(appointmentDate, timeslot, currentTech, room, appointments)) {

                                        technician = currentTech;
                                        technicianIndex = (technicianIndex + 1) % rotationList.size(); // Move to the next technician in rotation
                                        break;
                                }
                                technicianIndex = (technicianIndex + 1) % rotationList.size();
                        } while (technicianIndex != startIdx);

                        if (technician == null) {
                                logTextArea.appendText(String.format("Cannot find an available technician for %s at slot %d.\n",
                                        room.getServiceName(), timeslot.getSlotNumber()));
                                return;
                        }
                        Imaging newImagingAppointment = new Imaging(appointmentDate, timeslot, patient, technician, room);
                        appointments.add(newImagingAppointment);
                        addVisitToPatient(patient, newImagingAppointment);
                        logTextArea.appendText(String.format("\nImaging appointment scheduled successfully:\nDate: %s\nTime: %s\nPatient: %s\nTechnician: %s\nRoom: %s\n\n",
                                appointmentDate, timeslot, patient.getProfile().getFullName() + " (" + patient.getProfile().getDob() + ")",
                                technician.getProfile().getFullName(), room.getServiceName()));

                } catch (Exception e) {
                        logTextArea.appendText("Error scheduling imaging appointment: " + e.getMessage() + "\n");
                }
        }

        /**
         * Helper method to add a visit to the patient's visit history.
         */
        private void addVisitToPatient(Patient patient, Appointment appointment) {
                Visit newVisit = new Visit(appointment);
                if (patient.getVisit() == null) {
                        patient.setVisit(newVisit);
                } else {
                        Visit currentVisit = patient.getVisit();
                        while (currentVisit.getNext() != null) {
                                currentVisit = currentVisit.getNext();
                        }
                        currentVisit.setNext(newVisit);
                }
        }

        /**
         * Method that checks for a duplicate appointment.
         * @param profile Passed in profile
         * @param appointmentDate Passed in Appointment date.
         * @param timeslot Passed in timeslot of the appointment.
         * @return Ture or False
         */
        private boolean isDuplicateAppointment(Profile profile, Date appointmentDate, Timeslot timeslot) {
                for (Appointment appointment : appointments) {
                        if (appointment.getDate().equals(appointmentDate) &&
                                appointment.getTimeslot().equals(timeslot) &&
                                appointment.getPatient().getProfile().equals(profile)) {
                               logTextArea.appendText(String.format("%s %s has an existing appointment at the same time slot.%n",
                                        profile.getFullName(),
                                        profile.getDob()));
                                return true;
                        }
                }
                return false;
        }


        /**
         * Checks if a timeslot is booked by a specified provider on a particular date, and prints availability status.
         *
         * @param date      the date of the timeslot
         * @param timeslot  the timeslot to check
         * @param provider  the provider associated with the timeslot
         * @return true if the timeslot is booked by the provider, false otherwise
         */
        private boolean isTimeslotBooked(Date date, Timeslot timeslot, Provider provider) {
                for (Appointment appointment : appointments) {
                        if (appointment.getDate().equals(date) &&
                                appointment.getTimeslot().equals(timeslot) &&
                                appointment.getProvider().equals(provider)) {
                                return true; // Timeslot is already booked by the provider
                        }
                }
                return false; // Timeslot is available
        }

        /**
         * Checks if a radiology room is available at a technician's location on a specified date and timeslot.
         *
         * @param appointmentDate the date of the appointment
         * @param timeslot       the timeslot of the appointment
         * @param technician     the technician associated with the appointment
         * @param room           the radiology room to check availability for
         * @param appointments   the list of existing appointments
         * @return true if the room is available at the location, false otherwise
         */
        private boolean isRoomAvailableAtLocation(Date appointmentDate, Timeslot timeslot, Technician technician, Radiology room, List<Appointment> appointments) {
                for (Appointment appointment : appointments) {
                        if (appointment instanceof Imaging) {
                                Imaging imagingAppointment = (Imaging) appointment;
                                Provider provider = (Provider) imagingAppointment.getProvider();
                                if (provider.getLocation().equals(technician.getLocation()) &&
                                        imagingAppointment.getRoom() == room &&
                                        imagingAppointment.getDate().equals(appointmentDate) &&
                                        imagingAppointment.getTimeslot().equals(timeslot)) {
                                        return false;
                                }
                        }
                }
                return true;
        }

        /**
         * Finds an appointment by date, timeslot, and patient profile without requiring a provider.
         *
         * @param date           the date of the appointment
         * @param timeslot       the timeslot of the appointment
         * @param patientProfile the profile of the patient
         * @return the appointment if found, null otherwise
         */
        private Appointment findAppointmentWithoutProvider(Date date, Timeslot timeslot, Profile patientProfile) {
                for (Appointment appointment : appointments) {
                        if (appointment.getDate().equals(date) &&
                                appointment.getTimeslot().equals(timeslot) &&
                                appointment.getPatient().getProfile().equals(patientProfile)) {
                                return appointment;
                        }
                }
                return null; // Return null if no matching appointment is found
        }

        /**
         * Helper method to log sorted appointments into the text area.
         */
        private void displayAppointments() {
                for (Appointment appointment : appointments) {
                        logTextArea.appendText(appointment.toString() + "\n");
                }
                logTextArea.appendText("End of list.\n\n");
        }

        /**
         * Displays the list of providers, sorted according to specific criteria.
         */
        private void displayProviders() {
                logTextArea.appendText("Providers Loaded to the list.\n");
                Sort.provider(providerList);
                for (Provider provider : providerList) {
                        logTextArea.appendText(provider.toString() +"\n");
                }
                logTextArea.appendText("\n");
        }

        /**
         * Displays the rotation list of technicians, in reverse order, with formatted output.
         */
        private void displayRotationList() {
                logTextArea.appendText("Rotation list for the technicians.\n");
                reverseList(rotationList);
                for (int i = 0; i < rotationList.size(); i++) {
                        Technician tech = rotationList.get(i);
                        logTextArea.appendText(String.format("%s (%s)", tech.getProfile().getFullName(), tech.getLocation().getCity()));

                        if (i < rotationList.size() - 1) {
                                logTextArea.appendText(" --> ");
                        }
                }
                logTextArea.appendText("\n\n");  // For spacing between sections
        }

        /**
         * Reverses the order of the technicians in the rotation list.
         * @param rotationList the list of technicians to be reversed
         */
        private void reverseList(List<Technician> rotationList) {
                int n = rotationList.size();
                for (int i = 0; i < n / 2; i++) {
                        Technician temp = rotationList.get(i);
                        rotationList.set(i, rotationList.get(n - 1 - i));
                        rotationList.set(n - 1 - i, temp);
                }
        }

        /**
         * Helper method to accumulate unique patients and their total charges.
         *
         * @param appointments The list of appointments.
         * @param uniquePatients Array to store unique patients.
         * @param totalCharges Array to store total charges for each patient.
         * @return The number of unique patients.
         */
        private int accumulatePatientCharges(List<Appointment> appointments, Patient[] uniquePatients, int[] totalCharges) {
                int patientCount = 0;
                for (int i = 0; i < appointments.size(); i++) {
                        Appointment appointment = appointments.get(i);
                        Patient patient = (Patient) appointment.getPatient();
                        Provider provider = (Provider) appointment.getProvider();
                        int visitCharge = provider.rate();
                        boolean found = false;
                        for (int j = 0; j < patientCount; j++) {
                                Patient existingPatient = uniquePatients[j];
                                if (existingPatient.getProfile().equals(patient.getProfile())) {
                                        // Patient already exists, so add the charge to their total
                                        totalCharges[j] += visitCharge;
                                        found = true;
                                        break;
                                }
                        }
                        if (!found) {
                                uniquePatients[patientCount] = patient;
                                totalCharges[patientCount] = visitCharge;
                                patientCount++;
                        }
                }
                return patientCount;
        }


        /**
         * Method that looks through the list and matches the given provider.
         * @param providerName name of the provider
         * @return Doctor object if found; otherwise null.
         */
        private Doctor parseProvider(String providerName) {
                if (providerName == null || providerName.isEmpty()) {
                        return null; }
                String[] parts = providerName.split(" - ");
                if (parts.length != 2) {
                        return null; }
                String fullName = parts[0];
                String npi = parts[1];
                String[] nameParts = fullName.split(" ");
                if (nameParts.length < 2) {
                        return null; }
                String firstName = nameParts[0];
                String lastName = nameParts[1];

                for (Provider provider : providerList) {
                        if (provider instanceof Doctor) {
                                Doctor doctor = (Doctor) provider;
                                if (doctor.getProfile().getFname().equalsIgnoreCase(firstName) &&
                                        doctor.getProfile().getLname().equalsIgnoreCase(lastName) &&
                                        doctor.getNpi().equals(npi)) {
                                        return doctor;
                                }
                        }
                }
                return null;
        }

        /**
         * Parses a date string and creates a Date object, validating its format and logical constraints.
         *
         * @param dateStr  the date string to parse
         * @param isAppointmentDate a flag indicating if the date is for an appointment (applies specific validations)
         * @return the Date object created from the string, or null if validation fails
         */
        private Date parseDate(String dateStr, boolean isAppointmentDate) {
                try {
                        String[] parts = dateStr.split("/");
                        if (parts.length != 3) throw new IllegalArgumentException("Invalid date format.");

                        int month = Integer.parseInt(parts[0]);
                        int day = Integer.parseInt(parts[1]);
                        int year = Integer.parseInt(parts[2]);

                        Date date = new Date(year, month, day);
                        if (isAppointmentDate) {
                                if (!date.isValid()) {
                                        throw new IllegalArgumentException(String.format("Appointment date: %s is not a valid calendar date.", dateStr));
                                }
                                if (date.isInPast()) {
                                        throw new IllegalArgumentException(String.format("Appointment date: %s is today or a date before today.", dateStr));
                                }
                                if (date.isMoreThanSixMonthsAhead()) {
                                        throw new IllegalArgumentException(String.format("Appointment date: %s is not within six months.", dateStr));
                                }
                                if (date.isWeekend()) {
                                        throw new IllegalArgumentException(String.format("Appointment date: %s is on a Saturday or Sunday.", dateStr));
                                }
                        } else {
                                if (!date.isValid()) {
                                        throw new IllegalArgumentException(String.format("Patient dob: %s is not a valid calendar date.", dateStr));
                                }
                                if (date.isFutureDate()) {
                                        throw new IllegalArgumentException(String.format("Patient dob: %s is today or a date after today.", dateStr));
                                }
                        }
                        return date;
                } catch (IllegalArgumentException e) {
                        logTextArea.appendText(e.getMessage() + "\n");
                        return null;
                }
        }
}

