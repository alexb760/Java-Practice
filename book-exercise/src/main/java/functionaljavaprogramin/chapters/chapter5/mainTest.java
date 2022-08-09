package functionaljavaprogramin.chapters.chapter5;

import static functionaljavaprogramin.chapters.chapter5.List.list;

/**
 * @author Alexander Bravo
 */
public class mainTest {

  public static void main(String[] args) {
//    System.out.println(List.list().toString());
//    System.out.println(List.list(1,2,3,4,5,6,7,8,8,6,5,4,4,2,2).head());
//    System.out.println(List.list(1,2,3,4,5,6,7,8,8,6,5,4,4,2,2).tail());
    System.out.println(List.list(1,2,3,4,5,6,7,8,8,6,5,4,4,2,2).dropWhile(x -> x <= 7));
    System.out.println(List.list(1,2,3,4,5,6,7,8,8,6,5,4,4,2,2).drop(10));
    System.out.println(List.list(1,2,3,4,5,6,7,8,8,6,5,4,4,2,2).drop(10).setHead(10).cons(11));
  }
}
