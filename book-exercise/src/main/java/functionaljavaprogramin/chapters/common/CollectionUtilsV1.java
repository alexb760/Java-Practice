package functionaljavaprogramin.chapters.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @author Alexander Bravo
 */
public class CollectionUtilsV1 {


 public static <T> List<T> list() {
  return Collections.emptyList();
 }

 public static <T> List<T> list(T t) {
  return Collections.singletonList(t);
 }

 public static <T> List<T> list(List<T> ts) {
  return Collections.unmodifiableList(new ArrayList<>(ts));
 }

 @SafeVarargs
 public static <T> List<T> list(T... t) {
  return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(t, t.length)));
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

 public static <T> List<T> tail(List<T> list) {
  if (list.size() == 0) {
   throw new IllegalStateException("tail of empty list");
  }
  List<T> workList = copy(list);
  workList.remove(0);
  return Collections.unmodifiableList(workList);
 }

 public static <T, U> U foldLeft(List<T> ts, U identity, Function<U, Function<T, U>> f) {
  U result = identity;
  for (T t : ts) {
   result = f.apply(result).apply(t);
  }
  return result;
 }

 public static <T, U> U foldRight(List<T> ts, U identity, Function<T, Function<U, U>> f) {
  return ts.isEmpty() ? identity : f.apply(head(ts)).apply(foldRight(tail(ts), identity, f));
 }

 public static <T> List<T> append(List<T> list, T t) {
  List<T> ts = copy(list);
  ts.add(t);
  return Collections.unmodifiableList(ts);
 }

 public static <T> List<T> prepend(T t, List<T> list) {
  return foldLeft(list, list(t), a -> b -> append(a, b));
 }

 public static <T> List<T> reverse(List<T> list) {
  return foldLeft(list, list(), x -> y -> prepend(y, x));
 }

 public static <T> List<T> reverse2(List<T> list) {
  return foldLeft(list, list(), x -> y -> foldLeft(x, list(y), a -> b -> append(a, b)));
 }

 public static <T, U> List<U> map(List<T> list, Function<T, U> f) {
  return mapViaFoldLeft(list, f);
 }

 public static <T, U> List<U> mapViaFoldLeft(List<T> list, Function<T, U> f) {
  return foldLeft(list, list(), x -> y -> append(x, f.apply(y)));
 }

 public static <T, U> List<U> mapViaFoldRight(List<T> list, Function<T, U> f) {
  return foldRight(list, list(), x -> y -> prepend(f.apply(x), y));
 }

 public static <T> List<T> unfold(T seed, Function<T, T> f, Function<T, Boolean> p) {
  List<T> result = new ArrayList<>();
  T temp = seed;
  while (p.apply(temp)) {
   result = append(result, temp);
   temp = f.apply(temp);
  }
  return result;
 }

 public static List<Integer> range(int start, int end) {
  return unfold(start, x -> x + 1, x -> x < end);
 }

 public static <T> List<T> iterate(T seed, Function<T, T> f, int n) {
  List<T> result = new ArrayList<>();
  T temp = seed;
  for (int i = 0; i < n; i++) {
   result.add(temp);
   temp = f.apply(temp);
  }
  return result;
 }

 public static <T> List<T> iterate(T seed, Function<T, T> f, Function<T, Boolean> p) {
  List<T> result = new ArrayList<>();
  T temp = seed;
  while (p.apply(temp)) {
   result.add(temp);
   temp = f.apply(temp);
  }
  return result;
 }
}
