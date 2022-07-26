package functionaljavaprogramin.chapters.operators;

import java.util.function.Function;

/**
 * @author Alexander Bravo
 */
public class CurryFunctions {

  // Write a method to partially apply a curried function of two arguments to its second argument.

  /**
   * With our previous function, the answer to the problem would be a method with the following
   * signature:
   *
   * <p><A, B, C> Function<A, C> partialB(B b, Function<A, Function<B, C>> f)
   *
   * <p>This exercise is slightly more difficult, but still simple if you carefully consider the
   * types. Remember, you should always trust the types! They won’t give you an immediate solution
   * in all cases, but they will lead you to the solution. This function has only one possible
   * implementation, so if you find an implementation that compiles, you can be sure it’s correct!
   *
   * <p>What you know is you must return a function from A to C. So you can start the implementation
   * by writing this:
   *
   * <p><A, B, C> Function<A, C> partialB(B b, Function<A, Function<B, C>> f) { return a ->
   *
   * <p>Here, a is a variable of type A. After the right arrow, you must write an expression that’s
   * composed of the function f and the variables a and b, and it must evaluate to a function from A
   * to C. The function f is a function from A to B -> C, so you can start by applying it to the A
   * you have:
   *
   * <p><A, B, C> Function<A, C> partialB(B b, Function<A, Function<B, C>> f) { return a ->
   * f.apply(a)
   *
   * <p>This gives you a function from B to C. You need a C, and you already have a B, so once
   * again, the answer is straightforward:
   *
   * <p><A, B, C> Function<A, C> partialB(B b, Function<A, Function<B, C>> f) { return a ->
   * f.apply(a).apply(b); }
   *
   * <p>That’s it! In fact, you had nearly nothing to do but to follow the types.
   *
   * <p>As I said, the most important thing is that you had a curried version of the function.
   * You’ll probably learn quickly how to write curried functions directly. One task that comes back
   * frequently when starting to write functional Java programs is converting methods with several
   * arguments into curried functions. This is extremely simple.
   */
  static <A, B, C> Function<A, C> partialB(B b, Function<A, Function<B, C>> f) {
  return a -> f.apply(a).apply(b);
 }

  // Write a functional method to partially apply a curried function of two arguments to its first
  // argument.

  /**
   * You have nothing to do! The signature of this method is as follows:
   *
   * <p><A, B, C> Function<B, C> partialA(A a, Function<A, Function<B, C>> f)
   *
   * <p>You can see immediately that partially applying the first argument is as simple as applying
   * the second argument (a function) to the first one:
   *
   * <p><A, B, C> Function<B, C> partialA(A a, Function<A, Function<B, C>> f) { return f.apply(a); }
   *
   * <p>(If you’d like to see an example of how partialA may be used, please look at the unit test
   * for this exercise, in the accompanying code.)
   *
   * <p>You may note that the original function was of type Function<A, Function<B, C>>, which means
   * A → B → C. What if you want to partially apply this function to the second argument?
   */
  static <A, B, C> Function<B, C> partialA(A a, Function<A, Function<B, C>> f) {
  return f.apply(a);
 }

  // Convert the following method into a curried function:
  //
  // <A, B, C, D> String func(A a, B b, C c, D d) {
  //  return String.format("%s, %s, %s, %s", a, b, c, d);
  // }

 static <A, B, C, D> Function<A, Function<B, Function<C, Function<D, String>>>> function(){
   return a -> b -> c -> d -> String.format("%s, %s, %s, %s, ", a, b, c, d);
 }

  public static void main(String[] args) {
  Function<Double, Function<Double, Double>> f = x -> g ->  x  * g;
   Function<Double, Double> f1 = CurryFunctions.partialB(2D, f);
   Function<Double, Double> f2 = CurryFunctions.partialB(3D, f);
   Function<Double, Double> f3 = CurryFunctions.partialB(4D, f);
   System.out.println(f1.apply(2D));
   System.out.println(f2.apply(2D));
   System.out.println(f3.apply(2D));

   Function<Double, Double> partialA = CurryFunctions.partialA(2D, f);
    System.out.println(partialA.apply(2D));

    System.out.println(function()
        .apply("Hello")
        .apply("Functional")
        .apply("Java")
        .apply("World"));

  }



}
