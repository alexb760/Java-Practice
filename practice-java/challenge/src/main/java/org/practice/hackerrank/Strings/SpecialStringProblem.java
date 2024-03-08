package org.practice.hackerrank.Strings;

/**
 *
 * A string is said to be a special string if either of two conditions is met:
 *
 *     All of the characters are the same, e.g. aaa.
 *     All characters except the middle one are the same, e.g. aadaa.
 *
 * A special substring is any substring of a string which meets one of those criteria. Given a string, determine how many special substrings can be formed from it.
 *
 * Example
 * s = 'mnonopoo'
 * s contains the following 12 special substrings:
 *  {m,n,o,n,o,p,o,o,non,opo,oo}
 */

public class SpecialStringProblem {

    /**
     * example
     * s='aaaa'
     * output = 10
     *
     * @param n length string
     * @param s string
     * @return number of substring.
     */
    public static long substrCount(int n, String s) {
        char[] chars = s.toCharArray();
        long retCount = n;
        boolean countMatch = true;
        char rootChar;
        for(int i = 0; i < n-1; i++){
            rootChar = chars[i];
            for(int y = i+1; y < n; y++){
                if(chars[y]==rootChar){
                    retCount++; continue;
                }else if(((y+1)!=n) && (chars[y+1] == rootChar) && (y+(y-i))<n){
                    countMatch = true;
                    for(int x = y+(y-i); x > y; x--){
                        if(chars[x]!=rootChar){
                            countMatch=false; break;
                        }
                    }
                    if(countMatch)
                        retCount++;
                    break;
                }else{
                    break;
                }
            }
        }
        return retCount;
    }

    public static int subString2(int n, String s){
        int count = n;
        char[] chars = s.toCharArray();
        char previuos;
        char elementSubGroup='0';
        int subGroupBefore =0;
        boolean flag = true;
        for (int i = 1; i < n; i++){
            previuos = chars[n - 1];
            if (previuos == chars[i]){
                count++;
            }
            if (previuos == chars[i] && flag){
                subGroupBefore++;
                elementSubGroup = previuos;
            }else if (flag){
                count++;
                flag = false;
            }

            if (subGroupBefore == n -1){
                count++;
            }
            if (elementSubGroup == chars[i] && !flag){
                subGroupBefore--;
            }
            if (!flag && subGroupBefore == 0){
                count++;
                flag = true;
            }else if (elementSubGroup != chars[i] && flag){
                subGroupBefore = 0;
            }
            if (i+1 < n){
                if (previuos == chars[i] && previuos == chars[i+1]){
                    count++;
                }
                if (previuos == chars[i+1] && previuos != chars[i]){
                    count++;
                }
            }
        }
        return count;
    }

    public static long countSpecialSubstrings(int length, String input) {
        if (input == null || length != input.length()) {
            throw new IllegalArgumentException("Invalid input");
        }

        char[] chars = input.toCharArray();
        long specialSubstringCount = length;
        boolean isSpecialSubstring;

        for (int i = 0; i < length - 1; i++) {
            char currentChar = chars[i];
            for (int j = i + 1; j < length; j++) {
                if (chars[j] == currentChar) {
                    specialSubstringCount++;
                    continue;
                } else if ((j + 1) != length && chars[j + 1] == currentChar && (j + (j - i)) < length) {
                    isSpecialSubstring = checkIfSpecialSubstring(chars, currentChar, j, i);
                    if (isSpecialSubstring) {
                        specialSubstringCount++;
                    }
                    break;
                } else {
                    break;
                }
            }
        }
        return specialSubstringCount;
    }

    private static boolean checkIfSpecialSubstring(char[] chars, char currentChar, int j, int i) {
        for (int x = j + (j - i); x > j; x--) {
            if (chars[x] != currentChar) {
                return false;
            }
        }
        return true;
    }




    public static void main(String[] args) {
        //String s = "aaaa";
        String s = "abcbaba";
        //String s = "aaabaaa";
        System.out.println(substrCount(s.length(), s));
    }
}
