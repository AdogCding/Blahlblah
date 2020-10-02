package AdaptorPattern;

public class DomesticDuck implements Duck {

    @Override
    public void fly() {
        System.out.println("duck is flying");
    }

    @Override
    public void quack() {
        System.out.println("Quack");
    }
}
