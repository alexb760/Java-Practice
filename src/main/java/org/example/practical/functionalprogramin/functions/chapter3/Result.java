package org.example.practical.functionalprogramin.functions.chapter3;


/**
 * First define a special component to handle the result of the computation.
 *
 * @author Alexander Bravo
 */
public interface Result {
 public class Success implements Result{}
 public class Failure implements Result{
  private final String message;

  public Failure(String messageError) {
   message = messageError;
  }

  public String getMessage() {
   return message;
  }
 }
}
