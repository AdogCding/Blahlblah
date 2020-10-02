package StrategyPattern;

public class WildDogRun implements RunBehavior {

    @Override
    public void doBehavior() {
        System.out.println("Wild dog run");
    }
}
