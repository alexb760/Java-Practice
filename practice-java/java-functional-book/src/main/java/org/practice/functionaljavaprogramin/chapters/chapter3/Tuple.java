package org.practice.functionaljavaprogramin.chapters.chapter3;

/**
 * Since Java does not provide any in-build <code>Tuple</code> helpers.
 * We build our own tuple representation. a simple one.
 *
 * @author Alexander Bravo
 */
public class Tuple <K, V> {
    private final K _1;
    private final V _2;

    public Tuple(K _1, V _2) {
        this._1 = _1;
        this._2 = _2;
    }

    public K get_1() {
        return _1;
    }

    public V get_2() {
        return _2;
    }
}
