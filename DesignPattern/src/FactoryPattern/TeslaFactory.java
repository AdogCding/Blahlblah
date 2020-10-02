package FactoryPattern;

public class TeslaFactory extends CarFactory {

    @Override
    protected Car createCar(CarName name) {
        return new Tesla();
    }
}
