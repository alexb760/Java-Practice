package functionaljavaprogramin.chapters.chapter3;

/**
 * you’ll need an interface with a single method to represent an effect. Second, because the
 * emailChecker function returns a Result, the validate method could return this Result. In such a
 * case, you’d no longer need the validate method. Third, you’ll need to "bind" an effect to the
 * Result. But because the result may be a success or a failure, it would be better to bind two
 * effects and let the Result class choose which one to apply.
 *
 * <p>You may prefer the Consumer interface of Java 8. Although the name was badly chosen, it does
 * the same job.
 *
 * @author Alexander Bravo
 */
public interface Effect<T> {
  void apply(T t);
}
