package AdaptorPattern;

// Adaptor which turn a duck into a turkey
public class TurkeyAdaptor implements Duck {
    Turkey turkey;
    public TurkeyAdaptor(Turkey turkey){
        this.turkey = turkey;
    }
    @Override
    public void fly() {
        turkey.fly();
    }

    @Override
    public void quack() {
        turkey.gobble();
    }
}
