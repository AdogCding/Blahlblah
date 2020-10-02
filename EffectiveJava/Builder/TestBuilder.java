package EffectiveJava.Builder;

import EffectiveJava.Builder.Car;

public class TestBuilder {
    public static void main(String[] args) {
        Car car = new Car.Builder("HP", 12).setLength(111).setWeight(1111).build();
    }
}
