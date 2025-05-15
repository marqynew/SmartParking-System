public interface ParkingObserver {
    void onPaymentStatusChanged(String reservationId, PaymentStatus status);
}