package StrategyPattern;

public class WildDogBark implements BarkBehavior {
    @Override
    public void doBehavior() {
        System.out.println("Wild Dog is barking");
    }
}
