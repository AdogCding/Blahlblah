package StrategyPattern;
/*
This is an abstract object without any realisation of behavior that a real dog should perform.
In Strategy Pattern, algorithm's details should not be included in the client who use them.
 */
public abstract class Dog {
    private RunBehavior runBehavior;
    private BarkBehavior barkBehavior;
    public void setRunBehavior(RunBehavior runBehavior) {
        this.runBehavior = runBehavior;
    }
    public void setBarkBehavior(BarkBehavior barkBehavior) {
        this.barkBehavior = barkBehavior;
    }
    public void bark() {
        this.barkBehavior.doBehavior();
    }
    public void run() {
        this.runBehavior.doBehavior();
    }
}
