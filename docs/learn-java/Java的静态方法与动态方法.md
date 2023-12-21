# JAVA中的静态有两种含义：

- 静态方法与非静态方法
- 静态绑定与动态绑定
#### 静态方法与非静态方法
- 静态方法
也叫作**类方法**，在调用时，无需指定是哪个实例在调用它，不能使用**this**和**super**关键字来调用
- 非静态方法
也叫作实例方法，在调用时，要指定是哪个实例在调用，可以使用**this**和**super**关键字

其中区分这两种方法最重要的一个点是是否能用this关键字，对于this关键字，规范中提到，它可以出现在以下几种场合
- 实例方法
```java
public class Demo {
    public String name = "Demo";
    public void demo() {
        System.out.println(this.demo);
    }
}
```
- 实例的初始化器
```java
public class Demo {
    public String name;
    public Demo() {}
    {
        name = "Demo";
    }
}
```
- 构造器
```java
public class Demo {
    public String name;
    public Demo() {
        this.name = "Demo";
    }
}
```
- 实例变量的初始化器
```java
public class Demo {
    public String name = "Demo";
    public Demo() {
        // do nothing
    }
}
```
那么这些方法是如何拿到this实例的呢。JAVA中函数传递参数是通过局部变量区的内存，参数在这部分内存中是连续存放的，最开始的局部变量0就是调用者的引用。但是这种传递参数的方式是隐式的，在方法描述符中是无法看到实例的参数的。

#### 静态绑定与与动态绑定
JAVA中存在三种多态
- 子类型多态（override）
- ad-hoc多态（overload）
- 参数化多态（泛型）

JAVA中根据两种绑定方法可以分为两种函数
- 虚方法（动态绑定，或者叫迟绑定）
- 非虚方法（静态绑定，或者叫早绑定）
那么这种分类方法同（静态，动态）的分类有什么联系呢
- 非虚方法包括：
    - static修饰过的方法，也就是上文中提到过的静态方法
    - private，final修饰过的方法（final有时会被inline处理）
- 虚方法包括：
    - 其他的方法

那么构造器呢，根据规范，它无法继承也无法隐藏，不会参与到多态当中。
同时，要注意构造器必须同new运算符同时使用，因为构造器只能负责实例的初始化，只有new运算符才会让JVM新建一个实例。

#### 双分派与访问者模式
当使用JAVA的多态有一定的局限性。
假如存在以下场景，一个父亲有一个儿子和一个女儿，当他拜访儿子的时候他会说“你好，儿子”，当他拜访女儿的时候他会说“你好，女儿”。
此来创建一个Father类，如下
```java
public class Father {
    public void visit(Son son) {
        System.out.println("Hi, son");
    }
    public void visit(Daughter daughter) {
        System.out.println("Hi daughter");
    }
    public void visit(Child child) {
        if (child instanceof Daughter) {
            visit((Daughter) child;
        } else {
            visit((Son) son);
        }
    }
}
```
父亲的孩子们都继承一个公共的类
```java
public class Child {}
```
儿子类
```java
public class Son {

}
```
女儿类
```java
public class Daughter {

}
```
这个场景的主类
```java
public class Main {
    public static void main(String[] args) {
        Father father = new Father;
        Child son = new Son();
        Child daughter = new Daughter;
        father.visit(son);
        father.visit(daughter);
    }
}
```
当父亲要拜访的角色变多时，情况会更加复杂，这时，我们只能利用的是overload来实现分派方法。那么我们应该想办法用上子类型的多态，也就是使用访问者模式。反过来，我们让被访问者去接受访问者.
场景的主类改成
```java
public class Main {
    public static void main(String[] args) {
        Father father = new Father;
        Child son = new Son();
        Child daughter = new Daughter;
        son.accept(father);
        daughter.visit(father);
    }
}
```
这样的话，儿子和女儿的类和他们的基类也要改写
```java
public class Child {
    public void accept(Father father);
}
```
儿子类
```java
public class Son {
    @Override
    public void accept(Father father) {
        father.visit(this);
    }
}
```
女儿类
```java
public class Daughter {
    @Override
    public void accept(Father father) {
        father.visit(this);
    }
}
```
父亲类就可以写成
```java
public class Father {
    public void visit(Son son) {
        System.out.println("Hi, son");
    }
    public void visit(Daughter daughter) {
        System.out.println("Hi daughter");
    }
}
```
可以看到，这里用到了两个条件来决定具体调用哪个方法
- ``who.accept()``
#### 参考
- https://www.iteye.com/blog/rednaxelafx-652719