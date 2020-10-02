package AdaptorPattern;
// wrap a turkey to a duck object, the client will not aware of the fact that this duck is a turkey disguised.
public class AdaptorTest {
    public static void main(String[] args) {
        Duck fakeDuck = new TurkeyAdaptor(new RealTurkey());
        fakeDuck.fly();
        fakeDuck.quack();
    }
}
