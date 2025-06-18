package org.example.Algo;

import java.util.Arrays;

public class BinarySearch {
    public static void main(String[] args) {
        int[] array = new int[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        System.out.println(Arrays.toString(array));
        System.out.println(BinarySearch(array, 313));
    }

    private static String BinarySearch(int[] array, int search) {
        int low = 0;
        int hight = array.length;

        while (low < hight) {
            int mid = (low + hight) / 2;
            int guess = array[mid];
            if (guess == search) {
                return search + " - найдено в массиве";
            }
            if (guess > search) {
                hight = mid - 1;

            } else {
                low = mid + 1;
            }


        }
        return search + " - не найдено в массиве";
    }
}