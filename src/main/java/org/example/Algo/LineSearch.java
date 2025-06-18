package org.example.Algo;

public class LineSearch {
    public static void main(String[] args) {
        int[] arr = new int[]{43, 71, 15, 21, 65, 92, 37, 59, 60, 84};
        int minValue = arr[0];
        int minValueIndex = 0;

        for(int i = 1; i < arr.length; i++){
            if(arr[i] < minValue){
                minValue = arr[i];
                minValueIndex = i;
            }
        }
        System.out.println("minVValue = " + minValue);
        System.out.println("minValueIndex = " + minValueIndex);
    }
}