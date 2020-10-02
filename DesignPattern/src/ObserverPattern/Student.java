package ObserverPattern;

public class Student implements Observer{
    Subject observable;
    public Student(Subject observable) {
        this.observable = observable;
        observable.registerObserver(this);
    }
    @Override
    public void update() {
        System.out.println("Got updated");
    }
}
