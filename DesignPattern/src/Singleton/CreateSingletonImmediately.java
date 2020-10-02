package Singleton;
// another way to create a singleton make sure multi-processing safety is to create the object just after loaded in jvm
public class CreateSingletonImmediately {
    private static CreateSingletonImmediately singleton = new CreateSingletonImmediately();
    public String name;
    private CreateSingletonImmediately() {
        name = "eager";
    }
    private CreateSingletonImmediately getInstance() {
        return singleton;
    }
}
