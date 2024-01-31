package org.practice.codility;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Alexander Bravo
 */
public class WordMachine {

  public static int solution(String S) {
//    Stack<Integer> machine = new Stack<>();
    Deque<Integer> machine = new ArrayDeque<>();
    int hasLast = 0;
    int topMost = 0;
    try {
      for (String ope : S.split(" ")) {
        switch (ope) {
          case "DUP":
            machine.push(machine.peek());
            break;
          case "POP":
            machine.pop();
            break;
          case "+":
            machine.push(machine.pop() + machine.pop());
            break;
          case "-":
            machine.push(machine.pop() - machine.pop());
            break;
          default:
            machine.push(Integer.parseInt(ope));
        }
      }
    } catch (Exception e) {
      topMost = -1;
    }

    topMost = topMost == -1 ? topMost : machine.pop();
    return topMost >= 0xFFFFF ? -1 : topMost;
  }

  public static void main(String[] args) {
    String S = "4 5 6 - 7 +";
    String A = "13 DUP 4 POP 5 DUP + DUP + -";
    String C = "3 DUP 5 - -";
    String D = "5 6 + -";
    String B = "1048575 DUP +";
    //    System.out.println(0xFFFFF == 1048575);
    System.out.println(solution(S));
    System.out.println(solution(A));
    System.out.println(solution(C));
    System.out.println(solution(D));
    System.out.println(solution(B));
  }
}
