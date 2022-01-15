### 1.1.6

**Exercise 1.1**: Below is a sequence of expressions. What is the result printed by the interpreter in response to each expression? Assume that the sequence is to be evaluated in the order in which it is presented.

```
10
(+ 5 3 4)
(- 9 1)
(/ 6 2)
(+ (* 2 4) (- 4 6))
(define a 3)
(define b (+ a 1))
(+ a b (* a b))
(= a b)
(if (and (> b a) (< b (* a b)))
    b
    a)
(cond ((= a 4) 6)
      ((= b 4) (+ 6 7 a))
      (else 25))
(+ 2 (if (> b a) b a))
(* (cond ((> a b) a)
         ((< a b) b)
         (else -1))
   (+ a 1))
```
My Answer:
> 10
>
> 12
>
> 3
>
> 6
>
> 19
>
> #f
>
> 4
>
> 16
>
> 6
>
> 17



**Exercise 1.2**: 
Translate the following expression into prefix form:

$\frac{5+4+(2-(3-(6+\frac{4}{5})))}{3(6-2)(2-7)}$

My Answer:

```lisp
(define dividend (+ 5 4 (- 2 (- 3 (+ 6 (/ 4 5))))))
(define divisor (* 3 (- 6 2) (- 2 7)))
(/ dividend divisor)
```

**Exercise 1.3:** 

Define a procedure that takes three numbers as arguments and returns the sum of the squares of the two larger numbers.

My Answer:

```lisp
(define (square x) (* x x))

(define (smallest x y z) 
(
    cond ((and (< x y) (< x z)) x)
    ((and (< y x) (< y z)) y)
    ((and (< z x) (< z y)) z)
    (else z)
))

(define 
    (sum-of-square-larger x y z)
    ( -
        (+ (square x) (square y) (square z))
        (square (smallest x y z))
    )
)
```

**Exercise 1.4:** 

Observe that our model of evaluation allows for combinations whose operators are compound expressions. Use this observation to describe the behavior of the following procedure:

```lisp
(define (a-plus-abs-b a b)
  ((if (> b 0) + -) a b))
```

My Answer : 

> Sum up two number a and b, if  b is positive, then applies a plus b, otherwise a minus b which means a plus negative b

**Exercise 1.5:** Ben Bitdiddle has invented a test to determine whether the interpreter he is faced with is using applicative-order evaluation or normal-order evaluation. He defines the following two procedures:

```
(define (p) (p))

(define (test x y) 
  (if (= x 0) 
      0 
      y))
```

Then he evaluates the expression

```
(test 0 (p))
```

What behavior will Ben observe with an interpreter that uses applicative-order evaluation? What behavior will he observe with an interpreter that uses normal-order evaluation? Explain your answer. (Assume that the evaluation rule for the special form `if` is the same whether the interpreter is using normal or applicative order: The predicate expression is evaluated first, and the result determines whether to evaluate the consequent or the alternative expression.)

My Answer:

> if interpreter is using applicative-order evaluation, then the expression would evaluate `(p)` first, which make this evaluation becomes infinite looping. If interpreter is using normal-order, then `(test 0 (p))` would be expanded to `(if (= 0 0) 0 (p))`, it would return 0 instantly.

**Exercise 1.6:** Alyssa P. Hacker doesn’t see why `if` needs to be provided as a special form. “Why can’t I just define it as an ordinary procedure in terms of `cond`?” she asks. Alyssa’s friend Eva Lu Ator claims this can indeed be done, and she defines a new version of `if`:

```
(define (new-if predicate 
                then-clause 
                else-clause)
  (cond (predicate then-clause)
        (else else-clause)))
```

Eva demonstrates the program for Alyssa:

```
(new-if (= 2 3) 0 5)
5

(new-if (= 1 1) 0 5)
0
```

Delighted, Alyssa uses `new-if` to rewrite the square-root program:

```
(define (sqrt-iter guess x)
  (new-if (good-enough? guess x)
          guess
          (sqrt-iter (improve guess x) x)))
```

What happens when Alyssa attempts to use this to compute square roots? Explain.

My Answer:

> It can never get a returned value for `new-if` is a procedure called. In Scheme, a procedure call is using applicative-order ,which means it requires `then-clause` and `else-clause` evaluated first. In this example, it would call `sqrt-iter` then get an infinite recursion.

**Exercise 1.7:** The `good-enough?` test used in computing square roots will not be very effective for finding the square roots of very small numbers. Also, in real computers, arithmetic operations are almost always performed with limited precision. This makes our test inadequate for very large numbers. Explain these statements, with examples showing how the test fails for small and large numbers. An alternative strategy for implementing `good-enough?` is to watch how `guess` changes from one iteration to the next and to stop when the change is a very small fraction of the guess. Design a square-root procedure that uses this kind of end test. Does this work better for small and large numbers?

My Answer:

>As the precision of the result is depending on `good-enough?`'s difference. If input value is smaller than `0.0001`, then this procedure would be suitable. If the input number is fairly large, the procedure would go recursion many times for this precision, which is ineffective

**Exercise 1.8:** Newton’s method for cube roots is based on the fact that if y is an approximation to the cube root of x, then a better approximation is given by the value

$\frac{\frac{x}{y^2}+2y}{3}$

Use this formula to implement a cube-root procedure analogous to the square-root procedure. 

