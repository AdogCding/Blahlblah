在Spring中我们可以见到有以下一些写法
```java
public class Foo {
    @before("....")
    public void foo() {

    }
}
```
这样，在执行```Foo.foo()```时，会先执行```before()```中的内容。
如果有Python 的使用经验，会发现和Python的装饰器很像。但Java想要实现在原有类的基础上添加新功能却不那么容易，要使用代理模式。
代理分为动态代理和静态代理
动态代理在运行时生成一个代理类。
例如，我们有一个需求，作用为

1. 打招呼
2. 说再见

先看一下Python的简单实现
```python
def greeting():
    print("morning")


def bye():
    print("bye")


if __name__ == '__main__':
    greeting()
    bye()
```
如果使用Java，首先创建一个接口，要有两个方法
```java
public interface Polite {
    public void greeting() throws Throwable;
    public void bye() throws Throwable;
}
```
接口实现类
```java
public class Person implements Polite {
    String name;
    public EnglishGreeting(String name) {
        this.name = name;
    }
    @Override
    public void greeting() {
    System.out.println("Morning "+name);
    }

    @Override
    public void bye() {
        System.out.println("Bye "+name);
    }

```

为什么Java需要先定义接口而不是直接实现类？因为接下来实现动态代理的方法是基于接口的。
现在我们需要第三个功能，我们希望在**打招呼**和**说再见**之前要保持微笑。静态方式实现代理需要提前创建一个类，并且将需要修改的类传给这个代理类，编译它，在运行时加载进来，让这个代理类来代替原本的类，与其他类进行交互。但是这样很不灵活。
Python提供了装饰器的功能让我们可以不用修改
```python
def smile(polite_func):
    def __smile():
        print("smile ~")
        polite_func()

    return __smile


@smile
def greeting():
    print("morning")


@smile
def bye():
    print("bye")


if __name__ == '__main__':
    greeting()
    bye()
```
```python
@decorator
def func():
    return
# 其实是一个语法糖
# 等同于
func = decorator(func)
```
JAVA 提供了一个方法让我们能够在运行时动态的创建代理类，也就是"InvocationHandler.newProxyInstance"这个方法。
需要我们传入三个参数(原本的类，原本类的 classloader，InvocationHandler 的 Instance)。
在这个 InvocationHandler 的实例中，我们改写这个原本的类的方法。
```java
public class ProxyDemo {
    public static void main(String[] args) throws Throwable {
        // 要被代理的类
        EnglishGreeting englishGreeting = new EnglishGreeting("John");
        // 代理类
        Greeting greetingProxy = (Greeting) Proxy.newProxyInstance(
        englishGreeting.getClass().getClassLoader(),
        englishGreeting.getClass().getInterfaces(),
        new InvocationHandler() {
            @Override
            // 代理类中被代理的方法
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
其实，当我们调用```greetingProxy.morning()```时，调用的是```InvocationHanlder```的 ```invoke``` 方法。这里是```Proxy.newInstance```为我们创建了一种类的实例
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
用 ```Proxy.newInstance``` 创建出来的类是基于被代理类实现的接口，只有这个接口中的方法才能*被代理*。
与JDK提供的接口代理服务不同的，CGLib基于ASM实现了使用类进行代理。
假设类
```java
public class Person {
    public void greeting() {
        System.out.println("Hi");
    }
    public void bye() {
        System.out.println("bye");
    }
}
```
可以看到这个```person```类没有实现任何接口，接下来要做的是创建代理类
```java
public class CglibProxy implements MethodInterceptor {
    private Enhancer enhancer = new Enhancer();
    public Object getProxy(Object obj) {
        enhancer.setSuperclass(obj.getClass());
        enhancer.setCallback(new CglibProxy());
        return enhancer.create();
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("wave ~");
        methodProxy.invokeSuper(o, objects);
        return null;
    }
}
```
```intercept```和```Enhance```实现了使用类，而不是接口来动态代理。但是能否像Python一样简单的使用```@something```，目前我还没有找到办法，能够想到的只有在运行期间处理注解，再根据注解处理方法。

