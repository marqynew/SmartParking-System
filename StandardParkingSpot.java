public class StandardParkingSpot extends ParkingSpot {
    public StandardParkingSpot(String id, String location, double basePrice) {
        super(id, location, basePrice);
    }
    
    @Override
    public double getPricePerHour() {
        return basePrice;
    }
}