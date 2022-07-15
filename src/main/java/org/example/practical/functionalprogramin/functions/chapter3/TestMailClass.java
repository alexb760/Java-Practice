package org.example.practical.functionalprogramin.functions.chapter3;

import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Bad implementation.
 *
 * 1. Lets firs creates an interface to support error handle. <code>Result</code>
 *
 * @author Alexander Bravo
 */
public class TestMailClass {
 final static Pattern emailPattern = Pattern.compile("^[a-z0-9._%+-]+@[a-z-9.-]+\\.[a-z]{2,4}$");

 static Function<String, Result> emailChecker = s -> {
  if (s == null) {
   return new Result.Failure("email must not be null");
  } else if (s.length() == 0) {
   return new Result.Failure("email must not be empty");
  } else if (emailPattern.matcher(s).matches()) {
   return new Result.Success();
  } else {
   return new Result.Failure("email" + s + " is invalid.");
  }
 };

 static void testMail(String mail){
  if (emailPattern.matcher(mail).matches()){
   sendVerificationMail(mail);
  }else {
   logError("email " + mail + " is invalid");
  }

 }

 private static void sendVerificationMail(String mail) {
    System.out.println("sending mail to..." + mail);
 }

 private static void logError(String s) {
    System.out.println(s);
 }

 public static void main(String[] args) {
  validate("john.doe@acme.com");
  validate(null);
  validate("");
  validate("paul.smith@acme.com");
  }

 /**
  * New method where we can use our Result component.
  * But this still isn’t satisfactory. Using instanceof to determine whether the result
  * is a success is ugly. And using a cast to access the failure message is even more so.
  * But worse than this is the fact that you have some program logic in the validate method that can’t be tested.
  * This is because the method is an effect, which means it doesn’t return a value but mutates the outside world.
  *
  * @param s mail sent to.
  */
 static void validate(String s) {
  Result result = emailChecker.apply(s);
  if (result instanceof Result.Success) {
   sendVerificationMail(s);
  } else {
   logError(((Result.Failure) result).getMessage());
  }
 }

}

