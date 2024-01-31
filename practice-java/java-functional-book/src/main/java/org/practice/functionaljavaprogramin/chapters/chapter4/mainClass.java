package org.practice.functionaljavaprogramin.chapters.chapter4;

import java.util.List;
import java.util.function.Function;

import static org.practice.functionaljavaprogramin.chapters.chapter4.TailCall.Suspend.ret;
import static org.practice.functionaljavaprogramin.chapters.chapter4.TailCall.Suspend.sus;

/**
 * @author Alexander Bravo
 */
public class mainClass {

 // Corecursion
 static int add(int x , int y){
  while (--y >= 0) {
   ++x;
  }
  return x;
 }

 // Recursion.
 static int addRec(int x, int y){
  return y == 0
      ? x
      : addRec(x+2, --y);
 }

 //Functional Recursion
 public static int addFunctional(int x, int y) {
  return addRecFunctional(x, y).eval();
 }

 private static TailCall<Integer> addRecFunctional(int x, int y) {
  return y == 0
      ? ret(x)
      : sus(() -> addRecFunctional(x + 1, y - 1));
 }

 static String addSI(String s, Integer i) {
  return "(" + s + " +" + i + ")";
 }

 private static String addIS(Integer i, String s) {
  return "(" + i + " +" + s + ")";
 }

  public static void main(String[] args) {
    // recursion
    //System.out.println(addRec( 5, 20000));

//   System.out.println(addRecFunctional( 5, 1_000_000_000 ).eval());

   List<Integer> list = CollectionUtilities.list(1, 2, 3, 4, 5);
   String identity = "0";
   Function<String, Function<Integer, String>> f = x -> y -> addSI(x, y);
   Function<Integer, Function<String, String>> fRight = x -> y -> addIS(x, y);
    System.out.println(CollectionUtilities.foldLeft(list, identity, f));
    System.out.println(CollectionUtilities.foldRight(list, identity, fRight));
  }
}
