# Recursion, Corecursion, and Memoization.
`````java
static int addRec(int x,int y){
    return y==0
    ?x
    :addRec(++x,--y);
    }
`````
## Implementing Recursion in Java

To understand what’s happening, you must look at how Java handles method calls. When a method is
called, Java suspends what it’s currently doing and pushes the environment on the stack to make a
place for executing the called method. When this method returns, Java pops the stack to restore the
environment and resume program execution. If you call one method after another, the stack always
holds at most one of these method call environments.

But methods aren’t composed only by calling them one after the other. Methods call methods. If
method1 calls method2 as part of its implementation, Java again suspends the method1 execution,
pushes the current environment on the stack, and starts executing method2. When method2 returns,
Java pops the last pushed environment from the stack and resumes execution (of method1 in this case)
. When method1 completes, Java again pops the last environment from the stack and resumes what it
was doing before calling this method.

Method calls may be deeply nested, and this nesting depth does have a limit, which is the size of
the stack. In current situations, the limit is somewhere around a few thousand levels, and it’s
possible to increase this limit by configuring the stack size. But because the same stack size is
used for all threads, increasing the stack size generally wastes space. The default stack size
varies from 320 KB to 1024 KB, depending on the version of Java and the system used. For a 64-bit
Java 8 program with minimal stack usage, the maximum number of nested method calls is about 7,000.
in java 11 is about 20.0000 calls.
Generally, you won’t need more, except in specific cases. One such case is recursive method calls.

## Using Tail Call Elimination

Pushing the environment on the stack is typically necessary in order to resume computation after the
called method returns, but not always. When the call to a method is the last thing the calling
method does, there’s nothing to resume when the method returns, so it should be OK to resume
directly with the caller of the current method instead of the current method itself. A method call
occurring in the last position, meaning it’s the last thing to do before returning, is called a tail
call. Avoiding pushing the environment to the stack to resume method processing after a tail call is
an optimization technique known as tail call elimination (TCE). Unfortunately, Java doesn’t use TCE.

Tail call elimination is sometimes called tail call optimization (TCO). TCE is generally an
optimization, and you can live without it. But when it comes to recursive function calls, TCE is no
longer an optimization. It’s a necessary feature. That’s why TCE is a better term than TCO when it
comes to handling recursion.

# Abstracting Recursion

So far, so good, but why bother with all this if Java doesn’t have TCE? Well, Java doesn’t have it,
but you can do without it. All you need to do is the following:

    Represent unevaluated method calls

    Store them in a stack-like structure until you encounter a terminal condition

    Evaluate the calls in "last in, first out" (LIFO) order

Most examples of recursive methods use the factorial function. Other examples use the Fibonacci
series. The factorial method presents no particular interest beside being recursive. The Fibonacci
series is more interesting, and we’ll come back to it later. To start with, you’ll use the much
simpler recursive addition method shown at the beginning of this chapter.

Recursive and corecursive functions are both functions where f(n) is a composition of f(n - 1), f(n
- 2), f(n - 3), and so on, until a terminal condition is encountered (generally f(0) or f(1)).
Remember that in traditional programming, composing generally means composing the results of an
evaluation. This means that composing function f(a) and g(a) consists of evaluating g(a) and then
using the result as input to f. But it doesn’t have to be done that way. In Chapter 2, you developed
a compose method to compose functions, and a higherCompose function to do the same thing. Neither
evaluated the composed functions. They only produced another function that could be applied later.

Recursion and corecursion are similar, but there’s a difference. You create a list of function calls
instead of a list of functions. With corecursion, each step is terminal, so it may be evaluated in
order to get the result and use it as input for the next step. With recursion, you start from the
other end, so you have to put non-evaluated calls in the list until you find a terminal condition,
from which you can process the list in reverse order. You stack the steps until the last one is
found, and then you process the stack in reverse order (last in, first out), again evaluating each
step and using the result as the input for the next (in fact, the previous) one.

The problem is that Java uses the thread stack for both recursion and corecursion, and its capacity
is limited. Typically, the stack overflows after 6,000 to 7,000 steps. What you have to do is create
a function or a method returning a non-evaluated step. To represent a step in the calculation,
you’ll use an abstract class called TailCall (because you want to represent a call to a method that
appears in the tail position).

This TailCall abstract class has two subclasses. One represents an intermediate call, when the
processing of one step is suspended to call the method again for evaluating the next step. This is
represented by a subclass named Suspend. It’s instantiated with Supplier<TailCall>>, which
represents the next recursive call. This way, instead of putting all TailCalls in a list, you’ll
construct a linked list by linking each tail call to the next. The benefit of this approach is that
such a linked list is a stack, offering constant time insertion as well as constant time access to
the last inserted element, which is optimal for a LIFO structure.

The second subclass represents the last call, which is supposed to return the result, so you’ll call
it Return. It won’t hold a link to the next TailCall, because there’s nothing next, but it’ll hold
the result. Here’s what you get:

````java
public abstract class TailCall<T> {
    public static class Return<T> extends TailCall<T> {
        private final T t;

        public Return(T t) {
            this.t = t;
        }
    }

    public static class Suspend<T> extends TailCall<T> {
        private final Supplier<TailCall<T>> resume;

        private Suspend(Supplier<TailCall<T>> resume) {
            this.resume = resume;
        }
    }
}
````
