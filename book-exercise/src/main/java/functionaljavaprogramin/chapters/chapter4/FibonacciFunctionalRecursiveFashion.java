package functionaljavaprogramin.chapters.chapter4;

import java.math.BigInteger;

/**
 * @author Alexander Bravo
 */
public class FibonacciFunctionalRecursiveFashion {

  // If you run this test program, you’ll get the first 10 (or 9, according to the original
  // definition) Fibonacci numbers:
  //
  // 0 1 1 2 3 5 8 13 21 34 55
  //
  // Based on what you know about naive recursion in Java, you may think that this method will
  // succeed in calculating f(n) for n, up to 6,000 to 7,000 before overflowing the stack. Well,
  // let’s check it. Replace int n = 10 with int n = 6000 and see what happens. Launch the program
  // and take a coffee break. When you return, you’ll realize that the program is still running. It
  // will have reached somewhere around 1,836,311,903 (your mileage may vary—you could get a
  // negative number!), but it’ll never finish. No stack overflow, no exception—just hanging in the
  // wild. What’s happening?
  //
  // The problem is that each call to the function creates two recursive calls. So to calculate
  // f(n), you need 2n recursive calls. Let’s say your method needs 10 nanoseconds to execute. (Just
  // guessing, but you’ll see soon that it doesn’t change anything.) Calculating f(5000) will take
  // 25000 × 10 nanoseconds. Do you have any idea how long this is? This program will never
  // terminate because it would need longer than the expected duration of the solar system (if not
  // the universe!).
  static int _fibonacci(int number) {
    if (number == 0 || number == 1) return number;
    return _fibonacci(number - 1) + _fibonacci(number - 2);
  }

  // To make a usable Fibonacci function, you have to change it to use a single tail recursive call.
  // There’s also another problem: the results are so big that you’ll soon get an arithmetic
  // overflow, resulting in negative numbers.
  static BigInteger _fibonacciBigIntegerAccumulator(
      BigInteger acc1, BigInteger acc2, BigInteger x) {
    if (x.equals(BigInteger.ZERO)) return BigInteger.ZERO;
    if (x.equals(BigInteger.ONE)) return acc1.add(acc2);
    return _fibonacciBigIntegerAccumulator(acc2, acc1.add(acc2), x.subtract(BigInteger.ONE));
  }

  // Arithmetic overflow over element at 45 fibonacci position
  static int naiveFibonacciRecursion(int x) {
    return _fibonacci(x);
  }

  // This is only one possible implementation. You may organize accumulators, initial values, and
  // conditions in a slightly different manner, as long as it works. Now you can call fib(5000), and
  // it’ll give you the result in a couple of nanoseconds. Well, it’ll take a few dozen
  // milliseconds, but only because printing to the console is a slow operation. We’ll come back to
  // this shortly.
  //
  // The result is impressive, whether it’s the result of the computation (1,045 digits!) or the
  // increase in speed due to the transformation of a dual recursive call into a single one. But you
  // still can’t use the method with values higher than 20.000.
  static BigInteger singleTailRecursion(int x) {
    return _fibonacciBigIntegerAccumulator(BigInteger.ONE, BigInteger.ZERO, BigInteger.valueOf(x));
  }

  public static void main(String[] args) {
    //
    for (int i = 0; i <= 10000; i++) {
      //          System.out.println(naiveFibonacciRecursion(i));
      System.out.println(singleTailRecursion(i));
    }
  }
}
