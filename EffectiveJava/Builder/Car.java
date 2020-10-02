package EffectiveJava.Builder;
// if the parameters are messy, use builder to create objects
public class Car {
    private String name;    // required
    private int price;  // required
    private enum Color {RED, BLUE, YELLOW};
    private Color color;    //alternative
    private int length;
    private int weight;
    private Car() {}
    private Car(Builder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.color = builder.color;
        this.weight = builder.weight;
        this.length = builder.length;
    }
    // accept a builder as parameter

    public static class Builder {
        private String name;    // required
        private int price;  // required
        private Car.Color color = Color.RED;    //alternative
        private int length = 10;
        private int weight = 10;
        public Builder setLength(int length) {
            this.length = length;
            return this;
        }
        public Builder setWeight(int weight) {
            this.weight = weight;
            return this;
        }
        public Car build() {
            return new Car(this);
        }
        public Builder(String name, int price) {
            this.name = name;
            this.price = price;
        }
    }
}
