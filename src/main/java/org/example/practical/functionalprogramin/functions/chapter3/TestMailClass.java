package org.example.practical.functionalprogramin.functions.chapter3;

import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Bad implementation.
 *
 * 1. Lets firs creates an interface to support error handle. <code>Result</code>
 * 2. Now you need a functional interface representing an executable program <code>Executable </code>:
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
  validate("john.doe@acme.com").exec();
  validate(null).exec();
  validate("").exec();
  validate("paul.smith@acme.com").exec();
  }

  /**
   * New method where we can use our Result component. But this still isn’t satisfactory. Using
   * instanceof to determine whether the result is a success is ugly. And using a cast to access the
   * failure message is even more so. But worse than this is the fact that you have some program
   * logic in the validate method that can’t be tested. This is because the method is an effect,
   * which means it doesn’t return a value but mutates the outside world.
   *
   * <p>Executable
   *
   * <p>The validate method now returns Executable instead of void. It no longer has any side
   * effect, and it’s a pure function. When an Executable is returned , it can be executed by
   * calling its exec method.
   *
   * <p>Note that the Executable could also be passed to other methods or stored away to be executed
   * later. In particular, it could be put in a data structure and executed in sequence after all
   * computations are done. This allows you to separate the functional part of the program from the
   * part that mutates the environment.
   *
   * <p>You’ve also replaced the if … else control structure with the ternary operator. This is a
   * matter of preference. The ternary operator is functional because it returns a value and has no
   * side effect. In contrast, the if … else structure can be made functional by making it mutate
   * only local variables, but it can also have side effects. If you see imperative programs with
   * many embedded if … else structures, ask yourself how easy it would be to replace them with the
   * ternary operator. This is often a good indication of how close to functional the design is.
   * Note, however, that it’s also possible to make the ternary operator nonfunctional by calling
   * nonfunctional methods to get the resulting values.
   *
   * @param s mail sent to.
   */
  static Executable validate(String s) {
  Result result = emailChecker.apply(s);
  return  (result instanceof Result.Success)
      ? () -> sendVerificationMail(s)
      : () -> logError(((Result.Failure) result).getMessage());
  }

 }


