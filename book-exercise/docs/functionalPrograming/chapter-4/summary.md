# Summary.

A recursive function is a function that’s defined by referencing itself.

In Java, recursive methods push the current computation state onto the stack before recursively calling themselves.

The Java default stack size is limited. It can be configured to a larger size, but this generally wastes space because all threads use the same stack size.

Tail recursive functions are functions in which the recursive call is in the last (tail) position.

In some languages, recursive functions are optimized using tail call elimination (TCE).

Java doesn’t implement TCE, but it’s possible to emulate it.

Lambdas may be made recursive.

Memoization allows functions to remember their computed values in order to speed up later accesses.

Memoization can be made automatic.
