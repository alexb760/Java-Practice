package org.example.practical.functionalprogramin.functions.chapter4;

import java.util.function.Supplier;

/**
 * Abstraction class.
 *
 * <p>This {@link TailCall} abstract class has two subclasses. One represents an intermediate call,
 * when the processing of one step is suspended to call the method again for evaluating the next
 * step. This is represented by a subclass named Suspend. It’s instantiated with
 * Supplier<TailCall>>, which represents the next recursive call. This way, instead of putting all
 * TailCalls in a list, you’ll construct a linked list by linking each tail call to the next. The
 * benefit of this approach is that such a linked list is a stack, offering constant time insertion
 * as well as constant time access to the last inserted element, which is optimal for a LIFO
 * structure.
 *
 * <p>The isSuspend method returns true in Suspend, and false in Return. The following listing shows this first version.
 * @author Alexander Bravo
 */
public abstract class TailCall<T> {
  private TailCall() {}

  /**
   * @return Return next call.
   */
  public abstract TailCall<T> resume();

  /**
   * Return the result of the whole operations.
   *
   * @return Return a T value.
   */
  public abstract T eval();

  /**
   * Determine whether a method is suspended or not.
   *
   * @return true or false if method is suspended or not.
   */
  public abstract boolean isSuspend();

  public static class Return<T> extends TailCall<T> {

    private final T t;

    public Return(T t) {
      this.t = t;
    }

    // The resume method has no implementation in Return and will throw a runtime exception.
    @Override
    public TailCall<T> resume() {
      throw new IllegalStateException("Return has not resume");
    }

    // The eval method returns the result stored in the Return class. In the first version, it’ll
    // throw a runtime exception if called on the Suspend class.
    @Override
    public T eval() {
      return t;
    }

    @Override
    public boolean isSuspend() {
      return false;
    }
  }

  public static class Suspend<T> extends TailCall<T> {

    private final Supplier<TailCall<T>> resume;

    public Suspend(Supplier<TailCall<T>> resume) {
      this.resume = resume;
    }

    @Override
    public TailCall<T> resume() {
      return resume.get();
    }

    @Override
    public T eval() {
      TailCall<T> tailRec = this;
      while (tailRec.isSuspend()) {
        tailRec = tailRec.resume();
      }
      return tailRec.eval();
    }

    @Override
    public boolean isSuspend() {
      return true;
    }

    public static <T> Return<T> ret(T t) {
      return new Return<>(t);
    }

    public static <T> Suspend<T> sus(Supplier<TailCall<T>> s) {
      return new Suspend<>(s);
    }
  }
}
