package FactoryPattern;

public abstract class CarFactory {
    public Car orderCar(CarName name) {
        Car car;
        car = createCar(name);
        return car;
    }
    protected abstract Car createCar(CarName name);
}
