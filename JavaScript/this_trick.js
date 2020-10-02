function f() {
    console.log(this)
}
let a = {
    content:"hello"
}
f = f.bind(a)
f()