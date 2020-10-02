## Design Pattern
1. 创建型：
    1. Singleton Pattern
    让一个class在运行时仅有一个实例的设计模式。因此用一个static的变量来获取这个实例的引用。
    一般有三种思路来实现单例模式：
        1. 加载class时就创建
    2. Factory Pattern
    3. Abstract Factory Pattern
    4. Builder Pattern
    5. Prototype Pattern
    6. Factory Method
2. 结构型：
    2. Observer Pattern
    3. Decorator Pattern
    5. Command Pattern
    7. Adaptor Pattern
3. 行为型：
    1. Strategy Pattern(策略模式)
    把算法的不同实现分别封装起来，使用相同的接口让他们之间可以相互替换。也就是说让算法独立于使用它们的客户
    比如，对于一个Duck类，不同的Duck有不同的飞行方式。假设有三种鸭子：
        1. 野鸭可以长途飞行
        2.家养的鸭子只能跳跃
        3.塑料鸭子无法飞行
    如果根据每个鸭子的特性，重新编写fly()方法，那么不够灵活，也会导致很多重复代码。
    这样就可以定义一个Fly()的接口，不同的飞行方式（也即是具体的类）都会实现这样一个接口。
    这个接口让这些具体的实现成为一个集合，对于这个集合可以相互替换。推广开来，如果一个类要求有一个排序的方法。可以使用策略模式，定义一个Sort的接口，然后可以随意实现为“冒泡排序”，“堆排序”，“选择排序”等，使用时灵活选择即可。也就是说，策略的具体调用，需要**调用者**来灵活选择。

