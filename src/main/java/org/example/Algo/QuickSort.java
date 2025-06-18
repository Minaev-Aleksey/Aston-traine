package org.example.Algo;

public class QuickSort {
    public static void main(String[] args) {

    }

    private static void quickSort(int[] array, int from, int to) {
        if (from < to) {
            int divideIndex = partition(array, from,to);
            printSortStep(array, from, to, divideIndex);
            quickSort(array, from, divideIndex -1);
            quickSort(array, divideIndex, to);
        }
    }
    private static int partition(int[] arr, int from, int to){
        int rightIndex = to;
        int leftIndex = from;

        int pivot = arr[from];
        while (leftIndex <=rightIndex){
            while (arr[leftIndex] < pivot){
                leftIndex++;
            }
            while (arr[rightIndex]>pivot){
                rightIndex--;
            }

            if(leftIndex <= rightIndex){
                swap(arr, rightIndex, leftIndex);
                leftIndex++;
                rightIndex--;
            }
        }

        return leftIndex;
    }

    private static void swap(int[] array, int index1, int index2){

    }

    private static void printSortStep(int[] array, int from, int to, int divideIndex){

    }
}
