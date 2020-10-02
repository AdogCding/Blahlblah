package ObserverPattern;

public class PatternTest {
    public static void main(String[] args) {
        Teacher teacher = new Teacher();
        Student student = new Student(teacher);
        teacher.notifyObserver();
    }
}
