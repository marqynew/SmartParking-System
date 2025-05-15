import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static SmartParkingSystem parkingSystem;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {
        try {
            parkingSystem = SmartParkingSystem.getInstance();
            parkingSystem.addObserver(new PaymentNotificationService());
            boolean running = true;

            while (running) {
                showMainMenu();
                int choice = getIntInput("Pilih menu (1-5): ");

                switch (choice) {
                    case 1:
                        showAvailableSpots();
                        break;
                    case 2:
                        makeReservation();
                        break;
                    case 3:
                        processPayment();
                        break;
                    case 4:
                        showAllSpots();
                        break;
                    case 5:
                        running = false;
                        System.out.println("Terima kasih telah menggunakan Smart Parking System!");
                        break;
                    default:
                        System.out.println("Pilihan tidak valid!");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void showMainMenu() {
        System.out.println("\n=== SMART PARKING SYSTEM ===");
        System.out.println("1. Lihat Spot Parkir Tersedia");
        System.out.println("2. Buat Reservasi");
        System.out.println("3. Proses Pembayaran");
        System.out.println("4. Lihat Semua Spot");
        System.out.println("5. Keluar");
    }

    private static void showAvailableSpots() {
        System.out.println("\n=== Spot Parkir Tersedia ===");
        List<ParkingSpot> spots = parkingSystem.getAvailableSpots();
        if (spots.isEmpty()) {
            System.out.println("Maaf, tidak ada spot parkir yang tersedia saat ini.");
            return;
        }
        for (ParkingSpot spot : spots) {
            System.out.println("Spot: " + spot.getId() + " di " + spot.getLocation() + 
                             " (Rp." + spot.getPricePerHour() + "/jam)");
        }
    }

    private static void showAllSpots() {
        System.out.println("\n=== Semua Spot Parkir ===");
        for (ParkingSpot spot : parkingSystem.getAllSpots()) {
            System.out.println("Spot: " + spot.getId() + " di " + spot.getLocation() + 
                             " (Rp." + spot.getPricePerHour() + "/jam) - " + 
                             (spot.isOccupied() ? "Terisi" : "Kosong"));
        }
    }

    private static void makeReservation() {
        try {
            System.out.println("\n=== Buat Reservasi Baru ===");
            
            System.out.println("\nMasukkan data diri:");
            System.out.print("Nama: ");
            String name = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("No. Telepon: ");
            String phone = scanner.nextLine();

            String userId = "U" + System.currentTimeMillis();
            User user = new User(userId, name, email, phone);

            showAvailableSpots();
            System.out.print("\nPilih ID spot parkir: ");
            String spotId = scanner.nextLine();

            int duration = getIntInput("Durasi parkir (jam): ");
            
            LocalDateTime startTime = LocalDateTime.now();
            LocalDateTime endTime = startTime.plusHours(duration);

            System.out.println("\nDetail Reservasi:");
            System.out.println("Waktu Mulai: " + startTime.format(formatter));
            System.out.println("Waktu Selesai: " + endTime.format(formatter));

            Reservation reservation = parkingSystem.makeReservation(user, spotId, startTime, endTime);
            
            System.out.println("\nReservasi berhasil dibuat!");
            System.out.println("ID Reservasi: " + reservation.getId());
            System.out.println("Total Biaya: Rp." + reservation.calculateCost());

        } catch (Exception e) {
            System.out.println("Error membuat reservasi: " + e.getMessage());
        }
    }

    private static void processPayment() {
        try {
            System.out.println("\n=== Proses Pembayaran ===");
            System.out.print("Masukkan ID Reservasi: ");
            String reservationId = scanner.nextLine();

            System.out.println("\nMasukkan detail pembayaran:");
            System.out.print("Nomor Kartu: ");
            String cardNumber = scanner.nextLine();
            System.out.print("Tanggal Expired (MM/YY): ");
            String expiryDate = scanner.nextLine();
            System.out.print("CVV: ");
            String cvv = scanner.nextLine();

            PaymentInfo paymentInfo = new PaymentInfo(cardNumber, expiryDate, cvv);
            parkingSystem.processPayment(reservationId, paymentInfo);

        } catch (Exception e) {
            System.out.println("Error memproses pembayaran: " + e.getMessage());
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Mohon masukkan angka yang valid!");
            }
        }
    }
}