## JavaScript原型链学习

#### `prototype` VS `__prop__`

JavaScript通过原型链的方式实现复用。在对象实例中，有一个特殊的属性`__prop__`，它指向的是它的原型。当我们使用构造函数来创建一个对象的时候，这个构造函数存在一个prototype属性，这个属性指向同构造函数创建出来的对象的`__prop__`相同。

例如

```javascript
function Animal(name) {
    this.name = name
    this.makeNoise = () => {
        console.log('...')        
    }
    this.introduce = function(){
        console.log(`I am ${this.name}`)
    }
}
```
上边这个构造函数创建的对象的`__prop__`同`Animal.prototype`指向相同的对象。但是我们一般不直接使用`obj.__prop__`来得到该对象的原型，而是使用`Object.getPrototypeOf(obj)`

```javascript
let animal = new Animal('animal')
console.assert(Object.getPrototypeOf(animal) === Animal.prototype);
```

#### 用call方法实现继承
用`call`方法，也就是使用`this`在运行时绑定的特性，在`this`中创建父类的属性和方法
```javascript
function Dog(species) {
    this.species = species
    Animal.call(this, 'dog')
    this.makeNoise = () => {
        console.log('wuff')
    }
}
```
但是这样做的话，Dog的创建的对象其实原型并不是Animal

#### 用Object.create()实现继承
为了弥补缺点，我们在继承的构造函数中把this的原型替换成父类的一个实例。
```javascript
function Animal(name) {
    this.name = name
    this.makeNoise = () => {
        console.log('...')        
    }
    this.introduce = function(){
        console.log(`I am ${this.name}`)
    }
}
function Dog(species) {
    Object.setPrototypeOf(this, new Animal('dog')) 
    this.species = species
    this.makeNoise = () => {
        console.log('wuff')
    }
    this.run = () => {
        console.log('run')
    }
}
```
关于继承网上有很多实现方式，但是es6已经规范了语法，用`class`和`extends`关键字可以实现原型链的继承。
如下写法
```javascript
class Subject {
    constructor() {

    }   
}

class Math extends Subject {
    constructor() {
        super()
    }
}

let math = new Math()
console.log(math instanceof Subject)
```
如果不想使用es6提供的语法糖，官方也有`Object.create()`来实现继承。`Object.create()`可以在MDN搜索到用法。