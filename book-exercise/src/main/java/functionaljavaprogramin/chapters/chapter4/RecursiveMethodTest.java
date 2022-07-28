package functionaljavaprogramin.chapters.chapter4;

import static functionaljavaprogramin.chapters.chapter4.TailCall.Suspend.ret;
import static functionaljavaprogramin.chapters.chapter4.TailCall.Suspend.sus;

import java.util.function.Function;

/**
 * In theory, recursive functions shouldn’t be more difficult to create than methods, if functions
 * are implemented as methods in an anonymous class. But lambdas aren’t implemented as methods in
 * anonymous classes.
 *
 * <p>The first problem is that, in theory, lambdas can’t be recursive. But this is theory. In fact,
 * you learned a trick to work around this problem in Chapter 2. A statically defined recursive add
 * function looks like this:
 *
 * <p><code>
 *         static Function<Integer, Function<Integer, TailCall<Integer>>> add =
 *         a -> b -> b == 0
 *         ? ret(a)
 *         : sus(() -> ContainingClass.add.apply(a + 1).apply(b - 1));
 * </code>
 *
 * <p>Here, ContainingClass stands for the name of the class in which the function is defined. Or
 * you may prefer an instance function instead of a static one:
 *
 * <p><code>
 *     Function<Integer, Function<Integer, TailCall<Integer>>> add =
 *     a -> b -> b == 0
 *     ? ret(a)
 *     : sus(() -> this.add.apply(a + 1).apply(b - 1));
 * </code>
 *
 * <p>But here, you have the same problem you had with the add method. You must call eval on the
 * result. You could use the same trick, with a helper method alongside the recursive
 * implementation. But you should make the whole thing self-contained. In other languages, such as
 * Scala, you can define helper functions locally, inside the main function. Can you do the same in
 * Java?
 *
 * @author Alexander Bravo
 */
public class RecursiveMethodTest {

  // Defining a function inside a function isn’t directly possible in Java. But a function written
  // as a lambda is a class. Can you define a local function in that class? In fact, you can’t. You
  // can’t use a static function, because a local class can’t have static members, and anyway, they
  // have no name. Can you use an instance function? No, because you need a reference to this. And
  // one of the differences between lambdas and anonymous classes is the 'this' reference. Instead of
  // referring to the anonymous class instance, the 'this' reference used in a lambda refers to the
  // enclosing instance.
  //
  // The solution is to declare a local class containing an instance function, as shown in the
  // following listing.
  static Function<Integer, Function<Integer, Integer>> add =
      x ->
          y -> {
            class AddHelper {
              Function<Integer, Function<Integer, TailCall<Integer>>> addHelper =
                  a -> b -> b == 0 ? ret(a) : sus(() -> this.addHelper.apply(a + 1).apply(b - 1));
            }
            return new AddHelper().addHelper.apply(x).apply(y).eval();
          };

  public static void main(String[] args) {
    // applying recursive function.
    System.out.println(add.apply(1).apply(100_000_000));
  }
}
