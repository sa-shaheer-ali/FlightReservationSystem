import java.util.*;
//ak flight ke class banev :)
class Flight {
    private String flightNumber;
    private String source;
    private String destination;
    private String date;
    private int availableSeats;
    private double fare;

    public Flight(String flightNumber, String source, String destination, String date, int availableSeats, double fare) {
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.availableSeats = availableSeats;
        this.fare = fare;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getDate() {
        return date;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getFare() {
        return fare;
    }
}
//user classs h
class User {
    private String username;
    private String password;
    private String email;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }
}
//another class
class BookingRequest {
    String username;
    String flightNumber;

    BookingRequest(String username, String flightNumber) {
        this.username = username;
        this.flightNumber = flightNumber;
    }
}
//kinda mainn class 
public class FlightReservationSystem {
    private List<Flight> flights;
    private Map<String, User> users; 
    private Queue<BookingRequest> bookingQueue;
    private Stack<BookingRequest> cancellationStack;

    public FlightReservationSystem() {
        flights = new ArrayList<>();
        users = new HashMap<>();
        bookingQueue = new LinkedList<>();
        cancellationStack = new Stack<>();
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public void registerUser(User user) {
        users.put(user.getUsername(), user);
    }

    public void submitBookingRequest(String username, String flightNumber) {
        bookingQueue.offer(new BookingRequest(username, flightNumber));
    }

    public void processBookingRequests() {
        while (!bookingQueue.isEmpty()) {
            BookingRequest request = bookingQueue.poll();
            boolean success = bookFlight(request.username, request.flightNumber);
            if (success) {
                System.out.println("Booking for " + request.username + " on flight " + request.flightNumber + " succeeded.");
            } else {
                System.out.println("Booking for " + request.username + " on flight " + request.flightNumber + " failed. Seats not available or invalid flight.");
            }
        }
    }

    public void submitCancellationRequest(String username, String flightNumber) {
        cancellationStack.push(new BookingRequest(username, flightNumber));
    }

    public void processCancellationRequests() {
        while (!cancellationStack.isEmpty()) {
            BookingRequest request = cancellationStack.pop();
            boolean success = cancelBooking(request.username, request.flightNumber);
            if (success) {
                System.out.println("Cancellation for " + request.username + " on flight " + request.flightNumber + " succeeded.");
            } else {
                System.out.println("Cancellation for " + request.username + " on flight " + request.flightNumber + " failed. Flight not found or user not registered.");
            }
        }
    }

    private boolean bookFlight(String username, String flightNumber) {
        User user = users.get(username);
        Flight flight = findFlightByNumber(flightNumber);
        if (user != null && flight != null && flight.getAvailableSeats() > 0) {
            flight.setAvailableSeats(flight.getAvailableSeats() - 1);
            return true;
        }
        return false;
    }

    private boolean cancelBooking(String username, String flightNumber) {
        User user = users.get(username);
        Flight flight = findFlightByNumber(flightNumber);
        if (user != null && flight != null) {
            flight.setAvailableSeats(flight.getAvailableSeats() + 1);
            return true;
        }
        return false;
    }

    public List<Flight> searchFlights(String source, String destination, String date) {
        List<Flight> result = new ArrayList<>();
        for (Flight flight : flights) { 
            if (flight.getSource().equals(source) &&
                flight.getDestination().equals(destination) &&
                flight.getDate().equals(date)) {
                result.add(flight); 
            }
        }
        return result;
    }

    private Flight findFlightByNumber(String flightNumber) {
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                return flight; 
            }
        }
        return null; 
    }
//yahan p sara menu hora
    public static void main(String[] args) {
        FlightReservationSystem system = new FlightReservationSystem();
        Scanner scanner = new Scanner(System.in);

      
        system.addFlight(new Flight("AI101", "New York", "Los Angeles", "2024-12-15", 150, 500.00));
        system.addFlight(new Flight("DL202", "Chicago", "Miami", "2024-12-15", 120, 350.00));
        system.addFlight(new Flight("PIA911", "Karachi", "Barcelona", "2024-12-17", 4, 5350.00));
        system.addFlight(new Flight("QA402", "Lahore", "Rome", "2024-12-28", 15, 1350.00));

       
        system.registerUser(new User("user1", "password14", "user1@gmail.com"));
        system.registerUser(new User("user2", "password32", "user2@hotmail.com"));
        system.registerUser(new User("vini", "papaparez", "cry@example.com"));
        system.registerUser(new User("Lesnar", "romanreigns", "paulheiman@example.com"));

       
        while (true) {
            System.out.println("\n1. Book Flight");
            System.out.println("2. Cancel Booking");
            System.out.println("3. Search Flights");
            System.out.println("4. Exit");
            System.out.println("5. Add user");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter flight number: ");
                    String flightNumber = scanner.nextLine();
                    system.submitBookingRequest(username, flightNumber);
                    system.processBookingRequests();
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    String cancelUsername = scanner.nextLine();
                    System.out.print("Enter flight number: ");
                    String cancelFlightNumber = scanner.nextLine();
                    system.submitCancellationRequest(cancelUsername, cancelFlightNumber);
                    system.processCancellationRequests();
                    break;
                case 3:
                    System.out.print("Enter source: ");
                    String source = scanner.nextLine();
                    System.out.print("Enter destination: ");
                    String destination = scanner.nextLine();
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    List<Flight> flights = system.searchFlights(source, destination, date);
                    System.out.println("Available flights:");
                    for (Flight flight : flights) {
                        System.out.println("Flight: " + flight.getFlightNumber() + " | Fare: $" + flight.getFare() + " | Seats available: " + flight.getAvailableSeats());
                    }
                    break;
                case 4:
                    System.exit(0);
                    break;
                case 5:
                System.out.println("enter your name:");
                String name=scanner.nextLine();
                System.out.println("enter your password:");
                String password=scanner.nextLine();
                System.out.println("enter your mail:");
                String mail=scanner.nextLine();
                User user=new User(name, password, mail);
                system.registerUser(user);
                System.out.println("the user "+name +" has been added.");
                break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}