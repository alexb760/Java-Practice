package org.practice.functionaljavaprogramin.chapters.chapter5;

/**
 * @author Alexander Bravo
 */
public class OperarionWhitFolding {

  public static Integer sumViaFoldingLeft(List<Integer> list) {
    return list.foldLeft(0, x -> y -> y + x);
  }

  public static Double productViaFoldingLeft(List<Double> list) {
    return list.foldLeft(1.0, x -> y -> y * x);
  }

  // Note that once again, the second parameter of method length (representing each element of the
  // list on each recursive call of the method) is ignored. This method is as inefficient as the
  // previous one and should not be used in production code.
  public static <A> Integer lenghtViaFoldingLeft(List<A> list) {
    return list.foldLeft(0, x -> ignore -> x + 1);
  }
}
