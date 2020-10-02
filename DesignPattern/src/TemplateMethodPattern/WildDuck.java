package TemplateMethodPattern;

public class WildDuck extends Duck{

    @Override
    void fly() {
        System.out.println("I always fly");
    }

    @Override
    void quack() {
        System.out.println("I am a free duck");
    }
}
