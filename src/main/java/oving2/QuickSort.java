
package oving2;

import java.util.Arrays;
import java.util.Date;

public class QuickSort {


    public void sort(int[] values) {
        // check for empty or null array
        if (values ==null || values.length==0){
            return;
        }
        quicksort(0, values.length - 1, values);
    }

    private void quicksort(int low, int high, int[] numbers) {
        int i = low, j = high;
        // Get the pivot element from the middle of the list
        int pivot = numbers[low + (high-low)/2];

        // Divide into two lists
        while (i <= j) {
            // If the current value from the left list is smaller than the pivot
            // element then get the next element from the left list
            while (numbers[i] < pivot) {
                i++;
            }
            // If the current value from the right list is larger than the pivot
            // element then get the next element from the right list
            while (numbers[j] > pivot) {
                j--;
            }

            // If we have found a value in the left list which is larger than
            // the pivot element and if we have found a value in the right list
            // which is smaller than the pivot element then we exchange the
            // values.
            // As we are done we can increase i and j
            if (i <= j) {
                exchange(i, j, numbers);
                i++;
                j--;
            }
        }
        // Recursion
        if (low < j)
            quicksort(low, j, numbers);
        if (i < high)
            quicksort(i, high, numbers);
    }

    private void exchange(int i, int j, int[] numbers) {
        int temp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = temp;
    }


    // DualPivotQuickSort
    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static void dualPivotQuickSort(int[] arr, int low, int high)
    {
        if (low < high)
        {
            // piv[] stores left pivot and right pivot.
            // piv[0] means left pivot and
            // piv[1] means right pivot
            int[] piv;
            piv = partition(arr, low, high);

            dualPivotQuickSort(arr, low, piv[0] - 1);
            dualPivotQuickSort(arr, piv[0] + 1, piv[1] - 1);
            dualPivotQuickSort(arr, piv[1] + 1, high);
        }
    }

    static int[] partition(int[] arr, int low, int high)
    {
        if (arr[low] > arr[high])
            swap(arr, low, high);

        // p is the left pivot, and q
        // is the right pivot.
        int j = low + 1;
        int g = high - 1, k = low + 1,
                p = arr[low], q = arr[high];

        while (k <= g)
        {

            // If elements are less than the left pivot
            if (arr[k] < p)
            {
                swap(arr, k, j);
                j++;
            }

            // If elements are greater than or equal
            // to the right pivot
            else if (arr[k] >= q)
            {
                while (arr[g] > q && k < g)
                    g--;

                swap(arr, k, g);
                g--;

                if (arr[k] < p)
                {
                    swap(arr, k, j);
                    j++;
                }
            }
            k++;
        }
        j--;
        g++;

        // Bring pivots to their appropriate positions.
        swap(arr, low, j);
        swap(arr, high, g);

        // Returning the indices of the pivots
        // because we cannot return two elements
        // from a function, we do that using an array.
        return new int[] { j, g };
    }
    /**
     * Method for checking computation time of different algorithms in milliseconds. Shamelessly stolen from the lecture notes.
     *
     * @return The average computational time.
     */
    public String timecalculator(int[] arr, int low, int high, int methodID){
        Date start = new Date();
        double tid;
        Date slutt;
        switch (methodID){
            case 1:
                start = new Date();
                    dualPivotQuickSort(arr,low,high);
                    slutt = new Date();
                tid = (double)(slutt.getTime()-start.getTime());
                return "Millisekunder per sortering: " + (tid);
            case 2:
                start = new Date();
                    this.sort(arr);
                    slutt = new Date();
                tid = (double)(slutt.getTime()-start.getTime()) ;
                return "Millisekunder per sortering: " + (tid);
        }
        return null;
    }

    public int summerTabell(int[] tabell){
        int sum = 0;
        for (int i = 0; i < tabell.length; i++) {
            sum += tabell[i];
        }
        return sum;
    }
    public boolean sjekksSortering(int[] tabell){

        for (int i = 0; i < tabell.length - 1; i++) {
            if (tabell[i] > tabell[i+1] ){
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
        int[] arr = new int[10000];
        for(int i = 0; i < arr.length; i++){
            arr[i] = i;
        }

        int[] tabell = new int[10000000];
        QuickSort sorter = new QuickSort();
        for(int i = 0; i < tabell.length; i++){
            tabell[i] = (int) (Math.random()*100);
        }

        System.out.println(quickSort.summerTabell(arr));
        System.out.println(quickSort.timecalculator(arr,  0, arr.length -1, 2));
        System.out.println(quickSort.summerTabell(arr));
        System.out.println(quickSort.sjekksSortering(arr));
        //swap(arr, 0, ((arr.length - 1) / 3));
        //swap(arr, (arr.length-1), arr.length - 1 - (arr.length - 1)/3);
        System.out.println(quickSort.summerTabell(arr));
        System.out.println(quickSort.timecalculator(arr,  (arr.length - 1) / 3, arr.length - 1 - (arr.length - 1)/3, 2));
        System.out.println(quickSort.summerTabell(arr));
        System.out.println(quickSort.sjekksSortering(arr));
    }
}

    }
        return middle;
        }
            if (t[left] > t[middle]) swap(t, left, middle);
            swap(t, middle, right);
        if (t[middle] > t[right]) {
        if (t[left] > t[middle]) swap(t, left, middle);
        int middle = (right + left) / 2;
    private static int median3sort(int []t, int left, int right) {

    }
        return indexLeft;
        swap(t, indexLeft, right - 1);
        }
            swap(t, indexLeft, indexRight);
            if (indexLeft >= indexRight) break;
            while (t[--indexRight] > pivot);
            while (t[++indexLeft] < pivot);
        for (indexLeft = left, indexRight = right - 1;;) {
        swap(t, middle, right - 1);
        int pivot = t[middle];
        int middle = median3sort(t, left, right);
        int indexLeft, indexRight;
    private static int split(int []t, int left, int right) {

    }
        }
            median3sort(t, left, right);
        } else {
            quicksortBook(t, partitionIndex + 1, right);
            quicksortBook(t, left, partitionIndex - 1);
            int partitionIndex = split(t, left, right);
        if (right - left > 2) {
    private static void quicksortBook(int []t, int left, int right) {

    }
        quicksortBook(t, 0, t.length - 1);
    public static void sortBook(int []t) {
     */
     * Quicksort from a book (one pivot)
    /**