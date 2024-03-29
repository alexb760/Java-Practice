package org.practice.hackerrank.arrays;

import java.util.List;

public class SixTimeSix2DArray {

    /**
     * Sum all the hourglass in the 2D array.
     * so that:
     * <prep>
     *     1,1,1,0,0,0
     *     0,1,0,0,0,0
     *     1,1,1,0,0,0
     *     0,0,2,4,4,0
     *     0,0,0,2,0,0
     *     0,0,1,2,4,0
     * </prep>
     *   so that
     *   1,1,1
     *     1
     *   1,1,1
     *   sum = 7
     *   compute the max sum of the hourglass.
     * @param arr hourglass representation. only 6x6 2D dimension allowed.
     * @return max sum.
     */
    public static int hourglassSum(List<List<Integer>> arr){
        int sum = Integer.MIN_VALUE;
        int prev = 0;
        for (int i = 0; i < arr.size(); i++){
            List<Integer> row = arr.get( i );
            for (int k = 0 ; k < row.size(); k++){
                if ((k + 2) < row.size() && (i+2) < arr.size()){
                    prev = row.get(k) + row.get(k+1) + row.get(k+2);
                    prev += arr.get(i+1).get(k+1);
                    prev += arr.get(i+2).get(k) + arr.get(i+2).get(k+1) + arr.get(i+2).get(k+2);
                   sum = Math.max(sum, prev);
                }else {
                    break;
                }
            }
        }
        return sum;
    }

    public static int hourglassSumIA(List<List<Integer>> arr) {
        if (arr == null || arr.size() < 3 || arr.get(0).size() < 3) {
            throw new IllegalArgumentException("Input list must be at least 3x3.");
        }

        int maxSum = Integer.MIN_VALUE;

        for (int i = 0; i <= arr.size() - 3; i++) {
            for (int k = 0; k <= arr.get(i).size() - 3; k++) {
                int currentHourglassSum = arr.get(i).get(k) + arr.get(i).get(k + 1) + arr.get(i).get(k + 2)
                        + arr.get(i + 1).get(k + 1)
                        + arr.get(i + 2).get(k) + arr.get(i + 2).get(k + 1) + arr.get(i + 2).get(k + 2);

                maxSum = Math.max(maxSum, currentHourglassSum);
            }
        }

        return maxSum;
    }


    public static void main(String[] args) {
        List<List<Integer>> arr = List.of(
                List.of(1,1,1,0,0,0),
                List.of(0,1,0,0,0,0),
                List.of(1,1,1,0,0,0),
                List.of(0,0,2,4,4,0),
                List.of(0,0,0,2,0,0),
                List.of(0,0,1,2,4,0));

        List<List<Integer>> arr1 = List.of(
                List.of(0,-4,-6,0,-7,-6),
                List.of(-1,-2,-6,-8,-3,-1),
                List.of(-8,-4,-2,-8,-8,-6),
                List.of(-3,-1,-2,-5,-7,-4),
                List.of(-3,-5,-3,-6,-6,-6),
                List.of(-3,-6,0,-8,-6,-7));

        int sum = hourglassSum(arr);
        System.out.println(sum);

    }
}
