import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Reservation {
    private String id;
    private User user;
    private ParkingSpot parkingSpot;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private PaymentStatus paymentStatus;
    
    public Reservation(String id, User user, ParkingSpot parkingSpot, 
                      LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.user = user;
        this.parkingSpot = parkingSpot;
        this.startTime = startTime;
        this.endTime = endTime;
        this.paymentStatus = PaymentStatus.PENDING;
    }
    
    public double calculateCost() {
        long hours = ChronoUnit.HOURS.between(startTime, endTime);
        return hours * parkingSpot.getPricePerHour();
    }
    
    public String getId() { return id; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public User getUser() { return user; }
    public void setPaymentStatus(PaymentStatus status) { this.paymentStatus = status; }
}