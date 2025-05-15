
public class ParkingSpotFactory {
    public ParkingSpot createParkingSpot(String type, String id, String location, double basePrice) {
        switch (type) {
            case "PREMIUM":
                return new PremiumParkingSpot(id, location, basePrice);
            case "STANDARD":
            default:
                return new StandardParkingSpot(id, location, basePrice);
        }
    }
}