package org.example.practical.functionalprogramin.functions.chapter3;

import java.util.regex.Pattern;

/**
 * Bad implementation.
 *
 * 1. Lets firs creates an interface to support error handle. <code>Result</code>
 *
 * @author Alexander Bravo
 */
public class TestMailClass {
 final static Pattern emailPatter = Pattern.compile("^[a-z0-9._%+-]+@[a-z-9.-]+\\.[a-z]{2,4}$");

 static void testMail(String mail){
  if (emailPatter.matcher(mail).matches()){
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
  testMail("john.doe@acme.com");
  testMail(null);
  testMail("paul.smith@acme.com");
  }
}
