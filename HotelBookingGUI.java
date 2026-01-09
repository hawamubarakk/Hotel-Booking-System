import javax.swing.*;
import java.util.*;

// ---------------- ROOM CLASS ----------------
class Room {
    int number;
    String type;
    boolean isClean;

    public Room(int number, String type, boolean isClean) {
        this.number = number;
        this.type = type;
        this.isClean = isClean;
    }
}

// ---------------- BOOKING CLASS ----------------
class Booking {
    int id;
    String user;
    String hotelName;
    Room room;
    String checkInDate;
    String checkOutDate;
    String status;
    String paymentStatus;

    public Booking(int id, String user, String hotelName, Room room,
                   String checkInDate, String checkOutDate,
                   String status, String paymentStatus) {
        this.id = id;
        this.user = user;
        this.hotelName = hotelName;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
        this.paymentStatus = paymentStatus;
    }
}

// ---------------- USER ACCOUNT ----------------
class UserAccount {
    String email;
    String password;
    String role;

    public UserAccount(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}

// ================= MAIN CLASS =================
public class HotelBookingGUI extends JFrame {

    static List<Room> roomList = new ArrayList<>();
    static List<Booking> bookings = new ArrayList<>();
    static List<UserAccount> accounts = new ArrayList<>();
    static int nextBookingId = 205;

    JTextField emailField;
    JPasswordField passwordField;

    // ---------------- BUBBLE SORT ----------------
    public static void bubbleSortRooms() {
        for (int i = 0; i < roomList.size() - 1; i++) {
            for (int j = 0; j < roomList.size() - i - 1; j++) {
                if (roomList.get(j).number > roomList.get(j + 1).number) {
                    Room temp = roomList.get(j);
                    roomList.set(j, roomList.get(j + 1));
                    roomList.set(j + 1, temp);
                }
            }
        }
    }

    // ---------------- BINARY SEARCH ----------------
    public static Room binarySearchRoom(int roomNumber) {
        int low = 0, high = roomList.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (roomList.get(mid).number == roomNumber)
                return roomList.get(mid);
            else if (roomList.get(mid).number < roomNumber)
                low = mid + 1;
            else
                high = mid - 1;
        }
        return null;
    }

    // ---------------- LOGIN ----------------
    public HotelBookingGUI() {
        setTitle("Hotel Booking Login");
        setSize(350, 200);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel emailLbl = new JLabel("Email:");
        emailLbl.setBounds(30, 30, 80, 25);
        add(emailLbl);

        emailField = new JTextField();
        emailField.setBounds(100, 30, 200, 25);
        add(emailField);

        JLabel passLbl = new JLabel("Password:");
        passLbl.setBounds(30, 70, 80, 25);
        add(passLbl);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 70, 200, 25);
        add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(120, 110, 100, 30);
        add(loginBtn);

        loginBtn.addActionListener(e -> {
            for (UserAccount acc : accounts) {
                if (acc.email.equals(emailField.getText()) &&
                        acc.password.equals(new String(passwordField.getPassword()))) {

                    dispose();
                    if (acc.role.equalsIgnoreCase("Admin"))
                        new AdminMenu().setVisible(true);
                    else
                        new UserMenu(acc.email).setVisible(true);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Invalid Credentials");
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ================= ADMIN MENU =================
    class AdminMenu extends JFrame {
        AdminMenu() {
            setTitle("Admin Menu");
            setSize(400, 300);
            setLayout(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            JButton addRoom = new JButton("Add Room");
            JButton viewRooms = new JButton("View Rooms");
            JButton viewBookings = new JButton("View Bookings");
            JButton cleanRoom = new JButton("Mark Room Clean");
            JButton exit = new JButton("Exit");

            addRoom.setBounds(100, 20, 200, 30);
            viewRooms.setBounds(100, 60, 200, 30);
            viewBookings.setBounds(100, 100, 200, 30);
            cleanRoom.setBounds(100, 140, 200, 30);
            exit.setBounds(100, 180, 200, 30);

            add(addRoom); add(viewRooms); add(viewBookings);
            add(cleanRoom); add(exit);

            // Add Room
            addRoom.addActionListener(e -> {
                try {
                    int num = Integer.parseInt(JOptionPane.showInputDialog("Room Number:"));
                    String type = JOptionPane.showInputDialog("Room Type:");
                    roomList.add(new Room(num, type, true));
                    JOptionPane.showMessageDialog(this, "Room Added");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Input");
                }
            });

            // View Rooms
            viewRooms.addActionListener(e -> {
                bubbleSortRooms();
                StringBuilder sb = new StringBuilder();
                for (Room r : roomList)
                    sb.append("Room ").append(r.number)
                            .append(" | ").append(r.type)
                            .append(" | Clean: ").append(r.isClean).append("\n");

                JOptionPane.showMessageDialog(this,
                        sb.length() == 0 ? "No Rooms" : sb.toString());
            });

            // View Bookings
            viewBookings.addActionListener(e -> {
                StringBuilder sb = new StringBuilder();
                for (Booking b : bookings)
                    sb.append("ID: ").append(b.id)
                            .append(" | User: ").append(b.user)
                            .append(" | Room: ").append(b.room.number)
                            .append(" | Hotel: ").append(b.hotelName)
                            .append("\n");

                JOptionPane.showMessageDialog(this,
                        sb.length() == 0 ? "No Bookings" : sb.toString());
            });

            // Clean Room
            cleanRoom.addActionListener(e -> {
                try {
                    int num = Integer.parseInt(JOptionPane.showInputDialog("Room Number:"));
                    bubbleSortRooms();
                    Room r = binarySearchRoom(num);

                    if (r != null && !r.isClean) {
                        r.isClean = true;
                        JOptionPane.showMessageDialog(this, "Room Cleaned");
                    } else {
                        JOptionPane.showMessageDialog(this, "Room Not Found / Already Clean");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Input");
                }
            });

            exit.addActionListener(e -> System.exit(0));
            setLocationRelativeTo(null);
        }
    }

    // ================= USER MENU =================
    class UserMenu extends JFrame {
        UserMenu(String email) {
            setTitle("User Menu");
            setSize(400, 300);
            setLayout(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            JButton book = new JButton("Book Room");
            JButton view = new JButton("My Bookings");
            JButton pay = new JButton("Payment Status");
            JButton exit = new JButton("Exit");

            book.setBounds(100, 20, 200, 30);
            view.setBounds(100, 60, 200, 30);
            pay.setBounds(100, 100, 200, 30);
            exit.setBounds(100, 140, 200, 30);

            add(book); add(view); add(pay); add(exit);

            // -------- BOOK ROOM WITH PREDEFINED HOTELS --------
            book.addActionListener(e -> {
                bubbleSortRooms();
                ArrayList<Room> available = new ArrayList<>();
                for (Room r : roomList)
                    if (r.isClean) available.add(r);

                if (available.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No Rooms Available");
                    return;
                }

                // Room selection
                String[] roomOptions = new String[available.size()];
                for (int i = 0; i < available.size(); i++)
                    roomOptions[i] = "Room " + available.get(i).number + " (" + available.get(i).type + ")";

                String selectedRoomStr = (String) JOptionPane.showInputDialog(
                        this, "Select Room", "Room Booking",
                        JOptionPane.QUESTION_MESSAGE, null, roomOptions, roomOptions[0]);
                if (selectedRoomStr == null) return;

                int roomIndex = Arrays.asList(roomOptions).indexOf(selectedRoomStr);
                Room selectedRoom = available.get(roomIndex);

                // Predefined hotels
                String[] hotels = {"Hilton", "Marriott", "Hyatt"};
                String hotel = (String) JOptionPane.showInputDialog(
                        this, "Select Hotel", "Hotel Selection",
                        JOptionPane.QUESTION_MESSAGE, null, hotels, hotels[0]);
                if (hotel == null) return;

                // Dates input
                String checkIn = JOptionPane.showInputDialog("Check-In Date (YYYY-MM-DD):");
                if (checkIn == null || checkIn.isEmpty()) return;

                String checkOut = JOptionPane.showInputDialog("Check-Out Date (YYYY-MM-DD):");
                if (checkOut == null || checkOut.isEmpty()) return;

                Booking b = new Booking(nextBookingId++, email, hotel, selectedRoom, checkIn, checkOut, "Confirmed", "Unpaid");
                bookings.add(b);
                selectedRoom.isClean = false;

                JOptionPane.showMessageDialog(this, "Booking Successful!\nBooking ID: " + b.id);
            });

            // View My Bookings
            view.addActionListener(e -> {
                StringBuilder sb = new StringBuilder();
                for (Booking b : bookings) {
                    if (b.user.equals(email)) {
                        sb.append("ID: ").append(b.id)
                                .append("\nHotel: ").append(b.hotelName)
                                .append("\nRoom: ").append(b.room.number)
                                .append("\nCheck-In: ").append(b.checkInDate)
                                .append("\nCheck-Out: ").append(b.checkOutDate)
                                .append("\n----------------\n");
                    }
                }
                JOptionPane.showMessageDialog(this,
                        sb.length() == 0 ? "No Bookings" : sb.toString());
            });

            // Payment Status
            pay.addActionListener(e -> {
                StringBuilder sb = new StringBuilder();
                for (Booking b : bookings)
                    if (b.user.equals(email))
                        sb.append("Booking ").append(b.id)
                                .append(" | Payment: ").append(b.paymentStatus).append("\n");

                JOptionPane.showMessageDialog(this,
                        sb.length() == 0 ? "No Payments" : sb.toString());
            });

            exit.addActionListener(e -> System.exit(0));
            setLocationRelativeTo(null);
        }
    }

    // ---------------- INITIAL DATA ----------------
    public static void initializeData() {
        roomList.add(new Room(101, "Single", true));
        roomList.add(new Room(102, "Double", true));
        roomList.add(new Room(103, "Suite", true));

        accounts.add(new UserAccount("admin@hotel.com", "admin123", "Admin"));
        accounts.add(new UserAccount("user@hotel.com", "user123", "User"));
    }

    public static void main(String[] args) {
        initializeData();
        SwingUtilities.invokeLater(HotelBookingGUI::new);
    }
}
