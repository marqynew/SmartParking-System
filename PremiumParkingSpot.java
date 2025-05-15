public class PremiumParkingSpot extends ParkingSpot {
    public PremiumParkingSpot(String id, String location, double basePrice) {
        super(id, location, basePrice);
    }
    
    @Override
    public double getPricePerHour() {
        return basePrice * 1.2; 
    }
}
