package org.practice.hackerrank.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Sherlock considers a string to be valid if all characters of the string appear the same number of times. It is also valid if he can remove just character at index in the string, and the remaining characters will occur the same number of times. Given a string
 *
 * , determine if it is valid. If so, return YES, otherwise return NO.
 *
 * Example
 * s='abcc'
 * This is a valid string because frequencies are {a:1,b:1,c:1}.
 *
 * This is a valid string because we can remove one 'c' and have 1
 * of each character in the remaining string.
 *
 * s='abccc'
 * This string is not valid as we can only remove 1 occurrence of c. That leaves character frequencies of
 * {a:1,b:1,c:2}.
 */
public class SherklockValidStringProblem {
    public static String isValid(String s) {
        Map<Character, Integer> frequency = new HashMap<>();
        for (Character ch: s.toCharArray()){
            frequency.computeIfPresent(ch, (key, freq) -> freq + 1);
            frequency.computeIfAbsent(ch, (freq) -> 1);
        }
        TreeSet<Integer> couter = new TreeSet<>();
        List<Integer> apparences = new ArrayList<>();
        for (Integer value : frequency.values()) {
            couter.add(value);
            apparences.add(value);
        }
        if (couter.size() > 2) return "NO";
        Integer higher = couter.last();
        Integer lower = couter.first();
        if (higher.equals(lower)) return "YES";
        if (Collections.frequency(apparences, lower) == 1) return "YES";
        if (higher - 1 != lower) return "NO";
        if (Collections.frequency(apparences, higher) > 1) return "NO";
        return "YES";
    }

    public static String isValidAI(String s) {
        Map<Character, Integer> charFrequencies = new HashMap<>();
        for (Character ch: s.toCharArray()){
            charFrequencies.computeIfPresent(ch, (key, freq) -> freq + 1);
            charFrequencies.computeIfAbsent(ch, (freq) -> 1);
        }
        Map<Integer, Integer> freqFrequencies = new HashMap<>();
        for (Integer value : charFrequencies.values()) {
            freqFrequencies.computeIfPresent(value, (key, freq) -> freq + 1);
            freqFrequencies.computeIfAbsent(value, (freq) -> 1);
        }
        if (freqFrequencies.size() > 2) return "NO";
        Integer higher = Collections.max(freqFrequencies.keySet());
        Integer lower = Collections.min(freqFrequencies.keySet());
        if (higher.equals(lower)) return "YES";
        if (freqFrequencies.get(lower) == 1) return "YES";
        if (higher - 1 != lower) return "NO";
        if (freqFrequencies.get(higher) > 1) return "NO";
        return "YES";
    }


    public static void main(String[] args) {
        System.out.println(isValid("aabbcd"));
    }
}
