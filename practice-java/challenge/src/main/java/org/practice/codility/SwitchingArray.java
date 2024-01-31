package org.practice.codility;

/**
 * @author Alexander Bravo
 */
public class SwitchingArray {
  public static int solution(int[] A) {
    if (A.length == 1 || A.length == 2) {
      return A.length;
    }
    int index = 0;
    int slideArray = 2;
    int j = 2;
    while (j < A.length) {
      if (A[j] != A[j - 2]) {
        slideArray = Math.max(slideArray, j - index);
        index = j - 1;
      }
      j++;
    }
    slideArray = Math.max(slideArray, j - index);
    return slideArray;
  }

  public static void main(String[] args) {
    int[] A = {7, 4, -2, 4, -2, -9};
    int[] B = {7, -5, -5, -5, 7, -1, 7};
    System.out.println(solution(B));
  }
}
