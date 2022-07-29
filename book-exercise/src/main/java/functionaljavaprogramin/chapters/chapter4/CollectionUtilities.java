package functionaljavaprogramin.chapters.chapter4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Collection Utilities using functional programing.
 *
 * @author Alexander Bravo
 */
public class CollectionUtilities {

  public static <T> List<T> list() {
    return Collections.emptyList();
  }

  public static <T> List<T> list(T t) {
    return Collections.singletonList(t);
  }

  public static <T> List<T> list(List<T> ts) {
    return List.copyOf(ts);
  }

  @SafeVarargs
  public static <T> List<T> list(T... t) {
    return List.of(Arrays.copyOf(t, t.length));
  }

  // The head() method is simple. If the list is empty, you throw an exception. Otherwise, you read
  // the element at index 0 and return it.
  public static <T> T head(List<T> list) {
    if (list.size() == 0) {
      throw new IllegalStateException("head of empty list");
    }
    return list.get(0);
  }

  // The copy method is also basic. It’s the same as the list-creation method, taking a list as its
  // argument.
  // Note that copy is private. It returns a mutable list. To make a copy from the outside, you can
  // call list(List<T>), which returns an immutable list. Also note that this example throws
  // exceptions when calling head or tail on an empty list. This isn’t functional, because you
  // should always catch exceptions but never throw them in order to be referentially transparent.
  // It is, however, simpler at this stage. In Chapter 5, when you look at functional lists, you’ll
  // see that the head and tail methods will be declared protected. This way, they’ll be usable only
  // inside the List class, and no exception will ever leak out of this class.
  private static <T> List<T> copy(List<T> ts) {
    return new ArrayList<>(ts);
  }

  // The tail method is slightly more complex. It must make a copy of its argument, remove the first
  // element, and return the result:
  public static <T> List<T> tail(List<T> list) {
    if (list.size() == 0) {
      throw new IllegalStateException("tail of empty list");
    }
    List<T> workList = copy(list);
    workList.remove(0);
    return Collections.unmodifiableList(workList);
  }

  public static <T> List<T> append(List<T> list, T t) {
    List<T> ts = copy(list);
    // Add operation is an imperative program
    // This operation is not usable in functional programing it mutates the argument.
    ts.add(t);
    return Collections.unmodifiableList(ts);
  }

  // List folding transforms a list into a single value by using a specific operation. The resulting
  // value may be of any type—it doesn’t have to be of the same type as the elements of the list.
  // Folding to a result that’s the same type as the list elements is a specific case called
  // reducing. Computing the sum of the elements of a list of integers is a simple case of reducing.
  public static Integer fold(
      List<Integer> is, Integer identity, Function<Integer, Function<Integer, Integer>> f) {
    int result = identity;
    for (Integer i : is) {
      result = f.apply(result).apply(i);
    }
    return result;
  }

  public static <T, U> U foldLeft(List<T> ts, U identity, Function<U, Function<T, U>> f) {
    U result = identity;
    for (T t : ts) {
      result = f.apply(result).apply(t);
    }
    return result;
  }

  // Write a recursive version of foldRight. Beware that a naive recursive version won’t fully work
  // in Java because it uses the stack to accumulate intermediate calculations. In Chapter 4, you’ll
  // learn how to make stack-safe recursion available.
  // The naive version will work for at least 5,000 elements, which is enough for an exercise:
  public static <T, U> U foldRight(List<T> ts, U identity, Function<T, Function<U, U>> f) {
    // tail recursive
    return ts.isEmpty() ? identity : f.apply(head(ts)).apply(foldRight(tail(ts), identity, f));
  }

  // Recursive stack-safe.
  public static <T, U> TailCall<U> _foldRight(
      List<T> ts, U identity, Function<U, Function<T, U>> f) {
    return ts.isEmpty()
        ? TailCall.Suspend.ret(identity)
        : TailCall.Suspend.sus(() -> _foldRight(tail(ts), f.apply(identity).apply(head(ts)), f));
  }

  // range function  recursive by using an accumulator
  public static List<Integer> rangeAcc(List<Integer> acc, Integer start, Integer end) {
    return end <= start ? acc : rangeAcc(append(acc, start), start + 1, end);
  }

  // must turn this method into a main method and a helper method by using true recursion:
  public static List<Integer> range(Integer start, Integer end) {
    return _rangeAcc(list(), start, end).eval();
  }

  //Recursive using stack-safe recursion.
  private static TailCall<List<Integer>> _rangeAcc(List<Integer> acc, Integer start, Integer end) {
    return end <= start
        ? TailCall.Suspend.ret(acc)
        : TailCall.Suspend.sus(() -> _rangeAcc(append(acc, start), start + 1, end));
  }
}
