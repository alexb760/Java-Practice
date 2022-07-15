package org.example.practical.functionalprogramin.functions.operators;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 2.3.4 Polymorphic Higher-Order Functions Variance describes how parameterized types behave in
 * relation to subtyping. Covariance means that Matcher<Red> is considered a subtype of
 * Matcher<Color> if Red is a subtype of Color. In such case, Matcher<T> is said to be covariant on
 * T. If, on the contrary, Matcher<Color> is considered a subtype of Matcher<Red>, then Matcher<T>
 * is said to be contravariant on T. In Java, although an Integer is a subtype of Object, a
 * List<Integer> is not a subtype of List<Object>. You may find this strange, but a List<Integer> is
 * an Object, but it is not a List<Object>. And a Function<Integer, Integer> is not a
 * Function<Object, Object>. (This is much less surprising!)
 *
 * <p>In Java, all parameterized types are said to be invariant on their parameter.
 *
 * @author Alexander Bravo
 */
public class PolymorphicHOF {

  static <T, U, V>
      OperatorFunctionHOF<
              OperatorFunctionHOF<U, V>,
              OperatorFunctionHOF<OperatorFunctionHOF<T, U>, OperatorFunctionHOF<T, V>>>
          higherCompose() {
    return (OperatorFunctionHOF<U, V> f) ->
        (OperatorFunctionHOF<T, U> g) -> (T x) -> f.apply(g.apply(x));
  }

  /**
   * Write the higherAndThen function that composes the functions the other way around, which means
   * that higherCompose(f, g) is equivalent to higherAndThen(g, f)
   */
  static <T, U, V>
      OperatorFunctionHOF<
              OperatorFunctionHOF<T, U>,
              OperatorFunctionHOF<OperatorFunctionHOF<U, V>, OperatorFunctionHOF<T, V>>>
          higherAndThen() {
    return (OperatorFunctionHOF<T, U> f) ->
        (OperatorFunctionHOF<U, V> g) -> (T x) -> g.apply(f.apply(x));
  }

  public static void main(String[] args) {
    OperatorFunctionHOF<Integer, Integer> triple = x -> x * 3;
    OperatorFunctionHOF<Integer, Integer> square = x -> x * x;

    Integer res =
        PolymorphicHOF.<Integer, Integer, Integer>higherCompose()
            .apply(square)
            .apply(triple)
            .apply(2);
    System.out.println(res);
    testingHighOrderFunctions();

    Function<Double, Function<Double, Function<Double, Double>>> curryingFunction =
        tax -> value -> discount -> (value + value * tax) - discount;
    System.out.println(curryingFunction.apply(0.9).apply(9D).apply(3D));
  }

  // Testing High Order Functions.
  static void testingHighOrderFunctions() {
    OperatorFunctionHOF<Double, Integer> f = a -> (int) (a * 3);
    OperatorFunctionHOF<Long, Double> g = a -> a + 2.0;
    if (Integer.valueOf(9).equals(f.apply(g.apply(1L)))) {
      System.out.println("True");
    }

    if (Integer.valueOf(9)
        .equals(
            PolymorphicHOF.<Long, Double, Integer>higherCompose().apply(f).apply(g).apply(1L))) {
      System.out.println("True");
    }
  }
}
