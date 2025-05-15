public class PaymentNotificationService implements ParkingObserver {
    @Override
    public void onPaymentStatusChanged(String reservationId, PaymentStatus status) {
        if (status == PaymentStatus.PAID) {
            System.out.println("Notifikasi: Pembayaran untuk reservasi " + reservationId + " telah berhasil");
            System.out.println("SMS dan Email notifikasi telah dikirim ke pengguna");
        }
    }
}