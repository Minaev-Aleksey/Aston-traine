package org.example.Algo;

import java.lang.reflect.Array;

public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = new int[]{43, 71, 15, 21, 65, 92, 37, 59, 60, 84};
        printSortArray(arr);
        boolean isSorted = false;

        while (!isSorted) {
            isSorted = true;
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] < arr[i - 1]) {
                    int temp = arr[i];
                    arr[i] = arr[i - 1];
                    arr[i - 1] = temp;
                    isSorted = false;
                }
            }
            printSortArray(arr);
        }
    }

    private static void printSortArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}