package org.example.Algo;

public class SortChoice {
    public static void printArray(int[] array){
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    private static int min(int[] array, int start){
        int minValue = array[start];
        int minValueIndex = start;

        for(int i = start+1; i < array.length; i++){
            if(array[i] < minValue){
                minValue = array[i];
                minValueIndex = i;
            }
        }
        return minValueIndex;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{43, 71, 15, 21, 65, 92, 37, 59, 60, 84};
        for(int step = 0; step < arr.length; step++){
            printArray(arr);
            int index = min(arr, step);
            int temp = arr[step];
            arr[step] = arr[index];
            arr[index] = temp;
        }
    }
}
