🚂 Ticket Booking System: A Java Masterpiece by Atharva Kumar

Welcome to the Ticket Booking System, a console-based train reservation powerhouse crafted with pure Java brilliance! This project is not just an application—it’s a testament to my journey of mastering core Java concepts, conquering complex programming challenges, and building a robust, feature-rich system from the ground up. If you’re looking for a showcase of Java expertise, you’ve just hit the jackpot! 🎉
Dive into this repository to witness how I transformed raw Java knowledge into a fully functional train ticket booking system, complete with user authentication, train search, seat booking, and persistent data storage. Buckle up—this README will take you through the project’s awesomeness, my learning adventure, and why this is a shining beacon of my programming prowess! 🚀

🌟 Project Overview: What’s This All About?
The Ticket Booking System is a console-based Java application that simulates a real-world train reservation platform, inspired by systems like IRCTC. It allows users to sign up, log in, search for trains, book seats, view bookings, and cancel tickets—all with a seamless, user-friendly interface. Built with clean, modular code and powered by core Java principles, this project demonstrates my ability to design, implement, and optimize a complex application.
Key Features That’ll Blow Your Mind:

Secure User Authentication: Sign up with a unique username and password, securely hashed using BCrypt for top-notch security. Login is smooth and case-insensitive, ensuring a user-friendly experience.
Train Search Magic: Search for trains between any source and destination, with results pulled from a JSON-based train database. The system intelligently validates routes based on station order.
Seat Booking Bliss: Book a specific seat on your chosen train, with real-time seat availability checks. Already booked? No worries—the system’s got you covered with clear error messages.
Booking Management: View all your bookings with detailed ticket information or cancel a ticket by ID, all persisted across sessions.
Persistent Data Storage: User and train data are stored in JSON files (data/users.json and data/trains.json), ensuring your bookings and account details are safe even after you exit.
Error-Proof Design: Robust input validation and exception handling prevent crashes, making the application as reliable as a high-speed train! 🚄

This isn’t just a project—it’s a showcase of Java mastery, problem-solving, and attention to detail. From file I/O to object-oriented design, every line of code reflects my deep understanding of Java’s core concepts.
🛠️ How I Built This: A Java Learning Odyssey
Building the Ticket Booking System was a thrilling journey through the heart of Java programming. As a passionate learner, I dove headfirst into core Java concepts, mastering each one and applying them to create this feature-packed application. Here’s how I leveraged Java’s power to bring this project to life:
1. Object-Oriented Programming (OOP): The Blueprint of Brilliance

Classes and Objects: I designed modular classes like User, Train, and Ticket, each encapsulating specific responsibilities. This ensured clean, reusable code.
Encapsulation: Private fields with public getters/setters (e.g., in User) protected data integrity while allowing controlled access.
Abstraction: The UserBookingService and TrainService classes abstract complex logic (e.g., file operations, seat booking) behind simple method calls, making the codebase maintainable.
Polymorphism: Stream operations and method overloading in UserBookingService allowed flexible data processing, showcasing Java’s dynamic capabilities.

2. File I/O and JSON Mastery

I harnessed Java’s file handling (java.io.File) to read and write user and train data to data/users.json and data/trains.json.
Using the Jackson library (jackson-databind:2.17.2), I mastered JSON serialization and deserialization, mapping JSON data to Java objects with precision.
I implemented robust error handling to create directories and initialize empty files if they didn’t exist, ensuring seamless data persistence.

3. Exception Handling: No Crash Zone

I wrapped critical operations (file I/O, user input) in try-catch blocks to handle IOExceptions, NumberFormatExceptions, and more, making the application bulletproof.
Custom error messages guide users through invalid inputs (e.g., booking an already reserved seat), enhancing the user experience.

4. Collections Framework: Data Powerhouse

I used List<User>, List<Train>, and List<List<Integer>> (for seats) to manage dynamic data efficiently.
Stream API operations (e.g., filter, findFirst) in UserBookingService.loginUser and TrainService.searchTrains optimized data retrieval with elegant, functional-style code.

5. Security with BCrypt

I integrated the BCrypt library (org.mindrot:jbcrypt:0.4) to hash passwords securely, ensuring user credentials are protected.
The UserServiceUtil class demonstrates my ability to implement secure authentication, a critical feature for any real-world application.

6. Input/Output Handling

I mastered Java’s Scanner class to create a responsive console interface, handling both numeric and string inputs with care.
By consuming newlines (scanner.nextLine() after nextInt()), I eliminated common input bugs, ensuring a smooth user experience.

7. Modular Design and Dependency Management

The project is structured into packages (org.example.Services, org.example.entities, org.example.util) for clarity and scalability.
Gradle (build.gradle) manages dependencies and enforces JDK 21 compatibility, showcasing my ability to configure professional build systems.

Every feature in this project reflects a core Java concept mastered through hands-on practice, debugging, and iteration. From wrestling with JSON deserialization errors to perfecting user input validation, I transformed challenges into opportunities to grow as a Java developer.
🔥 Why This Project Showcases My Java Mastery
The Ticket Booking System isn’t just a working application—it’s a monument to my Java expertise. Here’s why this project screams “Java Master”:

Complexity Conquered: I built a multi-feature system with user authentication, data persistence, and real-time seat management, tackling real-world complexity with clean code.
Problem-Solving Prowess: From fixing input mismatches to resolving file path issues, I debugged and optimized the application to run flawlessly.
Scalable Design: The modular architecture (services, entities, utilities) makes it easy to add features like a GUI, database, or multi-user support.
Attention to Detail: User-friendly prompts, case-insensitive logins, and robust error handling show my commitment to a polished user experience.
Production-Ready Skills: Secure password hashing, JSON persistence, and Gradle configuration demonstrate my ability to build professional-grade software.

This project is proof that I don’t just know Java—I own it. Every line of code is a step in my journey to becoming a world-class Java developer.
🏗️ How to Run This Beast
Ready to see the Ticket Booking System in action? Follow these steps to run it on your machine:
Prerequisites

JDK 21 or later (I tested with JDK 24 for cutting-edge performance)
Gradle 8.10.2 (or let Gradle Wrapper handle it)
Git (to clone the repo)

Setup

Clone the Repository:
git clone https://github.com/AtharvaKumar/Ticket_Booking_System.git
cd Ticket_Booking_System


Prepare Data Files:

The data directory contains trains.json (train data) and users.json (user data).
If data/users.json is missing, the application creates an empty one ([]).
Ensure data/trains.json is populated (included in the repo).


Run the Application:
./gradlew run


Interact with the System:

Choose options 1–7 from the menu.
Try signing up, logging in, searching for trains (e.g., Bangalore to Delhi), booking a seat, and managing bookings.
Watch the magic unfold in the console! 😎



Example Workflow

Select 1 to sign up (e.g., username: atharva, password: honuman).
Select 2 to log in with the same credentials.
Select 4 to search trains (e.g., source: bangalore, destination: delhi).
Select 5 to book a seat (e.g., row 1, column 1).
Select 3 to view bookings or 6 to cancel one.

📚 Tech Stack: The Tools of a Java Titan

Java 21: The backbone of the project, leveraging modern Java features.
Jackson 2.17.2: For seamless JSON serialization/deserialization.
BCrypt 0.4: For secure password hashing.
Lombok 1.18.34: To reduce boilerplate (ready for future enhancements).
Gradle: For dependency management and build automation.
JSON Files: Lightweight, file-based persistence for users and trains.

🚀 Future Enhancements: The Sky’s the Limit
While the Ticket Booking System is a fully functional masterpiece, I’m always thinking ahead. Here are some exciting features I could add to take it to the next level:

GUI with JavaFX: Transform the console app into a sleek, visual interface.
Database Integration: Replace JSON files with SQLite or MySQL for scalability.
Multi-User Support: Add concurrent booking with file locking or a server.
Unit Tests: Implement JUnit tests for bulletproof reliability.
Dynamic Date Input: Prompt users for travel dates with format validation.
REST API: Expose booking features via a Spring Boot backend.

These ideas showcase my vision for scaling this project into a full-fledged, production-ready system.
🙌 Why You Should Care About This Project
If you’re a recruiter, fellow developer, or tech enthusiast, this project is a window into my skills and passion. It demonstrates:

Technical Mastery: Deep knowledge of Java’s core concepts, from OOP to file I/O.
Problem-Solving: Ability to debug complex issues (e.g., input handling, JSON errors).
Clean Code: Modular, readable, and maintainable code that follows best practices.
Real-World Application: A practical system that solves a tangible problem (train booking).
Growth Mindset: My journey from learning Java basics to building a feature-rich app.

This isn’t just a GitHub repo—it’s a story of dedication, learning, and coding excellence.



Built with 💪 by Atharva Kumar, Java Enthusiast and Future Tech Leader
