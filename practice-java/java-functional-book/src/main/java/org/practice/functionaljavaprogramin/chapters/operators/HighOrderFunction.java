package org.practice.functionaljavaprogramin.chapters.operators;


/**
 * Exercise 2.3.3 High order function.
 *
 * @author Alexander Bravo */
interface OperatorFunctionHOF<T, U> {
  U apply(T arg);
}

public class HighOrderFunction {

  public static void main(String[] args) {
    OperatorFunctionHOF<
            OperatorFunctionHOF<Integer, Integer>,
            OperatorFunctionHOF<
                OperatorFunctionHOF<Integer, Integer>, OperatorFunctionHOF<Integer, Integer>>>
        compose = x -> y -> z -> x.apply(y.apply(z));

    // Applying code.
      OperatorFunctionHOF<Integer, Integer> triple = x -> x * 3;
      OperatorFunctionHOF<Integer, Integer> square = x -> x * x;

      OperatorFunctionHOF<Integer, Integer> functionHOF = compose.apply(square).apply(triple);

    System.out.println(functionHOF.apply(3));
  }
}
