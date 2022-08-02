package functionaljavaprogramin.chapters;

/**
 * (https://github.com/fpinjava/fpinjava)
 *
 * Basic Definition of a pure function: It must not mutate anything outside the function. No
 *
 * <p>internal mutation may be visible from the outside.
 *
 * <p>It must not mutate its argument.
 *
 * <p>It must not throw errors or exceptions.
 *
 * <p>It must always return a value.
 *
 * <p>When called with the same argument, it must always return the same result.
 *
 * <p>small and silly example of a Functional interface and anonymous classes
 *
 * <p>Creating a compose function which just put together some function.
 *
 * <p>Applying Polymorphic function by using generic type
 *
 * @author Alexander Bravo
 */
public class SpecialFunction {

  interface OperationFunction<T, U> {
    U apply(T arg);
  }

  public static OperationFunction<Integer, Integer> triple =
      new OperationFunction<Integer, Integer>() {
        @Override
        public Integer apply(Integer arg) {
          return arg * 3;
        }
      };

  public static OperationFunction<Integer, Integer> square =
      new OperationFunction<Integer, Integer>() {
        @Override
        public Integer apply(Integer arg) {
          return arg * arg;
        }
      };

  /**
   * Function composition is a powerful concept, but when implemented in Java, it presents a big
   * danger. Composing a couple of functions is harmless. But think about building a list of 10,000
   * functions and composing them into a single one. (This could be done through a fold, an
   * operation you’ll learn about in Chapter 3.)
   *
   * <p>In imperative programming, each function is evaluated before the result is passed as the
   * input of the next function. But in functional programming, composing functions means building
   * the resulting function without evaluating anything. Composing functions is powerful because
   * functions can be composed without being evaluated. But as a consequence, applying the composed
   * function results in numerous embedded method calls that will eventually overflow the stack.
   * This can be demonstrated with a simple example (using lambdas, which will be introduced in the
   * next section):
   *
   * <p><code>
   *     int fnum = 10_000;
   *     Function<Integer, Integer> g = x -> x;
   *     Function<Integer, Integer> f = x-> x + 1;
   * for (int i = 0; i < fnum; i++) { g = Function.compose(f, g); };
   *
   * System.out.println(g.apply(0));
   * </code>
   *
   * <p>This program will overflow the stack when fnum is around 7,500. Hopefully you won’t usually
   * compose several thousand functions, but you should be aware of this.
   *
   * @param f1 Function 1
   * @param f2 Function 2
   * @return Stack of composed function.
   */
  public static OperationFunction<Integer, Integer> compose(
      OperationFunction<Integer, Integer> f1, OperationFunction<Integer, Integer> f2) {
    return new OperationFunction<Integer, Integer>() {
      @Override
      public Integer apply(Integer arg) {
        return f1.apply(f2.apply(arg));
      }
    };
  }

  /**
   * Lambada composition.
   *
   * @param f1 Function 1
   * @param f2 Function 2
   * @return Stack of composed function.
   */
  public static OperationFunction<Integer, Integer> lambdaCompose(
      OperationFunction<Integer, Integer> f1, OperationFunction<Integer, Integer> f2) {
    return arg -> f1.apply(f2.apply(arg));
  }

  public static void main(String[] args) {
    System.out.println(compose(triple, square).apply(3));
    System.out.println(lambdaCompose(triple, square).apply(3));
  }
}
