package Singleton;
// not compatible for multi-process
public class ClassicSingleton {
    private static ClassicSingleton classicSingleton;
    private String singletonName;
    private ClassicSingleton(String name) {
        this.singletonName = name;
    }
    public static ClassicSingleton getInstance(String name) {
        if (classicSingleton == null) {
            classicSingleton = new ClassicSingleton(name);
        }
        return classicSingleton;
    }

    public String getSingletonName() {
        return singletonName;
    }

    public static void main(String[] args) {
        ClassicSingleton singleton = ClassicSingleton.getInstance("newInstance");
        System.out.println(singleton.getSingletonName());
    }
}
