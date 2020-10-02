package StrategyPattern;
// this is a real object
public class WildDog extends Dog{
    public WildDog() {
        this.setBarkBehavior(new WildDogBark());
        this.setRunBehavior(new WildDogRun());
    }
}
