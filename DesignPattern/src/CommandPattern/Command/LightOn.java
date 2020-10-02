package CommandPattern.Command;

import CommandPattern.Staff.Light;

public class LightOn implements Command {
    Light light;
    public LightOn(Light light) {
        this.light = light;
    }
    @Override
    public void execute() {
        light.turnOn();
    }

    @Override
    public void undo() {
        light.turnOff();
    }
}
