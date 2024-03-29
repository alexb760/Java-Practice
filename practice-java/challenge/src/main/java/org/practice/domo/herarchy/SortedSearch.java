package org.practice.domo.herarchy;

import java.util.Arrays;

public class SortedSearch {
    public static int countNumbers(int[] sortedArray, int lessThan) {
       //return Arrays.binarySearch(sortedArray, lessThan);
        int low = 0;
        int high = sortedArray.length -1;
        while (low <= high){
            int mid = low + (high - low) / 2;
            if (sortedArray[mid] < lessThan){
                low = mid + 1;
            }else {
                high = mid -1;
            }
        }
        return low;
    }

    public static void main(String[] args) {
        System.out.println(SortedSearch.countNumbers(new int[] { 1, 3, 5, 7 }, 4));
    }
}
