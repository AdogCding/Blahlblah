package TemplateMethodPattern;

public class DomesticDuck extends Duck {
    @Override
    void fly() {
        System.out.println("I usually don't fly");
    }

    @Override
    void quack() {
        System.out.println("I am a happy duck");
    }
}
