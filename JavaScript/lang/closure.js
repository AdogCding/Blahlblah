// 闭包
function outer(n) {
    function inner() {
        return n + 1;
    }
    return inner
}
let func = outer(10);
console.log(func())
// 柯里化
function add(a) {
    return (b) => {
        return a + b
    }
}
console.log(add(1)(2))