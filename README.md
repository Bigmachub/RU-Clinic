# Doctor Appointment Scheduler

## 📋 Overview
This project is a Java-based application that allows users to create and manage appointments with doctors using a scheduling system. It demonstrates core object-oriented programming (OOP) principles, specifically **encapsulation** and **abstraction**, to build a modular and scalable solution.

## 💡 Features
- Create and manage doctor profiles
- Schedule appointments with available doctors
- View appointment schedules
- Ensure no overlapping appointments
- Encapsulated logic for doctors, schedules, and patients
- Abstraction used for simplifying the interaction between user and system

## 🧠 OOP Principles Applied

### 🔒 Encapsulation
- Data such as doctor availability, appointment time slots, and patient information are protected using private fields and accessed/modified only through public methods.
- Ensures each class controls its own data, improving code safety and maintainability.

### 🧩 Abstraction
- Complex implementation details like conflict checking and slot management are hidden behind clean and simple interfaces or methods.
- Users interact with high-level methods like `makeAppointment()` or `getSchedule()` without worrying about internal scheduling logic.

## 🛠️ Technologies Used
- Java (Core language)
- Custom Classes for Doctor, Appointment, Patient, and Schedule

## 🚀 Getting Started

### Prerequisites
- Java 8 or higher
- IDE like IntelliJ IDEA, Eclipse, or VSCode

### Running the Program
1. Clone this repository
2. Compile the Java files:
   ```bash
   javac *.java
