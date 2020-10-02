function outer(n) {
    function inner() {
        return n + 1;
    }
    return inner
}
let func = outer(10);
console.log(func())