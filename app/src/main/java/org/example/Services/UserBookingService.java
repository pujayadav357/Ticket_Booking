package org.example.Services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Ticket;
import org.example.entities.Train;
import org.example.entities.User;
import org.example.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserBookingService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<User> userList;
    private User user;
    private static final String USER_FILE_PATH = "data/users.json";

    public UserBookingService() throws IOException {
        loadUserListFromFile();
    }

    public UserBookingService(User user) throws IOException {
        this.user = user;
        loadUserListFromFile();
    }

    public User getUser() {
        return user;
    }

    private void loadUserListFromFile() throws IOException {
        System.out.println("Loading user list from: " + USER_FILE_PATH);
        File file = new File(USER_FILE_PATH);
        try {
            if (file.exists()) {
                userList = objectMapper.readValue(file, new TypeReference<List<User>>() {});
                System.out.println("Loaded " + userList.size() + " users");
            } else {
                System.out.println("User file not found at " + file.getAbsolutePath() + ". Initializing empty user list.");
                userList = new ArrayList<>();
            }
        } catch (IOException ex) {
            System.err.println("Error loading user list: " + ex.getMessage());
            throw ex;
        }
    }

    public Boolean loginUser() {
        System.out.println("Attempting login for user: " + user.getName());
        Optional<User> foundUser = userList.stream().filter(user1 ->
                        user1.getName().equalsIgnoreCase(user.getName()) &&
                                UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword()))
                .findFirst();
        if (foundUser.isPresent()) {
            this.user = foundUser.get();
            System.out.println("Login successful for user: " + user.getName());
            return true;
        }
        System.out.println("Login failed: No matching user found");
        return false;
    }

    public Boolean signUp(User user1) throws IOException {
        System.out.println("Attempting signup for user: " + user1.getName());
        try {
            if (userList.stream().anyMatch(u -> u.getName().equalsIgnoreCase(user1.getName()))) {
                System.out.println("User already exists: " + user1.getName());
                return false;
            }
            userList.add(user1);
            saveUserList();
            System.out.println("User data saved to: " + new File(USER_FILE_PATH).getAbsolutePath());
            return true;
        } catch (IOException ex) {
            System.err.println("Error saving user data: " + ex.getMessage());
            throw ex;
        }
    }

    public void saveUserList() throws IOException {
        File outputFile = new File(USER_FILE_PATH);
        try {
            outputFile.getParentFile().mkdirs();
            objectMapper.writeValue(outputFile, userList);
        } catch (IOException ex) {
            System.err.println("Error saving user list: " + ex.getMessage());
            throw ex;
        }
    }

    public void fetchBookings() {
        if (user == null) {
            System.out.println("No user logged in.");
            return;
        }
        Optional<User> userFetched = userList.stream().filter(user1 ->
                        user1.getName().equalsIgnoreCase(user.getName()) &&
                                UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword()))
                .findFirst();
        if (userFetched.isPresent()) {
            List<Ticket> tickets = userFetched.get().getTicketsBooked();
            if (tickets.isEmpty()) {
                System.out.println("No bookings found.");
            } else {
                tickets.forEach(ticket -> System.out.println(ticket.getTicketInfo()));
            }
        } else {
            System.out.println("User not found or invalid credentials.");
        }
    }

    public Boolean cancelBooking(String ticketId) {
        if (user == null) {
            System.out.println("No user logged in.");
            return false;
        }
        if (ticketId == null || ticketId.isEmpty()) {
            System.out.println("Ticket ID cannot be empty.");
            return false;
        }
        try {
            boolean removed = user.getTicketsBooked().removeIf(ticket -> ticket.getTicketId().equals(ticketId));
            if (removed) {
                saveUserList();
                System.out.println("Ticket with ID " + ticketId + " canceled.");
                return true;
            }
            System.out.println("No ticket found with ID " + ticketId);
            return false;
        } catch (IOException ex) {
            System.err.println("Error saving user data: " + ex.getMessage());
            return false;
        }
    }

    public List<Train> getTrains(String source, String destination) {
        try {
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source, destination);
        } catch (IOException ex) {
            System.err.println("Error fetching trains: " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    public List<List<Integer>> fetchSeats(Train train) {
        return train.getSeats();
    }

    public Boolean bookTrainSeat(Train train, int row, int seat, String source, String dest, String dateOfTravel) {
        if (user == null) {
            System.out.println("No user logged in.");
            return false;
        }
        try {
            TrainService trainService = new TrainService();
            List<List<Integer>> seats = train.getSeats();
            if (row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
                if (seats.get(row).get(seat).equals(0)) {
                    seats.get(row).set(seat, 1);
                    train.setSeats(seats);
                    trainService.addTrain(train);
                    Ticket ticket = new Ticket(
                            UUID.randomUUID().toString(),
                            user.getUserId(),
                            source,
                            dest,
                            dateOfTravel,
                            train
                    );
                    user.getTicketsBooked().add(ticket);
                    saveUserList();
                    return true;
                }
                System.out.println("Seat already booked.");
                return false;
            }
            System.out.println("Invalid seat selection.");
            return false;
        } catch (IOException ex) {
            System.err.println("Error booking seat: " + ex.getMessage());
            return false;
        }
    }
}