package AdaptorPattern;

public class RealTurkey implements Turkey {
    @Override
    public void gobble() {
        System.out.println("Gobbling");
    }

    @Override
    public void fly() {
        System.out.println("I fly a short distance");
    }
}
