package functionaljavaprogramin.chpates.chapter5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.practice.functionaljavaprogramin.chapters.chapter5.List.list;

import org.junit.jupiter.api.Test;
import org.practice.functionaljavaprogramin.chapters.chapter5.List;

/**
 * @author Alexander Bravo
 */
public class exercise_5_3 {

 @Test
 public void testDropWhile(){

  assertEquals("[NIL]", List.<Integer>list().dropWhile(x -> x < 3).toString());
  assertEquals("[1, 2, 3, NIL]", list(1, 2, 3).dropWhile(x -> x > 3).toString());
  assertEquals("[3, NIL]", list(1, 2, 3).dropWhile(x -> x < 3).toString());
  assertEquals("[NIL]", list(1, 2, 3).dropWhile(x -> x < 5).toString());
 }
}
