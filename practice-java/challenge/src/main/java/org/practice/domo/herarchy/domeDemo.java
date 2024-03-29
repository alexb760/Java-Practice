package org.practice.domo.herarchy;

import java.util.HashMap;
import java.util.Map;

public class domeDemo {

        public static int[] findTwoSum(int[] list, int sum) {
            int[] bestSum = null;
            for (int i = 0; i < list.length ; i++) {
                for (int j = 1; j < list.length; j++) {
                    if (list[i] + list[j] == sum) {
                        bestSum = new int[2];
                        bestSum[0] = i;
                        bestSum[1] = j;
                        break;
                    }
                }
                if (bestSum != null){
                    break;
                }
            }
            return bestSum;
        }
        public static int[] findTwoSumIA(int[] list, int sum) {
            Map<Integer, Integer> numMap = new HashMap<>();
            for (int i = 0; i < list.length; i++) {
                int complement = sum - list[i];
                if (numMap.containsKey(complement)) {
                    return new int[] { numMap.get(complement), i };
                }
                numMap.put(list[i], i);
            }
            return null;
        }



    public static void main(String[] args) {
            int[] indices = findTwoSumIA(new int[] { 3, 1, 5, 7, 5, 9 }, 10);
            if(indices != null) {
                System.out.println(indices[0] + " " + indices[1]);
            }
        }

}
