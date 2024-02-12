package org.practice.hackerrank.dictionaries;

import java.util.HashSet;
import java.util.Set;

/**
 * Given two strings, determine if they share a common substring. A substring may be as small as one character.
 *
 * Example
 * S1 = 'and'
 * S2 = 'art'
 * These share the common substring a
 *
 * S1 = 'be'
 * S2 = 'cat'
 *
 * These do not share a substring.
 */
public class GivenTwoStringCommonSubstring {

    public static String twoStringDetailedWay(String s1, String s2){
        //my
        Set<String> dic = new HashSet<>();
        for (int index = 0; index < s1.length(); index++) {
            dic.add(s1.substring(index));
            dic.add(String.valueOf(s1.charAt(index)));
        }
        int count = 0;
        String sub;
        Character ch;
        for (int index = 0; index < s2.length(); index++) {
            sub = s2.substring(index);
            ch = s2.charAt(index);
            if (dic.contains(sub) || dic.contains(String.valueOf(ch))){
                count++;
            }
        }
        return count > 0 ? "YES" : "NO";
    }

    public static String twoStringOptimalSolution(String s1, String s2){
        Set<Character> dic = new HashSet<>();
        for (char c : s1.toCharArray()) {
            dic.add(c);
        }
        for (char c : s2.toCharArray()) {
            if (dic.contains(c)) {
                return "YES";
            }
        }
        return "NO";
    }

    public static void main(String[] args) {
        System.out.println(twoStringOptimalSolution("hackerrankcommunity", "cdecdecdecde"));
    }
}
