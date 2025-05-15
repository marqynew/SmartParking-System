import java.time.*;
import java.util.*;

public class SmartParkingSystem {
    private List<ParkingObserver> observers;
    private static SmartParkingSystem instance;
    private Map<String, ParkingSpot> parkingSpots;
    private Map<String, Reservation> reservations;
    
    
    private SmartParkingSystem() {
        this.parkingSpots = new HashMap<>();
        this.reservations = new HashMap<>();
        this.observers = new ArrayList<>();
        initializeParkingSpots();
    }
    
    public static SmartParkingSystem getInstance() {
        if (instance == null) {
            instance = new SmartParkingSystem();
        }
        return instance;
    }
    
    private void initializeParkingSpots() {
        ParkingSpotFactory factory = new ParkingSpotFactory();
        addParkingSpot(factory.createParkingSpot("STANDARD", "A1", "Lantai 1 - A1", 5000.0));
        addParkingSpot(factory.createParkingSpot("STANDARD", "A2", "Lantai 1 - A2", 5000.0));
        addParkingSpot(factory.createParkingSpot("PREMIUM", "B1", "Lantai 1 - B1", 6000.0));
        addParkingSpot(factory.createParkingSpot("PREMIUM", "B2", "Lantai 1 - B2", 6000.0));
    }
    
    private void addParkingSpot(ParkingSpot spot) {
        parkingSpots.put(spot.getId(), spot);
    }
    
    public void addObserver(ParkingObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(ParkingObserver observer) {
        observers.remove(observer);
    }
    
    private void notifyObservers(String reservationId, PaymentStatus status) {
        for (ParkingObserver observer : observers) {
            observer.onPaymentStatusChanged(reservationId, status);
        }
    }
    
    public List<ParkingSpot> getAvailableSpots() {
        List<ParkingSpot> availableSpots = new ArrayList<>();
        for (ParkingSpot spot : parkingSpots.values()) {
            if (!spot.isOccupied()) {
                availableSpots.add(spot);
            }
        }
        return availableSpots;
    }
    
    public List<ParkingSpot> getAllSpots() {
        return new ArrayList<>(parkingSpots.values());
    }
    
    public Reservation makeReservation(User user, String parkingSpotId, 
                                     LocalDateTime startTime, LocalDateTime endTime) 
            throws Exception {
        ParkingSpot spot = parkingSpots.get(parkingSpotId);
        if (spot == null || spot.isOccupied()) {
            throw new Exception("Spot parkir tidak tersedia");
        }
        
        String reservationId = "RES" + System.currentTimeMillis();
        Reservation reservation = new Reservation(reservationId, user, spot, startTime, endTime);
        reservations.put(reservationId, reservation);
        spot.setOccupied(true);
        
        return reservation;
    }
    
    public void processPayment(String reservationId, PaymentInfo paymentInfo) 
            throws Exception {
        Reservation reservation = reservations.get(reservationId);
        if (reservation == null) {
            throw new Exception("Reservasi tidak ditemukan");
        }
        
        System.out.println("Memproses pembayaran sebesar Rp." + reservation.calculateCost());
        reservation.setPaymentStatus(PaymentStatus.PAID);
        notifyObservers(reservationId, PaymentStatus.PAID);
        System.out.println("Pembayaran berhasil");
    }
}
