package TemplateMethodPattern;

public abstract class Duck {
    abstract void fly();
    abstract void quack();
    public void showItself() {
        // note that this method is a template method
        // allow sub-class to implement following methods
        fly();
        quack();
        hook();
    }
    boolean hook() {
        // sub-class might override this method
        return true;
    }
}
