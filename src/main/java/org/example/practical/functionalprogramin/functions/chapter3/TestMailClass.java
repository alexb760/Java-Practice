package org.example.practical.functionalprogramin.functions.chapter3;

import static org.example.practical.functionalprogramin.functions.chapter3.supliers.Case.match;
import static org.example.practical.functionalprogramin.functions.chapter3.supliers.Case.mcase;

import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Bad implementation.
 *
 * <p>1. Lets firs creates an interface to support error handle. <code>Result</code> 2. Now you need
 * a functional interface representing an executable program <code>Executable </code>. 3. Lets
 * decouple <code>validate()</code> from effects <code>sendVerificationMail</code> and <code>
 * logError</code> by doing that also we need to bind a <code>Result</code> to an effect. so lets
 * creates a interface <code>Effect</code>
 *
 * 3. Lest get rid of if and else conditions by implementing <code>Suppliers</code>
 *
 * @author Alexander Bravo
 */
public class TestMailClass {
  static final Pattern emailPattern = Pattern.compile("^[a-z0-9._%+-]+@[a-z-9.-]+\\.[a-z]{2,4}$");

  static Effect<String> success = s -> System.out.println("mail sent to: " + s);
  static Effect<String> failure = s -> System.out.println("Error message logged: " + s);

  static Function<String, Result<String>> emailChecker =
      s -> {
        if (s == null) {
          return Result.failure("email must not be null");
        } else if (s.length() == 0) {
          return Result.failure("email must not be empty");
        } else if (emailPattern.matcher(s).matches()) {
          return Result.success(s);
        } else {
          return Result.failure("email" + s + " is invalid.");
        }
      };

  static Function<String, Result<String>> emailSupplierChecker = s ->
      match(
          mcase(() -> Result.success(s)),
          mcase(() -> s == null, ()-> Result.failure("Email must not be null")),
          mcase(()-> s.length() == 0, () -> Result.failure("mail can not be empty")),
          mcase(()-> !emailPattern.matcher(s).matches(), ()-> Result.failure("email" +s+ " is invalid")));


  static void testMail(String mail) {
    if (emailPattern.matcher(mail).matches()) {
      sendVerificationMail(mail);
    } else {
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
    emailChecker
        .apply("john.doe@acme.com")
        .bind(TestMailClass::sendVerificationMail, TestMailClass::logError);
    emailChecker.apply(null).bind(TestMailClass::sendVerificationMail, TestMailClass::logError);
    emailChecker.apply("").bind(TestMailClass::sendVerificationMail, TestMailClass::logError);

    // Just a different way using Effect interface function.
    emailChecker.apply("this.mail@mail.com").bind(success, failure);
    emailChecker.apply("").bind(success, failure);
    emailChecker.apply(null).bind(success, failure);

    //  validate("john.doe@acme.com").exec();
    //  validate(null).exec();
    //  validate("").exec();
    //  validate("paul.smith@acme.com").exec();

    // Full functional implementation.
    System.out.println("###### Full Functional #####");
    emailSupplierChecker.apply("").bind(success, failure);
    emailSupplierChecker.apply("this.mail@mail.com").bind(success, failure);
    emailSupplierChecker.apply(null).bind(success, failure);
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
    return (result instanceof Result.Success)
        ? () -> sendVerificationMail(s)
        : () -> logError(((Result.Failure) result).getMessage());
  }
}
