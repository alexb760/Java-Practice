package functionaljavaprogramin.chpates.chapter5;

import static functionaljavaprogramin.chapters.chapter5.List.list;
import static org.junit.jupiter.api.Assertions.assertEquals;
import functionaljavaprogramin.chapters.chapter5.List;
import org.junit.jupiter.api.Test;

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
