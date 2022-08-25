package functionaljavaprogramin.chapters.chapter5;

import functionaljavaprogramin.chapters.chapter4.TailCall;
import java.util.function.Function;

/**
 * The list is created as abstract class, parameterized by the type of its elements by the type
 * variable <A>.
 *
 * <p>Singly linked list implementation.
 *
 * @author Alexander Bravo
 */
public abstract class List<A> {
  public abstract A head();

  public abstract List<A> tail();

  public abstract boolean isEmpty();
  // Ex 5.2:
  // Implement setHead, an instance method for replacing the first element of a List with a new
  // value.
  public abstract List<A> setHead(A h);
  // Ex 5.3:
  // Write a toString method to display the content of a list. An empty list will be displayed as
  // [NIL], and a list containing the integers from 1 to 3 will be displayed as [1, 2, 3, NIL].
  // For a list of arbitrary objects, the toString method will be called to display each object.
  public abstract String toString();
  // Ex 5.4
  public abstract List<A> drop(int elements);
  // Ex 5.5:
  /**
   * Note that when calling dropWhile on an empty list, you may face a problem. The following
   * code, for example, won’t compile:
   *<p> list().dropWhile(f)
   *<p>
   * The reason for this is that Java is unable to infer the type of the list from the function
   * you pass to the dropWhile method. Let’s say you’re dealing with a list of integers. You can
   * then use either this solution:
   *<p> List<Integer> list = list();
   *<p> list.dropWhile(f);
   *<p> or this one:
   *<p> List.<Integer>list().dropWhile(f);
   *
   * @param condition Condition to evaluate.
   * @return List after condition evaluation.
   */
  public abstract List<A> dropWhile(Function<A, Boolean> condition);
  public abstract List<A> reverse();
  //Ex. 5.9
  public abstract int length();
  // Ex. 5.10
  // The Nil implementation will obviously return identity. For the Cons implementation, start with
  // defining a front-end method foldLeft calling a stack-based tail recursive helper method
  // foldLeft_ with an accumulator acc initialized to identity and a reference to this:
  public abstract <B> B foldLeft(B identity, Function<B, Function<A, B>> f);

  // Singleton instance representing an empty list
  public static final List NIL = new Nil();

  private List() {}

  private static class Nil<A> extends List<A> {

    /** private constructor to Nil (Not in List) */
    private Nil() {}

    @Override
    public A head() {
      throw new IllegalStateException("Head called in empty list");
    }

    @Override
    public List<A> tail() {
      throw new IllegalStateException("Tail called in empty list");
    }

    @Override
    public boolean isEmpty() {
      return true;
    }

    @Override
    public List<A> setHead(A h) {
      throw new IllegalStateException("setHead call on an empty list");
    }

    @Override
    public String toString() {
      return "[NIL]";
    }

    @Override
    public List<A> drop(int elements) {
      return this;
    }

    @Override
    public List<A> dropWhile(Function<A, Boolean> condition) {
      return this;
    }

    @Override
    public List<A> reverse() {
      return this;
    }

    @Override
    public int length() {
      return 0;
    }

    @Override
    public <B> B foldLeft(B identity, Function<B, Function<A, B>> f) {
      return identity;
    }
  }

  // A cons (Constructor) subclass representing a non-empty list
  private static class Cons<A> extends List<A> {

    private final A head;
    private final List<A> tail;

    /**
     * A private constructor taking as parameters a head and tail.
     *
     * @param head Head list
     * @param tail Tail list
     */
    private Cons(A head, List<A> tail) {
      this.head = head;
      this.tail = tail;
    }

    @Override
    public A head() {
      return head;
    }

    @Override
    public List<A> tail() {
      return tail;
    }

    @Override
    public boolean isEmpty() {
      return false;
    }

    // The Cons implementation corresponds to the else clause of the static method:
    @Override
    public List<A> setHead(A h) {
      return new Cons<>(h, tail());
    }

    @Override
    public String toString() {
      return String.format("[%sNIL]", toString(new StringBuilder(), this).eval());
    }

    @Override
    public List<A> drop(int elements) {
      return elements <= 0 ? this : drop(this, elements).eval();
    }

    @Override
    public List<A> dropWhile(Function<A, Boolean> condition) {
      return dropWhile_(this, condition).eval();
    }

    @Override
    public List<A> reverse() {
      return reverse_(list(), this).eval();
    }

    @Override
    // Note that this implementation, beside being stack-based recursive, has very poor performance.
    // Even if transformed to heap-based, it’s still O(n), meaning the time needed to return the
    // length is proportional to the length of the list. In following chapters, you’ll see how to
    // get the length of a linked list in constant time.
    public int length() {
      return foldRight(this, 0, x -> y -> y + 1);
    }

    @Override
    public <B> B foldLeft(B identity, Function<B, Function<A, B>> f) {
      return foldLeft_(identity, this, f).eval();
    }

    private <B> TailCall<B> foldLeft_(B acc, List<A> list, Function<B, Function<A, B>> f) {
      return list.isEmpty()
          ? TailCall.Suspend.ret(acc)
          : TailCall.Suspend.sus(() -> foldLeft_(f.apply(acc).apply(list.head()), list.tail(), f));
    }

    private TailCall<List<A>> reverse_(List<A> acc, List<A> list){
      return list.isEmpty()
          ? TailCall.Suspend.ret(acc)
          : TailCall.Suspend.sus(() -> reverse_(new Cons<>(list.head(), acc), list.tail()));
    }

    // Ex. 5.6
    // No ready for production since list1 is too long, will overflow the stack.
    public  List<A> concat(List<A> list1, List<A> list2) {
      return list1.isEmpty() ? list2 : new Cons<>(list1.head(), concat(list1.tail(), list2));
    }

    // Ex 5.5
    // List need to be in order.
    private TailCall<List<A>> dropWhile_(List<A> list, Function<A, Boolean> condition) {
      return !list.isEmpty() && condition.apply(list.head())
          ? TailCall.Suspend.sus(() -> dropWhile_(list.tail(), condition))
          : TailCall.Suspend.ret(list);
    }

    // Ex 5.4
    private TailCall<List<A>> drop(List<A> list, int n) {
      return n <= 0 || list.isEmpty()
          ? TailCall.Suspend.ret(list)
          : TailCall.Suspend.sus(() -> drop(list.tail(), n - 1));
    }

    // Ex 5.3:
    // The cons method is recursive and uses a StringBuilder as the accumulator. Note that the
    // StringBuilder, although it’s a mutable object, has a functional-friendly append method,
    // because it returns the mutated StringBuilder instance
    private TailCall<StringBuilder> toString(StringBuilder acc, List<A> list) {
      return list.isEmpty()
          ? TailCall.Suspend.ret(acc)
          : TailCall.Suspend.sus(() -> toString(acc.append(list.head()).append(", "), list.tail()));
    }
  }

  // Static factory method for constructing an empty list.
  @SuppressWarnings("unchecked")
  public static <A> List<A> list() {
    return NIL;
  }

  @SafeVarargs
  public static <A> List<A> list(A... as) {
    List<A> n = list();
    for (int i = as.length - 1; i >= 0; i--) {
      n = new Cons<>(as[i], n);
    }
    return n;
  }

  /**
   * Ex 5.1: Implement the instance functional method cons, adding an element at the beginning of a
   * list. (Remember cons stands for construct.)
   *
   * @param a new head element
   * @return new immutable list.
   */
  public List<A> cons(A a) {
    return new Cons<>(a, this);
  }

  // Ex 5.2: Imperative way to set a setHead method.
  @Deprecated
  public static <A> List<A> setHeadImperative(List<A> list, A h) {
    if (list.isEmpty()) {
      throw new IllegalStateException("setHead called on an empty list");
    } else {
      return new Cons<>(h, list.tail());
    }
  }

  // And if you need a static method, it can simply call the instance implementation:
  public static <A> List<A> setHead(List<A> list, A h) {
    return list.setHead(h);
  }

  /**
   * The foldRight method uses recursion, but it’s not tail recursive, so it will rapidly overflow
   * the stack. How rapidly depends on several factors, the most important of which is the size of
   * the stack. In Java, the size of the stack is configurable through the -Xss command-line
   * parameter, but the major drawback is that the same size is used for all threads. Using a bigger
   * stack would be a waste of memory for most threads.
   *
   * <p>Instead of using foldRight, create a foldLeft method that’s tail recursive and can be made
   * stack-safe. Here’s its signature:
   *
   * <p>public abstract <B> B foldLeft(B identity, Function<B, Function<A, B>> f);
   *
   */
  public static <A, B> B foldRight(List<A> list, B identity, Function<A, Function<B, B>> f) {
    return list.isEmpty()
        ? identity
        : f.apply(list.head()).apply(foldRight(list.tail(), identity, f));
  }

  //Ej. 5.14:
  // Since foldRight is a stack-safe methos it has some limitations so.
  // we converted to a HEAP-SAFE implementation using recursion.
  public static <B, A> B foldRightHeapSafe(List<A> list, B identity, Function<A, Function<B, B>> f){
    return foldRight_Heap_safe(identity, list, f).eval();
  }

  // Heap-safe
  private static <A, B> TailCall<B> foldRight_Heap_safe(
      B acc,
      List<A> list,
      Function<A, Function<B, B>> f){
    return list.isEmpty()
        ? TailCall.Suspend.ret(acc)
        : TailCall.Suspend.sus(
            () -> foldRight_Heap_safe(f.apply(list.head()).apply(acc), list.tail(), f));
  }



}
