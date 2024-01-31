package org.practice.functionaljavaprogramin.chapters.chapter3;

/**
 * First define a special component to handle the result of the computation.
 *
 * @author Alexander Bravo
 */
public interface Result<T> {
  void bind(Effect<T> success, Effect<String> failure);

  static <T> Result<T> failure(String message) {
    return new Failure<>(message);
  }

  static <T> Result<T> success(T value) {
    return new Success<>(value);
  }

  class Success<T> implements Result<T> {
    private final T value;

    public Success(T value) {
      this.value = value;
    }

    @Override
    public void bind(Effect<T> success, Effect<String> failure) {
      success.apply(value);
    }
  }

  class Failure<T> implements Result<T> {
    private final String message;

    public Failure(String messageError) {
      message = messageError;
    }

    public String getMessage() {
      return message;
    }

    @Override
    public void bind(Effect<T> success, Effect<String> failure) {
      failure.apply(message);
    }
  }
}
