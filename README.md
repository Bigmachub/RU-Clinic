# 🏥 Doctor Appointment Scheduler

## 📌 Overview
This is a JavaFX desktop application that allows users to schedule and manage appointments with doctors, including general practitioners, radiologists, and imaging technicians. The application was built with modular design principles and demonstrates **encapsulation** and **abstraction** in object-oriented programming.

---

## 🛠 Tech Stack
- **Java 22**
- **JavaFX 22.0.1**
- **FXML** for GUI layout
- **JUnit 4/5** for testing
- **Maven** for dependency and build management

---

## 🎯 Features
- Create and manage patient profiles
- Schedule appointments for different types of medical services (e.g. imaging, radiology)
- View and modify timeslots
- Prevent overlapping appointments
- Modular architecture with packages for:
  - Clinic logic (`clinic/`)
  - GUI components (`gui/`)
  - People management (`persons/`)
  - Utility classes (`util/`)

---

## 🧠 Object-Oriented Design

### 🔒 Encapsulation
All core data (e.g., `Timeslot`, `Visit`, `Appointment`, `Doctor`) is kept private and accessed via public getters/setters, ensuring that internal state is safely maintained.

### 🧩 Abstraction
The scheduling system hides complex logic (e.g., conflict resolution, patient-provider matching) behind simple interfaces and controller classes, making the system intuitive to use and extend.

---

## 🧬 Project Structure
\`\`\`
src/main/java/com/example/project3/
├── clinic/             # Core clinic scheduling logic
│   ├── Appointment.java
│   ├── Imaging.java
│   ├── Radiology.java
│   └── Visit.java
├── gui/                # JavaFX GUI logic
│   ├── ClinicManagerMain.java
│   └── ClinicManagerController.java
├── persons/            # Person hierarchy (Doctors, Technicians, Patients)
│   ├── Doctor.java
│   ├── Technician.java
│   ├── Patient.java
├── util/               # Utility classes
│   ├── Date.java
│   ├── Sort.java
│   └── Location.java
resources/
└── clinic-view.fxml    # GUI layout using FXML
\`\`\`

---

## 🚀 Getting Started

### ✅ Prerequisites
- Java 22+
- Maven 3.8+
- JavaFX SDK (configured for your environment)

### 📦 Installation & Run
1. Clone the repository:
   \`\`\`bash
   git clone https://github.com/your-username/Project3-main.git
   cd Project3-main
   \`\`\`

2. Run the app:
   \`\`\`bash
   mvn clean javafx:run
   \`\`\`

> Ensure JavaFX is correctly linked in your development environment.

---

## 🧪 Testing
Run unit tests with:
\`\`\`bash
mvn test
\`\`\`

---

## 🙋‍♂️ Author
**Andy Chen**  
Feel free to reach out with questions or ideas for improvement.

---

## 📜 License
This project is for educational/demo purposes and does not currently use a specific license.
