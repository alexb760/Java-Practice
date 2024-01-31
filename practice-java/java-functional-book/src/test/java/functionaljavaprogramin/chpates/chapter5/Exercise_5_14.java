package functionaljavaprogramin.chpates.chapter5;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.practice.functionaljavaprogramin.chapters.chapter5.List.list;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.practice.functionaljavaprogramin.chapters.chapter5.List;

/**
 * Testing exercise 5.14 folding right heap-safe and stack-safe (non-productive method)
 *
 * @author Alexander Bravo
 */
public class Exercise_5_14 {

 @Test
 public void testFoldRightSum(){
  List<Integer> list = list(1, 2, 3, 4, 5, 6, 8, 7, 8);
  Integer sum = List.foldRightHeapSafe(list, 0, x -> y -> x + y);
  Assertions.assertEquals(44, sum);
 }

// @Test
 @RepeatedTest(5)
 public void testFoldRightLargeList() {
  List<Integer> list = list();
  for (int i = 0; i < 1_000_000; i++) {
   list.cons(i);
  }
  assertEquals(1, List.foldRightHeapSafe (list, 1, x -> y -> y * x));
 }
}
