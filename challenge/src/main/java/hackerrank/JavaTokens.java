package main.java.hackerrank;

import java.util.Scanner;

/**
 * Given a string, , matching the regular expression [A-Za-z !,?._'@]+, split the string into
 * tokens. We define a token to be one or more consecutive English alphabetic letters. Then, print
 * the number of tokens, followed by each token on a new line.
 *
 * <p>Note: You may find the String.split method helpful in completing this challenge.
 *
 * <p>Input Format
 *    "He is a very very good boy, isn't he?"
 * <p>A single string, .
 *
 * <p>Constraints
 *
 * <p>is composed of the following: English alphabetic letters, blank spaces, exclamation
 * points (!), commas (,), question marks (?), periods (.), underscores (_), apostrophes ('), and at
 * symbols (@). Output Format
 *
 * <p>On the first line, print an integer, n , denoting the number of tokens in string (they do not
 * need to be unique). Next, print each of the tokens on a new line in the same order as they appear
 * in input string .
 *
 * @author Alexander Bravo
 */
public class JavaTokens {
  public static void main(String[] args) {
   Scanner scan = new Scanner(System.in);
   String s = scan.nextLine();

   // [^a-z A-Z] -> Matches a single character not present in the list below
   // given "I am very @ exited. isn't" will match a set of [@ . '] and then replaced with spaces.
   s = s.replaceAll("[^a-z A-Z]", " ");
   //\s+ -> matches any whitespace character (equivalent to [\r\n\t\f\v ])
   // + matches the previous token between one and unlimited times, as many times as
   s = s.replaceAll("\\s+", " ");
   s = s.trim();
   String[] tokens = s.split(" ");
   if (s.length() > 0){
    System.out.println(tokens.length);
    for (String token : tokens){
     System.out.println(token);
    }
   }else {
      System.out.println(0);
   }
   scan.close();
  }
}
