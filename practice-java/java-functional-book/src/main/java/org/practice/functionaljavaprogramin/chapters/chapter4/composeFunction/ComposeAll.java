package org.practice.functionaljavaprogramin.chapters.chapter4.composeFunction;

import org.practice.functionaljavaprogramin.chapters.chapter4.CollectionUtilities;

import static org.practice.functionaljavaprogramin.chapters.chapter4.CollectionUtilities.foldLeft;
import static org.practice.functionaljavaprogramin.chapters.chapter4.CollectionUtilities.foldRight;
import static org.practice.functionaljavaprogramin.chapters.chapter4.CollectionUtilities.reverse;

import java.util.List;
import java.util.function.Function;

/**
 * @author Alexander Bravo
 */
public class ComposeAll {
 static <T> Function<T, T> composeAllViaFoldLeft(List<Function<T, T>> list) {
  return x -> foldLeft(reverse(list), x, a -> b -> b.apply(a));
 }

 static <T> Function<T, T> composeAllViaFoldRight(List<Function<T, T>> list) {
  return x -> foldRight(list, x, a -> a::apply);
 }

 static <T> Function<T, T> andThenAllViaFoldLeft(List<Function<T, T>> list) {
  return x -> foldLeft(list, x, a -> b -> b.apply(a));
 }

 static <T> Function<T, T> andThenAllViaFoldRight(List<Function<T, T>> list) {
  return x -> foldRight(reverse(list), x, a -> a::apply);
 }
}



class main {
  public static void main(String[] args) {
   //We’ve implemented andThenAll rather than composeAll! To get the correct result, you first have to reverse the list:
   Function<String, String> f1 = x -> "(a" + x + ")";
   Function<String, String> f2 = x -> "{b" + x + "}";
   Function<String, String> f3 = x -> "[c" + x + "]";
   System.out.println(ComposeAll.composeAllViaFoldLeft(CollectionUtilities.list(f1, f2, f3)).apply("x"));
   System.out.println(ComposeAll.composeAllViaFoldRight(CollectionUtilities.list(f1, f2, f3)).apply("x"));
  }
}
