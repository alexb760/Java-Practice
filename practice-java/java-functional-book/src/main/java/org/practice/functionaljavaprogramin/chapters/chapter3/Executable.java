package org.practice.functionaljavaprogramin.chapters.chapter3;

/**
 * You could have used the standard Runnable interface,
 * but most code verifiers raise a warning if this interface is used for something
 * other than running a thread. So you’ll use your own interface.
 *
 * @author Alexander Bravo
 */
public interface Executable {
 void exec();
}
