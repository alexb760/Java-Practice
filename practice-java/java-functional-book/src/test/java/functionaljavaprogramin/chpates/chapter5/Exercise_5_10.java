package functionaljavaprogramin.chpates.chapter5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.practice.functionaljavaprogramin.chapters.chapter5.List.list;

import org.junit.jupiter.api.Test;
import org.practice.functionaljavaprogramin.chapters.chapter5.List;

/**
 * @author Alexander Bravo
 */
public class Exercise_5_10 {
  @Test
  public void testFoldLeft() {
    assertEquals(Integer.valueOf(0), List.<Integer>list().foldLeft(0, x -> y -> x + y));
    assertEquals(Integer.valueOf(6), list(1, 2, 3).foldLeft(0, x -> y -> x + y));
    assertEquals(Double.valueOf(1.0), List.<Double>list().foldLeft(1.0, x -> y -> x * y));
    assertEquals(Double.valueOf(24.0), list(1.0, 2.0, 3.0, 4.0).foldLeft(1.0, x -> y -> x * y));
  }

  @Test
  public void testFoldLeftLargeList() {
    List<Integer> list = list();
    for (int i = 0; i < 1_000_000; i++) {
      list.cons(i);
    }
    assertEquals(1, list.foldLeft(1, x -> y -> y * x));
  }
}
