package org.example.practical.functionalprogramin.functions.chapter3;


/**
 * @author Alexander Bravo
 */
public interface Result {
 public class Success implements Result{}
 public class Failure implements Result{
  private final String MessageError;

  public Failure(String messageError) {
   MessageError = messageError;
  }

  public String getMessageError() {
   return MessageError;
  }
 }
}
