package functionaljavaprogramin.chapters.chapter4;

import static functionaljavaprogramin.chapters.chapter4.TailCall.Suspend.ret;
import static functionaljavaprogramin.chapters.chapter4.TailCall.Suspend.sus;

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

  public static void main(String[] args) {
    // recursion
    //System.out.println(addRec( 5, 20000));

   System.out.println(addRecFunctional( 5, 1_000_000_000 ).eval());

  }
}
