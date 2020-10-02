/**
 * 在全局环境下，this在严格模式下指向undefined，非严格模式下指向window
 */
function f1() {
    console.log(this)
}
function f2() {
    'use strict'
    console.log(this)
}
f1() // window for chrome, global for node
f2() // undefined
/**
 * 上下文对象中调用this，this指向最后调用它的对象
 */
const father = {
    name: "father",
    son: {
        name: "son",
        sayHi: function () {
            return "Hi "+this.name;
        }
    }
}
console.log(father.son.sayHi()) //Hi son
/**
 * 几个变种
 */
const o1 = {
    name: "o1",
    fn: function() {
        return this.name;
    }
}
const o2 = {
    name: "o2",
    fn: function() {
        return o1.fn()
    }
}
const o3 = {
    name:"o3",
    fn: function() {
        let fn = o1.fn
        return fn()
    }
}
const o4 = {
    name:"o4",
    fn: o1.fn
}
/**
 * 输出o1，因为最后的调用者是o1
 */
console.log(o1.fn())
/**
 * 输出o1，因为最后调用者依然是o1
 */
console.log(o2.fn())
/**
 * 输出undefined，因为最后的fn调用是裸奔
 */
console.log(o3.fn())
/**
 * 输出o4，此时o4的fn已经绑定了函数，最后调用者为o4
 */
console.log(o4.fn())
/**
 * call/apply/bind改变this的指向
 */
const foo = {
    name: "foo",
    fn: function() {
        return this.name
    }
}
const bar = {
    name: "bar"
}
console.log(foo.fn()) // foo
console.log(foo.fn.call(bar))   // bar, this指向bar
/**
 * 构造函数和this
 */
function Foo(name) {
    this.name = name
}
const instance = new Foo("Potter")
/**
 * new 创建一个空对象并让构造函数中的this指向这个对象，然后返回这个对象
 */
console.log(instance.name)
/**
 * 
 */
function return_single_value() {
    this.name = "Peter"
    return 1
}
function return_obj_value() {
    this.name = "John"
    return {name:"Jessie"}
}
const instance1 = new return_single_value()
const instance2 = new return_obj_value()
console.log(instance1.name) // peter， 没有区别
console.log(instance2.name) // Jessie， 被返回的对象覆盖了
/**
 * 箭头函数
 */
var name = "Amy"
const arrow = {
    name:"arrow",
    fn: function () {
        let iner = () => {
            console.log(this)
        }
        iner()
    }
}
arrow.fn() //this指向arrow
/**
 * 优先级 call/apply > 隐式， new > bind
 */