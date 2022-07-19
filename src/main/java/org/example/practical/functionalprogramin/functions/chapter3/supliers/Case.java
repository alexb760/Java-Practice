package org.example.practical.functionalprogramin.functions.chapter3.supliers;

import java.util.function.Supplier;
import org.example.practical.functionalprogramin.functions.chapter3.Result;
import org.example.practical.functionalprogramin.functions.chapter3.Tuple;

/**
 * I said that the class must represent a <code> Supplier<Boolean> </code> for the condition and a
 * <code> Supplier<Result<T>>> </code> for the resulting value. The simplest way to do this is to
 * define it as follows:
 *
 * @author Alexander Bravo
 */
public class Case<T> extends Tuple<Supplier<Boolean>, Supplier<Result<T>>> {

  public Case(Supplier<Boolean> booleanSupplier, Supplier<Result<T>> resultSupplier) {
    super(booleanSupplier, resultSupplier);
  }

  /**
   * Defines the normal case.
   *
   * @param condition Suppliers boolean condition
   * @param value If condition value to return.
   * @param <T> type of value to return.
   * @return A {@link Case} Case representation of the case.
   */
  public static <T> Case<T> mcase(Supplier<Boolean> condition, Supplier<Result<T>> value) {
    return new Case<>(condition, value);
  }

  public static <T> DefaultCase<T> mcase(Supplier<Result<T>> value) {
    return new DefaultCase<>(() -> true, value);
  }

  /**
   * As I previously mentioned, the default case has to come first in the argument list because the
   * second argument is a vararg, but this case is used last. You test all cases one by one by
   * evaluating them through a call to the get method. If the result is true, you return the
   * corresponding value after having evaluated it. If no case matches, the default case is used.
   *
   * <p>Note that evaluation means evaluation of the returned value. No effect is applied at this
   * time. The following listing shows the complete class.
   *
   * @param defaultCase if none of the matcher is true then default success case will be returned.
   * @param matchers List of cases to evaluate.
   * @param <T> Type of message to return.
   * @return {@link Result} representation of whatever the matcher return
   */
  @SafeVarargs
  public static <T> Result<T> match(DefaultCase<T> defaultCase, Case<T>... matchers) {
    for (Case<T> matcher : matchers) {
      if (matcher.get_1().get()) {
        return matcher.get_2().get();
      }
    }
    return defaultCase.get_2().get();
  }

  private static class DefaultCase<T> extends Case<T> {
    public DefaultCase(Supplier<Boolean> booleanSupplier, Supplier<Result<T>> resultSupplier) {
      super(booleanSupplier, resultSupplier);
    }
  }
}
