# Hotel Booking System (Java Swing)

## Overview
This is a simple **Hotel Booking System** built in **Java** using **Swing GUI**.  
The system allows **Admins** to manage rooms and bookings, and **Users** to book rooms, view their bookings, and check payment status.

---

## Features

### Admin
- Add new rooms with room number and type
- View all rooms and their cleanliness status
- View all bookings
- Mark rooms as clean
- Exit system

### User
- Book available rooms from predefined hotels
- View personal bookings
- Check payment status
- Exit system

---

## Classes

- **Room** – Represents a hotel room with number, type, and cleanliness status.
- **Booking** – Represents a room booking with details like hotel name, check-in/check-out dates, and payment status.
- **UserAccount** – Represents a user with email, password, and role (Admin/User).
- **HotelBookingGUI** – Main class containing:
  - Login screen
  - Admin menu
  - User menu
  - Room sorting (Bubble Sort)
  - Room search (Binary Search)

---

## Getting Started

### Prerequisites
- Java JDK 8 or higher
- IDE (Eclipse, IntelliJ IDEA, or VS Code with Java support)

### Running the Project
1. Clone the repository:
   ```bash
   git clone https://github.com/hawamubarakk/Hotel-Booking-System.git
