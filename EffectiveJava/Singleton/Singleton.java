package EffectiveJava.Singleton;
// use enum type as a singleton would prevent singleton from "reflect attack" && serializable
// as the field in enum is default to be static and unable to be serialized.
public enum Singleton {
    SINGLETON;
    int value;
    public void setValue(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
