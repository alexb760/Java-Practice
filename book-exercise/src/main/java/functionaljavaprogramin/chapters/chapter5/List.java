package functionaljavaprogramin.chapters.chapter5;

/**
 * The list is created as abstract class, parameterized by the type of its elements by the type
 * variable <A>.
 *
 *  Singly list implementation.
 *
 * @author Alexander Bravo
 */
abstract class List<A> {
  public abstract A head();

  public abstract List<A> tail();

  public abstract boolean isEmpty();

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
}
