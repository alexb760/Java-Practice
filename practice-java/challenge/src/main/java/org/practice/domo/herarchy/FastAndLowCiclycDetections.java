package org.practice.domo.herarchy;

public class FastAndLowCiclycDetections {

    public static boolean fastSlowDetection(int[] cyclicArray){
        int slow = cyclicArray[0];
        int fast = cyclicArray[1];
        int i = 1;
        while (i < cyclicArray.length){
            if (slow == fast){
                return true;
            }
            slow = cyclicArray[i+1];
            fast = cyclicArray[i+2];
            i++;
        }

        return false;
    }

    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,2,6};
        System.out.println(fastSlowDetection(arr));
    }
}
