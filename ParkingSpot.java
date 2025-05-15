public abstract class ParkingSpot {
    protected double basePrice;
    protected String id;
    protected boolean occupied;
    protected String location;
    
    
    public ParkingSpot(String id, String location, double basePrice) {
        this.id = id;
        this.location = location;
        this.basePrice = basePrice;
        this.occupied = false;
    }
    
    public abstract double getPricePerHour();
    
    public boolean isOccupied() { return occupied; }
    public void setOccupied(boolean occupied) { this.occupied = occupied; }
    public String getLocation() { return location; }
    public String getId() { return id; }
}