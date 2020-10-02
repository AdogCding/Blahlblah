package Singleton;
// use double check lock
public class DoubleCheckLock {
    private volatile static DoubleCheckLock singleton;
    private String name;
    private DoubleCheckLock(String name) {
        this.name = name;
    }
    public static DoubleCheckLock getInstance() {
        if (singleton == null) {
            synchronized (DoubleCheckLock.class) {
                if (singleton == null)
                    singleton = DoubleCheckLock("double check lock");
            }
        }
        return singleton;
    }
}
