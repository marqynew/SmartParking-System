

public class IoTSensor {
    private String id;
    private boolean occupied;
    
    public IoTSensor(String id) {
        this.id = id;
    }
    
    public void updateStatus(boolean occupied) {
        this.occupied = occupied;
        notifyStatusChange();
    }
    
    private void notifyStatusChange() {
        System.out.println("Sensor " + id + " status changed to: " + 
                          (occupied ? "occupied" : "vacant"));
    }
}
