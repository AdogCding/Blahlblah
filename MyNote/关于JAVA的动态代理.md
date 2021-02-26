代理模式是为了解决软件设计原则中的开闭原则而引入的，例如有一个类，已经存在，但是内容不可以修改，要想给这个类添加更多的功能，使用代理是一个比较合适的选择。
动态代理与静态代理不同的是，在运行时生成一个代理类。
例如，我们有一个类，EnglishGreeting，作用为

1. 打招呼
2. 说再见
   为了能实现代理，我们需要让这个类实现 Greeting 接口

```java
public interface Greeting {
    public void morning() throws Throwable;
    public void bye() throws Throwable;
}
```

类的代码为

```java
public class EnglishGreeting implements Greeting {
    String name;
    public EnglishGreeting(String name) {
        this.name = name;
    }
    @Override
    public void morning() {
    System.out.println("Morning "+name);
    }

    @Override
    public void bye() {
        System.out.println("Bye "+name);
    }

```

为什么需要一个 Greeting 接口？因为接下来实现动态代理的方法是基于接口的。
现在我们需要第三个功能，我们希望这个类在打招呼以后要挥手。静态方式实现代理需要提前创建一个类，并且将需要修改的类传给这个代理类，编译它，在运行时加载进来，让这个代理类来代替原本的类，与其他类进行交互（外部调用...）。但是这样很不灵活，
JAVA 提供了一个方法让我们能够在运行时动态的创建代理类，也就是"InvocationHandler.newProxyInstance"这个方法。
需要我们传入三个函数(原本的类，原本类的 classloader，InvocationHandler 的 Instance)。
在这个 InvocationHandler 的实例中，我们改写这个原本的类的方法。
```java
public class ProxyDemo {
public static void main(String[] args) throws Throwable {
EnglishGreeting englishGreeting = new EnglishGreeting("John");
Greeting greetingProxy = (Greeting) Proxy.newProxyInstance(
englishGreeting.getClass().getClassLoader(),
englishGreeting.getClass().getInterfaces(),
new InvocationHandler() {
@Override
public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
System.out.println("~wave hands");
return method.invoke(englishGreeting, args);
}
}
);
greetingProxy.morning();
greetingProxy.bye();
}
}
```
结果可想而知，当我们调用"greetingProxy.morning()"时，调用的是 override 的 invoke 方法。这里是"Proxy.newInstance"为我们创建了一种类的实例
```java
public class EnglishGreetingProxy implements Greeting {
    private InvocationHandler invocationHandler;
    private Greeting greeting;
    public EnglishGreetingProxy(InvocationHandler handler, Greeting greeting) {
        invocationHandler = handler;
        this.greeting = greeting;
    }
    @Override
    public void morning() throws Throwable {
        invocationHandler.invoke(this, greeting.getClass().getMethod("morning"), new Object[]{});
    }

    @Override
    public void bye() throws Throwable {
        invocationHandler.invoke(this, greeting.getClass().getMethod("bye"), new Object[]{});
    }
}
```
用 Proxy.newInstance 创建出来的类差不多就这个意思。所以这里的动态代理是基于接口的，创建的这个类也是实现类这个接口，，并且只有这个接口中的方法才能 override
