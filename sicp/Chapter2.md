# Chapter 2

**Exercise 1.10**: The following procedure computes a mathematical function called Ackermann’s function.

```lisp
(define (A x y)
  (cond ((= y 0) 0)
        ((= x 0) (* 2 y))
        ((= y 1) 2)
        (else (A (- x 1)
                 (A x (- y 1))))))
```

What are the values of the following expressions?

```lisp
(A 1 10)
(A 2 4)
(A 3 3)
```

*My Solution*:

> 1024 65536 65536

Consider the following procedures, where A is the procedure defined above:

```lisp
(define (f n) (A 0 n))
(define (g n) (A 1 n))
(define (h n) (A 2 n))
(define (k n) (* 5 n n))
```

Give concise mathematical definitions for the functions computed by the procedures f, g, and h for positive integer values of n. For example, (k n) computes $5n^2$.

*My Solution*:

> $f(n)=A(0, n)=2n$

> $g(n) = A(1, n) = A(0, A(1, n-1)) = 2A(1, n - 1)=(2^n-1)A(1, 1) = 2^{n-1} \times 2 = 2^n$

> $h(n-1) = A(2, n) = A(1, A(2, n-1)) =g(A(2, n-1)) = 2^{A(2, n - 1)} = 2^{h(n-1)}$

**Exercise 1.11**: A function f is defined by the rule that f(n)=n if n<3 and f(n)=f(n−1)+2f(n−2)+3f(n−3) if n≥3. Write a procedure that computes f by means of a recursive process. Write a procedure that computes f by means of an iterative process.

*My Solution*:
```lisp
#lang racket

(define (cal x y z) (+ x (* 2 y) (* 3 z)))

(define (a-fun n) (if (< n 3) n (a-fun-iter 2 1 0 3 n)) )

(define (a-fun-iter x y z count target)
  ( cond ((= count target) (cal x y z))
         (else (a-fun-iter (cal x y z) (* 2 x) (* 3 y) (+ 1 count) target))
         )
  )
```

**Exercise 1.12**: The following pattern of numbers is called Pascal’s triangle.

```text
         1
       1   1
     1   2   1
   1   3   3   1
 1   4   6   4   1
       . . .
```

The numbers at the edge of the triangle are all 1, and each number inside the triangle is the sum of the two numbers above it.  Write a procedure that computes elements of Pascal’s triangle by means of a recursive process.

```lisp
#lang racket

(define (pascal-triangle n)
  (pascal-triangle-recur 1 1 n)
  )
(define (pascal-triangle-recur row col n)
  (if (= n 1) (pascal row col)
      (cond ((= row col) (pascal-triangle-recur (+ row 1) 1 (- n 1)))
            (else (pascal-triangle-recur row (+ col 1) (- n 1)))
            )
      )
  )
(define (pascal row col)
  (cond ((= row 1) 1)
        ((or (= col 1) (= col row)) 1)
        (else (+ (pascal (- row 1)  (- col 1))
                 (pascal (- row 1) col)))))
```

**Exercise 1.13**: Prove that `Fib(n)` is the closest integer to $φ^{n}/\sqrt{5}$, where $φ=(1+\sqrt{5})/2$. Hint: Let $ψ=(1−\sqrt{5})/2$. Use induction and the definition of the Fibonacci numbers (see 1.2.2) to prove that $Fib(n)=(φ^{n}−ψ^{n})/\sqrt{5}$.
> https://codology.net/post/sicp-solution-exercise-1-13