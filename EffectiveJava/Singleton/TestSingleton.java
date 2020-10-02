package EffectiveJava.Singleton;

public class TestSingleton {
    public static void main(String[] args) {
        Singleton singleton = Singleton.SINGLETON;
        singleton.setValue(190);
        System.out.println(singleton.getValue());
    }
}
