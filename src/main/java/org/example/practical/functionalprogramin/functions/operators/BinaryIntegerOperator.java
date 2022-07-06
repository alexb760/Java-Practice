package org.example.practical.functionalprogramin.functions.operators;

/**
 * Let’s try to define a function for adding two integers. You’ll apply a function to the first
 * argument, and this will return a function. The type will be as follows:
 *
 * <p>Function<Integer, Function<Integer, Integer>>
 *
 * <p>This may seem a bit complicated, particularly if you think that it could have been written
 * like this:
 *
 * <p>Integer -> Integer -> Integer
 *
 * <p>Note that because of associativity, this is equivalent to
 *
 * <p>Integer -> (Integer -> Integer)
 *
 * <p>where the left Integer is the type of the argument, and the element between parentheses is the
 * return type, which obviously is a function type. If you remove the word Function from
 * <code>Function<Integer, Function<Integer, Integer>> </code>, you get this:
 *
 * <p> <code> <Integer, <Integer, Integer>> </code>
 *
 * @author Alexander Bravo
 */
interface OperatorFunction<T, U> {
  U apply(T arg);
}

/**
 * You can see that you’ll soon have problems with the length of the lines! Java has no type aliases,
 * but you can achieve the same result through inheritance. If you have many functions to define
 * with the same type, you can extend it with a much shorter identifier, like this:
 */
interface BinaryIntegerOperator
    extends OperatorFunction<Integer, OperatorFunction<Integer, Integer>> {}

class main {
  public static void main(String[] args) {
    BinaryIntegerOperator add = x -> y -> x + y;
    BinaryIntegerOperator mul = x -> y -> x * y;

    //Applying Curried Functions
    //You’ve seen how to write curried function types and how to implement them. But how do you apply them?
    // Well, just like any function. You apply the function to the first argument, and then apply
    // the result to the next argument, and so on until the last one. For example,
    // you can apply the add function to 3 and 5:
    System.out.println(add.apply(2).apply(5));
    //Applying Curried Functions
    System.out.println(mul.apply(5).apply(2));
  }
}
