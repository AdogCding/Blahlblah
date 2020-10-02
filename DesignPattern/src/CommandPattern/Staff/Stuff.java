package CommandPattern.Staff;

public abstract class Stuff {
    private Boolean isOn;
    public void turnOn() {
        this.isOn = true;
    }
    public void turnOff() {
        this.isOn = false;
    }
}
