package org.practice.hackerrank.Strings;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

/**
 * You are given a string containing characters A and B
 * only. Your task is to change it into a string such that there are no matching adjacent characters. To do this, you are allowed to delete zero or more characters in the string.
 *
 * Your task is to find the minimum number of required deletions.
 *
 * Example
 * s = 'AABAAB'
 * Remove an 'A' at positions 0 and 3 to make s = 'ABAB' in deletions.
 */
public class AlternatingCharacterProblem {

    public static int alternatingCharacters(String s) {
        char[] chars = s.toCharArray();
        int count = 0;
        Character previosA = null;
        int index = 0;
        for (int i = 0; i < chars.length; i++) {
            previosA = chars[i];
            for (int j = i+1; j < chars.length && previosA.compareTo(chars[j]) == 0 ; j++) {
                if (previosA.compareTo(chars[j]) == 0) {
                    count++;
                    index = j;
                }
                i = index;
            }
        }
        return count;
    }

    public static int alternatingCharactersAI(String s) {
        char[] chars = s.toCharArray();
        int count = 0;
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == chars[i + 1]) {
                count++;
            }
        }
        return count;
    }



    public static void main(String[] args) {
        System.out.println(alternatingCharacters("AABAAB"));
        System.out.println(alternatingCharacters("AAAA"));
        System.out.println(alternatingCharacters("ABABABAB"));
        System.out.println(alternatingCharacters("BABABA"));
        System.out.println(alternatingCharactersAI("AAABBB"));
    }
}
