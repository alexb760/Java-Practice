package org.practice;

import java.util.Map;
import java.util.Optional;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** @author Alexander Bravo */
enum Operations {
  ADD(Integer::sum, "+"),
  SUBTRACT((x, y) -> x - y, "-"),
  MULTIPLY((x, y) -> x * y, "*"),
  DIVIDE((x, y) -> x / y, "/");

    // There are two ways to initialize all the values
    // Best performance static initializer
    //    static {
    //        for (Operations o : values()){
    //            VALUE_MAP.put(o.databaseConvertor, o);
    //        }
    //    }
    // Or single java 8 style line
    private static final Map<String, Operations> VALUE_MAP =
        Stream.of(values()).collect(Collectors.toMap(o -> o.databaseConvertor, o -> o));


  private final IntBinaryOperator operator;
  private final String databaseConvertor;

  Operations(IntBinaryOperator operator, String databaseConvertor) {
    this.operator = operator;
    this.databaseConvertor = databaseConvertor;
  }

  public static Optional<Operations> fromString(String databaseValue) {
    return Optional.ofNullable(VALUE_MAP.get(databaseValue));
  }

  public String toDatabaseValue() {
    return databaseConvertor;
  }

  public IntBinaryOperator toBinaryOperator() {
    return operator;
  }
}
