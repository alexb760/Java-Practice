# Data Handling with Lists

5.1 How to Classify Data Collections

Data collections can be classified from many different points of view. You can classify data
collections as linear, associative, and graph:

    Linear collections are collections in which elements are related along a single dimension. In such a collection, each element has a relation to the next element. The most common example of a linear collection is the list.

    Associative collections are collections that can be viewed as a function. Given an object o, a function f(o) will return true or false according to whether this object belongs to the collection or not. Unlike in linear collections, there’s no relation between the elements of the collection. These collections aren’t ordered, although it is possible to define an order on the elements. The most common examples of associative collections are the set and the associative array (which is also called a map or dictionary). We’ll study a functional implementation of maps in Chapter 11.

    Graphs are collections in which each element is in relationships with multiple other elements. A particular example is the tree, and more specifically the binary tree, where each element is related to two other elements. You’ll learn more about trees from a functional perspective in Chapter 10.

5.1.1 Different Types of Lists

In this chapter, we’ll focus on the most common type of linear collections, the list. The list is
the most used data structure in functional programming, so it’s generally used to teach functional
programming concepts. Be aware, however, that what you’ll learn in this chapter is not specific to
lists but is shared by many other data structures (which may not be collections).

Lists can be further classified based on several different aspects, including the following:

    Access—Some lists will be accessed from one end only, and others will be accessed from both ends. Some will be written from one end and read from the other end. Finally, some lists may allow access to any element using its position in the list; the position of an element is also called its index.

    Type of ordering—In some lists, the elements will be read in the same order in which they were inserted. This kind of structure is said to be FIFO (first in, first out). In others, the order of retrieval will be the inverse of the order of insertion (LIFO, or last in, first out). Finally, some lists will allow you to retrieve the elements in a completely different order.

    Implementation—Access type and ordering are concepts strongly related to the implementation you choose for the list. If you choose to represent the list by linking each element to the next, you’ll get a completely different result, from the access point of view, than from an implementation based on an indexed array. Or if you choose to link each element to the next as well as to the previous element, you’ll get a list that can be accessed from both ends.

## An immutable Persistent, Singly List Implementation.

The list class is implemented as an abstract class. The List class contains two private static
subclasses to represent the two possible forms a List can take: Nil for an empty list, and Cons for
a non-empty one.

The List class defines three abstract methods: ``head()``, which will return the first element of the
list; ``tail()``, which will return the rest of the list (without the first element); and ``isEmpty()``,
which will return true if the list is empty and false otherwise. The List class is parameterized
with type parameter A, which represents the type of the list elements.

Subclasses have been made private, so you construct lists through calls to the static factory
methods. These methods can be statically imported:

``import static fpinjava.datastructures.List.*;``

They can then be used without referencing the enclosing class, as follows:


    List<Integer> ex1 = list(); 
    List<Integer> ex2 = list(1); 
    List<Integer> ex3 = list(1, 2);


Note that the empty list has no type parameter. In other words, it’s a raw type that can be used to
represent an empty list of elements of any types. As such, creating or using an empty list will
generate a warning by the compiler. The advantage is that you can use a singleton for the empty
list. Another solution would have been to use a parameterized empty list, but this would have caused
much trouble. You’d have had to create a different empty list for each type parameter. To solve this
problem, you use a singleton empty list with no parameter type. This generates a compiler warning.
In order to restrict this warning to the List class and not let it leak to the List users, you don’t
give direct access to the singleton. That’s why there’s a (parameterized) static method to access
the singleton, and a ``@SuppressWarnings(“rawtypes”)`` on the NIL property, as well as a
@SuppressWarnings(“unchecked”) on the list() method.

Note that the list(A … a) method is annotated with @SafeVarargs to indicate that the method doesn’t
do anything that could lead to heap pollution. This method uses an imperative implementation based
on a for loop. This isn’t very "functional," but it’s a trade-off for simplicity and performance. If
you insist on implementing it in a functional way, you can do so. All you need is a function taking
an array as its argument and returning its last element, and another one to return the array without
its last element. Here’s one possible solution:

    @SafeVarargs 
    public static <A> List<A> list(A... as) { return list_(list(), as).eval(); }
    
    public static <A> TailCall<List<A>> list_(List<A> acc, A[] as) 
    { return as.length == 0 ? ret(acc)
        : sus(() -> list_(new Cons<>(as[as.length -1], acc), Arrays.copyOfRange(as, 0, as.length - 1))); 
    }

Be sure, however, not to use this implementation, because it’s 10,000 times slower than the
imperative one. This is a good example of when not to be blindly functional. The imperative version
has a functional interface, and this is what you need. Note that recursion isn’t the problem.
Recursion using TailCall is nearly as fast as iteration. The problem here is the copyOfRange method,
which is very slow.
