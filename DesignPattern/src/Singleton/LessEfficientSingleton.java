package Singleton;
// use syn keyword to guarantee the single property in singleton
// However, synchronized function would slow down the processing
public class LessEfficientSingleton {
    private static LessEfficientSingleton singleton;
    private String singletonName;
    private LessEfficientSingleton(String name) {
        this.singletonName = name;
    }
    public synchronized LessEfficientSingleton getInstance(String name) {
        return new LessEfficientSingleton(name);
    }
}
