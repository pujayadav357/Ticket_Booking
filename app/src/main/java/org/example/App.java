package org.example;

import org.example.Services.UserBookingService;
import org.example.entities.Train;
import org.example.entities.User;
import org.example.util.UserServiceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class App {
    private static String source = null;
    private static String dest = null;

    public static void main(String[] args) {
        System.out.println("Running Train Booking System");
        System.out.println("Working directory: " + System.getProperty("user.dir"));
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService;
        Train trainSelectedForBooking = null;
        try {
            userBookingService = new UserBookingService();
        } catch (IOException ex) {
            System.err.println("Error initializing service: " + ex.getMessage());
            ex.printStackTrace();
            scanner.close();
            return;
        }
        while (option != 7) {
            System.out.println("Choose option:");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a Seat");
            System.out.println("6. Cancel my Booking");
            System.out.println("7. Exit the App");
            System.out.print("Enter your choice: ");
            // Read input as a string to handle non-integer input safely
            String input = scanner.nextLine().trim();
            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
                continue;
            }
            switch (option) {
                case 1:
                    System.out.println("Enter the username to signup");
                    String nameToSignUp = scanner.nextLine().trim();
                    System.out.println("Enter the password to signup");
                    String passwordToSignUp = scanner.nextLine().trim();
                    if (nameToSignUp.isEmpty() || passwordToSignUp.isEmpty()) {
                        System.out.println("Username and password cannot be empty.");
                        break;
                    }
                    User userToSignup = new User(nameToSignUp, passwordToSignUp,
                            UserServiceUtil.hashPassword(passwordToSignUp), new ArrayList<>(), UUID.randomUUID().toString());
                    try {
                        if (userBookingService.signUp(userToSignup)) {
                            System.out.println("Sign up successful! You can now log in.");
                            userBookingService = new UserBookingService(userToSignup);
                        } else {
                            System.out.println("Sign up failed!");
                        }
                    } catch (IOException ex) {
                        System.err.println("Error during signup: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.println("Enter the username to Login");
                    String nameToLogin = scanner.nextLine().trim();
                    System.out.println("Enter the password to Login");
                    String passwordToLogin = scanner.nextLine().trim();
                    if (nameToLogin.isEmpty() || passwordToLogin.isEmpty()) {
                        System.out.println("Username and password cannot be empty.");
                        break;
                    }
                    User userToLogin = new User(nameToLogin, passwordToLogin,
                            UserServiceUtil.hashPassword(passwordToLogin), new ArrayList<>(), UUID.randomUUID().toString());
                    try {
                        userBookingService = new UserBookingService(userToLogin);
                        if (userBookingService.loginUser()) {
                            System.out.println("Login successful!");
                        } else {
                            System.out.println("Login failed: Invalid username or password");
                            userBookingService = new UserBookingService();
                        }
                    } catch (IOException ex) {
                        System.err.println("Error during login: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                    break;
                case 3:
                    System.out.println("Fetching your bookings");
                    if (userBookingService.getUser() == null) {
                        System.out.println("Please log in first.");
                        break;
                    }
                    userBookingService.fetchBookings();
                    break;
                case 4:
                    System.out.println("Type your source station");
                    source = scanner.nextLine().toLowerCase().trim();
                    System.out.println("Type your destination station");
                    dest = scanner.nextLine().toLowerCase().trim();
                    if (source.isEmpty() || dest.isEmpty()) {
                        System.out.println("Source and destination cannot be empty.");
                        source = null;
                        dest = null;
                        trainSelectedForBooking = null;
                        break;
                    }
                    List<Train> trains = userBookingService.getTrains(source, dest);
                    if (trains.isEmpty()) {
                        System.out.println("No trains found for the given route.");
                        trainSelectedForBooking = null;
                        source = null;
                        dest = null;
                        break;
                    }
                    int index = 1;
                    for (Train t : trains) {
                        System.out.println("Train " + index + ": ID " + t.getTrainId() + ", No: " + t.getTrainNo());
                        for (Map.Entry<String, String> entry : t.getStationTimes().entrySet()) {
                            System.out.println("  Station: " + entry.getKey() + ", Time: " + entry.getValue());
                        }
                        index++;
                    }
                    System.out.println("Select a train by number (1, 2, 3...):");
                    try {
                        int trainIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (trainIndex >= 0 && trainIndex < trains.size()) {
                            trainSelectedForBooking = trains.get(trainIndex);
                        } else {
                            System.out.println("Invalid train selection.");
                            trainSelectedForBooking = null;
                            source = null;
                            dest = null;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        trainSelectedForBooking = null;
                        source = null;
                        dest = null;
                    }
                    break;
                case 5:
                    if (trainSelectedForBooking == null || source == null || dest == null) {
                        System.out.println("No train selected or source/destination not set. Please search for a train first.");
                        break;
                    }
                    if (userBookingService.getUser() == null) {
                        System.out.println("Please log in first.");
                        break;
                    }
                    System.out.println("Select a seat out of these seats (0 = available, 1 = booked)");
                    List<List<Integer>> seats = userBookingService.fetchSeats(trainSelectedForBooking);
                    for (int i = 0; i < seats.size(); i++) {
                        System.out.println("Row " + (i + 1) + ": " + seats.get(i));
                    }
                    System.out.println("Enter the row number");
                    int row;
                    try {
                        row = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid row input.");
                        break;
                    }
                    System.out.println("Enter the column number");
                    int col;
                    try {
                        col = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid column input.");
                        break;
                    }
                    System.out.println("Enter the date of travel (e.g., 2023-12-08T18:30:00Z)");
                    String dateOfTravel = scanner.nextLine().trim();
                    if (dateOfTravel.isEmpty()) {
                        System.out.println("Date of travel cannot be empty.");
                        break;
                    }
                    System.out.println("Booking your seat...");
                    try {
                        Boolean booked = userBookingService.bookTrainSeat(trainSelectedForBooking, row - 1, col - 1, source, dest, dateOfTravel);
                        if (booked) {
                            System.out.println("Booked! Enjoy your journey");
                        } else {
                            System.out.println("Cannot book this seat.");
                        }
                    } catch (Exception ex) {
                        System.err.println("Error booking seat: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                    break;
                case 6:
                    if (userBookingService.getUser() == null) {
                        System.out.println("Please log in first.");
                        break;
                    }
                    System.out.println("Enter the ticket ID to cancel");
                    String ticketId = scanner.nextLine().trim();
                    if (ticketId.isEmpty()) {
                        System.out.println("Ticket ID cannot be empty.");
                        break;
                    }
                    userBookingService.cancelBooking(ticketId);
                    break;
                case 7:
                    System.out.println("Exiting the application.");
                    break;
                default:
                    System.out.println("Invalid option. Please choose between 1 and 7.");
                    break;
            }
        }
        scanner.close();
    }
}