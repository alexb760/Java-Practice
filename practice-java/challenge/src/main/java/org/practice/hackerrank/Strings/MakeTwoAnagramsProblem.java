package org.practice.hackerrank.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A student is taking a cryptography class and has found anagrams to be very useful. Two strings are anagrams of each other if the first string's letters can be rearranged to form the second string. In other words, both strings must contain the same exact letters in the same exact frequency. For example, bacdc and dcbac are anagrams, but bacdc and dcbad are not.
 *
 * The student decides on an encryption scheme that involves two large strings. The encryption is dependent on the minimum number of character deletions required to make the two strings anagrams. Determine this number.
 *
 * Given two strings, A
 * and B, that may or may not be of the same length, determine the minimum number of character deletions required to make A and B
 *
 * anagrams. Any characters can be deleted from either of the strings.
 *
 * Example
 * A = 'cde'
 * B = 'dcf'
 *
 * Delete 'e' from A and 'f' from B so that the remaining strings are 'cd' and 'dc' which are anagrams. This takes 2 character deletions.
 */
public class MakeTwoAnagramsProblem {

    public static int makeAnagram(String a, String b){

        int[] charCounts = new int[26];
        a.chars().forEach(c->charCounts[c-'a']++);
        b.chars().forEach(c->charCounts[c-'a']--);
        return Arrays.stream(charCounts).map(Math::abs).sum();
    }
    public static void main(String[] args) {
        //System.out.println(makeAnagram("fcrxzwscanmligyxyvym", "jxwtrhvujlmrpdoqbisbwhmgpmeoke"));
        System.out.println(makeAnagram("cde", "abc"));
    }
}
